package com.odk.baseapi.request.document;

import com.odk.base.vo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * DirectoryUpdateRequest
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/1/22
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DirectoryUpdateRequest extends BaseRequest {

    @Serial
    private static final long serialVersionUID = -4586775028421668821L;

    /**
     * 目录id
     */
    private String id;

    /**
     * 目录名称
     */
    private String directoryName;
}
