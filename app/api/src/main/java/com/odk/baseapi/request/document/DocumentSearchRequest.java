package com.odk.baseapi.request.document;

import com.odk.base.vo.request.PageRequest;
import lombok.Data;

import java.io.Serial;

/**
 * DocumentSearchRequest
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/1/1
 */
@Data
public class DocumentSearchRequest extends PageRequest {

    @Serial
    private static final long serialVersionUID = 240620906235995462L;

    /**
     * 文件名
     */
    private String fileName;
}
