package com.odk.basedomain.domain.inter;

import com.odk.baseutil.entity.OrganizationEntity;

import java.util.Set;

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

    /**
     * 查找组织下的所有用户ID
     *
     * @param orgId
     * @return
     */
    Set<String> getUsersByOrgId(String orgId);

    /**
     * 根据用户id，查询用户关联组织
     *
     * @param userId
     * @return
     */
    Set<String> getOrgIdsByUserId(String userId);

    /**
     * 判断用户是否有组织权限
     *
     * 即用户关联组织存在orgIds下，如orgIds = [1,2] userId org = [2,3]，返回true
     *
     * @param orgIds
     * @param userId
     * @return
     */
    boolean checkOrgPermission(Set<String> orgIds, String userId);

    /**
     * 检查orgId是否为空，如果为空，返回userId对应的orgId
     * 如果userId对应的orgId有多个，则抛异常
     * @param orgId
     * @param userId
     * @return
     */
    String checkAndReturnCurrentOrgId(String orgId, String userId);
}
