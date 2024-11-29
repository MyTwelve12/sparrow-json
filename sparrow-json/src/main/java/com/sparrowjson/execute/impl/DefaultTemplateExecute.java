package com.sparrowjson.execute.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sparrowjson.execute.FrontTemplateExecute;
import com.sparrowjson.util.BackAndFrontTemplateBuildUtils;
import com.sparrowjson.vo.FrontendVariablesVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class DefaultTemplateExecute implements FrontTemplateExecute {

    /**
     * 需要特殊处理的字段行
     */
    public static List<String> SPECIAL_COLUMNS_NAME = Arrays.asList("过滤(object)：", "字段展示(object)：");

    /**
     *
     */
    public final static List<String> OPERATION_LIST = Arrays.asList("operation", "columns", "filter", "actions");

    @Override
    public String getTemplateCode() {
        return "1";
    }

    @Override
    public void buildTemplateData() {

    }

    @Override
    public void dealSpecialLine(String currentLine, List<String> lineList) {
        String s = currentLine.replace("+", "");
        if (SPECIAL_COLUMNS_NAME.contains(s)) {
            Integer size = currentLine.length() - s.length();
            String addStr = "值数组(array)：\n";
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < size + 1; i++) {
                stringBuffer.append("+");
            }
            lineList.add(stringBuffer.append(addStr).toString());
        }
    }

    /**
     * @param jsonStr                文件json
     * @param templateArray          前端默认组件
     * @param fixedValueJSONArray    固定参数
     * @param changedValueJSONObject 可变参数
     */
    @Override
    public List<FrontendVariablesVO> combineFixedTemplate(String jsonStr, JSONArray templateArray, JSONArray fixedValueJSONArray, JSONObject changedValueJSONObject) {

        Map<String, JSONArray> resultMap = BackAndFrontTemplateBuildUtils.mergeDefaultTemplateToFront(jsonStr, templateArray);


        List<FrontendVariablesVO> frontVariableBOS = new ArrayList<>();
        if (fixedValueJSONArray != null && fixedValueJSONArray.size() > 0) {
            frontVariableBOS = JSONArray.parseArray(fixedValueJSONArray.toJSONString(), FrontendVariablesVO.class);
        }
        for (String str : OPERATION_LIST) {
            Object jsonObject = changedValueJSONObject.get(str);
            FrontendVariablesVO frontendVariablesVO = JSON.parseObject(JSON.toJSONString(jsonObject), FrontendVariablesVO.class);
            if (resultMap.get(str) != null) {
                frontendVariablesVO.setValue(JSON.toJSONString(resultMap.get(str)));
            }
            frontVariableBOS.add(frontendVariablesVO);
        }


        return frontVariableBOS;
    }
}
