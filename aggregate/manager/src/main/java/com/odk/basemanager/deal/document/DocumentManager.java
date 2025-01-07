package com.odk.basemanager.deal.document;

import com.odk.base.exception.AssertUtil;
import com.odk.base.exception.BizErrorCode;
import com.odk.base.exception.BizException;
import com.odk.base.idgenerator.SnowflakeIdUtil;
import com.odk.base.util.FileUtil;
import com.odk.base.vo.response.PageResponse;
import com.odk.basedomain.model.es.DocumentDO;
import com.odk.basedomain.model.file.FileDO;
import com.odk.basedomain.model.file.FileSearchDO;
import com.odk.basedomain.repository.es.DocumentRepository;
import com.odk.basedomain.repository.file.FileRepository;
import com.odk.basedomain.repository.file.FileSearchRepository;
import com.odk.basemanager.dto.document.DocUploadDTO;
import com.odk.basemanager.entity.FileEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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

    private DocumentRepository documentRepository;

    private FileRepository fileRepository;

    private FileSearchRepository fileSearchRepository;


    private TransactionTemplate transactionTemplate;

    @Value("${file.input.path}")
    private String baseFilePath;

    public Boolean deleteDoc(Long docId) {
        Optional<FileDO> byId = fileRepository.findById(docId);
        AssertUtil.isTrue(byId.isPresent(), BizErrorCode.PARAM_ILLEGAL, "文件不存在");
        FileUtil.deleteFile(byId.get().getFullFilePath());
        fileSearchRepository.deleteByFileId(docId);
        fileRepository.deleteById(docId);
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

    public Long uploadDoc(DocUploadDTO uploadDTO) {
        long mainId = SnowflakeIdUtil.nextId();
        String fullFilaPath = FileUtil.generateFullFileName(baseFilePath, String.valueOf(mainId), uploadDTO.getFileName());
        FileUtil.checkAndCreateFilePath(baseFilePath);
        transactionTemplate.executeWithoutResult(transactionStatus -> {
            try {
                //1.上传文件
                FileUtil.saveFile(fullFilaPath, uploadDTO.getFileInputStream());
                //2.获取文件内容
                String docContents = FileUtil.getFileContents(fullFilaPath);
                AssertUtil.isNotEmpty(docContents, "文件内容为空");
                log.info("文件内容 {}", docContents);

                //3.内容写到ES
//                documentDO.setId(mainId);
//                documentDO.setFileName(uploadDTO.getFileName());
//                documentDO.setContentType(uploadDTO.getContentType());
//                documentDO.setFileContents(docContents);
//                documentDO.setFileSize(uploadDTO.getFileSize());
//                documentDO.setFullFilePath(fullFilaPath);
//                documentDO.setCreateTimeMill(LocalDateTimeUtil.getCurrentDateTime().toInstant(ZoneOffset.UTC).toEpochMilli());
//                documentRepository.save(documentDO);

                //3.保存文件到db
                FileDO fileDO = new FileDO();
                fileDO.setId(mainId);
                fileDO.setFileName(uploadDTO.getFileName());
                fileDO.setContentType(uploadDTO.getContentType());
                fileDO.setFileSize(uploadDTO.getFileSize());
                fileDO.setFullFilePath(fullFilaPath);
                fileRepository.save(fileDO);

                //4.保存文件内容到db
                FileSearchDO fileSearchDO = new FileSearchDO();
                fileSearchDO.setFileId(mainId);
                fileSearchDO.setContent(docContents);
                fileSearchRepository.save(fileSearchDO);
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

    public DocumentDO searchById(Long id) {
        return documentRepository.findById(id).orElse(null);
    }


    @Autowired
    public void setDocumentRepository(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
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
}
