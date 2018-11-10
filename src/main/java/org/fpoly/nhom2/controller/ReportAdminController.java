package org.fpoly.nhom2.controller;

import org.fpoly.nhom2.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * ReportAdminController
 */
@Controller
public class ReportAdminController {

    @Autowired
    private ReportRepository reportRepository;

    @GetMapping(value = { "/admin/report/list", "/admin/report" })
    public String showReportList(Model model,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort) {
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by("reportId").ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by("reportId").descending();
        }
        Pageable pageable = PageRequest.of(page, size, sortable);
        model.addAttribute("page", reportRepository.findByStatus(false,pageable));
        return "admin-report-list";
    }
    
}