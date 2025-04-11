package com.luojiawei.his_service.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luojiawei.common.domain.po.DiagnosisReport;
import com.luojiawei.common.domain.vo.Diagnosises;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 罗佳炜
 * @since 2025-03-01
 */
@Mapper
public interface DiagnosisReportMapper extends BaseMapper<DiagnosisReport> {

    IPage<Diagnosises> getDiagnosisList(Page<Diagnosises> page, String status, Long id);

    IPage<Diagnosises> getDiagnosisHistory(Page<Diagnosises> page, String startDate, String endDate, String status,Long id);
}
