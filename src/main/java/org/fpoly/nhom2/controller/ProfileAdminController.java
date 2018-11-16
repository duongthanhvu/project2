package org.fpoly.nhom2.controller;

import java.util.Arrays;
import java.util.List;

import org.fpoly.nhom2.entiry.*;
import org.fpoly.nhom2.repository.*;
import org.fpoly.nhom2.service.FileUtil;
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
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    private FileUtil fileUtil;
    @Autowired
    private SkillListRepository skillListRepository;

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
    public String showProfileDetail(Model model, @RequestParam("profile") Integer profileId) {
        model.addAttribute("profile", profileRepository.getOne(profileId));
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
    public String addNewProfileProcess(@ModelAttribute("profile") Profile profile,
    @RequestParam("avatar-file") MultipartFile avatar, @RequestParam("skills") String str,
    @RequestParam("userId") User user) {
        profile.setProfileId(user.getUserId());
        addressRepository.save(profile.getAddress());
        profile.setAvatarPicture(fileUtil.saveFile(avatar, FileUtil.LOGO));
        profile.setUrlName(user.getUsername());
        profileRepository.save(profile);
        for (Education edu : profile.getEducations()) {
            edu.setProfile(profile);
            educationRepository.save(edu);
        }
        List<String> skills = Arrays.asList(str.split("\\s*,\\s*"));
        for (String skillName : skills) {
            Skill skill = skillRepository.findFirstByTitleIgnoreCase(skillName);
            if (skill == null) {
                skill = new Skill();
                skill.setTitle(skillName);
                skillRepository.save(skill);
            }
            SkillList sl = new SkillList();
            sl.setProfile(profile);
            sl.setSkill(skill);
            skillListRepository.save(sl);
        }
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