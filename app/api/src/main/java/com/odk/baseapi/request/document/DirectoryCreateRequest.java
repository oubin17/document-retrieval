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

    /**
     * 目录名称
     */
    private String directoryName;

    /**
     * 父节点id
     */
    private String parentId;

    /**
     * 组织ID：如果不传，默认是当前用户所属组织
     */
    private String orgId;
}
