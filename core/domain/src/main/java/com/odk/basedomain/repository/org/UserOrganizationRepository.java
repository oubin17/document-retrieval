package com.odk.basedomain.repository.org;

import com.odk.basedomain.model.org.UserOrganizationDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

/**
 * UserOrganizationRepository
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/1/15
 */
public interface UserOrganizationRepository extends JpaRepository<UserOrganizationDO, String> {

    /**
     * 查询用户关联的组织
     *
     * @param userId
     * @return
     */
    @Query(value = "select t1.org_id from t_user_organization t1 inner join t_organization t2 on t1.org_id = t2.id where t1.user_id = :userId and t2.status = '0'", nativeQuery = true)
    Set<String> findUserOrg(@Param("userId") String userId);

    /**
     * 根据组织id查询用户
     *
     * @param orgId
     * @return
     */
    List<UserOrganizationDO> findByOrgId(@Param("orgId") String orgId);
}
