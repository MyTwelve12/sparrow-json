package com.sparrowjson.vo.unit;

import lombok.Data;

import java.util.List;

@Data
public class FrontendCompleteBO {

    /**
     * 配置信息
     */
    private FrontPageConfigBO pageConfig;

    /**
     * 变量
     */
    private List<FrontVariableBO> variables;
}
