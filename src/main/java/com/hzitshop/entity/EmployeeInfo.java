package com.hzitshop.entity;

import com.baomidou.mybatisplus.activerecord.Model;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * <p>
 * <p>
 * </p>
 *
 * @author 冼耀基
 * @since 2017-03-01
 */
@TableName("employee_info")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class EmployeeInfo extends Model<EmployeeInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;
    /**
     * 用户名
     */
    private String name;

    @JsonIgnore(true)
    private String password;
    private String salt;
    /**
     * 微信号
     */
    @TableField("wechat_no")
    private String wechatNo;
    /**
     * 手机号
     */
    private String tel;
    /**
     * 用户姓名
     */
    @TableField("real_name")
    private String realName;

    @TableField("dept_id")
    private Integer deptId;

    @TableField("company_id")
    private Integer companyId;

    @TableField("create_by")
    private String createBy;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_by")
    private String updateBy;

    @TableField("update_time")
    private Date updateTime;

    private Integer isDelete;
    /**
     * 是否禁用用户:0:不禁用.1:禁用 默认为:0
     */
    private String isLocked;

    private String gender;
    /**
     * 0：不是咨询师,1:是咨询师 默认为:0
     */
    private String isConsultant;

    private String email;
    /**
     * 邮件是否发送成功:0:不成功，1：成功,默认:0
     */
    @JsonIgnore(true)
    private String isEmailActive;

    @JsonIgnore(true)
    @TableField("resource_ids")
    private String resourceIds;
    /**
     * 是否授权:0:没有授权,1:权限授权，2:角色授权
     */
    @JsonIgnore(true)
    private String isPermission;
    /**
     * 角色名称
     */
    @TableField("role_name")
    private String roleName;
    /**
     * 钉钉用户编号
     */
    @JsonIgnore(true)
    @TableField("dingding_id")
    private String dingdingId;

    public EmployeeInfo(Integer userId, String isLocked) {
        this.userId = userId;
        this.isLocked = isLocked;
    }

    public EmployeeInfo() {

    }

    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

}
