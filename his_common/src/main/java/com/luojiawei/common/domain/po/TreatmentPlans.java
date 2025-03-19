package com.luojiawei.common.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@TableName("treatment_plans")
@ApiModel(value="TreatmentPlans对象", description="")
public class TreatmentPlans implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "关联DiagnosisReport表")
    @JsonIgnore
    @JsonProperty("diagnosis_id")
    private Integer diagnosisId;

    @ApiModelProperty(value = "治疗类型（药物、手术、激光治疗等）")
    @JsonProperty("treatment_type")
    private Integer treatmentType;

    @ApiModelProperty(value = "治疗方案详细信息")
    @JsonProperty("treatment_detail")
    private String treatmentDetail;

    @ApiModelProperty(value = "治疗开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("start_time")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "治疗结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("end_time")
    private LocalDateTime endDate;

    @ApiModelProperty(value = "关联Docker表")
    @JsonIgnore
    private Integer doctorId;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonIgnore
    private LocalDateTime createdTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonIgnore
    private LocalDateTime updatedTime;


}
