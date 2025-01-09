package com.odk.baseapi.inter.document;

import com.odk.base.vo.response.PageResponse;
import com.odk.base.vo.response.ServiceResponse;
import com.odk.baseapi.request.document.DocumentSearchRequest;
import com.odk.baseapi.request.document.DocumentUploadRequest;
import com.odk.baseapi.vo.DocumentVO;
import com.odk.baseapi.vo.FileVO;

/**
 * DocumentApi
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/12/25
 */
public interface DocumentApi {

    /**
     * 文件上传
     *
     * @param docUploadRequest
     * @return
     */
    ServiceResponse<String> uploadDoc(DocumentUploadRequest docUploadRequest);

    /**
     * 根据id删除文档
     *
     * @param docId
     * @return
     */
    ServiceResponse<Boolean> deleteDoc(String docId);

    /**
     * 根据文件名称查找
     *
     * @param searchRequest
     * @return
     */
    ServiceResponse<PageResponse<FileVO>> searchByCondition(DocumentSearchRequest searchRequest);


    ServiceResponse<DocumentVO> searchById(Long id);

}
