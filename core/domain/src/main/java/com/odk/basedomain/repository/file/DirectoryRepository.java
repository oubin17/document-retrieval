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
public interface DirectoryRepository extends JpaRepository<DirectoryDO, Long> {

    /**
     * 根据父节点查找，按创建时间降序
     * @param parentId
     * @param status
     * @return
     */
    @Query(value = "select t1.* from t_directory t1 left join t_file t2 on t1.file_id = t2.id and t2.status != :status where t1.parent_id = :parentId and t1.status = :status order by t1.create_time desc", nativeQuery = true)
    List<DirectoryDO> findCurrentLevel(@Param("parentId") Long parentId, @Param("status") String status);

    /**
     * 根据父节点查找，按创建时间降序
     *
     * @param parentId
     * @param status
     * @return
     */
    List<DirectoryDO> findByParentIdAndStatusOrderByCreateTimeDesc(Long parentId, String status);

    /**
     * 根据id和类型查找
     *
     * @param id
     * @param directoryType
     * @param status
     * @return
     */
    DirectoryDO findByIdAndDirectoryTypeAndStatus(Long id, String directoryType, String status);

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
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
