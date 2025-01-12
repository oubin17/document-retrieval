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
     * 文件夹名称
     */
    private String directoryName;

    /**
     * 父节点ID
     */
    private String parentId;
}
