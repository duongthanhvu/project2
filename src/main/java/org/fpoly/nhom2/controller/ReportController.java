package org.fpoly.nhom2.controller;

import org.fpoly.nhom2.entiry.Report;
import org.fpoly.nhom2.repository.ReportRepository;
import org.fpoly.nhom2.service.LoggedInUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * ReportController
 */
@Controller
public class ReportController {

    @Autowired
    LoggedInUser loggedInUser;
    @Autowired
    ReportRepository reportRepository;

    @ResponseBody
    @PostMapping(value="/report/submit")
    public String submitReport(@ModelAttribute Report report) {
        if (loggedInUser.isAnonymousUser()) {
            return "Vui lòng đăng nhập trước khi gửi báo cáo";
        }
        report.setUser(loggedInUser.getUser());
        if(report.getDescription() == null || report.getDescription().isEmpty()){
            return "Lỗi, chưa nhập nội dung báo cáo!";
        }
        report.setStatus(false);
        reportRepository.save(report);
        return "success";
    }
    
}