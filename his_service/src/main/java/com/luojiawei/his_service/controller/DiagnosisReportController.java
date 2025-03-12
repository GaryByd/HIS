package com.luojiawei.his_service.controller;


import com.luojiawei.common.domain.dto.DiagnosisReportDetails;
import com.luojiawei.common.domain.dto.Result;
import com.luojiawei.common.domain.vo.DiagnosisListVO;

import com.luojiawei.common.domain.vo.RecommendedQuestions;
import com.luojiawei.common.domain.vo.inner.QuestionsVo;
import com.luojiawei.his_service.enums.GeneralQuestions;
import com.luojiawei.his_service.service.IDiagnosisReportService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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
        return diagnosisReportService.diagnosisReports(pageNumber, pageSize, status);
    }

    @ApiOperation(value = "分页获得历史diagnosises")
    @GetMapping("/history")
    public Result<DiagnosisListVO> diagnosisHistory(@RequestParam(value = "page") Integer pageNumber,
                                                    @RequestParam(value = "limit") Integer pageSize,
                                                    @RequestParam(value = "start_date",required = false) String startDate,
                                                    @RequestParam(value = "end_date",required = false) String endDate,
                                                    @RequestParam(value = "status",required = false) String status){
        return diagnosisReportService.diagnosisHistory(pageNumber, pageSize, startDate, endDate, status);
    }

    @ApiOperation(value = "获得单个diagnosis细节")
    @GetMapping("/reports/{id}")
    public Result<DiagnosisReportDetails> diagnosisReport(@PathVariable("id") Integer id) {
        return diagnosisReportService.diagnosisReport(id);
    }

    @ApiOperation(value = "返回一些通用问题")
    @GetMapping("/{diagnosisId}/qa/questions")
    public Result<RecommendedQuestions> getQuestions(@PathVariable("diagnosisId") Integer diagnosisId) {
        //将枚举枚举的文件都放到RecommendedQuestions中
        RecommendedQuestions recommendedQuestions = new RecommendedQuestions();
        ArrayList<QuestionsVo> questions = new ArrayList<>();
        for (GeneralQuestions generalQuestions : GeneralQuestions.values()) {
            QuestionsVo q = new QuestionsVo(generalQuestions.getId(), generalQuestions.getQuestion(), generalQuestions.getCategory());
            questions.add(q);
        }
        recommendedQuestions.setQuestions(questions);
        return Result.ok(recommendedQuestions);
    }
}
