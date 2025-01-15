package com.odk.baseutil.enums;

import com.odk.base.enums.IEnum;

/**
 * OrganizationTypeENum
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/1/15
 */
public enum OrganizationTypeEnum implements IEnum {

    LEVEL_0("0", "根节点"),

    /**
     * 根据实际情况调整
     */
    LEVEL_100("100", "叶子节点"),

    ;

    private final String code;

    private final String description;


    OrganizationTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
