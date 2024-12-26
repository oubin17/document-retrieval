package com.odk.basemanager.deal.document;

import com.odk.base.vo.response.PageResponse;
import com.odk.basedomain.domain.es.DocumentDO;
import com.odk.basedomain.repository.es.DocumentRepository;
import com.odk.basemanager.entity.SearchEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    public PageResponse<SearchEntity> search(String keyword, int pageNo, int pageSize) {
        List<DocumentDO> documentDOS = documentRepository.searchByFileContentsContains(keyword);
        List<SearchEntity> collect = documentDOS.stream().map(documentDO -> {
            SearchEntity searchEntity = new SearchEntity();
            BeanUtils.copyProperties(documentDO, searchEntity);
            return searchEntity;
        }).collect(Collectors.toList());
        return PageResponse.of(collect, 10);
    }

    @Autowired
    public void setDocumentRepository(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }
}
