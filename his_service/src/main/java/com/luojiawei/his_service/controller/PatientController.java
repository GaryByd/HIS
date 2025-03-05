package com.luojiawei.his_service.controller;


import com.luojiawei.his_service.domain.dto.AuthDTO;
import com.luojiawei.his_service.domain.dto.Result;
import com.luojiawei.his_service.domain.vo.LoginVo;
import com.luojiawei.his_service.service.IPatientService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 罗佳炜
 * @since 2025-03-01
 */
@RestController
@RequestMapping("/patient")
@RequiredArgsConstructor
public class PatientController {

    private final IPatientService patientService;

    @ApiOperation(value = "用户登录接口")
    @PostMapping("/auth/login")
    public Result<LoginVo> login(@RequestBody String code, HttpSession session){
        Result<LoginVo> login = patientService.login(code, session);
        return login;
    }

    @ApiOperation(value = "用户实名认证接口")
    @PostMapping("/auth/verify")
    public Result<Object> Verify(@RequestBody AuthDTO authDTO, HttpSession session){
        Result<Object> result = patientService.Verify(authDTO, session);
        return result;
    }



}
