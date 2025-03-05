package com.luojiawei.his_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luojiawei.his_service.domain.dto.DiagnosisReportDetails;
import com.luojiawei.his_service.domain.dto.Result;
import com.luojiawei.his_service.domain.dto.inner.DoctorInfo;
import com.luojiawei.his_service.domain.dto.inner.PatientInfo;
import com.luojiawei.his_service.domain.po.*;
import com.luojiawei.his_service.domain.vo.DiagnosisListVO;
import com.luojiawei.his_service.domain.vo.Diagnosises;
import com.luojiawei.his_service.mapper.*;
import com.luojiawei.his_service.service.IDiagnosisReportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
        IPage<Diagnosises> DiagnosisList = diagnosisReportMapper.getDiagnosisList(page,status);
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
        IPage<Diagnosises> DiagnosisList = diagnosisReportMapper.getDiagnosisHistory(page,startDate,endDate,status);
        DiagnosisListVO diagnosisListVO = new DiagnosisListVO();
        diagnosisListVO.setTotal((int) DiagnosisList.getTotal());
        diagnosisListVO.setPageNumber(pageNumber);
        diagnosisListVO.setPageSize(pageSize);
        diagnosisListVO.setReports(DiagnosisList.getRecords());
        return Result.ok(diagnosisListVO);
    }

    @Override
    public Result<Object> diagnosisReport(Integer id) {
        //先查询诊断报告是否有
        DiagnosisReport diagnosisReport = diagnosisReportMapper.selectById(id);
        if (diagnosisReport == null) {
            return Result.fail("该诊断报告不存在！");
        }
        //构建PatientInfo 通过diagnosis的id构建Patinet_info
        Patient patient = patientMapper.selectById(diagnosisReport.getPatientId());
        PatientInfo patientInfo = new PatientInfo(patient.getName(),patient.getIdCard());

        //构建DoctorInfo 通过diagnosis的DoctorId构建Doctor_info
        Doctor doctor = doctorMapper.selectById(diagnosisReport.getDoctorId());
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
                diagnosisReport.getDiseaseName(),
                diagnosisReport.getDiseaseCategory(),
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
