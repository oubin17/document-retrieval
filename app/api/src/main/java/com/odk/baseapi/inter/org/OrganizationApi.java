package com.odk.baseapi.inter.org;

import com.odk.base.vo.response.ServiceResponse;
import com.odk.baseutil.entity.OrganizationEntity;

/**
 * OrganizationApi
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/1/15
 */
public interface OrganizationApi {

    ServiceResponse<OrganizationEntity> getUserOrganization();
}
