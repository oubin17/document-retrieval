package com.odk.baseutil.enums;

import com.odk.base.enums.IEnum;

/**
 * DirSearchTypeEnum
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/1/12
 */
public enum DirSearchTypeEnum implements IEnum {

    CONTENT("1", "文件内容"),

    FILE_NAME("2", "文件名称"),
    ;

    private final String code;

    private final String description;

    DirSearchTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static DirSearchTypeEnum getByCode(String type) {
        for (DirSearchTypeEnum searchTypeEnum : DirSearchTypeEnum.values()) {
            if (searchTypeEnum.code.equals(type)) {
                return searchTypeEnum;
            }
        }
        return null;
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
