package com.sparrowjson.util;

/**
 * Description:
 * Company    : 上海黄豆网络科技有限公司
 *
 * @author : hll
 * Date       : 2024/11/6
 * Modify     : 修改日期          修改人员        修改说明          JIRA编号
 * v1.0.0       2024/11/6       hll    新增              1001
 ********************************************************************/

import cn.hutool.core.io.FileUtil;
import com.sparrowjson.constant.SparrowBackendConstant;
import com.sparrowjson.vo.CreateTableConfig;
import com.sparrowjson.vo.DeleteConfig;
import com.sparrowjson.vo.InsertConfig;
import com.sparrowjson.vo.InsertTableColumnsConfig;
import com.sparrowjson.vo.MenuConfig;
import com.sparrowjson.vo.QueryConfig;
import com.sparrowjson.vo.UpdateConfig;
import com.sparrowjson.vo.unit.FileDataUnitBO;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class ConfigFileReader {

    public MenuConfig readConfigFile(String filePath) {
        MenuConfig config = new MenuConfig();
        Map<String, Integer> currentIndexMap = new HashMap<>();// 记录当前处理的索引
        File file = FileUtil.file(filePath);
        String encoding = FileEncodingUtil.detectFileEncoding(file);
        BufferedReader utf8Reader = FileUtil.getReader(file, encoding);
        readFile(utf8Reader, config, currentIndexMap);
        return config;
    }


    private void readFile(BufferedReader utf8Reader, MenuConfig config, Map<String, Integer> currentIndexMap) {
        try (BufferedReader reader = new BufferedReader(utf8Reader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                // 解析基本信息
                if (line.startsWith("功能描述=")) {
                    config.setFunctionDesc(line.split("=")[1]);
                } else if (line.startsWith("数据库=")) {
                    config.setDatabase(line.split("=")[1]);
                } else if (line.startsWith("表=")) {
                    config.setTable(line.split("=")[1]);
                } else if (line.startsWith("表字段=")) {
                    config.setTableFields(parseList(line.split("=")[1]));
                } else if (line.startsWith("新建表=")) {
                    config.setTableFields(parseList(line.split("=")[1]));
                }
                // 解析带索引的配置
                else {
                    parseIndexedConfig(line, config, currentIndexMap);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取文件
     *
     * @param file
     * @return
     */
    public MenuConfig readConfigFile(File file) {
        MenuConfig config = new MenuConfig();
        Map<String, Integer> currentIndexMap = new HashMap<>();  // 记录当前处理的索引
        String encoding = FileEncodingUtil.detectFileEncoding(file);
        BufferedReader utf8Reader = FileUtil.getReader(file, encoding);
        readFile(utf8Reader, config, currentIndexMap);
        return config;
    }

    /**
     * 将前后端文件整合成同一个对象
     *
     * @param file
     * @return
     */
    public FileDataUnitBO readAndUnitBackAndFront(File file) {
        String encoding = FileEncodingUtil.detectFileEncoding(file);
        BufferedReader utf8Reader = FileUtil.getReader(file, encoding);

        FileDataUnitBO fileDataUnitBO = new FileDataUnitBO();
        List<String> backList = new ArrayList<>();
        List<String> frontList = new ArrayList<>();
        boolean backendFlag = true;
        try (BufferedReader reader = new BufferedReader(utf8Reader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                if (Objects.equals(SparrowBackendConstant.FRONT_TEMPLATE_SEPARATOR, line)) {
                    backendFlag = false;
                    continue;
                }
                if (backendFlag) {
                    backList.add(line);
                } else {
                    frontList.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileDataUnitBO.setBackList(backList);
        fileDataUnitBO.setFrontList(frontList);

        return fileDataUnitBO;
    }




    public void parseIndexedConfig(String line, MenuConfig config, Map<String, Integer> currentIndexMap) {
        // 解析配置类型和索引
        String[] parts = line.split("\\.");
        String configType = parts[0];
        String configField = parts.length > 1 ? parts[1] : "";

        // 提取索引
        int index = 0;
        if (configType.contains("[")) {
            index = Integer.parseInt(configType.substring(
                    configType.indexOf("[") + 1,
                    configType.indexOf("]")
            ));
            configType = configType.substring(0, configType.indexOf("["));
        }

        // 更新当前索引
        currentIndexMap.put(configType, index);

        // 根据配置类型处理
        switch (configType) {
            case "新增":
                handleInsertConfig(config, index, configField, line);
                break;
            case "更新":
                handleUpdateConfig(config, index, configField, line);
                break;
            case "删除":
                handleDeleteConfig(config, index, configField, line);
                break;
            case "查询":
                handleQueryConfig(config, index, configField, line);
                break;
            case "新建表":
                handleCreateTableConfig(config, index, configField, line);
                break;
            case "新增字段":
                handleInsertTableColumnConfig(config, index, configField, line);
                break;
        }
    }

    private void handleInsertTableColumnConfig(MenuConfig config, int index, String field, String line) {
        ensureConfigSize(config, config.getInsertTableColumns(), index);
        InsertTableColumnsConfig insertTableColumnsConfig = config.getInsertTableColumns().get(index);
        String[] split = field.split("=");
        insertTableColumnsConfig.setTableName(split[0]);
        insertTableColumnsConfig.setColumnField(split[1]);
    }

    private void handleCreateTableConfig(MenuConfig config, int index, String field, String line) {
        ensureConfigSize(config, config.getCreateTables(), index);
        CreateTableConfig createTableConfig = config.getCreateTables().get(index);
        String[] split = field.split("=");
        createTableConfig.setTableName(split[0]);
        createTableConfig.setTableField(split[1]);
    }

    private void handleInsertConfig(MenuConfig config, int index, String field, String line) {
        ensureConfigSize(config, config.getInsertConfigs(), index);
        InsertConfig insertConfig = config.getInsertConfigs().get(index);

        String value = line.substring(line.indexOf("=") + 1);
        String[] split = field.split("=");
        switch (split[0]) {
            case "新增参数字段":
                insertConfig.setInsertFields(value);
                break;
            case "唯一性校验":
                insertConfig.addOnlyOneCheck(value);
                break;
            case "功能描述":
                insertConfig.setName(value);
                break;
            case "新增转换规则":
                insertConfig.addRuleDB(value);
                break;
            case "批量新增字段":
                insertConfig.setBatchInsertFields(value);
                break;
            case "新增数据库表":
                insertConfig.setTableName(value);
                break;
        }
    }

    private void handleUpdateConfig(MenuConfig config, int index, String field, String line) {
        ensureConfigSize(config, config.getUpdateConfigs(), index);
        UpdateConfig updateConfig = config.getUpdateConfigs().get(index);

        String value = line.substring(line.indexOf("=") + 1);
        String[] split = field.split("=");
        switch (split[0]) {
            case "更新参数字段":
                updateConfig.setUpdateFields(value);
                break;
            case "更新条件字段":
                updateConfig.setConditions(value);
                break;
            case "唯一性校验":
                updateConfig.addOnlyOneCheck(value);
                break;
            case "更新转换规则":
                updateConfig.addRuleDB(value);
                break;
            case "功能描述":
                updateConfig.setName(value);
                break;
            case "更新数据库表":
                updateConfig.setTableName(value);
                break;
        }
    }

    private void handleDeleteConfig(MenuConfig config, int index, String field, String line) {
        ensureConfigSize(config, config.getDeleteConfigs(), index);
        DeleteConfig deleteConfig = config.getDeleteConfigs().get(index);

        String value = line.substring(line.indexOf("=") + 1);
        String[] split = field.split("=");
        switch (split[0]) {
            case "更新参数字段":
                deleteConfig.setUpdateFields(value);
                break;
            case "更新条件字段":
                deleteConfig.setConditions(value);
                break;
            case "唯一性校验":
                deleteConfig.addOnlyOneCheck(value);
                break;
            case "更新转换规则":
                deleteConfig.addRuleDB(value);
                break;
            case "功能描述":
                deleteConfig.setName(value);
                break;
            case "更新数据库表":
                deleteConfig.setTableName(value);
                break;
        }
    }

    private void handleQueryConfig(MenuConfig config, int index, String field, String line) {
        ensureConfigSize(config, config.getQueryConfigs(), index);
        QueryConfig queryConfig = config.getQueryConfigs().get(index);

        String value = line.substring(line.indexOf("=") + 1);
        String[] split = field.split("=");
        switch (split[0]) {
            case "查询目标字段":
                queryConfig.setQueryFields(value);
                break;
            case "数据库表":
                queryConfig.setTable(value);
                break;
            case "是否需要分页":
                queryConfig.setPagination(value.equals("是"));
                break;
            case "排序字段":
                queryConfig.setSortField(value);
                break;
            case "查询参数字段":
                queryConfig.setConditions(value);
                break;
            case "功能描述":
                queryConfig.setName(value);
                break;
            case "多表连接查询数据表":
                queryConfig.setMultiTables(value);
                break;
        }
    }

    private <T> void ensureConfigSize(MenuConfig config, List<T> list, int index) {
        while (list.size() <= index) {
            try {
                T newInstance = (T) createNewInstance(config, list);
                list.add(newInstance);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 其他解析方法保持不变...
    public List<String> parseList(String value) {
        value = value.replaceAll("[\\[\\]]", "");
        return Arrays.asList(value.split(",\\s*"));
    }

    private Object createNewInstance(MenuConfig config, List<?> list) throws Exception {
        if (list instanceof List) {
            if (list.isEmpty()) {
                // 根据类型创建对应的实例
                if (list == config.getInsertConfigs()) {
                    return new InsertConfig();
                } else if (list == config.getUpdateConfigs()) {
                    return new UpdateConfig();
                } else if (list == config.getDeleteConfigs()) {
                    return new DeleteConfig();
                } else if (list == config.getQueryConfigs()) {
                    return new QueryConfig();
                }
            } else {
                // 如果列表不为空，复制第一个元素的类型
                return list.get(0).getClass().getDeclaredConstructor().newInstance();
            }
        }
        throw new IllegalArgumentException("Unsupported list type");
    }

}
