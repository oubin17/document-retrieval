package com.odk.basedomain.domain;

import cn.dev33.satoken.stp.StpUtil;
import com.odk.base.enums.common.CommonStatusEnum;
import com.odk.base.exception.AssertUtil;
import com.odk.base.exception.BizErrorCode;
import com.odk.basedomain.domain.inter.OrganizationDomain;
import com.odk.basedomain.domain.inter.UserDomain;
import com.odk.basedomain.model.user.UserBaseDO;
import com.odk.basedomain.repository.user.UserBaseRepository;
import com.odk.baseutil.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * UserDomainImpl
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/1/16
 */
@Slf4j
@Service
public class UserDomainImpl implements UserDomain {

    private UserBaseRepository baseRepository;

    private OrganizationDomain organizationDomain;

    @Override
    public UserEntity getByUserId(String userId) {
        Optional<UserBaseDO> byId = this.baseRepository.findById(userId);
        if (byId.isPresent()) {
            UserEntity userEntity = new UserEntity();
            BeanUtils.copyProperties(byId.get(), userEntity);
            userEntity.setUserId(byId.get().getId());
            generateOrganization(userEntity);
            return userEntity;
        } else {
            return null;
        }
    }

    @Override
    public UserEntity getBySession() {
        return getByUserId(StpUtil.getLoginIdAsString());
    }

    @Override
    public UserEntity getByUserIdAndCheck(String userId) {

        Optional<UserBaseDO> userBaseDO = baseRepository.findById(userId);
        AssertUtil.isTrue(userBaseDO.isPresent(), BizErrorCode.PARAM_ILLEGAL);
        AssertUtil.isTrue(CommonStatusEnum.NORMAL.getCode().equals(userBaseDO.get().getUserStatus()), BizErrorCode.USER_STATUS_ERROR);
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userBaseDO.get(), userEntity);
        userEntity.setUserId(userBaseDO.get().getId());
        generateOrganization(userEntity);
        return userEntity;
    }

    /**
     * 补充组织信息
     *
     * @param userEntity
     */
    private void generateOrganization(UserEntity userEntity) {
        userEntity.setOrgIds(this.organizationDomain.getOrgIdsByUserId(userEntity.getUserId()));
        userEntity.setOrganizationTree(this.organizationDomain.getUserOrgTree(userEntity.getUserId()));
    }

    @Override
    public UserEntity getBySessionAndCheck() {
        return getByUserIdAndCheck(StpUtil.getLoginIdAsString());
    }

    @Autowired
    public void setBaseRepository(UserBaseRepository baseRepository) {
        this.baseRepository = baseRepository;
    }

    @Autowired
    public void setOrganizationDomain(OrganizationDomain organizationDomain) {
        this.organizationDomain = organizationDomain;
    }
}
