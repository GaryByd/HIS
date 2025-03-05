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
@TableName("cases")
@ApiModel(value="Cases对象", description="")
public class Cases implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "关联多个dignosisReport（;分隔）")
    private String dignosisReports;

    @ApiModelProperty(value = "治疗方案id")
    private Integer treatmentPlanId;

    @ApiModelProperty(value = "关联医院患者表")
    private Integer patientId;

    @ApiModelProperty(value = "症状描述（JSON格式，如视力模糊、眼痛等）")
    private String symptoms;

    @ApiModelProperty(value = "诊断方法（如眼底检查、眼压测量等）")
    private String diagnosisMethod;

    @ApiModelProperty(value = "恢复时长（单位：天）")
    private LocalDateTime recoveryTime;

    @ApiModelProperty(value = "记录创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;

    @ApiModelProperty(value = "病人名称")
    private String patientName;

    @ApiModelProperty(value = "病人身份证号")
    private String idCard;

    @ApiModelProperty(value = "状态: confirmed/archived")
    private String status;


}
