package com.luojiawei.his_service.controller;


import com.luojiawei.his_service.domain.dto.Result;
import com.luojiawei.his_service.domain.vo.DiagnosisListVO;
import com.luojiawei.his_service.service.IDiagnosisReportService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 罗佳炜
 * @since 2025-03-01
 */
@RestController
@RequestMapping("/diagnosis")
@RequiredArgsConstructor
public class DiagnosisReportController {
    private final IDiagnosisReportService diagnosisReportService;
    @ApiOperation(value = "分页获得的diagnosises")
    @GetMapping("/reports")
    public Result<DiagnosisListVO> diagnosisReports(@RequestParam(value = "page") Integer pageNumber,
                                                    @RequestParam(value = "limit") Integer pageSize,
                                                    @RequestParam(value = "status",required = false) String status){
        Result<DiagnosisListVO> diagnosisReports = diagnosisReportService.diagnosisReports(pageNumber, pageSize, status);
        return diagnosisReports;
    }

    @ApiOperation(value = "分页获得历史diagnosises")
    @GetMapping("/history")
    public Result<DiagnosisListVO> diagnosisHistory(@RequestParam(value = "page") Integer pageNumber,
                                                    @RequestParam(value = "limit") Integer pageSize,
                                                    @RequestParam(value = "start_date") String startDate,
                                                    @RequestParam(value = "end_date") String endDate,
                                                    @RequestParam(value = "status",required = false) String status){
        Result<DiagnosisListVO> diagnosisReports = diagnosisReportService.diagnosisHistory(pageNumber, pageSize, startDate, endDate, status);
        return diagnosisReports;
    }

    @ApiOperation(value = "获得单个diagnosis细节")
    @GetMapping("/report")
    public Result<Object> diagnosisReport(@RequestParam(value = "id") Integer id){
        Result<Object> diagnosisReport = diagnosisReportService.diagnosisReport(id);
        return diagnosisReport;
    }


}
