package com.odk.basedomain.model.es;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serial;

/**
 * Document
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/12/25
 */
@Data
@Document(indexName = "document_6")
public class DocumentDO {

    @Serial
    private static final long serialVersionUID = 9179760589675345038L;

    @Id
    private String id;

    /**
     * 文件名称
     */
    @Field(type = FieldType.Keyword)
    private String fileName;

    /**
     * 文件类型
     */
    private String contentType;

    /**
     * 文件内容
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
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
