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
@TableName("cases")
@ApiModel(value="Cases对象", description="")
public class Cases implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "关联多个dignosisReport（;分隔）")
    @JsonProperty("dignosis_reports")
    private String dignosisReports;

    @ApiModelProperty(value = "治疗方案id")
    @JsonProperty("treatment_plan_id")
    private Integer treatmentPlanId;

    @ApiModelProperty(value = "关联医院患者表")
    @JsonProperty("patient_id")
    private Integer patientId;

    @ApiModelProperty(value = "症状描述（JSON格式，如视力模糊、眼痛等）")
    private String symptoms;

    @ApiModelProperty(value = "诊断方法（如眼底检查、眼压测量等）")
    @JsonProperty("diagnosis_method")
    private String diagnosisMethod;

    @ApiModelProperty(value = "恢复时长（单位：天）")
    @JsonProperty("recovery_time")
    private LocalDateTime recoveryTime;

    @ApiModelProperty(value = "记录创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("created_time")
    private LocalDateTime createdTime;

    @ApiModelProperty(value = "病人名称")
    @JsonProperty("patient_name")
    private String patientName;

    @ApiModelProperty(value = "病人身份证号")
    @JsonProperty("id_card")
    private String idCard;

    @ApiModelProperty(value = "状态: confirmed/archived")
    @JsonProperty("status")
    private String status;


}
