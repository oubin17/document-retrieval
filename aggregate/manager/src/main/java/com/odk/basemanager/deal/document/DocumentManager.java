package com.odk.basemanager.deal.document;

import com.odk.basedomain.domain.es.DocumentDO;
import com.odk.basedomain.repository.es.DocumentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * DocumentManager
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/12/25
 */
@Slf4j
@Service
public class DocumentManager {

    private DocumentRepository documentRepository;

    public void add(Long id, String name) {
        DocumentDO documentDO = new DocumentDO();
        documentDO.setId(id);
        documentDO.setName(name);
        documentRepository.save(documentDO);
    }

    public DocumentDO searchById(Long id) {
        return documentRepository.findById(id).orElse(null);
    }



    @Autowired
    public void setDocumentRepository(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }
}
