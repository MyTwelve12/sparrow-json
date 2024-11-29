package com.sparrowjson.execute;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Component
public class FrontTemplateExecuteManager {

    @Resource
    private List<FrontTemplateExecute> list;

    public FrontTemplateExecute getExecute(String templateCode) {
        for (FrontTemplateExecute execute : list) {
            if (execute.getTemplateCode() != null && Objects.equals(templateCode, execute.getTemplateCode())) {
                return execute;
            }
        }
        return null;
    }


}
