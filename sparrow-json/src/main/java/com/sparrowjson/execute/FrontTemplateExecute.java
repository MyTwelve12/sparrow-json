package com.sparrowjson.execute;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sparrowjson.vo.FrontendVariablesVO;

import java.util.List;

public interface FrontTemplateExecute {

    /**
     * 获取模版的类型
     */
    String getTemplateCode();

    /**
     * 构建模版内容
     */
    void buildTemplateData();


    /**
     * 添加或删除特殊的行
     *
     * @param currentLine 当前行
     * @param lineList    所有的行
     */
    void dealSpecialLine(String currentLine, List<String> lineList);


    /**
     * @param jsonStr                文件json
     * @param templateArray          前端默认组件
     * @param fixedValueJSONArray    固定参数
     * @param changedValueJSONObject 可变参数
     */
    List<FrontendVariablesVO> combineFixedTemplate(String jsonStr, JSONArray templateArray, JSONArray fixedValueJSONArray, JSONObject changedValueJSONObject);


}
