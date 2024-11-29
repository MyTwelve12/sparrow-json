package com.sparrowjson.mapper;

import com.sparrowjson.dto.FrontendConfigDTO;
import com.sparrowjson.dto.SparrowBackendConfigDTO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface FrontendConfigMapper extends Mapper<FrontendConfigDTO> {

    /**
     * 根据功能获取前端配置信息
     *
     * @param functionName
     * @return
     */
    List<FrontendConfigDTO> listByFunctionName(@Param("functionName") String functionName);

//    /**
//     * 根据功能获
//     * @param functionName
//     * @return
//     */
//    List<FrontendConfigDTO> listByFunctionAndTypeList(@Param("functionName") String functionName);
}
