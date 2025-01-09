package com.odk.baseapi.request.document;

import com.odk.base.vo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * DirectoryCreateRequest
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/1/7
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DirectoryCreateRequest extends BaseRequest {

    @Serial
    private static final long serialVersionUID = 6741602495551747804L;

    private String directoryName;

    private String parentId;
}
