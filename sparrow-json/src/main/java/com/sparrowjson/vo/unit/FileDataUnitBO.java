package com.sparrowjson.vo.unit;

import lombok.Data;

import java.util.List;

/**
 * 文件数据
 */
@Data
public class FileDataUnitBO {

    /**
     * 后端文本
     */
    private List<String> backList;

    /**
     * 前端文本
     */
    private List<String> frontList;
}
