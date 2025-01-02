package com.odk.basedomain.domain;

import com.odk.basedomain.domain.inter.SearchDomain;
import com.odk.basedomain.model.es.DocumentDO;
import com.odk.basedomain.repository.es.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

/**
 * SearchDomainImpl
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/12/26
 */
@Service
public class SearchDomainImpl implements SearchDomain {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    private DocumentRepository documentRepository;

    @Override
    public Page<DocumentDO> searchByFileContentsContains(String keyword, Pageable pageable) {
        return documentRepository.searchByFileContentsContains(keyword, pageable);
    }

    @Autowired
    public void setDocumentRepository(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }
}
