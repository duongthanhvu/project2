package org.fpoly.nhom2.controller;

import org.fpoly.nhom2.entiry.Report;
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
            @RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort,
            @RequestParam(name = "status", required = false, defaultValue = "false") boolean status) {
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by("reportId").ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by("reportId").descending();
        }
        Pageable pageable = PageRequest.of(page, size, sortable);
        model.addAttribute("page", reportRepository.findByStatus(status,pageable));
        return "admin-report-list";
    }
    
    @GetMapping(value="/admin/report/solve")
    public String setReportAsSolved(@RequestParam("report") Report report) {
        report.setStatus(!report.getStatus());
        reportRepository.save(report);
        return "redirect:/admin/report";
    }
    
}