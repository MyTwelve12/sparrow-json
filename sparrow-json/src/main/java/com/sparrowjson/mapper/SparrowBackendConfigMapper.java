package com.sparrowjson.mapper;

import com.sparrowjson.dto.SparrowBackendConfigDTO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SparrowBackendConfigMapper extends Mapper<SparrowBackendConfigDTO> {

    List<SparrowBackendConfigDTO> listByTemplateAlias(@Param("templateAlias") String templateAlias);
}
