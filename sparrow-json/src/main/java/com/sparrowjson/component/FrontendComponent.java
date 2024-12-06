package com.sparrowjson.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sparrowjson.constant.SparrowBackendConstant;
import com.sparrowjson.dto.FrontendConfigDTO;
import com.sparrowjson.execute.FrontTemplateExecute;
import com.sparrowjson.execute.FrontTemplateExecuteManager;
import com.sparrowjson.mapper.FrontendConfigMapper;
import com.sparrowjson.util.BackAndFrontTemplateBuildUtils;
import com.sparrowjson.util.FrontParamsBuilder;
import com.sparrowjson.vo.FrontendPageConfigVO;
import com.sparrowjson.vo.FrontendVO;
import com.sparrowjson.vo.FrontendVariablesVO;
import com.sparrowjson.vo.unit.FrontendItemConfigBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
public class FrontendComponent {

    @Autowired
    private FrontendConfigMapper frontendConfigMapper;

    @Autowired
    private FrontTemplateExecuteManager frontTemplateExecuteManager;


    /**
     * @param templateLines 文件数据
     * @param templateCode  模版code
     */
    public FrontendVO buildFrontTemplateInfo(List<String> templateLines, String templateCode, String functionDesc) {

        String name = "";

        String description = "";


        if (!CollectionUtils.isEmpty(templateLines)) {
            //获取所有的前端的配置信息

            List<FrontendConfigDTO> frontendConfigDTOS = frontendConfigMapper.listByFunctionName(functionDesc);
            if (CollectionUtils.isEmpty(frontendConfigDTOS)) {
                //未配置解析参数
                log.info("请检查当前前端，当前前端的功能未配置");
                return null;
            }


            FrontTemplateExecute frontTemplateExecute = frontTemplateExecuteManager.getExecute(templateCode);
            if (frontTemplateExecute == null) {
                log.info("未找到当前的前端模版功能的执行器");
                //未找到处理器
                return null;
            }
            //todo 数组对象替换 例如：原本的是 主体，需要将主体替换成(object),不然在解析的时候无法知道填的到底是对象还是数组
            FrontendConfigDTO objectAndArray = frontendConfigDTOS.stream().filter(item -> Objects.equals(item.getType(), 1)).findFirst().orElse(null);
            //需要将确定将当前文本转成对象或者数组文本行
            Map<String, String> objectAndArrayMap = new HashMap<>();
            if (objectAndArray != null && !StringUtils.isEmpty(objectAndArray.getValue())) {
                objectAndArrayMap = BackAndFrontTemplateBuildUtils.changeToMap(Arrays.asList(objectAndArray.getValue()));
            }

            //todo 枚举常量替换 例如 【层级枚举】【显示类型枚举】【来源枚举】
            FrontendConfigDTO constantsEnumsArrays = frontendConfigDTOS.stream().filter(item -> Objects.equals(item.getType(), 8)).findFirst().orElse(null);
            Map<String, String> constantsEnumsHashMap = new HashMap<>();
            if (objectAndArray != null && !StringUtils.isEmpty(constantsEnumsArrays.getValue())) {
                constantsEnumsHashMap = BackAndFrontTemplateBuildUtils.changeToMap(Arrays.asList(constantsEnumsArrays.getValue()));
            }

            //todo 常量枚举的替换，例如用户实际填的是中文描述，我们现在需要将中文的描述转成相关的英文的描述
            List<FrontendConfigDTO> commonConfigList = frontendConfigDTOS.stream().filter(item -> Arrays.asList(2, 3, 4, 7).contains(item.getType())).collect(Collectors.toList());
            Map<String, FrontendItemConfigBO> commonConstantMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(commonConfigList)) {
                List<String> valueList = commonConfigList.stream().map(item -> item.getValue()).collect(Collectors.toList());
                commonConstantMap = BackAndFrontTemplateBuildUtils.changeObjectToMap(valueList);
            }

            //todo 默认前端组件模版比如输入组件input-text button ...
            FrontendConfigDTO defaultFrontTemplate = frontendConfigDTOS.stream().filter(item -> Objects.equals(item.getType(), 9)).findFirst().orElse(null);
            JSONArray defaultJSONFrontTemplate = new JSONArray();
            if (defaultFrontTemplate != null) {
                defaultJSONFrontTemplate = JSONArray.parseArray(defaultFrontTemplate.getValue());
            }

            //todo 模版中的固定的变量 例如：{"name":"primaryField","type":1,"desc":"主键key","value":"tid"}是固定的变量
            FrontendConfigDTO fixedValue = frontendConfigDTOS.stream().filter(item -> Objects.equals(item.getType(), 6)).findFirst().orElse(null);
            JSONArray fixedValueJSONArray = new JSONArray();
            if (fixedValue != null) {
                fixedValueJSONArray = JSONArray.parseArray(fixedValue.getValue());
            }
            //todo 模版中的可变的参数 {"name":"operation","type":1,"desc":"主键key","value":"?"}是固定的变量
            FrontendConfigDTO changedValue = frontendConfigDTOS.stream().filter(item -> Objects.equals(item.getType(), 5)).findFirst().orElse(null);
            JSONObject changedValueJSONObject = new JSONObject();
            if (changedValue != null) {
                changedValueJSONObject = JSONArray.parseObject(changedValue.getValue());
            }


            List<String> lineList = new ArrayList<>();
            //初始化一下
            String[] lines = new String[0];

            for (int i = 0; i < templateLines.size(); i++) {

                //当前行
                String currentLine = templateLines.get(i);

                if (i < 2) {
                    //第一第二行是标题和描述需要过滤
                    if (i == 0) {
                        name = currentLine.replace(SparrowBackendConstant.FRONT_PAGE_CONFIG_NAME, "");
                    } else if (i == 1) {
                        description = currentLine.replace(SparrowBackendConstant.FRONT_PAGE_CONFIG_DESC, "");
                    }
                    continue;
                }

                for (String keyItem : objectAndArrayMap.keySet()) {
                    currentLine = currentLine.replace(keyItem, objectAndArrayMap.get(keyItem));
                }
                lineList.add(currentLine);
                frontTemplateExecute.dealSpecialLine(currentLine, lineList);
            }
            lines = new String[lineList.size()];
            for (int i = 0; i < lineList.size(); i++) {
                lines[i] = lineList.get(i);
            }


            FrontParamsBuilder frontParamsBuilder = new FrontParamsBuilder();
            frontParamsBuilder.setLines(lines);
            frontParamsBuilder.setConstantsEnumsHashMap(constantsEnumsHashMap);
            frontParamsBuilder.setHashMap(commonConstantMap);

            String jsonStr = frontParamsBuilder.parseText();

            System.out.println("前端数据转化后的json: " + jsonStr);

            List<FrontendVariablesVO> list = frontTemplateExecute.combineFixedTemplate(jsonStr, defaultJSONFrontTemplate, fixedValueJSONArray, changedValueJSONObject);


            FrontendVO frontendVO = new FrontendVO();

            FrontendPageConfigVO pageConfigVO = new FrontendPageConfigVO();
            pageConfigVO.setName(name);
            pageConfigVO.setDescription(description);
            frontendVO.setPageConfig(pageConfigVO);
            frontendVO.setVariables(list);

            System.out.println("完整的前端json:  " + JSON.toJSONString(frontendVO));

            return frontendVO;
        }

        return null;
    }


}
