package com.odk.baseutil.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DirectoryEntity
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/1/7
 */
@Data
public class DirectoryEntity {

    /**
     * 节点id
     */
    private String id;

    /**
     * 目录名称
     */
    private String directoryName;

    /**
     * 目录类型：文件-2、目录-1
     */
    private String directoryType;

    /**
     * 文件id
     */
    private String fileId;

    /**
     * 父节点ID
     */
    private String parentId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 子目录
     */
    private List<DirectoryEntity> childDirectories;
}
