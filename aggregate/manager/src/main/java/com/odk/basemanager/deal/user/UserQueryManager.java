package com.odk.basemanager.deal.user;

import com.odk.basedomain.domain.inter.OrganizationDomain;
import com.odk.basedomain.domain.inter.UserDomain;
import com.odk.basedomain.model.user.UserAccessTokenDO;
import com.odk.basedomain.repository.user.UserAccessTokenRepository;
import com.odk.basedomain.repository.user.UserBaseRepository;
import com.odk.baseutil.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * UserQueryManager
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/11/5
 */
@Service
public class UserQueryManager {

    private static final Logger logger = LoggerFactory.getLogger(UserQueryManager.class);

    private UserBaseRepository baseRepository;

    private UserAccessTokenRepository accessTokenRepository;

    private OrganizationDomain organizationDomain;

    private UserDomain userDomain;

    /**
     * 根据userId查找用户
     *
     * @param userId
     * @return
     */
    public UserEntity queryByUserId(String userId) {
        return this.userDomain.getByUserId(userId);
    }

    /**
     * 检查用户状态
     *
     * @param userId
     * @return
     */
    public UserEntity queryByUserIdAndCheck(String userId) {
       return this.userDomain.getByUserIdAndCheck(userId);
    }

    /**
     * 根据登录凭证查找用户
     *
     * @param tokenType
     * @param tokenValue
     * @return
     */
    public UserEntity queryByAccessToken(String tokenType, String tokenValue) {

        UserAccessTokenDO userAccessTokenDO = accessTokenRepository.findByTokenTypeAndTokenValue(tokenType, tokenValue);
        if (null == userAccessTokenDO) {
            return null;
        }
        return queryByUserId(userAccessTokenDO.getUserId());
    }

    @Autowired
    public void setBaseRepository(UserBaseRepository baseRepository) {
        this.baseRepository = baseRepository;
    }

    @Autowired
    public void setAccessTokenRepository(UserAccessTokenRepository accessTokenRepository) {
        this.accessTokenRepository = accessTokenRepository;
    }

    @Autowired
    public void setOrganizationDomain(OrganizationDomain organizationDomain) {
        this.organizationDomain = organizationDomain;
    }

    @Autowired
    public void setUserDomain(UserDomain userDomain) {
        this.userDomain = userDomain;
    }
}
