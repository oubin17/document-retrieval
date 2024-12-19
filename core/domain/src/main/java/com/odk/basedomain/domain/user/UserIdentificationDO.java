package com.odk.basedomain.domain.user;

import com.odk.base.dos.BaseDO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;

/**
 * UserIdentificationDO
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/11/4
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "t_user_identification", indexes = {
        @Index(name = "idx_user_id", columnList = "user_id", unique = true)
})
@EntityListeners(AuditingEntityListener.class)
public class UserIdentificationDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = -7115218095274721902L;

    @Id
    @GeneratedValue(generator = "user-uuid")
    @GenericGenerator(name = "user-uuid", strategy = "com.odk.basedomain.idgenerate.CustomIDGenerator")
    private Long id;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 认证类型
     * {@link com.odk.base.enums.user.IdentificationTypeEnum}
     */
    @Column(name = "identify_type")
    private String identifyType;

    /**
     * 认证值
     */
    @Column(name = "identify_value")
    private String identifyValue;

}
