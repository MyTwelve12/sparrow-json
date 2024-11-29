package com.sparrowjson.util;

import java.util.Arrays;
import java.util.List;

/**
 * Description:
 * Company    : 上海黄豆网络科技有限公司
 *
 * @author : hll
 * Date       : 2024/11/12
 * Modify     : 修改日期          修改人员        修改说明          JIRA编号
 * v1.0.0       2024/11/12       hll    新增              1001
 ********************************************************************/
public class CommonDbFieldsUtil {

    // 定义常用的数据库字段名称
    public static final String ID = "id";
    public static final String CREATE_TIME = "create_time";
    public static final String UPDATE_TIME = "update_time";
    public static final String CREATED_BY = "created_by";
    public static final String UPDATED_BY = "updated_by";
    public static final String CREATED_ID = "created_id";
    public static final String UPDATED_ID = "updated_id";
    public static final String STATUS = "status";
    public static final String DEL_FLAG = "del_flag";

    // 将这些字段放入一个不可变的List中，方便复用
    public static final List<String> COMMON_FIELDS = Arrays.asList(
            ID,
            CREATE_TIME,
            UPDATE_TIME,
            CREATED_BY,
            UPDATED_BY,
            STATUS,
            DEL_FLAG,
            CREATED_ID,
            UPDATED_ID
    );
}
