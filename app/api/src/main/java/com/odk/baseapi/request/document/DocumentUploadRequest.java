package com.odk.baseapi.request.document;

import com.odk.base.vo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.InputStream;
import java.io.Serial;

/**
 * DocumentUploadRequest
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/12/25
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DocumentUploadRequest extends BaseRequest {

    @Serial
    private static final long serialVersionUID = 4216019730863430624L;

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
    private String dirId;

}
