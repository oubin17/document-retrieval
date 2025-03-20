package com.odk.basedomain.domain;

import com.google.common.collect.Lists;
import com.odk.basedomain.domain.inter.DocumentRedisSearchDomain;
import com.odk.redis.RedisSearchDO;
import com.odk.redis.RedisSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * DocumentRedisSearchDomainImpl
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/3/19
 */
@Service
public class DocumentRedisSearchDomainImpl implements DocumentRedisSearchDomain {

    private RedisSearchRepository redisSearchRepository;

    @Override
    public void save(RedisSearchDO redisSearchDO) {
        this.redisSearchRepository.save(redisSearchDO);

//        redisTemplate.opsForHash().put(
//                "FileDocument:" + redisSearchDO.getId(),
//                "document",
//                redisSearchDO
//        );
    }


    // 全文搜索
    public List<RedisSearchDO> searchContent(String keyword) throws Exception {
        keyword = keyword.replaceAll("([,.<>{}()\\[\\]\"'\\\\:;!@#$%^&*\\-=+~ ])", "\\\\$1");
        List<RedisSearchDO> redisSearchDOS = this.redisSearchRepository.fullTextSearch(keyword);
        return Lists.newArrayList();

    }

    @Autowired
    public void setRedisSearchRepository(RedisSearchRepository redisSearchRepository) {
        this.redisSearchRepository = redisSearchRepository;
    }
}
