package com.sparrowjson.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "t_frontend_config")
public class FrontendConfigDTO {


    @Id
    private Integer tid;

    /**
     * 功能名称
     */
    @Column(name = "function_name")
    private String functionName;

    /**
     * 类型
     */
    @Column(name = "type")
    private Integer type;

    /**
     * 值
     */
    @Column(name = "value")
    private String value;

}
