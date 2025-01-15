package com.odk.basedomain.model.org;

import com.odk.base.dos.BaseDO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;

/**
 * UserOrganizationDO
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/1/15
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "t_user_organization", indexes = {
        @Index(name = "idx_user_id", columnList = "user_id"),
        @Index(name = "idx_org_id", columnList = "org_id")
})
@EntityListeners(AuditingEntityListener.class)
public class UserOrganizationDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = 810340435864878874L;

    @Id
    @GeneratedValue(generator = "user-uuid")
    @GenericGenerator(name = "user-uuid", strategy = "com.odk.basedomain.idgenerate.CustomIDGenerator")
    private String id;

    @Column(name = "user_id")
    @Comment("用户ID")
    private String userId;

    @Column(name = "org_id")
    @Comment("组织ID")
    private String orgId;

}
