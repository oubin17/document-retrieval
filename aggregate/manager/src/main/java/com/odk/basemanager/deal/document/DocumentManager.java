package com.odk.basemanager.deal.document;

import com.odk.base.exception.AssertUtil;
import com.odk.base.exception.BizErrorCode;
import com.odk.base.exception.BizException;
import com.odk.base.idgenerator.SnowflakeIdUtil;
import com.odk.base.util.FileUtil;
import com.odk.base.util.LocalDateTimeUtil;
import com.odk.basedomain.domain.es.DocumentDO;
import com.odk.basedomain.domain.file.FileDO;
import com.odk.basedomain.repository.es.DocumentRepository;
import com.odk.basedomain.repository.file.FileRepository;
import com.odk.basemanager.dto.document.DocUploadDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.IOException;
import java.time.ZoneOffset;

/**
 * DocumentManager
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/12/25
 */
@Slf4j
@Service
public class DocumentManager {

    private DocumentRepository documentRepository;

    private FileRepository fileRepository;


    private TransactionTemplate transactionTemplate;

    @Value("${file.input.path}")
    private String baseFilePath;


    public Long uploadDoc(DocUploadDTO uploadDTO) {
        long mainId = SnowflakeIdUtil.nextId();
        String docId = String.valueOf(mainId);
        String fullFilaPath = FileUtil.generateFullFileName(baseFilePath, docId, uploadDTO.getFileName());
        DocumentDO documentDO = new DocumentDO();
        FileUtil.checkAndCreateFilePath(baseFilePath);
        transactionTemplate.executeWithoutResult(transactionStatus -> {
            try {
                //1.上传文件
                FileUtil.saveFile(fullFilaPath, uploadDTO.getFileInputStream());

                //2.获取文件内容
                String docContents = FileUtil.getFileContents(fullFilaPath);
                AssertUtil.isNotEmpty(docContents, "文件内容为空");
                log.info("文件内容 {}", docContents);

                //3.内容写到ES
                documentDO.setId(mainId);
                documentDO.setFileName(uploadDTO.getFileName());
                documentDO.setContentType(uploadDTO.getContentType());
                documentDO.setFileContents(docContents);
                documentDO.setFileSize(uploadDTO.getFileSize());
                documentDO.setFullFilePath(fullFilaPath);
                documentDO.setCreateTimeMill(LocalDateTimeUtil.getCurrentDateTime().toInstant(ZoneOffset.UTC).toEpochMilli());
                documentRepository.save(documentDO);
            } catch (IOException e) {
                log.error("文件上传失败，文件名：{}，错误信息：", uploadDTO.getFileName(), e);
                throw new BizException(BizErrorCode.SYSTEM_ERROR);
            } catch (Exception e) {
                log.error("文件上传发生未知错误,", e);
                throw e;
            }
            //4.保存文件到db
            FileDO fileDO = new FileDO();
            BeanUtils.copyProperties(documentDO, fileDO);
            fileRepository.save(fileDO);
        });

        return mainId;
    }


    public void add(Long id, String name) {
        DocumentDO documentDO = new DocumentDO();
        documentDO.setId(id);
        documentRepository.save(documentDO);
    }

    public DocumentDO searchById(Long id) {
        return documentRepository.findById(id).orElse(null);
    }


    @Autowired
    public void setDocumentRepository(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Autowired
    public void setFileRepository(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Autowired
    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }
}
