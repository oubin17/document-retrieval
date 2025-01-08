package com.odk.baseutil.enums;

import com.odk.base.enums.IEnum;

/**
 * DirectoryTypeEnum
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/1/7
 */
public enum DirectoryTypeEnum implements IEnum {

    FOLDER("1", "文件夹"),

    FILE("2", "文件");

    private final String code;

    private final String description;

    DirectoryTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }


    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}
