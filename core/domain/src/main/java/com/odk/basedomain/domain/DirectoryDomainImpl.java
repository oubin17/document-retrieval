package com.odk.basedomain.domain;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.odk.basedomain.domain.inter.DirectoryDomain;
import com.odk.basedomain.model.file.DirectoryDO;
import com.odk.basedomain.model.file.FileDO;
import com.odk.basedomain.model.file.FileSearchDO;
import com.odk.basedomain.repository.file.DirectoryRepository;
import com.odk.basedomain.repository.file.FileRepository;
import com.odk.basedomain.repository.file.FileSearchRepository;
import com.odk.baseutil.dto.document.DirSearchDTO;
import com.odk.baseutil.entity.DirectoryEntity;
import com.odk.baseutil.enums.DirSearchTypeEnum;
import com.odk.baseutil.enums.DirectoryTypeEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * DirectoryDomainImpl
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/1/7
 */
@Service
public class DirectoryDomainImpl implements DirectoryDomain {

    private static final String ROOT_NODE = "0";

    private DirectoryRepository directoryRepository;

    private FileSearchRepository fileSearchRepository;

    private FileRepository fileRepository;

    @Override
    public List<DirectoryEntity> directoryTree() {
        return buildDirectoryTree(ROOT_NODE);
    }


    @Override
    public List<DirectoryEntity> searchByFileContent(DirSearchDTO dirSearchDTO) {
        DirSearchTypeEnum byCode = DirSearchTypeEnum.getByCode(dirSearchDTO.getSearchType());
        List<FileSearchDO> byCondition;
        List<FileDO> fileDOList;
        Set<String> fileIds = Sets.newHashSet();

        if (byCode == DirSearchTypeEnum.CONTENT) {
            byCondition = fileSearchRepository.findByCondition(dirSearchDTO.getKeyword());
            fileIds = byCondition.stream().map(FileSearchDO::getFileId).collect(Collectors.toSet());
        } else if (byCode == DirSearchTypeEnum.FILE_NAME) {
            String fileName = "%" + dirSearchDTO.getKeyword() + "%";
            fileDOList = fileRepository.findByFileNameLike(fileName);
            fileIds = fileDOList.stream().map(FileDO::getId).collect(Collectors.toSet());

        }
        if (CollectionUtils.isEmpty(fileIds)) {
            return Lists.newArrayList();
        }
        List<DirectoryEntity> resultEntities = Lists.newArrayList();
        List<DirectoryEntity> fullDirectoryEntities = directoryTree();

        for (DirectoryEntity directoryEntity : fullDirectoryEntities) {
            DirectoryEntity directoryEntity1 = searchTree(directoryEntity, fileIds);
            if (null != directoryEntity1) {
                resultEntities.add(directoryEntity1);
            }
        }
        return resultEntities;
    }

    // 递归遍历目录树，匹配文件节点的名称
    private DirectoryEntity searchTree(DirectoryEntity node, Set<String> fileIds) {
        //如果是文件
        if (isCheckedFile(node, fileIds)) {
            // 如果是文件节点且名称包含关键字，返回该节点的副本
            return copyFromSourceEntity(node);
        } else if (DirectoryTypeEnum.FOLDER.getCode().equals(node.getDirectoryType())) {
            // 如果是目录节点，递归检查子节点
            DirectoryEntity directoryEntity = copyFromSourceEntity(node);
            for (DirectoryEntity child : node.getChildDirectories()) {
                DirectoryEntity matchedChild = searchTree(child, fileIds);
                if (matchedChild != null) {
                    directoryEntity.getChildDirectories().add(matchedChild);
                }
            }
            // 如果目录节点有匹配的子节点，返回该目录节点
            if (!directoryEntity.getChildDirectories().isEmpty()) {
                return directoryEntity;
            }
        }
        // 如果没有匹配的节点，返回 null
        return null;
    }

    private DirectoryEntity copyFromSourceEntity(DirectoryEntity sourceEntity) {
        DirectoryEntity targetEntity = new DirectoryEntity();
        targetEntity.setId(sourceEntity.getId());
        targetEntity.setDirectoryName(sourceEntity.getDirectoryName());
        targetEntity.setDirectoryType(sourceEntity.getDirectoryType());
        targetEntity.setFileId(sourceEntity.getFileId());
        targetEntity.setParentId(sourceEntity.getParentId());
        targetEntity.setCreateTime(sourceEntity.getCreateTime());
        targetEntity.setChildDirectories(Lists.newArrayList());
        return targetEntity;
    }

    /**
     * 是否符合条件
     *
     * @param node
     * @param fileIds
     * @return
     */
    private boolean isCheckedFile(DirectoryEntity node, Set<String> fileIds) {
        return node != null && DirectoryTypeEnum.FILE.getCode().equals(node.getDirectoryType()) && fileIds.contains(node.getFileId());
    }


    /**
     * 构造目录树
     *
     * @param parentId
     * @return
     */
    private List<DirectoryEntity> buildDirectoryTree(String parentId) {
        List<DirectoryDO> rootDirectories = this.directoryRepository.findCurrentLevel(parentId);
        if (CollectionUtils.isEmpty(rootDirectories)) {
            return Lists.newArrayList();
        }
        List<DirectoryEntity> directoryEntities = convertEntity(rootDirectories);
        for (DirectoryEntity entity : directoryEntities) {
            if (DirectoryTypeEnum.FOLDER.getCode().equals(entity.getDirectoryType())) {
                //如果该节点是文件夹，递归下一级
                entity.setChildDirectories(buildDirectoryTree(entity.getId()));
            }
        }
        return directoryEntities;
    }

    /**
     * 模型转换
     *
     * @param directoryDOS
     * @return
     */
    private List<DirectoryEntity> convertEntity(List<DirectoryDO> directoryDOS) {
        return directoryDOS.stream().map(directoryDO -> {
            DirectoryEntity entity = new DirectoryEntity();
            BeanUtils.copyProperties(directoryDO, entity);
            return entity;
        }).collect(Collectors.toList());
    }

    @Autowired
    public void setDirectoryRepository(DirectoryRepository directoryRepository) {
        this.directoryRepository = directoryRepository;
    }

    @Autowired
    public void setFileSearchRepository(FileSearchRepository fileSearchRepository) {
        this.fileSearchRepository = fileSearchRepository;
    }

    @Autowired
    public void setFileRepository(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }
}
