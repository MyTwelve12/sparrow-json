package com.sparrowjson.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sparrowjson.vo.unit.FrontendItemConfigBO;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FrontParamsBuilder {

    /**
     * 当前位置
     */
    private int currentIndex = 0;
    /**
     * 数据
     */
    private String[] lines;


    /**
     * 映射字段
     */
    private Map<String, FrontendItemConfigBO> hashMap = new HashMap<>();

    /**
     * 常量枚举映射
     */
    private Map<String, String> constantsEnumsHashMap = new HashMap<>();

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public String[] getLines() {
        return lines;
    }

    public void setLines(String[] lines) {
        this.lines = lines;
    }


    public Map<String, FrontendItemConfigBO> getHashMap() {
        return hashMap;
    }

    public void setHashMap(Map<String, FrontendItemConfigBO> hashMap) {
        this.hashMap = hashMap;
    }

    public Map<String, String> getConstantsEnumsHashMap() {
        return constantsEnumsHashMap;
    }

    public void setConstantsEnumsHashMap(Map<String, String> constantsEnumsHashMap) {
        this.constantsEnumsHashMap = constantsEnumsHashMap;
    }

    public String parseText() {
        JSONArray root = new JSONArray();

        while (currentIndex < lines.length) {
            if (getLevel(lines[currentIndex]) == 1) {
                root.add(parseObject(1));
            } else {
                currentIndex++;
            }
        }

        return root.toJSONString();
    }


    private JSONObject parseObject(int level) {
        JSONObject obj = new JSONObject();
        String currentLine = lines[currentIndex].trim();

        // 解析当前行的key和type
        String content = currentLine.substring(countLeadingDash(currentLine)).trim();
        String[] parts = content.split("：", 2);
        String keyPart = parts[0].trim();

        // 解析key和type
        //解析判断当前的是数组还是object
        String key = keyPart;
        if (keyPart.contains("(") && keyPart.contains(")")) {
            int startIndex = keyPart.indexOf("(");
            int endIndex = keyPart.indexOf(")");
            key = keyPart.substring(0, startIndex).trim();
        }
        if (Objects.equals(key, "过滤(object)") || Objects.equals(key, "过滤")) {
            System.out.println("=======>");
        }

        currentIndex++;

        // 处理子元素
        while (currentIndex < lines.length) {
            String nextLine = lines[currentIndex].trim();
            if (nextLine.isEmpty()) {
                currentIndex++;
                continue;
            }

            int nextLevel = getLevel(lines[currentIndex]);
            if (nextLevel <= level) {
                break;
            }

            // 解析子元素
            String childContent = nextLine.substring(countLeadingDash(nextLine)).trim();
            if (childContent.contains("：")) {
                String[] childParts = childContent.split("：", 2);
                String childKey = childParts[0].trim();
                //为了解析判断当前的是数组还是object

//                String mappedChildKey = hashMap.getOrDefault(childKey, childKey);
                String mappedChildKey = getDefaultMapValue(childKey, childKey, false);
                String childValue = childParts.length > 1 ? childParts[1].trim() : "";

                // 检查是否是对象或数组声明
                if (childKey.contains("(")) {
                    int startIndex = childKey.indexOf("(");
                    int endIndex = childKey.indexOf(")");
                    String childType = childKey.substring(startIndex + 1, endIndex).trim().toLowerCase();
                    String realChildKey = childKey.substring(0, startIndex).trim();
//                    String mappedRealChildKey = hashMap.getOrDefault(realChildKey, realChildKey);
                    String mappedRealChildKey = getDefaultMapValue(realChildKey, realChildKey, false);

                    if ("array".equals(childType)) {
                        obj.put(mappedRealChildKey, parseArray(nextLevel));
                    } else if ("object".equals(childType)) {
                        obj.put(mappedRealChildKey, parseObject(nextLevel));
                    }
                } else if (childValue.contains(" ") && !childValue.startsWith("\"")) {
                    // 检查是否在object内部
                    boolean isInsideObject = isInsideObject(nextLevel);
                    if (isInsideObject) {
                        // 将多属性行作为当前对象的属性
                        String[] properties = childValue.split(" ");
                        for (String prop : properties) {
                            if (prop.contains("：")) {
                                String[] propParts = prop.split("：", 2);
                                String propKey = propParts[0].trim();
                                String propValue = propParts[1].trim();
//                                String mappedPropKey = hashMap.getOrDefault(propKey, propKey);
                                String mappedPropKey = getDefaultMapValue(propKey, propKey, false);
//                                String mappedPropValue = hashMap.getOrDefault(propValue, propValue);

                                String mappedPropValue = getDefaultMapValue(propValue, propValue, true);
                                obj.put(mappedPropKey, mappedPropValue);
                            } else {
                                //todo 处理上层是object,下层是多个元素的场景处理第一个元素
                                //String newChildKey = hashMap.get(childKey) != "" ? hashMap.get(childKey) : childKey;
                                String newChildKey = getDefaultMapValue(childKey, childKey, false);
                                obj.put(newChildKey, prop);
                            }
                        }
                        currentIndex++;
                    } else {
                        // 创建新的对象存储多属性
                        JSONObject childObj = new JSONObject();
                        String[] properties = childValue.split(" ");
                        for (String prop : properties) {
                            if (prop.contains("：")) {
                                String[] propParts = prop.split("：", 2);
                                String propKey = propParts[0].trim();
                                String propValue = propParts[1].trim();
                                //String mappedPropKey = hashMap.getOrDefault(propKey, propKey);

                                String mappedPropKey = getDefaultMapValue(propKey, propKey, false);
                                // String mappedPropValue = hashMap.getOrDefault(propValue, propValue);
                                String mappedPropValue = getDefaultMapValue(propValue, propValue, true);

                                childObj.put(mappedPropKey, mappedPropValue);
                            }
                        }
                        obj.put(mappedChildKey, childObj);
                        currentIndex++;
                    }
                } else if (childValue.isEmpty()) {
                    obj.put(mappedChildKey, parseObject(nextLevel));
                } else {
//                    String mappedValue = hashMap.getOrDefault(childValue, childValue);
                    String mappedValue = getDefaultMapValue(childValue, childValue,true);
                    obj.put(mappedChildKey, mappedValue);
                    currentIndex++;
                }
            } else {
                currentIndex++;
            }
        }

        // 只在最外层包装对象
        if (level == 1) {
            JSONObject result = new JSONObject();
//            String mappedKey = hashMap.getOrDefault(key, key);
            String mappedKey = getDefaultMapValue(key, key, false);
            result.put(mappedKey, obj);
            return result;
        }

        return obj;
    }

    private void processProperty(JSONObject obj, String property) {
        if (property.contains("：")) {
            String[] propParts = property.split("：", 2);
            String propKey = propParts[0].trim();
            String propValue = propParts[1].trim();
//            String mappedPropKey = hashMap.getOrDefault(propKey, propKey);
            String mappedPropKey = getDefaultMapValue(propKey, propKey, false);

//            String mappedPropValue = hashMap.getOrDefault(propValue, propValue);
            String mappedPropValue = getDefaultMapValue(propValue, propValue, true);
            obj.put(mappedPropKey, mappedPropValue);
        }
    }

    private boolean isInsideObject(int currentLevel) {
        // 检查前一行是否是object声明
        if (currentIndex > 0) {
            String previousLine = lines[currentIndex - 1].trim();
            if (previousLine.contains("(object)")) {
                return true;
            }

            // 检查当前行的父级是否是object
            for (int i = currentIndex - 1; i >= 0; i--) {
                String line = lines[i].trim();
                int level = getLevel(lines[i]);
                if (level < currentLevel) {
                    return line.contains("(object)");
                }
            }
        }
        return false;
    }

    private JSONArray parseArray(int level) {
        JSONArray array = new JSONArray();
        currentIndex++;

        while (currentIndex < lines.length) {
            String line = lines[currentIndex].trim();
            if (line.isEmpty()) {
                currentIndex++;
                continue;
            }

            int nextLevel = getLevel(lines[currentIndex]);
            if (nextLevel <= level) {
                break;
            }

            String content = line.substring(countLeadingDash(line)).trim();
            if (content.contains("：")) {
                // 处理多属性对象
                JSONObject obj = new JSONObject();
                String[] properties = content.split(" ");

                for (String prop : properties) {
                    if (prop.contains("：")) {
                        String[] propParts = prop.split("：");
                        String propKey = propParts[0].trim();
                        if (hashMap.get(propKey) != null) {
//                            propKey = hashMap.get(propKey);
                            propKey = hashMap.get(propKey).getShowField();
                        }
                        String propValue = propParts.length > 1 ? propParts[1].trim() : "";

                        // 处理JSON数组
                        if (propValue.startsWith("[") && propValue.endsWith("]")) {
                            try {
                                JSONArray jsonArray = JSONArray.parseArray(propValue);
                                //这里需要将json数组里面的数据进行幻化
                                JSONArray propResultArray = new JSONArray();
                                for (int i = 0; i < jsonArray.size(); i++) {
                                    JSONObject jsonObject11 = (JSONObject) jsonArray.get(i);
                                    JSONObject jsonItem = new JSONObject();
                                    for (String key1 : jsonObject11.keySet()) {
//                                        jsonItem.put(hashMap.get(key1) != null ? hashMap.get(key1) : key1, jsonObject11.get(key1));
                                        jsonItem.put(hashMap.get(key1) != null ? hashMap.get(key1).getShowField() : key1, jsonObject11.get(key1));

                                    }
                                    propResultArray.add(jsonItem);
                                }
//                                obj.put(propKey, JSONArray.parseArray(propValue));
                                obj.put(propKey, propResultArray);
                            } catch (Exception e) {
                                obj.put(propKey, propValue);
                            }
                        } else if (propValue.startsWith("{") && propValue.endsWith("}")) {
                            obj.put(propKey, JSONObject.parseObject(propValue));
                        } else if (propValue.startsWith("【") && propValue.endsWith("】")) {
                            //枚举map
                            int startIndex = propValue.indexOf("【");
                            int endIndex = propValue.indexOf("】");
                            String subMapKey = propValue.substring(startIndex + 1, endIndex).trim();
                            String enumsValues = constantsEnumsHashMap.get(subMapKey);
                            if (enumsValues.startsWith("[") && enumsValues.endsWith("]")) {
                                JSONArray enumsArray = JSONArray.parseArray(enumsValues);
                                obj.put(propKey, enumsArray);
                            } else {
                                obj.put(propKey, JSONObject.parseObject(enumsValues));
                            }

                        } else {
                            //中文替换
                            getDefaultMapValue(propValue, propValue, true);
//                            String finalChildValue = StringUtils.isEmpty(hashMap.get(propValue)) ? propValue : hashMap.get(propValue);
                            String finalChildValue = getDefaultMapValue(propValue, propValue, true);
                            obj.put(propKey, finalChildValue);
                        }
                    }
                }
                array.add(obj);
                currentIndex++;
            }
        }

        return array;
    }

    private int getLevel(String line) {
        return countLeadingDash(line.trim());
    }

    private int countLeadingDash(String line) {
        int count = 0;
        while (count < line.length() && line.charAt(count) == '+') {
            count++;
        }
        return count;
    }


    private String getDefaultMapValue(String key, String value, boolean isTarget) {

        FrontendItemConfigBO frontendItemConfigBO = hashMap.get(key);
        if (frontendItemConfigBO != null) {
            if (isTarget) {
                return frontendItemConfigBO.getTargetField();
            } else {
                return frontendItemConfigBO.getShowField();
            }

        }
        return value;

    }

}
