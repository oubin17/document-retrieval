package com.odk.baseapi.inter.document;

import com.odk.base.vo.response.PageResponse;
import com.odk.base.vo.response.ServiceResponse;
import com.odk.baseapi.request.document.SearchRequest;
import com.odk.baseapi.vo.DocumentVO;

/**
 * SearchApi
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/12/26
 */
public interface SearchApi {

    ServiceResponse<PageResponse<DocumentVO>> search(SearchRequest searchRequest);

}