package com.odk.baseservice.impl.document;

import com.odk.base.exception.AssertUtil;
import com.odk.base.exception.BizErrorCode;
import com.odk.base.vo.response.PageResponse;
import com.odk.base.vo.response.ServiceResponse;
import com.odk.baseapi.inter.document.SearchApi;
import com.odk.baseapi.request.document.SearchRequest;
import com.odk.baseapi.vo.DocumentVO;
import com.odk.basemanager.deal.document.SearchManager;
import com.odk.basemanager.entity.SearchEntity;
import com.odk.baseservice.template.AbstractApiImpl;
import com.odk.baseutil.enums.BizScene;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * SearchService
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/12/26
 */
@Service
public class SearchService extends AbstractApiImpl implements SearchApi {

    private SearchManager searchManager;

    @Override
    public ServiceResponse<PageResponse<DocumentVO>> search(SearchRequest searchRequest) {
        return super.queryProcess(BizScene.SEARCH, searchRequest, new QueryApiCallBack<PageResponse<SearchEntity>, PageResponse<DocumentVO>>() {

            @Override
            protected void checkParams(Object request) {
                AssertUtil.notNull(request, BizErrorCode.PARAM_ILLEGAL);
                AssertUtil.isNotEmpty(searchRequest.getKeyword(), "查询关键字不为空");
            }

            @Override
            protected PageResponse<SearchEntity> doProcess(Object args) {
                return searchManager.search(searchRequest.getKeyword(), searchRequest.getPageNo(), searchRequest.getPageSize());
            }

            @Override
            protected PageResponse<DocumentVO> convertResult(PageResponse<SearchEntity> apiResult) {
                List<SearchEntity> pageList = apiResult.getPageList();
                List<DocumentVO> collect = pageList.stream().map(searchEntity -> {
                    DocumentVO documentVO = new DocumentVO();
                    BeanUtils.copyProperties(searchEntity, documentVO);
                    return documentVO;
                }).collect(Collectors.toList());

                return PageResponse.of(collect, apiResult.getCount());
            }

        });
    }

    @Autowired
    public void setSearchManager(SearchManager searchManager) {
        this.searchManager = searchManager;
    }
}