package com.sparrowjson.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Company    : 上海黄豆网络科技有限公司
 *
 * @author : hll
 * Date       : 2024/11/7
 * Modify     : 修改日期          修改人员        修改说明          JIRA编号
 * v1.0.0       2024/11/7       hll    新增              1001
 ********************************************************************/
@Data
public class BackConfig {

    private List<String> onlyOneCheck;

    private List<String> ruleDB;

    private List<String> rpcList;

    public void addOnlyOneCheck(String value) {
        if (onlyOneCheck == null) {
            onlyOneCheck = new ArrayList<>();
        }
        onlyOneCheck.add(value);
    }

    public void addRuleDB(String value) {
        if (ruleDB == null) {
            ruleDB = new ArrayList<>();
        }
        ruleDB.add(value);
    }

    public void addRpcList(String value) {
        if (rpcList == null) {
            rpcList = new ArrayList<>();
        }
        rpcList.add(value);
    }
}
