package com.luojiawei.common.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author 罗佳炜
 * @since 2025-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("patient")
@ApiModel(value="Patient对象", description="")
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "电话号码")
    private String phone;

    @ApiModelProperty(value = "身份证号")
    @JsonProperty("id_card")
    private String idCard;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "是否实名认证(0:否,1:是)")
    private int verified;

    @ApiModelProperty(value = "审核人")
    @JsonProperty("verified_by")
    private String verifiedBy;

    @ApiModelProperty(value = "审核时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("verified_time")
    private LocalDateTime verifiedTime;

    @ApiModelProperty(value = "open_id")
    @JsonProperty("open_id")
    private String openId;

    @ApiModelProperty(value = "医院患者编号")
    @JsonProperty("hospital_pid")
    private String hospitalPid;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("省份")
    private String province;

    @ApiModelProperty("县区")
    private String county;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("出生日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("birthday")
    private String birthday;

    @ApiModelProperty("年龄")
    private Integer age;

}
