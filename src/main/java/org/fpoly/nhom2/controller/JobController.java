package org.fpoly.nhom2.controller;

import org.fpoly.nhom2.entiry.Job;
import org.fpoly.nhom2.repository.JobRepository;
import org.fpoly.nhom2.repository.POCRepository;
import org.fpoly.nhom2.repository.ProfileRepository;
import org.fpoly.nhom2.repository.TagRepository;
import org.fpoly.nhom2.service.LoggedInUser;
import org.fpoly.nhom2.service.ViewCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * JobController
 */
@Controller
public class JobController {

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private POCRepository pOCRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private ViewCountService viewCountService;
    @Autowired
    private LoggedInUser loggedInUser;
    @Autowired
    private ProfileRepository profileRepository;

    @GetMapping(value = "/job")
    public String getMethodName(Model model,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort) {
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by("jobId").ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by("jobId").descending();
        }
        Pageable pageable = PageRequest.of(page, size, sortable);
        model.addAttribute("page", jobRepository.findAll(pageable));
        model.addAttribute("provinces", pOCRepository.findAll());
        model.addAttribute("tags", tagRepository.findAll());
        return "job-list";
    }

    @GetMapping(value = "/job/{urlTitle}")
    public String showJobDetail(Model model, @PathVariable("urlTitle") String urlTitle) {
        Job job = jobRepository.findByUrlTitle(urlTitle);
        model.addAttribute("job", job);
        model.addAttribute("jobs", jobRepository.findAll());
        if (loggedInUser.isAnonymousUser() == false) {
            model.addAttribute("profile", profileRepository.getOne(loggedInUser.getDefaultUserId()));
        }
        viewCountService.increaseViewCount(job.getViewCount());
        return "job-detail";
    }

    @GetMapping(value = "/job/search")
    public String searchForCompany(Model model,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort,
            @RequestParam(name = "location", required = false) Integer provinceId,
            @RequestParam(name = "tag", required = false) Integer tagId,
            @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword) {
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by("jobId").ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by("jobId").descending();
        }
        Pageable pageable = PageRequest.of(page, size, sortable);
        model.addAttribute("page", jobRepository.searchJob(provinceId, tagId, keyword, pageable));
        model.addAttribute("provinces", pOCRepository.findAll());
        model.addAttribute("tags", tagRepository.findAll());
        return "job-search-result";
    }

}