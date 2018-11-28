package org.fpoly.nhom2.controller;

import org.fpoly.nhom2.entiry.AppliedProfile;
import org.fpoly.nhom2.entiry.Cv;
import org.fpoly.nhom2.entiry.Job;
import org.fpoly.nhom2.repository.AppliedProfileRepository;
import org.fpoly.nhom2.service.FileUtil;
import org.fpoly.nhom2.service.LoggedInUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * ApplyJobController
 */
@Controller
public class ApplyJobController {

    @Autowired
    private AppliedProfileRepository appliedProfileRepository;
    @Autowired
    private LoggedInUser loggedInUser;
    @Autowired
    private FileUtil fileUtil;

    @PostMapping(value = "/user/apply")
    public String applyProcess(@RequestParam("cv") Cv cv, @RequestParam("cover_letter") MultipartFile coverLetter,
            @RequestParam("job") Job job) {
        AppliedProfile ap = new AppliedProfile();
        ap.setCoverLetter(fileUtil.saveFile(coverLetter, FileUtil.PDF));
        //TODO cho phép người dùng upload file cv khi chưa có cv
        ap.setCv(cv.getUniqueFilename());
        ap.setJob(job);
        ap.setProfile(loggedInUser.getUser().getProfile());
        appliedProfileRepository.save(ap);
        return "redirect:/job/" + job.getUrlTitle();
    }

}