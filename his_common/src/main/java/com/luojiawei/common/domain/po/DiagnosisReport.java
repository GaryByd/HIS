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
@TableName("diagnosis_report")
@ApiModel(value="DiagnosisReport对象", description="")
public class DiagnosisReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    @JsonProperty("id")
    private Integer id;

    @ApiModelProperty(value = "患者ID")
    @JsonProperty("patient_id")
    private Integer patientId;

    @ApiModelProperty(value = "医生ID")
    @JsonProperty("doctor_id")
    private Integer doctorId;

    @ApiModelProperty(value = "AI模型版本ID")
    @JsonProperty("aimodel_id")
    private Integer aimodelId;

    @ApiModelProperty(value = "AI诊断结果")
    @JsonProperty("result")
    private String result;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "confirmed/archived")
    private String status;

    @ApiModelProperty(value = "病人身份证号")
    @JsonProperty("id_card")
    private String idCard;

    @ApiModelProperty(value = "病人名称")
    private String name;

    @ApiModelProperty(value = "医生打分")
    private Integer score;

    @ApiModelProperty(value = "医生备注")
    private String remark;


}
