package com.odk.baseapi.response;

import com.odk.base.vo.response.PageResponse;
import com.odk.baseapi.vo.DocumentVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * SearchResponse
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/12/26
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SearchResponse extends PageResponse<DocumentVO> {

    @Serial
    private static final long serialVersionUID = 5263734192395802481L;
}
