package com.odk.baseapi.inter.document;

import com.odk.base.vo.response.ServiceResponse;
import com.odk.baseapi.vo.DocumentVO;

/**
 * DocumentApi
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/12/25
 */
public interface DocumentApi {


    ServiceResponse<Boolean> saveDocument(Long id, String name);

    ServiceResponse<DocumentVO> searchById(Long id);

}