package com.odk.basedomain.domain.es;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

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

    @Id
    private Long id;

    /**
     *
     */
    private String name;
}
