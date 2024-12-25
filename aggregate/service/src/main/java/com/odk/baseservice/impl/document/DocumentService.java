package com.odk.baseservice.impl.document;

import com.odk.base.vo.response.ServiceResponse;
import com.odk.baseapi.inter.document.DocumentApi;
import com.odk.baseapi.vo.DocumentVO;
import com.odk.basedomain.domain.es.DocumentDO;
import com.odk.basemanager.deal.document.DocumentManager;
import com.odk.baseservice.template.AbstractApiImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * DocumentService
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/12/25
 */
@Service
public class DocumentService extends AbstractApiImpl implements DocumentApi {

    private DocumentManager documentManager;

    @Override
    public ServiceResponse<Boolean> saveDocument(Long id, String name) {
        documentManager.add(id, name);
        return ServiceResponse.valueOfSuccess();
    }

    @Override
    public ServiceResponse<DocumentVO> searchById(Long id) {
        DocumentDO documentDO = documentManager.searchById(id);
        DocumentVO documentVO = new DocumentVO();
        documentVO.setId(documentDO.getId());
        documentVO.setName(documentDO.getName());
        return ServiceResponse.valueOfSuccess(documentVO);
    }

    @Autowired
    public void setDocumentManager(DocumentManager documentManager) {
        this.documentManager = documentManager;
    }
}
