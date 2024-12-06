package com.sparrowjson.controller;


import com.sparrowjson.database.DatabaseMetaDataUtil;
import com.sparrowjson.util.SnakeToCamelUtil;
import com.sparrowjson.vo.ColumnVO;
import com.sparrowjson.vo.unit.FrontendItemConfigBO;
import com.sparrowjson.vo.unit.GenerateBaseColumnVO;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@RestController
@RequestMapping("/database")
public class TestDataBaseController {


    public final static String COMMON_SPLIT = ".";


    /**
     * 将数据库表字段重新命名
     * @param vos
     * @return
     */
    @PostMapping("/v100/generateColumn")
    public List<FrontendItemConfigBO> generate(@RequestBody List<GenerateBaseColumnVO> vos) {
        List<FrontendItemConfigBO> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(vos)) {


            for (GenerateBaseColumnVO vo : vos) {

                String preName = vo.getPreName();

                List<ColumnVO> list = DatabaseMetaDataUtil.getCoulumns(vo.getTableName());
                if (!CollectionUtils.isEmpty(list)) {

                    for (ColumnVO columnVO : list) {
                        String columnName = columnVO.getColumnName();
                        String columnComment = columnVO.getComment();
                        FrontendItemConfigBO configBO = new FrontendItemConfigBO();
                        configBO.setName(preName + COMMON_SPLIT + columnComment);
                        configBO.setValue(columnName);
                        String showField = SnakeToCamelUtil.toCamelCase(columnName);
                        configBO.setShowField(showField);
                        configBO.setTargetField(showField);
                        result.add(configBO);
                    }
                }
            }
        }

        return result;

    }


}
