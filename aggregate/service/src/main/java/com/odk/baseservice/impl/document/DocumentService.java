package com.odk.baseservice.impl.document;

import com.odk.base.exception.AssertUtil;
import com.odk.base.exception.BizErrorCode;
import com.odk.base.vo.response.ServiceResponse;
import com.odk.baseapi.inter.document.DocumentApi;
import com.odk.baseapi.request.document.DocumentUploadRequest;
import com.odk.baseapi.vo.DocumentVO;
import com.odk.basedomain.model.es.DocumentDO;
import com.odk.basemanager.deal.document.DocumentManager;
import com.odk.basemanager.dto.document.DocUploadDTO;
import com.odk.baseservice.template.AbstractApiImpl;
import com.odk.baseutil.enums.BizScene;
import org.springframework.beans.BeanUtils;
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
    public ServiceResponse<Long> uploadDoc(DocumentUploadRequest docUploadRequest) {
        return super.queryProcess(BizScene.DOC_UPLOAD, docUploadRequest, new QueryApiCallBack<Long, Long>() {

            @Override
            protected void checkParams(Object request) {
                AssertUtil.notNull(request, BizErrorCode.PARAM_ILLEGAL);
            }

            @Override
            protected Object convert(Object request) {
                DocumentUploadRequest uploadRequest = (DocumentUploadRequest) request;
                DocUploadDTO uploadDTO = new DocUploadDTO();
                BeanUtils.copyProperties(uploadRequest, uploadDTO);
                return uploadDTO;
            }

            @Override
            protected Long doProcess(Object args) {
                DocUploadDTO uploadDTO = (DocUploadDTO) args;
                return documentManager.uploadDoc(uploadDTO);
            }

            @Override
            protected Long convertResult(Long docId) {
                return docId;
            }

        });
    }

    @Override
    public ServiceResponse<DocumentVO> searchById(Long id) {
        DocumentDO documentDO = documentManager.searchById(id);
        DocumentVO documentVO = new DocumentVO();
        documentVO.setId(documentDO.getId());
        return ServiceResponse.valueOfSuccess(documentVO);
    }

    @Autowired
    public void setDocumentManager(DocumentManager documentManager) {
        this.documentManager = documentManager;
    }
}
