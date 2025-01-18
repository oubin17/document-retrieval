package com.odk.baseservice.impl.document;

import cn.dev33.satoken.stp.StpUtil;
import com.odk.base.exception.AssertUtil;
import com.odk.base.exception.BizErrorCode;
import com.odk.base.vo.response.PageResponse;
import com.odk.base.vo.response.ServiceResponse;
import com.odk.baseapi.inter.document.DocumentApi;
import com.odk.baseapi.request.document.DocumentSearchRequest;
import com.odk.baseapi.request.document.DocumentUploadRequest;
import com.odk.baseapi.vo.DocumentVO;
import com.odk.baseapi.vo.FileVO;
import com.odk.basedomain.domain.inter.OrganizationDomain;
import com.odk.basedomain.model.es.DocumentDO;
import com.odk.basemanager.deal.document.DocumentManager;
import com.odk.baseutil.dto.document.DocUploadDTO;
import com.odk.baseutil.entity.FileEntity;
import com.odk.baseservice.template.AbstractApiImpl;
import com.odk.baseutil.enums.BizScene;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
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

    private OrganizationDomain organizationDomain;

    @Override
    public ServiceResponse<String> uploadDoc(DocumentUploadRequest docUploadRequest) {
        return super.executeProcess(BizScene.DOC_UPLOAD, docUploadRequest, new CallBack<String, String>() {

            @Override
            protected void checkParams(Object request) {
                AssertUtil.notNull(request, BizErrorCode.PARAM_ILLEGAL);
            }

            @Override
            protected Object convert(Object request) {
                if (docUploadRequest.getDirId() == null) {
                    //如果传空，默认在根目录上传
                    docUploadRequest.setDirId("0");
                }
                String currentOrgId = organizationDomain.checkAndReturnCurrentOrgId(docUploadRequest.getOrgId(), StpUtil.getLoginIdAsString());
                docUploadRequest.setOrgId(currentOrgId);
                DocUploadDTO uploadDTO = new DocUploadDTO();
                BeanUtils.copyProperties(docUploadRequest, uploadDTO);
                return uploadDTO;
            }

            @Override
            protected String doProcess(Object args) {
                DocUploadDTO uploadDTO = (DocUploadDTO) args;
                return documentManager.uploadDoc(uploadDTO);
            }

            @Override
            protected String convertResult(String docId) {
                return docId;
            }

        });
    }

    @Override
    public ServiceResponse<Resource> downloadDoc(String fileId) {
        return super.executeProcess(BizScene.DOC_DOWNLOAD, fileId, new CallBack<Resource, Resource>() {

            @Override
            protected void checkParams(Object request) {
                AssertUtil.notNull(fileId, BizErrorCode.PARAM_ILLEGAL);
            }

            @Override
            protected Resource doProcess(Object args) {
                return documentManager.downloadDoc(fileId);
            }

            @Override
            protected Resource convertResult(Resource resource) {
                return resource;
            }

        });
    }

    @Override
    public ServiceResponse<Boolean> deleteDoc(String docId) {
        return super.executeProcess(BizScene.DOC_DELETE, docId, new CallBack<Boolean, Boolean>() {

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
        return super.executeProcess(BizScene.DOC_SEARCH, searchRequest, new CallBack<PageResponse<FileEntity>, PageResponse<FileVO>>() {

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

    @Autowired
    public void setOrganizationDomain(OrganizationDomain organizationDomain) {
        this.organizationDomain = organizationDomain;
    }
}
