package com.luojiawei.his_service.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
    private Integer id;

    @ApiModelProperty(value = "患者ID")
    private Integer patientId;

    @ApiModelProperty(value = "医生ID")
    private Integer doctorId;

    @ApiModelProperty(value = "眼疾名称")
    private String diseaseName;

    @ApiModelProperty(value = "眼疾分类")
    private String diseaseCategory;

    @ApiModelProperty(value = "AI模型版本ID")
    private Integer aimodelId;

    @ApiModelProperty(value = "AI诊断结果")
    private String result;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "confirmed/archived")
    private String status;

    @ApiModelProperty(value = "病人身份证号")
    private String idCard;

    @ApiModelProperty(value = "病人名称")
    private String name;

    @ApiModelProperty(value = "医生打分")
    private Integer score;

    @ApiModelProperty(value = "医生备注")
    private String remark;


}
