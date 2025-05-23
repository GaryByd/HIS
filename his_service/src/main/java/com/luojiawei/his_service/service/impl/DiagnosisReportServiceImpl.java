package com.luojiawei.his_service.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luojiawei.common.domain.dto.DiagnosisReportDetails;
import com.luojiawei.common.domain.dto.Result;
import com.luojiawei.common.domain.dto.UserDTO;
import com.luojiawei.common.domain.dto.inner.DoctorInfo;
import com.luojiawei.common.domain.dto.inner.PatientInfo;
import com.luojiawei.common.domain.po.*;
import com.luojiawei.common.domain.vo.DiagnosisListVO;
import com.luojiawei.common.domain.vo.Diagnosises;

import com.luojiawei.common.utils.UserHolder;
import com.luojiawei.his_service.mapper.*;
import com.luojiawei.his_service.service.IDiagnosisReportService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 罗佳炜
 * @since 2025-03-01
 */
@Service
@RequiredArgsConstructor
public class DiagnosisReportServiceImpl extends ServiceImpl<DiagnosisReportMapper, DiagnosisReport> implements IDiagnosisReportService {

    private final DiagnosisReportMapper diagnosisReportMapper;
    private final PatientMapper patientMapper;
    private final DoctorMapper doctorMapper;
    private final EyeImagesMapper eyeImagesMapper;
    private final TreatmentPlansMapper treatmentPlansmapper;
    private final MedicationRecommendationsMapper medicationRecommendationsMapper;
    private final AiModelMapper aiModelMapper;
    @Override
    public Result<DiagnosisListVO> diagnosisReports(Integer pageNumber, Integer pageSize, String status) {
        Page<Diagnosises> page = new Page<>(pageNumber, pageSize);
        // 调用 Mapper 方法，执行分页查询
        //将用户的id拿出来
        UserDTO user = UserHolder.getUser();
        if(user.getId()==null) {
            return Result.fail("用户id为空");
        }
        IPage<Diagnosises> DiagnosisList = diagnosisReportMapper.getDiagnosisList(page,status,user.getId());
        DiagnosisListVO diagnosisListVO = new DiagnosisListVO();
        diagnosisListVO.setTotal((int) DiagnosisList.getTotal());
        diagnosisListVO.setPageNumber(pageNumber);
        diagnosisListVO.setPageSize(pageSize);
        diagnosisListVO.setReports(DiagnosisList.getRecords());
        return Result.ok(diagnosisListVO);
    }

    @Override
    public Result<DiagnosisListVO> diagnosisHistory(Integer pageNumber, Integer pageSize, String startDate, String endDate, String status) {
        Page<Diagnosises> page = new Page<>(pageNumber, pageSize);
        UserDTO user = UserHolder.getUser();
        if(user.getId()==null) {
            return Result.fail("用户id为空");
        }
        IPage<Diagnosises> DiagnosisList = diagnosisReportMapper.getDiagnosisHistory(page,startDate,endDate,status,user.getId());
        DiagnosisListVO diagnosisListVO = new DiagnosisListVO();
        diagnosisListVO.setTotal((int) DiagnosisList.getTotal());
        diagnosisListVO.setPageNumber(pageNumber);
        diagnosisListVO.setPageSize(pageSize);
        diagnosisListVO.setReports(DiagnosisList.getRecords());
        return Result.ok(diagnosisListVO);
    }

    @Override
    public Result<DiagnosisReportDetails> diagnosisReport(Integer id) {
        //先查询诊断报告是否有
        DiagnosisReport diagnosisReport = diagnosisReportMapper.selectById(id);
        if (diagnosisReport == null) {
            return Result.fail("该诊断报告不存在！");
        }
        //构建PatientInfo 通过diagnosis的id构建Patinet_info
        Patient patient = patientMapper.selectById(diagnosisReport.getPatientId());
        if(patient == null){
            return Result.fail("该患者不存在！");
        }
        PatientInfo patientInfo = new PatientInfo(patient.getName(),patient.getIdCard());

        //构建DoctorInfo 通过diagnosis的DoctorId构建Doctor_info
        Doctor doctor = doctorMapper.selectById(diagnosisReport.getDoctorId());
        if(doctor == null){
            return Result.fail("诊断报告错误,医生不存在！");
        }
        DoctorInfo doctorInfo = new DoctorInfo(doctor.getId(), doctor.getName(),doctor.getDepartment());

        //构建eye_images 通过诊断报告的id查询出所有的图片
        ArrayList<EyeImages> eyeImages = eyeImagesMapper.selectListByDiagnosisId(diagnosisReport.getId());

        //构建treatment_plan 通过诊断报告的id查询出所有的治疗方案
        TreatmentPlans treatmentPlan = treatmentPlansmapper.selectByDiagnosisId(diagnosisReport.getId());

        //构建药物治疗
        ArrayList<MedicationRecommendations> medicine = medicationRecommendationsMapper.selectByDiagnosisId(diagnosisReport.getId());

        //模型信息
        AiModel aiModel = aiModelMapper.selectById(diagnosisReport.getAimodelId());
        //构建DiagnosisReportDetails
        DiagnosisReportDetails diagnosisReportDetails = new DiagnosisReportDetails(
                diagnosisReport.getId(),
                patientInfo,
                doctorInfo,
                diagnosisReport.getResult(),
                diagnosisReport.getResult(),
                diagnosisReport.getResult(),
                diagnosisReport.getCreateTime(),
                diagnosisReport.getStatus(),
                diagnosisReport.getScore(),
                diagnosisReport.getRemark(),
                eyeImages,
                treatmentPlan,
                medicine,
                aiModel
        );
        return Result.ok(diagnosisReportDetails);
    }
}
