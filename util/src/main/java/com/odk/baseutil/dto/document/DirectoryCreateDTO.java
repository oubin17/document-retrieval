package com.odk.baseutil.dto.document;

import com.odk.base.dto.DTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DirectoryCreateDTO
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/1/7
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DirectoryCreateDTO extends DTO {

    /**
     * 目录名称
     */
    private String directoryName;

    /**
     * 父节点ID
     */
    private String parentId;

    /**
     * 组织ID：如果不传，默认是当前用户所属组织
     */
    private String orgId;
}
