package com.odk.baseweb.document;

import com.odk.base.vo.response.ServiceResponse;
import com.odk.baseapi.inter.document.DocumentApi;
import com.odk.baseapi.vo.DocumentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * DocumentController
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/12/25
 */
@RestController
@RequestMapping("/document")
public class DocumentController {

    private DocumentApi documentApi;

    @PostMapping("/save")
    public ServiceResponse<Boolean> saveDocument(@RequestParam Long id, @RequestParam String name) {
        return documentApi.saveDocument(id, name);
    }

    @GetMapping("/getById")
    public ServiceResponse<DocumentVO> getDocument(@RequestParam Long id) {
        return documentApi.searchById(id);
    }


    @Autowired
    public void setDocumentApi(DocumentApi documentApi) {
        this.documentApi = documentApi;
    }
}
