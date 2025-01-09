package com.odk.basedomain.repository.file;

import com.odk.basedomain.model.file.DirectoryDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * DirectoryRepository
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/1/7
 */
public interface DirectoryRepository extends JpaRepository<DirectoryDO, String> {

    /**
     * 根据父节点查找，按创建时间降序
     * @param parentId
     * @return
     */
    @Query(value = "select * from ( select t1.*, t2.status as file_status from t_directory t1 left join t_file t2 on t1.file_id = t2.id where t1.parent_id = :parentId and t1.status = '0' ) t3 where (t3.file_status is null or t3.file_status = '0' ) order by t3.create_time desc", nativeQuery = true)
    List<DirectoryDO> findCurrentLevel(@Param("parentId") String parentId);

    /**
     * 根据父节点查找，按创建时间降序
     *
     * @param parentId
     * @param status
     * @return
     */
    List<DirectoryDO> findByParentIdAndStatusOrderByCreateTimeDesc(String parentId, String status);

    /**
     * 根据id和类型查找
     *
     * @param id
     * @param directoryType
     * @param status
     * @return
     */
    DirectoryDO findByIdAndDirectoryTypeAndStatus(String id, String directoryType, String status);

    /**
     * 更新状态
     *
     * @param id
     * @param status
     * @return
     */
    @Modifying
    @Transactional
    @Query("UPDATE DirectoryDO d SET d.status = :status, d.updateTime = now() where d.id = :id")
    int updateStatus(@Param("id") String id, @Param("status") String status);
}
