package com.sparrowjson.handler;

import com.alibaba.fastjson.JSON;
import com.sparrowjson.constant.SparrowBackendConstant;
import com.sparrowjson.database.DatabaseMetaDataUtil;
import com.sparrowjson.dto.SparrowBackendConfigDTO;
import com.sparrowjson.enums.MultiTableQueryEnum;
import com.sparrowjson.enums.MultiTableQueryFieldEnum;
import com.sparrowjson.enums.OrderTypeEnum;
import com.sparrowjson.util.SnakeToCamelUtil;
import com.sparrowjson.util.StringUtil;
import com.sparrowjson.vo.BackConfig;
import com.sparrowjson.vo.BackendVariablesVO;
import com.sparrowjson.vo.ColumnVO;
import com.sparrowjson.vo.MenuConfig;
import com.sparrowjson.vo.QueryConfig;
import com.sparrowjson.vo.unit.FrontendItemConfigBO;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Description:
 * Company    : 上海黄豆网络科技有限公司
 *
 * @author : hll
 * Date       : 2024/11/7
 * Modify     : 修改日期          修改人员        修改说明          JIRA编号
 * v1.0.0       2024/11/7       hll    新增              1001
 ********************************************************************/
@Component("queryTargetRule")
public class QueryTargetRuleHandler implements VariableHandler {
    @Override
    public BackendVariablesVO convertVariables(SparrowBackendConfigDTO sparrowBackendConfigDTO, MenuConfig menuConfig, BackConfig backConfig) {
        BackendVariablesVO backendVariablesVO = createBackendVariablesVO(sparrowBackendConfigDTO);

        String config = sparrowBackendConfigDTO.getConfig();

        QueryConfig queryConfig = (QueryConfig) backConfig;
        if (StringUtil.isBlank(queryConfig.getQueryFields())) {
            return null;
        }

        TableRuleDTO tableRuleDTO = new TableRuleDTO();
        tableRuleDTO.setTableDataSource(menuConfig.getDatabase());
        tableRuleDTO.setTableName(menuConfig.getTable());
        //多表关联查询
        if (!StringUtil.isBlank(queryConfig.getMultiTables())) {
            multitablerQuery(config, tableRuleDTO, queryConfig, backendVariablesVO);
            return backendVariablesVO;
        }
        //单表查询
        if (!StringUtil.isBlank(queryConfig.getTable())) {
            tableRuleDTO.setTableName(queryConfig.getTable());
        }
//        Map<String, String> columnCommentMap = menuConfig.getColumnCommentMap();
//        if (queryConfig.getTable() != null && !Objects.equals(queryConfig.getTable(), menuConfig.getTable())) {
//            List<ColumnVO> coulumns = DatabaseMetaDataUtil.getCoulumns(queryConfig.getTable());
//            columnCommentMap = coulumns.stream().collect(Collectors.toMap(ColumnVO::getComment, ColumnVO::getColumnName, (v1, v2) -> v1));
//        }

        //前端配置模版
        Map<String, FrontendItemConfigBO> frontendItemConfigBOMap = new HashMap<>();


        String[] fields = queryConfig.getQueryFields().split(SparrowBackendConstant.COMMA_SEPARATOR);
        for (int i = 0; i < fields.length; i++) {

//            String realTableField = columnCommentMap.get(fields[i]);
//
//
//            if (StringUtil.isBlank(realTableField)) {
//                throw new IllegalArgumentException("字段:" + fields[i] + "没有对应的数据库字段");
//            }

            FrontendItemConfigBO frontendItemConfigBO = frontendItemConfigBOMap.get(fields[i]);

            if (frontendItemConfigBO == null) {
                throw new IllegalArgumentException("字段:" + fields[i] + "没有对应的数据库字段");
            }
            fields[i] = frontendItemConfigBO.getShowField();

            //fields[i] = convertField(realTableField);
        }
        tableRuleDTO.setTableField(String.join(",", fields));
        Integer outputType = 2;
        //'outputRule':{'outputType':3}，指定输出的类型，为三时 在查询那里代表要不要查询totalcount，以及按照分页查询格式返回。
        //一般不是分页查询的场景，这边固定为2，即为直接输出值
        if (queryConfig.isPagination()) {
            tableRuleDTO.setTablePage("pageDTO");
            outputType = 3;
            tableRuleDTO.setTableTotalCount(2);
        }
        if (StringUtil.isNotEmpty(queryConfig.getSortField())) {

            String[] sortFields = queryConfig.getSortField().split(SparrowBackendConstant.SLASH_SEPARATOR);
            if (sortFields.length != 2) {
                throw new IllegalArgumentException("排序字段格式不正确");
            }
//            String realTableField = columnCommentMap.get(sortFields[0]);
//            if (StringUtil.isBlank(realTableField)) {
//                throw new IllegalArgumentException("字段:" + sortFields[0] + "没有对应的数据库字段");
//            }

            FrontendItemConfigBO frontendItemConfigBO = frontendItemConfigBOMap.get(sortFields[0]);
            if (frontendItemConfigBO == null) {
                throw new IllegalArgumentException("字段:" + sortFields[0] + "没有对应的数据库字段");
            }
            String realTableField = frontendItemConfigBO.getShowField();

            String description = OrderTypeEnum.getDescription(sortFields[1]);
            tableRuleDTO.setTableSort(String.format("order by %s %s", realTableField, description));
        }
        config = config.replace("${tableRule}", JSON.toJSONString(tableRuleDTO));
        config = config.replace("${outputType}", outputType.toString());
        backendVariablesVO.setValue(config);
        return backendVariablesVO;
    }

    private BackendVariablesVO multitablerQuery(String config, TableRuleDTO tableRuleDTO, QueryConfig queryConfig, BackendVariablesVO backendVariablesVO) {
        Map<String, String> columnCommentMap = new HashMap<>();
        String[] tables = queryConfig.getMultiTables().split(SparrowBackendConstant.COMMA_SEPARATOR);
        for (String table : tables) {
            List<ColumnVO> coulumns = DatabaseMetaDataUtil.getCoulumns(table);
            columnCommentMap.putAll(coulumns.stream().collect(Collectors.toMap(ColumnVO::getComment, ColumnVO::getColumnName, (v1, v2) -> v1)));
        }
        for (MultiTableQueryEnum value : MultiTableQueryEnum.values()) {
            columnCommentMap.put(value.getDesc(), value.getCode());
        }
        for (MultiTableQueryFieldEnum value : MultiTableQueryFieldEnum.values()) {
            columnCommentMap.put(value.getDesc(), value.getCode());
        }

        if (!StringUtil.isBlank(queryConfig.getTable())) {
            String table = replaceColumnsWithComments(queryConfig.getTable(), columnCommentMap);
            tableRuleDTO.setTableName(table);
        }


        String[] fields = queryConfig.getQueryFields().split(SparrowBackendConstant.COMMA_SEPARATOR);
        for (int i = 0; i < fields.length; i++) {
            fields[i] = replaceColumnsWithComments(fields[i], columnCommentMap);
        }
        tableRuleDTO.setTableField(String.join(",", fields));
        Integer outputType = 2;
        //'outputRule':{'outputType':3}，指定输出的类型，为三时 在查询那里代表要不要查询totalcount，以及按照分页查询格式返回。
        //一般不是分页查询的场景，这边固定为2，即为直接输出值
        if (queryConfig.isPagination()) {
            tableRuleDTO.setTablePage("pageDTO");
            outputType = 3;
            tableRuleDTO.setTableTotalCount(2);
        }
        if (StringUtil.isNotEmpty(queryConfig.getSortField())) {

            String[] sortFields = queryConfig.getSortField().split(SparrowBackendConstant.SLASH_SEPARATOR);
            if (sortFields.length != 2) {
                throw new IllegalArgumentException("排序字段格式不正确");
            }
            String realTableField = columnCommentMap.get(sortFields[0]);
            if (StringUtil.isBlank(realTableField)) {
                throw new IllegalArgumentException("字段:" + sortFields[0] + "没有对应的数据库字段");
            }

            String description = OrderTypeEnum.getDescription(sortFields[1]);
            tableRuleDTO.setTableSort(String.format("order by %s %s", realTableField, description));
        }
        config = config.replace("${tableRule}", JSON.toJSONString(tableRuleDTO));
        config = config.replace("${outputType}", outputType.toString());
        backendVariablesVO.setValue(config);
        return backendVariablesVO;
    }

    public static String replaceColumnsWithComments(String str, Map<String, String> columnCommentMap) {
        String result = str;
        for (Map.Entry<String, String> entry : columnCommentMap.entrySet()) {
            String column = entry.getKey();
            String comment = entry.getValue();
            result = result.replaceAll("\\b" + column + "\\b", comment);
        }
        return result;
    }

    private static String convertField(String field) {
        if (field.contains("_")) {
            field = field + " as " + SnakeToCamelUtil.toCamelCase(field);
        }
        return field;
    }


    @Data
    public static class TableRuleDTO {
        private String tableDataSource;
        private String tableName;
        private String tableField;
        private String tablePage;
        private String tableSort;
        private Integer tableTotalCount;
    }
}
