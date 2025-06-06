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
@TableName("medication_recommendations")
@ApiModel(value="MedicationRecommendations对象", description="")
public class MedicationRecommendations implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @JsonProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "关联diagnoses表")
    @JsonProperty("diagnosis_id")
    private Integer diagnosisId;

    @ApiModelProperty(value = "药物名称")
    @JsonProperty("medication_name")
    private String medicationName;

    @ApiModelProperty(value = "药物剂量")
    @JsonProperty("dosage")
    private String dosage;

    @ApiModelProperty(value = "用药频率")
    @JsonProperty("frequency")
    private String frequency;

    @ApiModelProperty(value = "药物副作用")
    @JsonProperty("side_effects")
    private String sideEffects;

    @ApiModelProperty(value = "开始用药时间 ")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("start_time")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "停止用药时间 ")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("end_time")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "关联docker表，指向医生")
    @JsonProperty("doctor_id")
    private Integer doctorId;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("created_time")
    private LocalDateTime createdTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("updated_time")
    private LocalDateTime updatedTime;


}
