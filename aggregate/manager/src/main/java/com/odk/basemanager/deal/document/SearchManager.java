package com.odk.basemanager.deal.document;

import com.odk.base.util.LocalDateTimeUtil;
import com.odk.base.vo.response.PageResponse;
import com.odk.basedomain.domain.SearchDomain;
import com.odk.basedomain.model.es.DocumentDO;
import com.odk.basedomain.repository.es.DocumentRepository;
import com.odk.basemanager.entity.SearchEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * SearchManager
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/12/26
 */
@Service
public class SearchManager {

    private DocumentRepository documentRepository;

    private SearchDomain searchDomain;

    public PageResponse<SearchEntity> search(String keyword, int pageNo, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo - 1, pageSize);
        Page<DocumentDO> documentDOS = searchDomain.searchByFileContentsContains(keyword, pageRequest);
        List<SearchEntity> collect = documentDOS.stream().map(documentDO -> {
            SearchEntity searchEntity = new SearchEntity();
            BeanUtils.copyProperties(documentDO, searchEntity);
            searchEntity.setCreateTime(LocalDateTimeUtil.convertTimestampToLocalDateTime(documentDO.getCreateTimeMill()));
            return searchEntity;
        }).collect(Collectors.toList());
        return PageResponse.of(collect, documentDOS.getTotalPages());
    }

    @Autowired
    public void setSearchDomain(SearchDomain searchDomain) {
        this.searchDomain = searchDomain;
    }

    @Autowired
    public void setDocumentRepository(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }
}
