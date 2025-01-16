package com.odk.baseapi.request.document;

import com.odk.base.vo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * DirSearchRequest
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/1/12
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DirSearchRequest extends BaseRequest {

    @Serial
    private static final long serialVersionUID = 5021869453591508790L;

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
     * 查询当前组织下的文件
     */
    private String orgId;
}
