package com.sparrowjson.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparrowjson.vo.MenuConfig;
import com.sparrowjson.vo.unit.FrontendItemConfigBO;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 前后端模版构建
 */
public class BackAndFrontTemplateBuildUtils {

    /**
     * 构建后端的配置信息
     *
     * @param backList
     * @return
     */
    public MenuConfig buildBackendConfig(List<String> backList) {
        ConfigFileReader reader = new ConfigFileReader();
        MenuConfig config = new MenuConfig();
        Map<String, Integer> currentIndexMap = new HashMap<>();  // 记录当前处理的索引
        for (String line : backList) {
            // 解析基本信息
            if (line.startsWith("功能描述=")) {
                config.setFunctionDesc(line.split("=")[1]);
            } else if (line.startsWith("数据库=")) {
                config.setDatabase(line.split("=")[1]);
            } else if (line.startsWith("表=")) {
                config.setTable(line.split("=")[1]);
            } else if (line.startsWith("表字段=")) {
                config.setTableFields(reader.parseList(line.split("=")[1]));
            } else if (line.startsWith("新建表=")) {
                config.setTableFields(reader.parseList(line.split("=")[1]));
            }
            // 解析带索引的配置
            else {
                reader.parseIndexedConfig(line, config, currentIndexMap);
            }
        }
        return config;
    }


    /**
     * 构建前端配置信息
     *
     * @param frontendList
     * @return
     */
    public Object buildFrontendConfig(List<String> frontendList) {


        return null;
    }


    /**
     * 将当前的对象转成map
     */
    public static Map<String, String> changeToMap(List<String> list) {
        Map<String, String> map = new HashMap<>();

        for (String str : list) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = null;
            try {
                rootNode = objectMapper.readTree(str);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 判断是对象还是数组
            if (rootNode.isArray()) {
                //数组
                JSONArray jsonArray = JSONArray.parseArray(str);
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    for (String key : jsonObject.keySet()) {
                        map.put(key, (String) jsonObject.get(key));
                    }
                }


            } else if (rootNode.isObject()) {
                //对象
                JSONObject jsonObject = JSONObject.parseObject(str);
                for (String key : jsonObject.keySet()) {
                    map.put(key, (String) jsonObject.get(key));
                }

            }
        }

        return map;

    }



    /**
     * 将当前的对象转成map
     */
    public static Map<String, FrontendItemConfigBO> changeObjectToMap(List<String> list) {
        Map<String, FrontendItemConfigBO> map = new HashMap<>();

        for (String str : list) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = null;
            try {
                rootNode = objectMapper.readTree(str);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 判断是对象还是数组
            if (rootNode.isArray()) {
                //数组
                List<FrontendItemConfigBO> frontendItemConfigBOS = JSONArray.parseArray(str, FrontendItemConfigBO.class);
                for (FrontendItemConfigBO item : frontendItemConfigBOS) {
                    map.put(item.getName(), item);
                }

            } else if (rootNode.isObject()) {
                //对象
                FrontendItemConfigBO frontendItemConfigBO = JSON.parseObject(str, FrontendItemConfigBO.class);
                map.put(frontendItemConfigBO.getName(), frontendItemConfigBO);

            }
        }

        return map;

    }


    /**
     * @param targetJsonArrayStr 目标json
     * @param templateJsonArray  默认模版的json
     */
    public static Map<String, JSONArray> mergeDefaultTemplateToFront(String targetJsonArrayStr, JSONArray templateJsonArray) {

        JSONArray jsonArray = JSONArray.parseArray(targetJsonArrayStr);
        JSONArray result = new JSONArray();
        for (int i = 0; i < jsonArray.size(); i++) {

            FrontTemplateMergeUtils frontTemplateMergeUtils = new FrontTemplateMergeUtils();
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(jsonArray.get(i)));
            JSONObject itemResult = frontTemplateMergeUtils.mergeWithTemplates(templateJsonArray, jsonObject);
            result.add(itemResult);
        }

        //进行数据合并：
        Map<String, JSONArray> arrayMap = new HashMap<>();
        for (int i = 0; i < result.size(); i++) {
            JSONObject jsonObjectItem = (JSONObject) result.get(i);
            List<String> keySet = jsonObjectItem.keySet().stream().collect(Collectors.toList());
            String key = keySet.get(0);
            JSONObject itemObject = (JSONObject) jsonObjectItem.get(key);
            if (arrayMap.get(key) != null) {
                arrayMap.get(key).add(itemObject);
            } else {
                JSONArray jsonArray1 = new JSONArray();
                jsonArray1.add(itemObject);
                arrayMap.put(key, jsonArray1);
            }
        }

        return arrayMap;
    }
}
