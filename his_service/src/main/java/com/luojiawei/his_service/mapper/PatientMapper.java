package com.luojiawei.his_service.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luojiawei.his_service.domain.dto.UserDTO;
import com.luojiawei.his_service.domain.po.Patient;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luojiawei.his_service.domain.vo.Diagnosises;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 罗佳炜
 * @since 2025-03-01
 */

public interface PatientMapper extends BaseMapper<Patient> {

    UserDTO selectIdByOpenId(String openId);

    Patient selectByPhone(String phone);

}
