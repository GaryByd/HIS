package com.luojiawei.his_service.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@TableName("eye_images")
@ApiModel(value="EyeImages对象", description="")
public class EyeImages implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "图片路径")
    private String url;

    @ApiModelProperty(value = "关联报告ID")
    @JsonIgnore
    private Integer dignosisReportId;

    @ApiModelProperty(value = "眼别 OD/OS")
    private String eyePosition;

    @ApiModelProperty(value = "DICOM元数据")
    private String dicomData;


}
