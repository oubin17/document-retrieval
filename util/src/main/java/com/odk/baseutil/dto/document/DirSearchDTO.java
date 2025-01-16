package com.odk.baseutil.dto.document;

import com.odk.base.dto.DTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DirSearchDTO
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/1/12
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DirSearchDTO extends DTO {

    /**
     * 关键字
     */
    private String keyword;

    /**
     * 搜索类型：
     * 1-文件内容
     * 2-文件名称
     */
    private String searchType;

    /**
     * 组织id
     */
    private String orgId;
}
