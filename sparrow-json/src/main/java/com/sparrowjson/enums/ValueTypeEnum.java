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
public enum ValueTypeEnum {
    CONSTANT("常量", 1),
    VARIABLE("变量", 2);

    private final String description;
    private final int code;

    ValueTypeEnum(String description, int code) {
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
    public static ValueTypeEnum getCodeByDescription(String description) {
        for (ValueTypeEnum type : ValueTypeEnum.values()) {
            if (type.getDescription().equals(description)) {
                return type;
            }
        }
        return null;
    }
}
