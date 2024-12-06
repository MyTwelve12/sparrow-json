package com.sparrowjson.constant;

/**
 * Description:
 * Company    : 上海黄豆网络科技有限公司
 *
 * @author : hll
 * Date       : 2024/11/7
 * Modify     : 修改日期          修改人员        修改说明          JIRA编号
 * v1.0.0       2024/11/7       hll    新增              1001
 ********************************************************************/
public class SparrowBackendConstant {

    public static final String COMMA_SEPARATOR = ",";


    public static final String COLON_SEPARATOR = ":";

    public static final String SLASH_SEPARATOR = "/";

    public static final String SEMICOLON_SEPARATOR = ";";


    public static final String LEFT_SQUARE_BRACKET_SEPARATOR = "[";

    public static final String RIGHT_SQUARE_BRACKET_SEPARATOR = "]";

    public static final String LEFT_CURLY_BRACE_SEPARATOR = "{";

    public static final String RIGHT_CURLY_BRACE_SEPARATOR = "}";

    public static final String PIPE_SEPARATOR = "\\|";


    public static final String FRONT_TEMPLATE_SEPARATOR = "=================前端===================";

    public static final String FRONT_TEMPLATE_FUNCTION_NAME = "功能：";

    /**
     * 前后端借口url对应关系
     */
    public static final String FRONT_COMMON_URL_VALUE = "{\n" +
            "    \"name\": \"\",\n" +
            "    \"type\": 1,\n" +
            "    \"desc\": \"\",\n" +
            "    \"value\": \"<![CDATA[${r'${(backend.xxxx.endpoint.http.full-url)!}'}]]>\"\n" +
            "}";

    public static final String FRONT_SPLIT_PARAMS = "xxxx";


    /**
     * 前端配置页面名称
     */
    public static final String FRONT_PAGE_CONFIG_NAME = "前端配置页面名称:";

    /**
     * 前端配置页面描述
     */
    public static final String FRONT_PAGE_CONFIG_DESC = "资源页面相关描述:";
}
