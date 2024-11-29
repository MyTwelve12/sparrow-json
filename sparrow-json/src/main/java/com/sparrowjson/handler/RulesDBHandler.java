package com.sparrowjson.handler;

import com.alibaba.fastjson.JSON;
import com.sparrowjson.constant.SparrowBackendConstant;
import com.sparrowjson.dto.SparrowBackendConfigDTO;
import com.sparrowjson.enums.ConcatenateTypeEnum;
import com.sparrowjson.enums.MathOperationEnum;
import com.sparrowjson.enums.RuleTypeEnum;
import com.sparrowjson.enums.ValueTypeEnum;
import com.sparrowjson.util.StringUtil;
import com.sparrowjson.vo.BackConfig;
import com.sparrowjson.vo.BackendVariablesVO;
import com.sparrowjson.vo.MenuConfig;
import com.google.common.collect.Lists;
import lombok.Data;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * Company    : 上海黄豆网络科技有限公司
 *
 * @author : hll
 * Date       : 2024/11/7
 * Modify     : 修改日期          修改人员        修改说明          JIRA编号
 * v1.0.0       2024/11/7       hll    新增              1001
 ********************************************************************/
@Component("rulesDB")
public class RulesDBHandler implements VariableHandler {
    @Override
    public BackendVariablesVO convertVariables(SparrowBackendConfigDTO sparrowBackendConfigDTO, MenuConfig menuConfig, BackConfig backConfig) {
        BackendVariablesVO backendVariablesVO = createBackendVariablesVO(sparrowBackendConfigDTO);
        List<String> rulesDBList = backConfig.getRuleDB();
        List<RulesDBDTO> rulesDBDTOS = Lists.newArrayList();
        if (CollectionUtils.isEmpty(rulesDBList)) {
            return null;
        }

        Map<String, String> columnCommentMap = menuConfig.getColumnCommentMap();

        for (String rulesDB : rulesDBList) {
            if (StringUtil.isBlank(rulesDB)) {
                continue;
            }
            String[] onlyOneCheckFields = rulesDB.split(SparrowBackendConstant.SEMICOLON_SEPARATOR);
            //效验的字段
            if (onlyOneCheckFields.length != 2) {
                throw new RuntimeException(rulesDB + "=>配置错误");
            }
            RulesDBDTO rulesDBDTO = new RulesDBDTO();
            rulesDBDTO.setDatasourceName(menuConfig.getDatabase());
            rulesDBDTO.setTableName(menuConfig.getTable());
            //处理第一组数据，[{parentId:tid:=},{uid:tid:=}]
            String content = onlyOneCheckFields[0].replace(SparrowBackendConstant.LEFT_SQUARE_BRACKET_SEPARATOR, "")
                    .replace(SparrowBackendConstant.RIGHT_SQUARE_BRACKET_SEPARATOR, "");
            String[] groups = content.split(SparrowBackendConstant.COMMA_SEPARATOR);
            // 处理每一组数据，去除 "{" 和 "}" 并分隔出每个字段
            List<RulesDBFieldDTO> rulesDBFieldDTOS = Lists.newArrayList();
            for (String group : groups) {
                group = group.replace(SparrowBackendConstant.LEFT_CURLY_BRACE_SEPARATOR, "")
                        .replace(SparrowBackendConstant.RIGHT_CURLY_BRACE_SEPARATOR, "");
                String[] elements = group.split(SparrowBackendConstant.COLON_SEPARATOR);
                if (elements.length != 3) {
                    throw new RuntimeException(groups + "=>配置错误");
                }
                RulesDBFieldDTO rulesDBFieldDTO = new RulesDBFieldDTO();
                rulesDBFieldDTO.setSourceField(elements[0]);

                String realTableField = columnCommentMap.get(elements[1]);
                if (StringUtil.isBlank(realTableField)) {
                    throw new IllegalArgumentException("字段:" + elements[1] + "没有对应的数据库字段");
                }

                rulesDBFieldDTO.setTableField(realTableField);
                rulesDBFieldDTO.setCompare(elements[2]);
                rulesDBFieldDTOS.add(rulesDBFieldDTO);
            }
            rulesDBDTO.setField(rulesDBFieldDTOS);


            //处理第二组数据，[{order_num:计算:[{加/减:计算常量:1},{}]}|{}]
            String contentTransField = onlyOneCheckFields[1].substring(1, onlyOneCheckFields[1].length() - 1);
            String[] mainGroups = contentTransField.split(SparrowBackendConstant.PIPE_SEPARATOR);

            List<RulesDBTransFieldDTO> rulesDBTransFieldDTOS = Lists.newArrayList();
            //{order_num:计算:[{加/减:计算常量:1},{}]}
            for (String mainGroup : mainGroups) {
                RulesDBTransFieldDTO rulesDBTransFieldDTO = new RulesDBTransFieldDTO();
                String[] mainElements = mainGroup.substring(1, mainGroup.length() - 1)
                        .split(SparrowBackendConstant.COLON_SEPARATOR);
                if (mainElements.length != 3) {
                    throw new RuntimeException(mainGroup + "=>配置错误");
                }

                String realTableField = columnCommentMap.get(mainElements[0]);
                if (StringUtil.isBlank(realTableField)) {
                    throw new IllegalArgumentException("字段:" + mainElements[0] + "没有对应的数据库字段");
                }

                rulesDBTransFieldDTO.setTableItem(realTableField);
                rulesDBTransFieldDTO.setValueAlias(realTableField);

                String ruleType = mainElements[1];
                RuleTypeEnum codeByDescription = RuleTypeEnum.getCodeByDescription(ruleType);
                if (codeByDescription == null) {
                    throw new RuntimeException(ruleType + "=>配置未找到");
                }
                switch (codeByDescription) {
                    case CALCULATE:
                        rulesDBTransFieldDTO.setMathRule(buildMathRule(mainElements[2]));
                        break;
                    case CONCATENATE:
                        rulesDBTransFieldDTO.setConcatRule(buildConcatRule(mainElements[2], columnCommentMap));
                        break;
                }
                rulesDBTransFieldDTOS.add(rulesDBTransFieldDTO);
            }
            rulesDBDTO.setTransField(rulesDBTransFieldDTOS);
            rulesDBDTOS.add(rulesDBDTO);
        }
        backendVariablesVO.setValue(JSON.toJSONString(rulesDBDTOS));
        return backendVariablesVO;
    }

    private List<RulesDBConcatRuleDTO> buildConcatRule(String mainElement, Map<String, String> columnCommentMap) {
        //[{左拼接/右拼接,常量/变量,常量值:_/变量值:tid},{}]
        List<RulesDBConcatRuleDTO> concatRuleDTOS =  Lists.newArrayList();
        String[] mathElements = mainElement.replace(SparrowBackendConstant.LEFT_SQUARE_BRACKET_SEPARATOR, "")
                .replace(SparrowBackendConstant.RIGHT_SQUARE_BRACKET_SEPARATOR, "").split(SparrowBackendConstant.COMMA_SEPARATOR);
        for (String mathElement : mathElements) {
            String[] mathElementSplit = mathElement.replace(SparrowBackendConstant.LEFT_CURLY_BRACE_SEPARATOR, "")
                    .replace(SparrowBackendConstant.RIGHT_CURLY_BRACE_SEPARATOR, "").split(SparrowBackendConstant.SLASH_SEPARATOR);
            if (mathElementSplit.length != 3) {
                throw new RuntimeException(mathElement + "=>配置错误");
            }
            Integer codeByDescription = ConcatenateTypeEnum.getCodeByDescription(mathElementSplit[0]);
            if (codeByDescription == null) {
                throw new RuntimeException(mathElementSplit[0] + "=>配置未找到");
            }
            ValueTypeEnum codeValueType = ValueTypeEnum.getCodeByDescription(mathElementSplit[1]);
            if (codeValueType == null) {
                throw new RuntimeException(mathElementSplit[1] + "=>配置未找到");
            }
            RulesDBConcatRuleDTO rulesDBConcatRuleDTO = new RulesDBConcatRuleDTO();
            rulesDBConcatRuleDTO.setConcatLocation(codeByDescription);
            rulesDBConcatRuleDTO.setConcatType(codeValueType.getCode());
            if (codeValueType == ValueTypeEnum.CONSTANT) {
                if (NumberUtils.isNumber(mathElementSplit[2])) {
                    rulesDBConcatRuleDTO.setConcatConstant(Integer.parseInt(mathElementSplit[2]));
                } else {
                    rulesDBConcatRuleDTO.setConcatConstant(mathElementSplit[2]);
                }
            }
            if (codeValueType == ValueTypeEnum.VARIABLE) {
                RulesDBTransFieldConcatDTO concatVariable = new RulesDBTransFieldConcatDTO();

                String realTableField = columnCommentMap.get(mathElementSplit[2]);
                if (StringUtil.isBlank(realTableField)) {
                    throw new IllegalArgumentException("字段:" + mathElementSplit[2] + "没有对应的数据库字段");
                }

                concatVariable.setSourceField(realTableField);
                rulesDBConcatRuleDTO.setConcatVariable(concatVariable);
            }
            concatRuleDTOS.add(rulesDBConcatRuleDTO);
        }
        return concatRuleDTOS;
    }

    private List<RulesDBMathRuleDTO> buildMathRule(String mainElement) {
        //[{加/减:常量/变量:1},{}]
        List<RulesDBMathRuleDTO> mathRules = Lists.newArrayList();
        String[] mathElements = mainElement.replace(SparrowBackendConstant.LEFT_SQUARE_BRACKET_SEPARATOR, "")
                .replace(SparrowBackendConstant.RIGHT_SQUARE_BRACKET_SEPARATOR, "").split(SparrowBackendConstant.COMMA_SEPARATOR);
        for (String mathElement : mathElements) {
            String[] mathElementSplit = mathElement.replace(SparrowBackendConstant.LEFT_CURLY_BRACE_SEPARATOR, "")
                    .replace(SparrowBackendConstant.RIGHT_CURLY_BRACE_SEPARATOR, "").split(SparrowBackendConstant.SLASH_SEPARATOR);
            if (mathElementSplit.length != 3) {
                throw new RuntimeException(mathElement + "=>配置错误");
            }
            Integer codeByDescription = MathOperationEnum.getCodeByDescription(mathElementSplit[0]);
            if (codeByDescription == null) {
                throw new RuntimeException(mathElementSplit[0] + "=>配置未找到");
            }
            ValueTypeEnum codeValueType = ValueTypeEnum.getCodeByDescription(mathElementSplit[1]);
            if (codeValueType == null) {
                throw new RuntimeException(mathElementSplit[1] + "=>配置未找到");
            }
            RulesDBMathRuleDTO rulesDBMathRuleDTO = new RulesDBMathRuleDTO();
            rulesDBMathRuleDTO.setOperType(codeByDescription);
            rulesDBMathRuleDTO.setMathType(codeValueType.getCode());
            rulesDBMathRuleDTO.setMathConstant(Integer.parseInt(mathElementSplit[2]));
            mathRules.add(rulesDBMathRuleDTO);
        }
        return mathRules;
    }


    @Data
    public static class RulesDBDTO {
        private String datasourceName;
        private String tableName;
        private List<RulesDBFieldDTO> field;
        private List<RulesDBTransFieldDTO> transField;
    }

    @Data
    public static class RulesDBFieldDTO {
        private String sourceField;
        private String tableField;
        private String compare;
        private String connect = "AND";
    }

    @Data
    public static class RulesDBTransFieldDTO {
        private String tableItem;
        private String valueAlias;
        private String boObjKey = "tableUpdateDTO";
        private List<RulesDBConcatRuleDTO> concatRule;
        private List<RulesDBMathRuleDTO> mathRule;
    }

    @Data
    public static class RulesDBConcatRuleDTO {
        private Integer concatLocation;
        private Integer concatType;
        private Object concatConstant;
        private RulesDBTransFieldConcatDTO concatVariable;
    }

    @Data
    public static class RulesDBMathRuleDTO {
        private Integer operType;
        private Integer mathConstant;
        private Integer mathType;
    }

    @Data
    public static class RulesDBTransFieldConcatDTO {
        private String sourceField;
        private String boObjKey = "tableUpdateDTO";
        private Integer transTargetType = 1;
    }
}
