package com.odk.baseutil.enums;

/**
 * BizScene
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2023/11/11
 */
public enum BizScene {
    HELLO_WORLD("HELLO_WORLD", "HELLO_WORLD"),

    USER_REGISTER("USER_REGISTER", "用户注册"),

    USER_LOGIN("USER_LOGIN", "用户登录"),

    USER_LOGOUT("USER_LOGOUT", "注销登录"),

    USER_QUERY("USER_QUERY", "用户查询"),

    USER_PERMISSION_QUERY("USER_PERMISSION_QUERY", "用户权限查询"),

    USER_ROLE_ADD("USER_ROLE_ADD", "添加用户角色"),

    ROLE_RELA_ADD("ROLE_RELA_ADD", "用户添加角色"),

    //文件相关
    DOC_UPLOAD("DOC_UPLOAD", "文件上传"),

    DOC_DOWNLOAD("DOC_DOWNLOAD", "文件下载"),

    DOC_DELETE("DOC_DELETE", "文件上传"),

    DOC_SEARCH("DOC_SEARCH", "文件列表查询"),

    SEARCH("SEARCH", "搜索"),

    DIRECTORY_CREATE("DIRECTORY_CREATE",  "创建文件夹"),

    DIRECTORY_DELETE("DIRECTORY_DELETE", "删除文件夹"),

    DIRECTORY_TREE("DIRECTORY_TREE", "目录树"),

    DIRECTORY_SEARCH("DIRECTORY_SEARCH", "文件检索"),


    //组织
    USER_ORGANIZATION("USER_ORGANIZATION", "用户组织"),




    ;


    private final String code;

    private final String description;

    BizScene(String code, String description) {

        this.code = code;
        this.description = description;
    }


    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
