package com.odk.basedomain.domain;

import cn.dev33.satoken.stp.StpUtil;
import com.google.common.collect.Lists;
import com.odk.base.exception.AssertUtil;
import com.odk.base.exception.BizErrorCode;
import com.odk.basedomain.domain.inter.OrganizationDomain;
import com.odk.basedomain.model.org.OrganizationDO;
import com.odk.basedomain.model.org.UserOrganizationDO;
import com.odk.basedomain.repository.org.OrganizationRepository;
import com.odk.basedomain.repository.org.UserOrganizationRepository;
import com.odk.baseutil.entity.OrganizationEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * OrganizationDomainImpl
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/1/15
 */
@Service
public class OrganizationDomainImpl implements OrganizationDomain {


    private static final String ROOT_NODE = "0";

    private OrganizationRepository organizationRepository;

    private UserOrganizationRepository userOrganizationRepository;

    @Override
    public OrganizationEntity getUserOrgTree(String userId) {
        Set<String> orgIds = userOrganizationRepository.findUserOrg(userId);
        if (CollectionUtils.isEmpty(orgIds)) {
            return new OrganizationEntity();
        }
        List<OrganizationEntity> organizationEntities = buildDirectoryTree(ROOT_NODE);
        if (!CollectionUtils.isEmpty(organizationEntities)) {
            return searchTree(organizationEntities.get(0), orgIds);
        }

        return new OrganizationEntity();
    }


    @Override
    public Set<String> getUsersByOrgId(String orgId) {
        //判断用户是否有组织权限，用户状态正常，组织状态正常，有绑定关系
        Set<String> userOrg = this.userOrganizationRepository.findUserOrg(StpUtil.getLoginIdAsString());
        AssertUtil.isTrue(userOrg.contains(orgId), BizErrorCode.PARAM_ILLEGAL, "暂无该组织查询权限");
        List<UserOrganizationDO> byOrgId = this.userOrganizationRepository.findByOrgId(orgId);
        return byOrgId.stream().map(UserOrganizationDO::getUserId).collect(Collectors.toSet());

    }

    @Override
    public Set<String> getOrgIdsByUserId(String userId) {
        //判断用户是否有组织权限，用户状态正常，组织状态正常，有绑定关系
        return this.userOrganizationRepository.findUserOrg(userId);
    }

    @Override
    public boolean checkOrgPermission(Set<String> orgIds, String userId) {
        return false;
    }


    @Override
    public String checkAndReturnCurrentOrgId(String orgId, String userId) {
        //用户id对应组织
        Set<String> userOrg = this.userOrganizationRepository.findUserOrg(userId);
        if (StringUtils.isEmpty(orgId)) {
            AssertUtil.isTrue(userOrg.size() == 1, BizErrorCode.PARAM_ILLEGAL, "用户关联多个组织");
            return String.valueOf(userOrg.toArray()[0]);
        } else {
            AssertUtil.isTrue(userOrg.contains(orgId), BizErrorCode.PARAM_ILLEGAL, "无组织权限");
            return orgId;
        }
    }

    // 递归遍历目录树，匹配文件节点的名称
    private OrganizationEntity searchTree(OrganizationEntity root, Set<String> fileIds) {
        if (root == null) {
            return null;
        }
        // 递归处理子节点
        List<OrganizationEntity> filteredChildren = new ArrayList<>();
        for (OrganizationEntity child : root.getChildOrganizations()) {
            OrganizationEntity filteredChild = searchTree(child, fileIds);
            if (filteredChild != null) {
                filteredChildren.add(filteredChild);
            }
        }
        // 如果当前节点是目标节点，或者它的子节点包含目标节点，则保留当前节点
        if (fileIds.contains(root.getId()) || !filteredChildren.isEmpty()) {
            OrganizationEntity filteredNode = copyFromSourceEntity(root);
            filteredNode.getChildOrganizations().addAll(filteredChildren);
            return filteredNode;
        }
        // 否则返回null，表示当前节点不在最小子树中
        return null;
    }

    private OrganizationEntity copyFromSourceEntity(OrganizationEntity sourceEntity) {
        OrganizationEntity targetEntity = new OrganizationEntity();
        targetEntity.setId(sourceEntity.getId());
        targetEntity.setOrgName(sourceEntity.getOrgName());
        targetEntity.setOrgType(sourceEntity.getOrgType());
        targetEntity.setParentId(sourceEntity.getParentId());
        targetEntity.setChildOrganizations(Lists.newArrayList());
        return targetEntity;
    }

    /**
     * 构造目录树
     *
     * @param parentId
     * @return
     */
    private List<OrganizationEntity> buildDirectoryTree(String parentId) {
        List<OrganizationDO> rootOrganizations = this.organizationRepository.findCurrentLevel(parentId);
        if (CollectionUtils.isEmpty(rootOrganizations)) {
            return Lists.newArrayList();
        }
        List<OrganizationEntity> organizationEntities = convertEntity(rootOrganizations);
        for (OrganizationEntity entity : organizationEntities) {
            //如果该节点是目录，递归下一级
            entity.setChildOrganizations(buildDirectoryTree(entity.getId()));
        }
        return organizationEntities;
    }

    /**
     * 模型转换
     *
     * @param directoryDOS
     * @return
     */
    private List<OrganizationEntity> convertEntity(List<OrganizationDO> directoryDOS) {
        return directoryDOS.stream().map(directoryDO -> {
            OrganizationEntity entity = new OrganizationEntity();
            BeanUtils.copyProperties(directoryDO, entity);
            return entity;
        }).collect(Collectors.toList());
    }


    @Autowired
    public void setUserOrganizationRepository(UserOrganizationRepository userOrganizationRepository) {
        this.userOrganizationRepository = userOrganizationRepository;
    }

    @Autowired
    public void setOrganizationRepository(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }
}
