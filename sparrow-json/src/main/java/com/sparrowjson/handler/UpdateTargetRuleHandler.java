package com.sparrowjson.handler;

import com.sparrowjson.dto.SparrowBackendConfigDTO;
import com.sparrowjson.vo.BackConfig;
import com.sparrowjson.vo.BackendVariablesVO;
import com.sparrowjson.vo.DeleteConfig;
import com.sparrowjson.vo.MenuConfig;
import com.sparrowjson.vo.UpdateConfig;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Description:
 * Company    : 上海黄豆网络科技有限公司
 *
 * @author : hll
 * Date       : 2024/11/7
 * Modify     : 修改日期          修改人员        修改说明          JIRA编号
 * v1.0.0       2024/11/7       hll    新增              1001
 ********************************************************************/
@Component("updateTargetRule")
public class UpdateTargetRuleHandler implements VariableHandler {
    @Override
    public BackendVariablesVO convertVariables(SparrowBackendConfigDTO sparrowBackendConfigDTO, MenuConfig menuConfig, BackConfig backConfig) {
        BackendVariablesVO backendVariablesVO = createBackendVariablesVO(sparrowBackendConfigDTO);

        String config = sparrowBackendConfigDTO.getConfig();
        String tableName = null;
        if (backConfig instanceof UpdateConfig) {
            UpdateConfig updateConfig = (UpdateConfig) backConfig;
            tableName = updateConfig.getTableName();
        }
        if (backConfig instanceof DeleteConfig) {
            DeleteConfig deleteConfig = (DeleteConfig) backConfig;
            tableName = deleteConfig.getTableName();
        }

        config = config.replace("${datasourceName}", menuConfig.getDatabase());
        if (tableName != null && !Objects.equals(tableName, menuConfig.getTable())) {
            config = config.replace("${tableName}", tableName);
        } else {
            config = config.replace("${tableName}", menuConfig.getTable());
        }

        backendVariablesVO.setValue(config);
        return backendVariablesVO;
    }
}
