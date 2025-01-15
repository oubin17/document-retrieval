package com.odk.basedomain.domain;

import com.google.common.collect.Lists;
import com.odk.basedomain.domain.inter.OrganizationDomain;
import com.odk.basedomain.model.org.OrganizationDO;
import com.odk.basedomain.repository.org.OrganizationRepository;
import com.odk.basedomain.repository.org.UserOrganizationRepository;
import com.odk.baseutil.entity.OrganizationEntity;
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
            //如果该节点是文件夹，递归下一级
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
