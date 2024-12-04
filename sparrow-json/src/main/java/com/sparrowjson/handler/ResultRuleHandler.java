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

import java.util.ArrayList;
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
@Component("resultRule")
public class ResultRuleHandler implements VariableHandler {


    @Override
    public BackendVariablesVO convertVariables(SparrowBackendConfigDTO sparrowBackendConfigDTO, MenuConfig menuConfig, BackConfig backConfig) {
        BackendVariablesVO backendVariablesVO = createBackendVariablesVO(sparrowBackendConfigDTO);

        QueryConfig queryConfig = (QueryConfig) backConfig;

        List<String> rpcList = queryConfig.getRpcList();
        if (CollectionUtils.isEmpty(rpcList)) {
            return backendVariablesVO;
        }
        List<TableQueryRpcDTO> resultRules = Lists.newArrayList();

        for (String rpcId : rpcList) {
            final String[] rpcSplit = rpcId.split(SparrowBackendConstant.SLASH_SEPARATOR);
            if (rpcSplit.length != 5) {
                throw new IllegalArgumentException("" + rpcSplit + "  配置错误");
            }
            TableQueryRpcDTO tableQueryRpcDTO = new TableQueryRpcDTO();
            tableQueryRpcDTO.setClazz(rpcSplit[0]);
            tableQueryRpcDTO.setMethod(rpcSplit[1]);
            tableQueryRpcDTO.setTransKey(rpcSplit[1]);
            tableQueryRpcDTO.setBoObjList(parseParameters(rpcSplit[2]));
            tableQueryRpcDTO.setRpcType(determineRpcTypes(tableQueryRpcDTO.getBoObjList()));
            resultRules.add(tableQueryRpcDTO);
        }
        backendVariablesVO.setValue(JSON.toJSONString(resultRules));

        return backendVariablesVO;
    }

    private static List<TableQueryRpcObjListDTO> parseParameters(String paramStr) {
        // 移除方括号并分割参数
        String content = paramStr.substring(1, paramStr.length() - 1);
        String[] params = content.split(",");

        List<TableQueryRpcObjListDTO> boObjList = new ArrayList<>();
        for (String param : params) {
            String[] parts = param.split(":");
            if (parts[0].equals("集合")) {
                TableQueryRpcObjListDTO boObj = new TableQueryRpcObjListDTO();
                boObj.setEntityItem("$[*]." + parts[2]);
                boObj.setListTransType(Integer.parseInt(parts[3]));
                boObj.setDistinctType(Integer.parseInt(parts[4]));
                boObjList.add(boObj);
            } else if (parts[0].equals("boolean")) {
                TableQueryRpcObjListDTO boObj = new TableQueryRpcObjListDTO();
                boObj.setConstantValue(Boolean.parseBoolean(parts[2]));
                boObjList.add(boObj);
            }
        }
        return boObjList;
    }


    private static List<String> determineRpcTypes(List<TableQueryRpcObjListDTO> boObjList) {
        List<String> rpcTypes = new ArrayList<>();
        for (TableQueryRpcObjListDTO boObj : boObjList) {
            if (boObj.getEntityItem() != null) {
                //如果有entityItem字段，说明是集合类型
                rpcTypes.add("java.util.List");
            } else if (boObj.getConstantValue() != null) {
                //根据常量值类型确定参数类型
                if (boObj.getConstantValue() instanceof Boolean) {
                    rpcTypes.add("java.lang.Boolean");
                } else if (boObj.getConstantValue() instanceof Integer) {
                    rpcTypes.add("java.lang.Integer");
                } else if (boObj.getConstantValue() instanceof String) {
                    rpcTypes.add("java.lang.String");
                } else if (boObj.getConstantValue() instanceof Long) {
                    rpcTypes.add("java.lang.Long");
                }
            }
        }
        return rpcTypes;
    }


    @Data
    public static class TableQueryRpcDTO {
        private String clazz;
        private String method;
        private List<String> rpcType;
        private String transKey;

        private List<TableQueryRpcObjListDTO> boObjList;
    }

    @Data
    public static class TableQueryRpcObjListDTO {
        private String entityItem;
        private Integer ListTransType;
        private Integer distinctType;
        private Object constantValue;

    }

}
