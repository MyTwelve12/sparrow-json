package com.sparrowjson.enums;

/**
 * Description:
 * Company    : 上海黄豆网络科技有限公司
 *
 * @author : hll
 * Date       : 2024/11/11
 * Modify     : 修改日期          修改人员        修改说明          JIRA编号
 * v1.0.0       2024/11/11       hll    新增              1001
 ********************************************************************/
public enum OrderTypeEnum {

    ASC("升序", 1),
    DESC("降序", 2);

    private final String description;
    private final int code;

    // 构造函数
    OrderTypeEnum(String description, int code) {
        this.description = description;
        this.code = code;
    }

    // 获取描述
    public String getDescription() {
        return description;
    }

    // 获取代码
    public int getCode() {
        return code;
    }

    // 根据描述获取枚举
    public static String getDescription(String description) {
        for (OrderTypeEnum type : OrderTypeEnum.values()) {
            if (type.getDescription().equals(description)) {
                return type.name();
            }
        }
        return OrderTypeEnum.ASC.name();
    }
}
