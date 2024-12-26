package com.odk.baseapi.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * DocumentVO
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/12/25
 */
@Data
public class DocumentVO {

    private Long id;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件类型
     */
    private String contentType;

    /**
     * 文件大小
     */
    private String fileSize;

    /**
     * 文件全路径
     */
    private String fullFilePath;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
