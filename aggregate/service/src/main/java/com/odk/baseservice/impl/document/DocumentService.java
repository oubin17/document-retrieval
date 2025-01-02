package com.odk.baseservice.impl.document;

import com.odk.base.exception.AssertUtil;
import com.odk.base.exception.BizErrorCode;
import com.odk.base.vo.response.PageResponse;
import com.odk.base.vo.response.ServiceResponse;
import com.odk.baseapi.inter.document.DocumentApi;
import com.odk.baseapi.request.document.DocumentSearchRequest;
import com.odk.baseapi.request.document.DocumentUploadRequest;
import com.odk.baseapi.vo.DocumentVO;
import com.odk.baseapi.vo.FileVO;
import com.odk.basedomain.model.es.DocumentDO;
import com.odk.basemanager.deal.document.DocumentManager;
import com.odk.basemanager.dto.document.DocUploadDTO;
import com.odk.basemanager.entity.FileEntity;
import com.odk.baseservice.template.AbstractApiImpl;
import com.odk.baseutil.enums.BizScene;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public ServiceResponse<Boolean> deleteDoc(Long docId) {
        return super.queryProcess(BizScene.DOC_DELETE, docId, new QueryApiCallBack<Boolean, Boolean>() {

            @Override
            protected void checkParams(Object request) {
                AssertUtil.notNull(request, BizErrorCode.PARAM_ILLEGAL);
            }

            @Override
            protected Boolean doProcess(Object args) {
                return documentManager.deleteDoc(docId);
            }

            @Override
            protected Boolean convertResult(Boolean result) {
                return result;
            }

        });
    }

    @Override
    public ServiceResponse<PageResponse<FileVO>> searchByCondition(DocumentSearchRequest searchRequest) {
        return super.queryProcess(BizScene.DOC_SEARCH, searchRequest, new QueryApiCallBack<PageResponse<FileEntity>, PageResponse<FileVO>>() {

            @Override
            protected void checkParams(Object request) {
                AssertUtil.notNull(request, BizErrorCode.PARAM_ILLEGAL);
            }

            @Override
            protected PageResponse<FileEntity> doProcess(Object args) {
                return documentManager.searchByCondition(searchRequest.getFileName(), searchRequest.getPageNo(), searchRequest.getPageSize());
            }

            @Override
            protected PageResponse<FileVO> convertResult(PageResponse<FileEntity> apiResult) {
                List<FileEntity> pageList = apiResult.getPageList();
                List<FileVO> collect = pageList.stream().map(searchEntity -> {
                    FileVO documentVO = new FileVO();
                    BeanUtils.copyProperties(searchEntity, documentVO);
                    return documentVO;
                }).collect(Collectors.toList());

                return PageResponse.of(collect, apiResult.getCount());
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
