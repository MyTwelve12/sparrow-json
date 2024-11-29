package com.sparrowjson.enums;

/**
 * Description:
 * Company    : 上海黄豆网络科技有限公司
 *
 * @author : hll
 * Date       : 2024/11/13
 * Modify     : 修改日期          修改人员        修改说明          JIRA编号
 * v1.0.0       2024/11/13       hll    新增              1001
 ********************************************************************/
public enum MultiTableQueryFieldEnum {

    LEFT_JOIN("去重", "DISTINCT");

    private final String desc;
    private final String code;

    MultiTableQueryFieldEnum(String desc, String code) {
        this.desc = desc;
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public String getCode() {
        return code;
    }

    // 根据类型名称获取对应的连接类型
    public static MultiTableQueryFieldEnum fromJoinType(String type) {
        for (MultiTableQueryFieldEnum jt : MultiTableQueryFieldEnum.values()) {
            if (jt.desc.equalsIgnoreCase(type)) {
                return jt;
            }
        }
        throw new IllegalArgumentException("未知的类型: " + type);
    }
}
