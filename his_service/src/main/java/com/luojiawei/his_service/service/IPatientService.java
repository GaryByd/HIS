package com.luojiawei.his_service.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.luojiawei.common.domain.dto.AuthDTO;
import com.luojiawei.common.domain.dto.Result;
import com.luojiawei.common.domain.po.Patient;
import com.luojiawei.common.domain.vo.LoginVo;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 罗佳炜
 * @since 2025-03-01
 */
public interface IPatientService extends IService<Patient> {

    Result<LoginVo> login(String code, HttpSession session);

    Result<Object> Verify(AuthDTO authDTO, HttpSession session);

}
