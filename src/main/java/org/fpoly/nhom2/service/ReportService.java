package org.fpoly.nhom2.service;

import org.fpoly.nhom2.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ReportService
 */
@Component(value ="reportService")
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public long getReportCount(){
        return reportRepository.countByStatus(false);
    }
}