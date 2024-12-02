package com.sparrowjson.vo.unit;

import lombok.Data;

@Data
public class FrontendItemConfigBO {

    /**
     * 名称
     */
    private String name;

    /**
     * 前端展示字段
     */
    private String showField;

    /**
     * 值
     */
    private String value;

    /**
     * 目标字段
     */
    private String targetField;
}
