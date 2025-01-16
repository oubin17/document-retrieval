package com.odk.basedomain.domain.inter;

import com.odk.baseutil.entity.UserEntity;

/**
 * UserDomain
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/1/16
 */
public interface UserDomain {

    /**
     * 如果不存在，返回空
     *
     * @param userId
     * @return
     */
    UserEntity getByUserId(String userId);

    UserEntity getBySession();

    /**
     * 如果不存在，报错
     *
     * @param userId
     * @return
     */
    UserEntity getByUserIdAndCheck(String userId);

    UserEntity getBySessionAndCheck();
}
