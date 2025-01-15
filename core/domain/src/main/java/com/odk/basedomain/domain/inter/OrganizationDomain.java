package com.odk.basedomain.domain.inter;

import com.odk.baseutil.entity.OrganizationEntity;

/**
 * OrganizationDomain
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/1/15
 */
public interface OrganizationDomain {

    /**
     * 查询当前用户组织树
     *
     * @param userId
     * @return
     */
    OrganizationEntity getUserOrgTree(String userId);
}
