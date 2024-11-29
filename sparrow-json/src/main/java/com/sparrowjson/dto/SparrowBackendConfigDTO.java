package com.sparrowjson.dto;

import javax.persistence.*;
import java.util.Date;

@Table(name = "t_sparrow_backend_config")
public class SparrowBackendConfigDTO {
    @Id
    private Integer tid;

    /**
     * 模板别名
     */
    @Column(name = "template_alias")
    private String templateAlias;

    /**
     * 方法类型
     */
    private Integer type;

    /**
     * 方法code
     */
    private String code;

    /**
     * 描述
     */
    private String desc;

    /**
     * 创建人ID
     */
    @Column(name = "create_id")
    private Integer createId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新人ID
     */
    @Column(name = "update_id")
    private Integer updateId;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 删除标志 1：未删除；2：已删除
     */
    @Column(name = "del_flag")
    private Integer delFlag;

    /**
     * 方法配置
     */
    private String config;

    /**
     * @return tid
     */
    public Integer getTid() {
        return tid;
    }

    /**
     * @param tid
     */
    public void setTid(Integer tid) {
        this.tid = tid;
    }

    /**
     * 获取方法类型
     *
     * @return type - 方法类型
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置方法类型
     *
     * @param type 方法类型
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取方法code
     *
     * @return code - 方法code
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置方法code
     *
     * @param code 方法code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取创建人ID
     *
     * @return create_id - 创建人ID
     */
    public Integer getCreateId() {
        return createId;
    }

    /**
     * 设置创建人ID
     *
     * @param createId 创建人ID
     */
    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新人ID
     *
     * @return update_id - 更新人ID
     */
    public Integer getUpdateId() {
        return updateId;
    }

    /**
     * 设置更新人ID
     *
     * @param updateId 更新人ID
     */
    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取删除标志 1：未删除；2：已删除
     *
     * @return del_flag - 删除标志 1：未删除；2：已删除
     */
    public Integer getDelFlag() {
        return delFlag;
    }

    /**
     * 设置删除标志 1：未删除；2：已删除
     *
     * @param delFlag 删除标志 1：未删除；2：已删除
     */
    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * 获取方法配置
     *
     * @return config - 方法配置
     */
    public String getConfig() {
        return config;
    }

    /**
     * 设置方法配置
     *
     * @param config 方法配置
     */
    public void setConfig(String config) {
        this.config = config;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTemplateAlias() {
        return templateAlias;
    }

    public void setTemplateAlias(String templateAlias) {
        this.templateAlias = templateAlias;
    }
}
