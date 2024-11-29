package com.sparrowjson.vo.unit;

import lombok.Data;

/**
 * 前端参数变量
 */
@Data
public class FrontVariableBO {

    /**
     * 名称
     */
    private String name;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 描述
     */
    private String desc;

    /**
     * 值
     */
    private String value;
}
