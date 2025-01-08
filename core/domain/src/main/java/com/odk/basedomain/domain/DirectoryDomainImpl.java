package com.odk.basedomain.domain;

import com.google.common.collect.Lists;
import com.odk.basedomain.domain.inter.DirectoryDomain;
import com.odk.basedomain.model.file.DirectoryDO;
import com.odk.basedomain.repository.file.DirectoryRepository;
import com.odk.baseutil.entity.DirectoryEntity;
import com.odk.baseutil.enums.DirectoryTypeEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
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


    private DirectoryRepository directoryRepository;

    @Override
    public List<DirectoryEntity> directoryTree() {
        return buildDirectoryTree(0L);
    }

    /**
     * 构造目录树
     *
     * @param parentId
     * @return
     */
    private List<DirectoryEntity> buildDirectoryTree(Long parentId) {
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
}
