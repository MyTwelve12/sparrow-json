package com.sparrowjson.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
public class MenuConfig {
    private String functionDesc;
    private String database;
    private String table;
    private List<String> tableFields;
    private List<InsertConfig> insertConfigs = new ArrayList<>();
    private List<UpdateConfig> updateConfigs = new ArrayList<>();
    private List<DeleteConfig> deleteConfigs = new ArrayList<>();
    private List<QueryConfig> queryConfigs = new ArrayList<>();

    private List<CreateTableConfig> createTables = new ArrayList<>();

    private List<InsertTableColumnsConfig> insertTableColumns = new ArrayList<>();

    private List<ColumnVO> coulumns;

    private Map<String, String> columnCommentMap = new HashMap<>();



    public void setCoulumns(List<ColumnVO> coulumns) {
        this.coulumns = coulumns;
        this.columnCommentMap = coulumns.stream().collect(Collectors.toMap(ColumnVO::getComment, ColumnVO::getColumnName, (v1, v2) -> v1));
    }

}
