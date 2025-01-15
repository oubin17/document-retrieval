package com.odk.baseservice.impl.org;

import com.odk.base.vo.response.ServiceResponse;
import com.odk.baseapi.inter.org.OrganizationApi;
import com.odk.basemanager.deal.org.OrganizationManager;
import com.odk.baseservice.template.AbstractApiImpl;
import com.odk.baseutil.entity.OrganizationEntity;
import com.odk.baseutil.enums.BizScene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * OrganizationService
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/1/15
 */
@Service
public class OrganizationService extends AbstractApiImpl implements OrganizationApi {

    private OrganizationManager organizationManager;

    @Override
    public ServiceResponse<OrganizationEntity> getUserOrganization() {
        return super.queryProcess(BizScene.USER_ORGANIZATION, null, new QueryApiCallBack<OrganizationEntity, OrganizationEntity>() {
            @Override
            protected OrganizationEntity doProcess(Object args) {
                return organizationManager.getCurrentUserEntity();
            }

            @Override
            protected OrganizationEntity convertResult(OrganizationEntity permissionEntity) {

                return permissionEntity;
            }

        });
    }

    @Autowired
    public void setOrganizationManager(OrganizationManager organizationManager) {
        this.organizationManager = organizationManager;
    }
}
