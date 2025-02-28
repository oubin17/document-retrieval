package com.odk.basemanager.deal.document;

import cn.dev33.satoken.stp.StpUtil;
import com.odk.base.enums.common.CommonStatusEnum;
import com.odk.base.exception.AssertUtil;
import com.odk.base.exception.BizErrorCode;
import com.odk.base.exception.BizException;
import com.odk.base.idgenerator.SnowflakeIdUtil;
import com.odk.base.util.FileUtil;
import com.odk.base.vo.response.PageResponse;
import com.odk.basedomain.domain.inter.OrganizationDomain;
import com.odk.basedomain.model.file.DirectoryDO;
import com.odk.basedomain.model.file.FileDO;
import com.odk.basedomain.model.file.FileSearchDO;
import com.odk.basedomain.repository.file.DirectoryRepository;
import com.odk.basedomain.repository.file.FileRepository;
import com.odk.basedomain.repository.file.FileSearchRepository;
import com.odk.baseutil.dto.document.DocUploadDTO;
import com.odk.baseutil.entity.FileEntity;
import com.odk.baseutil.enums.DirectoryTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * DocumentManager
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/12/25
 */
@Slf4j
@Service
public class DocumentManager {

    private FileRepository fileRepository;

    private FileSearchRepository fileSearchRepository;

    private DirectoryRepository directoryRepository;

    private OrganizationDomain organizationDomain;


    private TransactionTemplate transactionTemplate;

    @Value("${file.input.path}")
    private String baseFilePath;

    public Resource downloadDoc(String fileId) {
        Optional<FileDO> byId = fileRepository.findById(fileId);
        AssertUtil.isTrue(byId.isPresent(), BizErrorCode.PARAM_ILLEGAL, "文件不存在");

        Set<String> orgIdsByUserId = organizationDomain.getOrgIdsByUserId(StpUtil.getLoginIdAsString());
        AssertUtil.isTrue(orgIdsByUserId.contains(byId.get().getOrgId()), BizErrorCode.PERMISSION_DENY);

        // 文件路径
        try {
            // 文件路径
            Path filePath = Paths.get(baseFilePath + byId.get().getFullFilePath()).toAbsolutePath().normalize();
            Resource resource = new UrlResource(filePath.toUri());
            // 检查文件是否存在
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new BizException("文件不存在或不可读");
            }
        } catch (MalformedURLException e) {
            log.error("文件路径错误：", e);
            throw new BizException("文件路径错误");
        }

    }

    public String uploadDoc(DocUploadDTO uploadDTO) {
        //检查目录是否合法
        if (!StringUtils.equals(uploadDTO.getDirId(), "0")) {
            DirectoryDO directoryDO = directoryRepository.findByIdAndDirectoryTypeAndStatus(uploadDTO.getDirId(), DirectoryTypeEnum.FOLDER.getCode(), CommonStatusEnum.NORMAL.getCode());
            AssertUtil.notNull(directoryDO, BizErrorCode.PARAM_ILLEGAL, "父节点路径非法");
        }

        String mainId = String.valueOf(SnowflakeIdUtil.nextId());
        String filePath = FileUtil.generateFileName(mainId, uploadDTO.getFileName());
        String fullFilaPath = FileUtil.generateFullFileName(baseFilePath, mainId, uploadDTO.getFileName());
        FileUtil.checkAndCreateFilePath(baseFilePath);
        transactionTemplate.executeWithoutResult(transactionStatus -> {
            try {
                //1.上传文件
                FileUtil.saveFile(fullFilaPath, uploadDTO.getFileInputStream());
                //2.获取文件内容
                String docContents = FileUtil.getFileContents(fullFilaPath);
                AssertUtil.isNotEmpty(docContents, "文件内容为空");
                //3.保存文件到db
                FileDO fileDO = new FileDO();
                fileDO.setId(mainId);
                fileDO.setFileName(uploadDTO.getFileName());
                fileDO.setContentType(uploadDTO.getContentType());
                fileDO.setFileSize(uploadDTO.getFileSize());
                fileDO.setFullFilePath(filePath);
                fileDO.setStatus(CommonStatusEnum.NORMAL.getCode());
                fileDO.setOrgId(uploadDTO.getOrgId());
                fileRepository.save(fileDO);

                //4.保存文件内容到db
                FileSearchDO fileSearchDO = new FileSearchDO();
                fileSearchDO.setFileId(mainId);
                fileSearchDO.setFileName(uploadDTO.getFileName());
                fileSearchDO.setContent(docContents);
                fileSearchDO.setOrgId(uploadDTO.getOrgId());
                fileSearchRepository.save(fileSearchDO);

                //5.创建叶子结点
                DirectoryDO leafDirectory = new DirectoryDO();
                leafDirectory.setDirectoryName(uploadDTO.getFileName());
                leafDirectory.setDirectoryType(DirectoryTypeEnum.FILE.getCode());
                leafDirectory.setFileId(mainId);
                leafDirectory.setParentId(uploadDTO.getDirId());
                leafDirectory.setStatus(CommonStatusEnum.NORMAL.getCode());
                leafDirectory.setOrgId(uploadDTO.getOrgId());
                directoryRepository.save(leafDirectory);

            } catch (BizException bizException) {
                FileUtil.deleteFile(fullFilaPath);
                throw bizException;
            } catch (IOException e) {
                log.error("文件上传失败，文件名：{}，错误信息：", uploadDTO.getFileName(), e);
                throw new BizException(BizErrorCode.SYSTEM_ERROR);
            } catch (Exception e) {
                log.error("文件上传发生未知错误,", e);
                throw e;
            }
        });
        return mainId;
    }

    public Boolean deleteDoc(String fileId) {
        Optional<FileDO> byId = fileRepository.findById(fileId);
        AssertUtil.isTrue(byId.isPresent(), BizErrorCode.PARAM_ILLEGAL, "文件不存在");
        Set<String> orgIdsByUserId = organizationDomain.getOrgIdsByUserId(StpUtil.getLoginIdAsString());
        AssertUtil.isTrue(orgIdsByUserId.contains(byId.get().getOrgId()), BizErrorCode.PERMISSION_DENY);
        FileUtil.deleteFile(baseFilePath + byId.get().getFullFilePath());
        transactionTemplate.executeWithoutResult(transactionStatus -> {
            fileSearchRepository.deleteByFileId(fileId);
            //文件配置信息软删除
            fileRepository.updateFileStatus(fileId, CommonStatusEnum.DELETE.getCode());
        });
        return true;
    }

    public PageResponse<FileEntity> searchByCondition(String fileName, int pageNo, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo - 1, pageSize, Sort.by(Sort.Direction.DESC, "createTime"));
        Page<FileDO> searchResult;
        if (StringUtils.isEmpty(fileName)) {
            searchResult = fileRepository.findAll(pageRequest);
        }else {
            fileName = "%" + fileName + "%";
            searchResult = fileRepository.findByFileNameLike(fileName, pageRequest);
        }
        PageResponse<FileEntity> documentEntityPageResponse = new PageResponse<>();
        documentEntityPageResponse.setCount((int) searchResult.getTotalElements());
        List<FileEntity> collect = searchResult.stream().map(fileDO -> {
            FileEntity fileEntity = new FileEntity();
            BeanUtils.copyProperties(fileDO, fileEntity);
            return fileEntity;
        }).toList();
        documentEntityPageResponse.setPageList(collect);
        return documentEntityPageResponse;
    }

    @Autowired
    public void setFileRepository(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Autowired
    public void setFileSearchRepository(FileSearchRepository fileSearchRepository) {
        this.fileSearchRepository = fileSearchRepository;
    }

    @Autowired
    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    @Autowired
    public void setDirectoryRepository(DirectoryRepository directoryRepository) {
        this.directoryRepository = directoryRepository;
    }

    @Autowired
    public void setOrganizationDomain(OrganizationDomain organizationDomain) {
        this.organizationDomain = organizationDomain;
    }
}
