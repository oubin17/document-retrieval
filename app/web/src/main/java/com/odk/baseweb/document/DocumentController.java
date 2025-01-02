package com.odk.baseweb.document;

import com.odk.base.vo.response.PageResponse;
import com.odk.base.vo.response.ServiceResponse;
import com.odk.baseapi.inter.document.DocumentApi;
import com.odk.baseapi.request.document.DocumentSearchRequest;
import com.odk.baseapi.request.document.DocumentUploadRequest;
import com.odk.baseapi.vo.FileVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    /**
     * 根据文件名称查找文件列表
     *
     * @param searchRequest
     * @return
     */
    @PostMapping("/search")
    public ServiceResponse<PageResponse<FileVO>> queryByFileName(@RequestBody DocumentSearchRequest searchRequest) {
        return documentApi.searchByCondition(searchRequest);
    }

    /**
     * 文件上传
     *
     * @param file
     * @param dirId
     * @return
     * @throws IOException
     */
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

    @DeleteMapping()
    public ServiceResponse<Boolean> deleteDocument(@RequestParam Long docId) {
        return documentApi.deleteDoc(docId);
    }

    @Autowired
    public void setDocumentApi(DocumentApi documentApi) {
        this.documentApi = documentApi;
    }
}
