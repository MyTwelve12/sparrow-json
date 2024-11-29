package com.sparrowjson.handler;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sparrowjson.constant.SparrowBackendConstant;
import com.sparrowjson.database.DatabaseMetaDataUtil;
import com.sparrowjson.dto.SparrowBackendConfigDTO;
import com.sparrowjson.util.SnakeToCamelUtil;
import com.sparrowjson.vo.BackConfig;
import com.sparrowjson.vo.BackendVariablesVO;
import com.sparrowjson.vo.ColumnVO;
import com.sparrowjson.vo.InsertConfig;
import com.sparrowjson.vo.MenuConfig;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

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
@Component("insertParam")
public class InsertParamHandler implements VariableHandler {


    @Override
    public BackendVariablesVO convertVariables(SparrowBackendConfigDTO sparrowBackendConfigDTO, MenuConfig menuConfig, BackConfig backConfig) {
        BackendVariablesVO backendVariablesVO = createBackendVariablesVO(sparrowBackendConfigDTO);

        String config = sparrowBackendConfigDTO.getConfig();
        InsertConfig insertConfig = (InsertConfig) backConfig;

        String insertFieldStr = insertConfig.getInsertFields();
        if (StringUtils.isBlank(insertFieldStr)) {
            return backendVariablesVO;
        }
        String tableName = insertConfig.getTableName();

        String[] insertFields = insertFieldStr.split(SparrowBackendConstant.COMMA_SEPARATOR);

        Map<String, String> columnCommentMap = menuConfig.getColumnCommentMap();
        if (tableName != null && !Objects.equals(tableName, menuConfig.getTable())) {
            List<ColumnVO> coulumns = DatabaseMetaDataUtil.getCoulumns(tableName);
            columnCommentMap = coulumns.stream().collect(Collectors.toMap(ColumnVO::getComment, ColumnVO::getColumnName, (v1, v2) -> v1));
        }

        List<TableInsertDTO> tableInsertDTOS = Lists.newArrayList();
        Map<String, Integer> tableFieldIndex = Maps.newHashMap();
        for (String tableField : insertFields) {
            TableInsertDTO tableInsertDTO = new TableInsertDTO();
            String realTableField = columnCommentMap.get(tableField);
            if (StringUtils.isBlank(realTableField)) {
                throw new IllegalArgumentException("字段:" + tableField + "没有对应的数据库字段");
            }
            tableInsertDTO.setTableField(realTableField);
            String insertFieldSuffix = "";
            //相同字段-拼接后缀
            if (tableFieldIndex.containsKey(realTableField)) {
                tableFieldIndex.put(realTableField, tableFieldIndex.get(realTableField) + 1);
                insertFieldSuffix = "_" + tableFieldIndex.get(realTableField);
            } else {
                tableFieldIndex.put(realTableField, 0);
            }
            tableInsertDTO.setInsertField(SnakeToCamelUtil.toCamelCase(realTableField) + insertFieldSuffix);

            tableInsertDTOS.add(tableInsertDTO);
        }
        config = config.replace("${insertParam}", JSON.toJSONString(tableInsertDTOS));
        backendVariablesVO.setValue(config);

        return backendVariablesVO;
    }

    @Data
    public static class TableInsertDTO {
        private String tableField;
        private String insertField;
    }

}
