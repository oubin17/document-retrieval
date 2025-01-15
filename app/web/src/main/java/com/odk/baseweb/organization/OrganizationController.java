package com.odk.baseweb.organization;

import com.odk.base.vo.response.ServiceResponse;
import com.odk.baseapi.inter.org.OrganizationApi;
import com.odk.baseutil.entity.OrganizationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * OrganizationController
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/1/15
 */
@RestController
@RequestMapping("/org")
public class OrganizationController {

    private OrganizationApi organizationApi;

    @GetMapping("/current")
    public ServiceResponse<OrganizationEntity> queryUserOrg() {
        return organizationApi.getUserOrganization();
    }

    @Autowired
    public void setOrganizationApi(OrganizationApi organizationApi) {
        this.organizationApi = organizationApi;
    }
}
