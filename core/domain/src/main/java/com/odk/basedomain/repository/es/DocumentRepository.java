package com.odk.basedomain.repository.es;

import com.odk.basedomain.domain.es.DocumentDO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * DocumentRepository
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/12/25
 */
public interface DocumentRepository extends ElasticsearchRepository<DocumentDO, Long> {

    /**
     * 查询文本匹配
     *
     * @param keyword
     * @return
     */
    List<DocumentDO> searchByFileContentsContains(String keyword);

}
