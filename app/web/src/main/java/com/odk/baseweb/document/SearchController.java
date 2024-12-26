package com.odk.baseweb.document;

import com.odk.base.vo.response.PageResponse;
import com.odk.base.vo.response.ServiceResponse;
import com.odk.baseapi.inter.document.SearchApi;
import com.odk.baseapi.request.document.SearchRequest;
import com.odk.baseapi.vo.DocumentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * SearchController
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/12/25
 */
@RestController
@RequestMapping("/search")
public class SearchController {

    private SearchApi searchApi;

    @PostMapping("/keyword")
    public ServiceResponse<PageResponse<DocumentVO>> search(@RequestBody SearchRequest searchRequest) {
        return searchApi.search(searchRequest);
    }

    @Autowired
    public void setSearchApi(SearchApi searchApi) {
        this.searchApi = searchApi;
    }
}
