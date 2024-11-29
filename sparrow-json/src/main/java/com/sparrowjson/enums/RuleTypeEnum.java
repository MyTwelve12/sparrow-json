package com.sparrowjson.enums;

/**
 * Description:
 * Company    : 上海黄豆网络科技有限公司
 *
 * @author : hll
 * Date       : 2024/11/8
 * Modify     : 修改日期          修改人员        修改说明          JIRA编号
 * v1.0.0       2024/11/8       hll    新增              1001
 ********************************************************************/
public enum RuleTypeEnum {
    CALCULATE("计算", 1),
    CONCATENATE("连接", 2);

    private final String description;
    private final int code;

    RuleTypeEnum(String description, int code) {
        this.description = description;
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }

    // 根据描述获取 code
    public static RuleTypeEnum getCodeByDescription(String description) {
        for (RuleTypeEnum type : RuleTypeEnum.values()) {
            if (type.getDescription().equals(description)) {
                return type;
            }
        }
        return null; // 未找到匹配项时返回 null
    }
}

