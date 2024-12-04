package com.sparrowjson.handler;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.sparrowjson.constant.SparrowBackendConstant;
import com.sparrowjson.dto.SparrowBackendConfigDTO;
import com.sparrowjson.vo.BackConfig;
import com.sparrowjson.vo.BackendVariablesVO;
import com.sparrowjson.vo.MenuConfig;
import com.sparrowjson.vo.QueryConfig;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;


/**
 * Description:
 * Company    : 上海黄豆网络科技有限公司
 *
 * @author : hll
 * Date       : 2024/11/7
 * Modify     : 修改日期          修改人员        修改说明          JIRA编号
 * v1.0.0       2024/11/7       hll    新增              1001
 ********************************************************************/
@Component("fillFieldByTransObj")
public class FillFieldByTransObjHandler implements VariableHandler {


    @Override
    public BackendVariablesVO convertVariables(SparrowBackendConfigDTO sparrowBackendConfigDTO, MenuConfig menuConfig, BackConfig backConfig) {
        BackendVariablesVO backendVariablesVO = createBackendVariablesVO(sparrowBackendConfigDTO);

        QueryConfig queryConfig = (QueryConfig) backConfig;

        List<String> rpcList = queryConfig.getRpcList();
        if (CollectionUtils.isEmpty(rpcList)) {
            return null;
        }
        List<TableQueryRpcDTO> resultRules = Lists.newArrayList();

        for (String rpcId : rpcList) {
            final String[] rpcSplit = rpcId.split(SparrowBackendConstant.SLASH_SEPARATOR);
            if (rpcSplit.length != 5) {
                throw new IllegalArgumentException("" + rpcSplit + "  配置错误");
            }
            TableQueryRpcDTO tableQueryRpcDTO = new TableQueryRpcDTO();
            tableQueryRpcDTO.setTransKey(rpcSplit[1]);

            List<FillKeyArrayDTO> arrayDTOS = Lists.newArrayList();
            FillKeyArrayDTO fillRule = parseFillRule(rpcSplit[3], rpcSplit[4]);
            arrayDTOS.add(fillRule);
            tableQueryRpcDTO.setFillKeyArray(arrayDTOS);

            resultRules.add(tableQueryRpcDTO);
        }
        backendVariablesVO.setValue(JSON.toJSONString(resultRules));

        return backendVariablesVO;
    }

    private static FillKeyArrayDTO parseFillRule(String mappingStr, String fieldPath) {
        // 解析 [userId:userNo:2] 格式的映射规则
        String[] mapping = mappingStr.substring(1, mappingStr.length() - 1).split(":");

        FillKeyArrayDTO fillRule = new FillKeyArrayDTO();
        ListFilterRuleDTO filterRule = new ListFilterRuleDTO();
        filterRule.setSourceFieldKey(mapping[1]);
        filterRule.setTargetFieldKey(mapping[0]);
        filterRule.setTargetFieldTransType(Integer.parseInt(mapping[2]));

        fillRule.setListFilterRule(filterRule);
        fillRule.setEntityItem("$[0]." + fieldPath);
        fillRule.setValueAlias(fieldPath.substring(fieldPath.lastIndexOf(".") + 1));
        fillRule.setTargetDefaultValue("");

        return fillRule;
    }





    @Data
    public static class TableQueryRpcDTO {
        private String transKey;
        private List<FillKeyArrayDTO> fillKeyArray;
    }

    @Data
    public static class FillKeyArrayDTO {
        private String entityItem;
        private String valueAlias;
        private String targetDefaultValue;
        private ListFilterRuleDTO listFilterRule;
    }

    @Data
    public static class ListFilterRuleDTO {
        private String sourceFieldKey;
        private String targetFieldKey;
        private Integer targetFieldTransType;
    }

}
