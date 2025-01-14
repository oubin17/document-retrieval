package com.odk.baseweb.document;

import com.odk.base.vo.response.PageResponse;
import com.odk.base.vo.response.ServiceResponse;
import com.odk.baseapi.inter.document.DocumentApi;
import com.odk.baseapi.request.document.DocumentSearchRequest;
import com.odk.baseapi.request.document.DocumentUploadRequest;
import com.odk.baseapi.vo.FileVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

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
    public ServiceResponse<String> uploadDocument(MultipartFile file, String dirId) throws IOException {
        DocumentUploadRequest request = new DocumentUploadRequest();
        request.setFileInputStream(file.getInputStream());
        request.setFileName(file.getOriginalFilename());
        request.setContentType(file.getContentType());
        request.setFileSize(file.getSize() / 1024 + "K");
        request.setDirId(dirId);
        return documentApi.uploadDoc(request);
    }

    /**
     * 文件下载
     *
     * @param fileId
     * @return
     */
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadDocument(@RequestParam("fileId") String fileId) {
        ServiceResponse<Resource> resourceServiceResponse = documentApi.downloadDoc(fileId);
        // 使用正则表达式去掉 _数字_ 部分
        String cleanedFileName = Objects.requireNonNull(resourceServiceResponse.getData().getFilename()).replaceAll("^_\\d+_", "");
        String encodedFileName = URLEncoder.encode(Objects.requireNonNull(cleanedFileName), StandardCharsets.UTF_8);
        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8\"" + encodedFileName + "\"");
        String contentType;
        if (cleanedFileName.endsWith(".pdf")) {
            contentType = MediaType.APPLICATION_PDF_VALUE;
        } else if (cleanedFileName.endsWith(".doc") || cleanedFileName.endsWith(".docx")) {
            contentType = "application/msword";
        } else {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE; // 默认二进制流
        }
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);
        // 返回 ResponseEntity
        return ResponseEntity.ok()
                .headers(headers)
                .body(resourceServiceResponse.getData());
    }

    @DeleteMapping()
    public ServiceResponse<Boolean> deleteDocument(@RequestParam("docId") String docId) {
        return documentApi.deleteDoc(docId);
    }

    @Autowired
    public void setDocumentApi(DocumentApi documentApi) {
        this.documentApi = documentApi;
    }
}
