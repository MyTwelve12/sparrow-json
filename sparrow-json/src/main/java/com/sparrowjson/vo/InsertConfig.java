package com.sparrowjson.vo;

import lombok.Data;


/**
 * Description:
 * Company    : 上海黄豆网络科技有限公司
 *
 * @author : hll
 * Date       : 2024/11/6
 * Modify     : 修改日期          修改人员        修改说明          JIRA编号
 * v1.0.0       2024/11/6       hll    新增              1001
 ********************************************************************/
@Data
public class InsertConfig extends BackConfig {

    private String insertFields;

    private String templateAlias = "add";

    private String name;

    private String batchInsertFields;

    private String tableName;
}
