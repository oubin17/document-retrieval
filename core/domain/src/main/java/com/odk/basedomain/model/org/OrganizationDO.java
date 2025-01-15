package com.odk.basedomain.model.org;

import com.odk.base.dos.BaseDO;
import com.odk.base.enums.common.CommonStatusEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;

/**
 * OrganizationDO
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/1/15
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "t_organization", indexes = {
        @Index(name = "idx_parent_id", columnList = "parent_id")
})
@EntityListeners(AuditingEntityListener.class)
public class OrganizationDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = -8005339640219197498L;

    @Id
    @GeneratedValue(generator = "user-uuid")
    @GenericGenerator(name = "user-uuid", strategy = "com.odk.basedomain.idgenerate.CustomIDGenerator")
    private String id;

    @Column(name = "org_name")
    @Comment("组织名称")
    private String orgName;

    /**
     * {@link com.odk.baseutil.enums.OrganizationTypeEnum}
     */
    @Column(name = "org_type")
    @Comment("组织类型")
    private String orgType;

    @Column(name = "parent_id")
    @Comment("父级组织")
    private String parentId;

    /**
     * 状态 {@link com.odk.base.enums.common.CommonStatusEnum}
     */
    @Column(name = "status")
    @Comment("组织状态")
    private String status = CommonStatusEnum.NORMAL.getCode();

}
