package com.odk.baseweb.document;

import com.odk.base.vo.response.ServiceResponse;
import com.odk.baseapi.inter.document.DocumentApi;
import com.odk.baseapi.request.document.DocumentUploadRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * DocumentController
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/12/25
 */
@RestController
@RequestMapping("/doc")
public class DocumentController {

    private DocumentApi documentApi;

    @PostMapping("/upload")
    public ServiceResponse<Long> uploadDocument(MultipartFile file, String dirId) throws IOException {
        DocumentUploadRequest request = new DocumentUploadRequest();
        request.setFileInputStream(file.getInputStream());
        request.setFileName(file.getOriginalFilename());
        request.setContentType(file.getContentType());
        request.setFileSize(file.getSize() / 1024 + "K");
        request.setDirId(dirId);
        return documentApi.uploadDoc(request);
    }

    @Autowired
    public void setDocumentApi(DocumentApi documentApi) {
        this.documentApi = documentApi;
    }
}
