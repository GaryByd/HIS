package com.luojiawei.his_service.mapper;

import com.luojiawei.his_service.domain.po.EyeImages;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.ArrayList;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 罗佳炜
 * @since 2025-03-01
 */
public interface EyeImagesMapper extends BaseMapper<EyeImages> {


    ArrayList<EyeImages> selectListByDiagnosisId(Integer id);


}
