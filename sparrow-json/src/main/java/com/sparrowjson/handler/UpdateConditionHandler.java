package com.sparrowjson.handler;

import com.alibaba.fastjson.JSON;
import com.sparrowjson.constant.SparrowBackendConstant;
import com.sparrowjson.database.DatabaseMetaDataUtil;
import com.sparrowjson.dto.SparrowBackendConfigDTO;
import com.sparrowjson.util.SnakeToCamelUtil;
import com.sparrowjson.util.StringUtil;
import com.sparrowjson.vo.BackConfig;
import com.sparrowjson.vo.BackendVariablesVO;
import com.sparrowjson.vo.ColumnVO;
import com.sparrowjson.vo.DeleteConfig;
import com.sparrowjson.vo.MenuConfig;
import com.sparrowjson.vo.UpdateConfig;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
@Component("updateCondition")
public class UpdateConditionHandler implements VariableHandler {
    @Override
    public BackendVariablesVO convertVariables(SparrowBackendConfigDTO sparrowBackendConfigDTO, MenuConfig menuConfig, BackConfig backConfig) {
        BackendVariablesVO backendVariablesVO = createBackendVariablesVO(sparrowBackendConfigDTO);

        String config = sparrowBackendConfigDTO.getConfig();
        String insertFieldStr = null;
        String tableName = null;
        if (backConfig instanceof UpdateConfig){
            UpdateConfig updateConfig = (UpdateConfig) backConfig;
            insertFieldStr = updateConfig.getConditions();
            tableName = updateConfig.getTableName();
        }
        if (backConfig instanceof DeleteConfig){
            DeleteConfig deleteConfig = (DeleteConfig) backConfig;
            insertFieldStr = deleteConfig.getConditions();
            tableName = deleteConfig.getTableName();
        }
        if (StringUtil.isBlank(insertFieldStr)) {
            return backendVariablesVO;
        }


        Map<String, String> columnCommentMap = menuConfig.getColumnCommentMap();
        if (tableName != null && !Objects.equals(tableName, menuConfig.getTable())) {
            List<ColumnVO> coulumns = DatabaseMetaDataUtil.getCoulumns(tableName);
            columnCommentMap = coulumns.stream().collect(Collectors.toMap(ColumnVO::getComment, ColumnVO::getColumnName, (v1, v2) -> v1));
        }

        //前端配置模版
        Map<String, FrontendItemConfigBO> frontendItemConfigBOMap = new HashMap<>();

        String[] insertFields = insertFieldStr.split(SparrowBackendConstant.COMMA_SEPARATOR);

        List<UpdateConditionDTO> tableInsertDTOS = Lists.newArrayList();
        Map<String, Integer> tableFieldIndex = Maps.newHashMap();
        for (String tableField : insertFields) {
            UpdateConditionDTO tableInsertDTO = new UpdateConditionDTO();
            if (tableField.contains(SparrowBackendConstant.SLASH_SEPARATOR)){
                String[] tableFieldLinker = tableField.split(SparrowBackendConstant.SLASH_SEPARATOR);
                tableField = tableFieldLinker[0];
                tableInsertDTO.setLinker(tableFieldLinker[1]);
            }
            String realTableField = columnCommentMap.get(tableField);
            if (StringUtil.isBlank(realTableField)) {
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

            tableInsertDTO.setUpdateField(SnakeToCamelUtil.toCamelCase(realTableField) + insertFieldSuffix);
            tableInsertDTOS.add(tableInsertDTO);
        }
        config = config.replace("${updateCondition}", JSON.toJSONString(tableInsertDTOS));
        backendVariablesVO.setValue(config);

        return backendVariablesVO;
    }

    @Data
    public static class UpdateConditionDTO {
        private String tableField;
        private String updateField;

        private String linker;
    }
}
