package org.fpoly.nhom2.controller;

import org.fpoly.nhom2.entiry.Education;
import org.fpoly.nhom2.entiry.Profile;
import org.fpoly.nhom2.repository.AddressRepository;
import org.fpoly.nhom2.repository.EducationRepository;
import org.fpoly.nhom2.repository.POCRepository;
import org.fpoly.nhom2.repository.ProfileRepository;
import org.fpoly.nhom2.repository.SkillRepository;
import org.fpoly.nhom2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ProfileAdminController
 */
@Controller
public class ProfileAdminController {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private POCRepository provinceRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private EducationRepository educationRepository;

    @RequestMapping(value = { "/admin/profile/list", "/admin/profile" }, method = RequestMethod.GET)
    public String showProfileList(Model model,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort) {
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by("profileId").ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by("profileId").descending();
        }
        Pageable pageable = PageRequest.of(page, size, sortable);
        model.addAttribute("page", profileRepository.findAll(pageable));
        return "admin-profile-list";
    }

    @RequestMapping(value = "/admin/profile/detail", method = RequestMethod.GET)
    public String showProfileDetail(@RequestParam("profile") Integer profileId) {
        // TODO hiển thị chi tiết hồ sơ
        return "admin-profile-detail";
    }

    @RequestMapping(value = "/admin/profile/add", method = RequestMethod.GET)
    public String showAddProfile(Model model, @RequestParam(value = "user", required = false) Integer userId) {
        if (userId == null) {
            return "redirect:/admin/profile";
            /*
             * TODO nếu request k có parameter thì yêu cầu người dùng chỉ định tài khoản
             * liên kết với hồ sơ
             */
        }
        Profile profile = new Profile();
        profile.setUser(userRepository.getOne(userId));
        model.addAttribute("profile", profile);
        model.addAttribute("skills", skillRepository.findAll());
        model.addAttribute("provinces", provinceRepository.findAll());
        return "admin-add-profile";
    }

    @RequestMapping(value = "/admin/profile/add", method = RequestMethod.POST)
    public String addNewProfileProcess(@ModelAttribute("profile") Profile profile) {
        profile.setProfileId(profile.getUser().getUserId());
        addressRepository.save(profile.getAddress());
        profileRepository.save(profile);
        for(Education edu : profile.getEducations()){
            edu.setProfile(profile);
            educationRepository.save(edu);
        }
        //TODO Thiếu
        return "redirect:/admin/profile/list";
    }

    @RequestMapping(value = "/admin/profile/edit", method = RequestMethod.GET)
    public String showEditprofile(@RequestParam("profile") Integer profileId, Model model) {

        return "admin-edit-profile";
    }

    @RequestMapping(value = "/admin/profile/edit", method = RequestMethod.POST)
    public String editprofileProcess(@ModelAttribute("profile") Profile profile) {

        return "redirect:/admin/profile/list";
    }

    @RequestMapping(value = "/admin/profile/delete", method = RequestMethod.GET)
    public String requestMethodName(@RequestParam("profile") Integer profileId) {
        // TODO thực hiện việc xóa address, xóa bài đăng, tin tuyển dụng -> xóa profile
        return "redirect:/admin/profile/list";
    }

}