package com.sparrowjson.enums;

public enum CommonTemplateCodeEnum {

    RESOURCE_FUNCTIONS("资源管理", "ct_f360509bfa6b4f6b89569a98912f4dce");

    private final String name;
    private final String templateCode;


    CommonTemplateCodeEnum(String name, String templateCode) {
        this.name = name;
        this.templateCode = templateCode;
    }


    public String getName() {
        return name;
    }

    public String getTemplateCode() {
        return templateCode;
    }


    // 根据描述获取 code
    public static String getNameByTemplate(String name) {
        for (CommonTemplateCodeEnum templateCodeEnum : CommonTemplateCodeEnum.values()) {
            if (templateCodeEnum.getName().equals(name)) {
                return templateCodeEnum.getTemplateCode();
            }
        }
        return null;
    }
}
