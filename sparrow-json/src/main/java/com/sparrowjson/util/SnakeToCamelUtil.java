package com.sparrowjson.util;

/**
 * Description:
 * Company    : 上海黄豆网络科技有限公司
 *
 * @author : hll
 * Date       : 2024/11/7
 * Modify     : 修改日期          修改人员        修改说明          JIRA编号
 * v1.0.0       2024/11/7       hll    新增              1001
 ********************************************************************/
public class SnakeToCamelUtil {
    public static String toCamelCase(String field) {
        // 将字段名转换为小写
        //field = field.toLowerCase();
        field = field.replace(".", "_");
        // 使用正则表达式匹配下划线和下划线后的第一个字母，将其转换为大写字母
        StringBuilder camelCase = new StringBuilder();
        boolean toUpperCase = false;

        for (char ch : field.toCharArray()) {
            if (ch == '_') {
                toUpperCase = true;
            } else if (toUpperCase) {
                camelCase.append(Character.toUpperCase(ch));
                toUpperCase = false;
            } else {
                camelCase.append(ch);
            }
        }

        return camelCase.toString();
    }

}

