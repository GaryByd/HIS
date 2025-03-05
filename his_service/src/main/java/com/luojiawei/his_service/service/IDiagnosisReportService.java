package com.luojiawei.his_service.service;

import com.luojiawei.his_service.domain.dto.Result;
import com.luojiawei.his_service.domain.po.DiagnosisReport;
import com.baomidou.mybatisplus.extension.service.IService;
import com.luojiawei.his_service.domain.vo.DiagnosisListVO;

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

    Result<Object> diagnosisReport(Integer id);
}
