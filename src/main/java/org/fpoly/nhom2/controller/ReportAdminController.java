package org.fpoly.nhom2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * ReportAdminController
 */
@Controller
public class ReportAdminController {

    @GetMapping(value="/admin/report")
    public String showReportList(@RequestParam String param) {
        return "report-list";
    }
    
}