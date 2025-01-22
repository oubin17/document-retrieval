package com.odk.basedomain.model.file;

import com.odk.base.dos.BaseDO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;

/**
 * DirectoryDO
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/1/7
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "t_directory", indexes = {
        @Index(name = "idx_parent_id", columnList = "parent_id")
})
@EntityListeners(AuditingEntityListener.class)
public class DirectoryDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = 2776986163176656341L;

    @Column(name = "directory_name")
    @Comment("目录名称")
    private String directoryName;

    /**
     * 目录类型：文件-0、目录-1
     */
    @Column(name = "directory_type")
    private String directoryType;

    /**
     * 文件id
     */
    @Column(name = "file_id")
    private String fileId;

    /**
     * 父节点ID，如果是根节点，parent_id为0
     */
    @Column(name = "parent_id")
    private String parentId;

    /**
     * 状态 {@link com.odk.base.enums.common.CommonStatusEnum}
     */
    @Column(name = "status")
    private String status;

    /**
     * 组织id
     */
    @Column(name = "org_id")
    private String orgId;
}
