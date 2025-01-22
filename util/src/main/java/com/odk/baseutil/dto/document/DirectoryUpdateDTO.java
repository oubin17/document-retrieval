package com.odk.baseutil.dto.document;

import com.odk.base.dto.DTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DirectoryUpdateDTO
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/1/22
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DirectoryUpdateDTO extends DTO {

    /**
     * 目录id
     */
    private String id;

    /**
     * 目录名称
     */
    private String directoryName;
}
