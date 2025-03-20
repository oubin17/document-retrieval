package com.odk.redis;

import com.redis.om.spring.annotations.Query;
import com.redis.om.spring.repository.RedisDocumentRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * RedisSearchRepository
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/3/20
 */
public interface RedisSearchRepository extends RedisDocumentRepository<RedisSearchDO, String> {

    /**
     * 精确匹配
     * @param fileName
     * @return
     */
    List<RedisSearchDO> findByFileName(String fileName);

    /**
     * 全文检索
     *
     * @param content
     * @return
     */
//    List<RedisSearchDO> findRedisSearchDOByContentLike(@Param("query") String content);


    @Query("@fileName:$query OR @content:$query")  // 联合搜索标题和内容
    List<RedisSearchDO> fullTextSearch(@Param("query") String query);
}
