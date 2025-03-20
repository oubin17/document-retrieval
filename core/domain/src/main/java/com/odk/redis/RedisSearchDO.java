package com.odk.redis;

import com.odk.base.dos.BaseDO;
import com.redis.om.spring.annotations.Document;
import com.redis.om.spring.annotations.Indexed;
import com.redis.om.spring.annotations.Searchable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * RedisSearchDO
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/3/19
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Document
public class RedisSearchDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = 782305432211664400L;

    @Indexed
    private String fileName;

    @Searchable
    private String content;
}
