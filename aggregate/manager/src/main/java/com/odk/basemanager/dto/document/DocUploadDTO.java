package com.odk.basemanager.dto.document;

import com.odk.base.dto.DTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.InputStream;

/**
 * DocUploadDTO
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/12/26
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DocUploadDTO extends DTO {


    /**
     * 文件输入流
     */
    private InputStream fileInputStream;

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
     * 文件夹id
     */
    private Long dirId;

}
