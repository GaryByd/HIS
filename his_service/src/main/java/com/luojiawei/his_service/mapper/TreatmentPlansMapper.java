package com.luojiawei.his_service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luojiawei.common.domain.po.TreatmentPlans;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 罗佳炜
 * @since 2025-03-01
 */
public interface TreatmentPlansMapper extends BaseMapper<TreatmentPlans> {

    TreatmentPlans selectByDiagnosisId(Integer id);
}
