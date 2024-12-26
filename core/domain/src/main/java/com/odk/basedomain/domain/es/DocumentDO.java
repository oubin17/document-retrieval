package com.odk.basedomain.domain.es;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serial;

/**
 * Document
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/12/25
 */
@Data
@Document(indexName = "document_1")
public class DocumentDO {

    @Serial
    private static final long serialVersionUID = 9179760589675345038L;

    @Id
    private Long id;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件类型
     */
    private String contentType;

    /**
     * 文件内容
     */
    private String fileContents;

    /**
     * 文件大小
     */
    private String fileSize;

    /**
     * 文件全路径
     */
    private String fullFilePath;

    /**
     * 创建时间戳
     */
    private Long createTimeMill;

}
