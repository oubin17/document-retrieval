package com.odk.baseapi.request.document;

import com.odk.base.vo.request.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * SearchRequest
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/12/26
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SearchRequest extends PageRequest {

    @Serial
    private static final long serialVersionUID = 8702728259439075136L;

    /**
     * 关键字
     */
    private String keyword;
}
