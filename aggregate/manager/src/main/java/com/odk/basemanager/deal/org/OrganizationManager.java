package com.odk.basemanager.deal.org;

import cn.dev33.satoken.stp.StpUtil;
import com.odk.basedomain.domain.inter.OrganizationDomain;
import com.odk.baseutil.entity.OrganizationEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * OrganizationManager
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/1/15
 */
@Slf4j
@Service
public class OrganizationManager {

    private OrganizationDomain organizationDomain;

    public OrganizationEntity getCurrentUserEntity() {
        return this.organizationDomain.getUserOrgTree(StpUtil.getLoginIdAsString());
    }

    @Autowired
    public void setOrganizationDomain(OrganizationDomain organizationDomain) {
        this.organizationDomain = organizationDomain;
    }
}
