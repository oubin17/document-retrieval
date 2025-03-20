package com.odk.basedomain.domain;

import com.odk.base.vo.response.PageResponse;
import com.odk.basedomain.domain.inter.DocumentRedisSearchDomain;
import com.odk.basedomain.domain.inter.SearchDomain;
import com.odk.basedomain.model.file.FileSearchDO;
import com.odk.redis.RedisSearchDO;
import com.odk.basedomain.repository.file.FileSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * SearchDomainImpl
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/12/26
 */
@Service
public class SearchDomainImpl implements SearchDomain {

//    @Autowired
//    private ElasticsearchTemplate elasticsearchTemplate;

    private FileSearchRepository fileSearchRepository;

    private DocumentRedisSearchDomain documentRedisSearchDomain;

    @Override
    public PageResponse<FileSearchDO> searchByFileContentsContains(String keyword, Pageable pageable) {

        try {
            List<RedisSearchDO> redisSearchDOS = documentRedisSearchDomain.searchContent(keyword);
            return  PageResponse.of(new ArrayList<>(), 0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

//        List<FileSearchDO> byCondition = fileSearchRepository.findByCondition(keyword, pageable.getPageNumber(), pageable.getPageSize());
//        int count = fileSearchRepository.conditionCount(keyword);
//        return PageResponse.of(byCondition, count);

    }

    @Autowired
    public void setFileSearchRepository(FileSearchRepository fileSearchRepository) {
        this.fileSearchRepository = fileSearchRepository;
    }


    @Autowired
    public void setDocumentRedisSearchDomain(DocumentRedisSearchDomain documentRedisSearchDomain) {
        this.documentRedisSearchDomain = documentRedisSearchDomain;
    }
}
