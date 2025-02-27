//package com.odk.basedomain.repository.es;
//
//import com.odk.basedomain.model.es.DocumentDO;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
//
///**
// * DocumentRepository
// *
// * @description:
// * @version: 1.0
// * @author: oubin on 2024/12/25
// */
//public interface DocumentRepository extends ElasticsearchRepository<DocumentDO, Long> {
//
//    /**
//     * 查询文本匹配
//     *
//     * @param keyword
//     * @return
//     */
//    Page<DocumentDO> searchByFileContentsContains(String keyword, Pageable pageable);
//
//}
