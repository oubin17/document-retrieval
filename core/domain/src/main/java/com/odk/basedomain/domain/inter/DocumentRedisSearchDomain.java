package com.odk.basedomain.domain.inter;

import com.odk.redis.RedisSearchDO;

import java.util.List;

/**
 * DocumentRedisSearchDomain
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/3/19
 */
public interface DocumentRedisSearchDomain {

    void save(RedisSearchDO redisSearchDO);

    List<RedisSearchDO> searchContent(String keyword) throws Exception;



}
