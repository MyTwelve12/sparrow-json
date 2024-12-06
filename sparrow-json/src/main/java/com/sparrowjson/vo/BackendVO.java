package com.sparrowjson.vo;

import lombok.Data;

import java.util.List;

/**
 * Description:
 * Company    : 上海黄豆网络科技有限公司
 *
 * @author : hll
 * Date       : 2024/10/30
 * Modify     : 修改日期          修改人员        修改说明          JIRA编号
 * v1.0.0       2024/10/30       hll    新增              1001
 ********************************************************************/
@Data
public class BackendVO {

    /**
     * 模版别名
     */
    private String templateAlias;

    /**
     * 功能别名
     */
    private String functionAlias;

    private BackendEndpointVO endpoint;

    private List<BackendVariablesVO> variables;
}
