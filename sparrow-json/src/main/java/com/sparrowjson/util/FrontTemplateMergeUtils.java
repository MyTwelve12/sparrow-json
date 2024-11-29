package com.sparrowjson.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FrontTemplateMergeUtils {


    private final Map<String, JSONObject> templateMap = new HashMap<>();
    private static final String TYPE_FIELD = "type";
    private static final String TEMPLATE_CODE_FIELD = "templateCode";
    private static final String DEFAULT_TEMPLATE = "默认";

    public JSONObject mergeWithTemplates(JSONArray templates, JSONObject target) {
        buildTemplateMap(templates);
        return processObject(target);
    }

    private void buildTemplateMap(JSONArray templates) {
        for (int i = 0; i < templates.size(); i++) {
            JSONObject template = templates.getJSONObject(i);
            String type = template.getString(TYPE_FIELD);
            String templateCode = template.getString(TEMPLATE_CODE_FIELD);
            String key = buildKey(type, templateCode);
            templateMap.put(key, (JSONObject) deepClone(template));
        }
    }

    private String buildKey(String type, String templateCode) {
        return type + ":" + (templateCode != null ? templateCode : DEFAULT_TEMPLATE);
    }

    private JSONObject processObject(JSONObject target) {
        if (target == null) return null;

        JSONObject result = new JSONObject(true);

        // 处理当前对象
        String type = target.getString(TYPE_FIELD);
        if (type != null) {
            // 查找并合并模板
            JSONObject template = findMatchingTemplate(type, target.getString(TEMPLATE_CODE_FIELD));
            if (template != null) {
                result = mergeWithTemplate(template, target);
            } else {
                // 复制除了templateCode之外的所有字段
                for (Map.Entry<String, Object> entry : target.entrySet()) {
                    String key = entry.getKey();
                    if (!TEMPLATE_CODE_FIELD.equals(key)) {
                        result.put(key, entry.getValue());
                    }
                }
            }
        } else {
            // 复制除了templateCode之外的所有字段
            for (Map.Entry<String, Object> entry : target.entrySet()) {
                String key = entry.getKey();
                if (!TEMPLATE_CODE_FIELD.equals(key)) {
                    result.put(key, entry.getValue());
                }
            }
        }

        // 递归处理所有子对象
        JSONObject processedResult = new JSONObject(true);
        for (Map.Entry<String, Object> entry : result.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof JSONObject) {
                processedResult.put(key, processObject((JSONObject) value));
            } else if (value instanceof JSONArray) {
                processedResult.put(key, processArray((JSONArray) value));
            } else {
                processedResult.put(key, value);
            }
        }

        return processedResult;
    }

    private JSONArray processArray(JSONArray array) {
        if (array == null) {
            return null;
        }

        JSONArray result = new JSONArray();
        for (int i = 0; i < array.size(); i++) {
            Object item = array.get(i);
            if (item instanceof JSONObject) {
                result.add(processObject((JSONObject) item));
            } else if (item instanceof JSONArray) {
                result.add(processArray((JSONArray) item));
            } else {
                result.add(item);
            }
        }
        return result;
    }

    private JSONObject findMatchingTemplate(String type, String templateCode) {
        if (type == null) {
            return null;
        }

        // 先尝试用指定的templateCode查找
        if (templateCode != null) {
            JSONObject template = templateMap.get(buildKey(type, templateCode));
            if (template != null) {
                return (JSONObject) deepClone(template);
            }
        }

        // 使用默认模板
        JSONObject defaultTemplate = templateMap.get(buildKey(type, DEFAULT_TEMPLATE));
        return defaultTemplate != null ? (JSONObject) deepClone(defaultTemplate) : null;
    }

    private JSONObject mergeWithTemplate(JSONObject template, JSONObject target) {
        JSONObject result = new JSONObject(true);

        // 1. 复制模板中的所有字段
        for (Map.Entry<String, Object> entry : template.entrySet()) {
            String key = entry.getKey();
            if (!TYPE_FIELD.equals(key) && !TEMPLATE_CODE_FIELD.equals(key)) {
                result.put(key, deepClone(entry.getValue()));
            }
        }

        // 2. 用目标对象的字段覆盖或补充（除了templateCode）
        for (Map.Entry<String, Object> entry : target.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (!TEMPLATE_CODE_FIELD.equals(key)) {
                if (value instanceof JSONObject && result.containsKey(key)) {
                    result.put(key, mergeWithTemplate((JSONObject)result.get(key), (JSONObject)value));
                } else {
                    result.put(key, deepClone(value));
                }
            }
        }

        // 3. 保留原始的type
        result.put(TYPE_FIELD, target.getString(TYPE_FIELD));

        return result;
    }

    private Object deepClone(Object obj) {
        if (obj == null) {
            return null;
        }

        if (obj instanceof JSONObject) {
            JSONObject source = (JSONObject) obj;
            JSONObject clone = new JSONObject(true);
            for (Map.Entry<String, Object> entry : source.entrySet()) {
                clone.put(entry.getKey(), deepClone(entry.getValue()));
            }
            return clone;
        } else if (obj instanceof JSONArray) {
            JSONArray source = (JSONArray) obj;
            JSONArray clone = new JSONArray();
            for (int i = 0; i < source.size(); i++) {
                clone.add(deepClone(source.get(i)));
            }
            return clone;
        } else {
            // 对于基本类型和字符串，直接返回
            return obj;
        }
    }
}
