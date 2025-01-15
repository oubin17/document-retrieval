package com.odk.basedomain.repository.org;

import com.odk.basedomain.model.org.OrganizationDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * OrganizationRepository
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/1/15
 */
public interface OrganizationRepository extends JpaRepository<OrganizationDO, String> {

    /**
     * 根据父节点查找，按创建时间降序
     * @param parentId
     * @return
     */
    @Query(value = "select * from t_organization where parent_id = :parentId order by create_time desc", nativeQuery = true)
    List<OrganizationDO> findCurrentLevel(@Param("parentId") String parentId);


}
