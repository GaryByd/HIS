package com.luojiawei.his_service.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.luojiawei.common.domain.dto.DiagnosisReportDetails;
import com.luojiawei.common.domain.dto.Result;
import com.luojiawei.common.domain.po.DiagnosisReport;
import com.luojiawei.common.domain.vo.DiagnosisListVO;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 罗佳炜
 * @since 2025-03-01
 */

public interface IDiagnosisReportService extends IService<DiagnosisReport> {

    Result<DiagnosisListVO> diagnosisReports(Integer pageNumber, Integer pageSize, String status);

    Result<DiagnosisListVO> diagnosisHistory(Integer pageNumber, Integer pageSize, String startDate, String endDate, String status);

    Result<DiagnosisReportDetails> diagnosisReport(Integer id);
}
