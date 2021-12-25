package com.sonic.simple.models.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlCharsetConstant;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;
import com.sonic.simple.models.base.TypeConverter;
import com.sonic.simple.models.dto.DevicesDTO;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author JayWenStar
 * @since 2021-12-17
 */
@ApiModel(value = "Devices对象", description = "")
@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("devices")
@TableComment("设备表")
@TableCharset(MySqlCharsetConstant.UTF8MB4)
@TableEngine(MySqlEngineConstant.InnoDB)
public class Devices implements Serializable, TypeConverter<Devices, DevicesDTO> {

    @TableId(value = "id", type = IdType.AUTO)
    @IsAutoIncrement
    private Integer id;

    @TableField
    @Column(value = "agent_id", isNull = false, comment = "所属agent的id")
    private Integer agentId;

    @TableField
    @Column(comment = "cpu架构")
    private String cpu;

    @TableField
    @Column(value = "img_url", comment = "手机封面")
    private String imgUrl;

    @TableField
    @Column(comment = "制造商")
    private String manufacturer;

    @TableField
    @Column(comment = "手机型号")
    private String model;

    @TableField
    @Column(comment = "设备名称")
    private String name;

    @TableField
    @Column(comment = "设备安装app的密码")
    private String password;

    @TableField
    @Column(isNull = false, comment = "系统类型 1：android 2：ios")
    private Integer platform;

    @TableField
    @Column(comment = "设备分辨率")
    private String size;

    @TableField
    @Column(comment = "设备状态")
    private String status;

    @TableField
    @Column(value = "ud_id", comment = "设备序列号")
    @Index(value = "IDX_UD_ID", columns = {"ud_id"})
    private String udId;

    @TableField
    @Column(comment = "设备系统版本")
    private String version;

    @TableField
    @Column(value = "nick_name", comment = "设备备注")
    private String nickName;

    @TableField
    @Column(comment = "设备当前占用者")
    private String user;

    @TableField
    @Column(value = "chi_name", comment = "中文设备")
    String chiName;

    @TableField
    @Column(defaultValue = "0", comment = "设备温度")
    Integer temperature;
}
