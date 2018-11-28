package org.fpoly.nhom2.controller;

import java.util.Arrays;
import java.util.List;

import org.fpoly.nhom2.entiry.Cv;
import org.fpoly.nhom2.entiry.Education;
import org.fpoly.nhom2.entiry.Profile;
import org.fpoly.nhom2.entiry.Skill;
import org.fpoly.nhom2.entiry.SkillList;
import org.fpoly.nhom2.entiry.User;
import org.fpoly.nhom2.repository.AddressRepository;
import org.fpoly.nhom2.repository.CVRepository;
import org.fpoly.nhom2.repository.EducationRepository;
import org.fpoly.nhom2.repository.POCRepository;
import org.fpoly.nhom2.repository.ProfileRepository;
import org.fpoly.nhom2.repository.SkillListRepository;
import org.fpoly.nhom2.repository.SkillRepository;
import org.fpoly.nhom2.repository.UserRepository;
import org.fpoly.nhom2.service.FileUtil;
import org.fpoly.nhom2.service.LoggedInUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * UserController
 */
@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private SkillListRepository skillListRepository;
    @Autowired
    private POCRepository provinceRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private EducationRepository educationRepository;
    @Autowired
    private FileUtil fileUtil;
    @Autowired
    private LoggedInUser loggedInUser;
    @Autowired
    private CVRepository cvRepository;

    @ResponseBody
    @GetMapping(value = "/api/user/search")
    public List<User> searchForUser(@RequestParam String q) {
        return userRepository.findTop10ByUsernameContainingIgnoreCase(q);
    }

    @GetMapping(value = "/user")
    public String showUserPage(Model model) {
        User user = userRepository.getOne(loggedInUser.getDefaultUserId());
        if (user.getProfile() == null) {
            return "redirect:/user/profile/new";
        } else {
            model.addAttribute("user", user);
            return "user";
        }
    }

    @GetMapping(value = "/user/profile")
    public String showUserProfile(Model model) {
        model.addAttribute("user", userRepository.getOne(loggedInUser.getDefaultUserId()));
        return "user-profile";
    }

    @GetMapping(value = "/user/profile/new")
    public String showAddNewProfile(Model model) {
        model.addAttribute("user", userRepository.getOne(loggedInUser.getDefaultUserId()));
        model.addAttribute("profile", new Profile());
        model.addAttribute("skills", skillRepository.findAll());
        model.addAttribute("provinces", provinceRepository.findAll());
        return "user-new-profile";
    }

    @PostMapping(value = "/user/profile/new")
    public String addNewProfilePocess(@ModelAttribute Profile profile,
            @RequestParam("avatar-file") MultipartFile avatar, @RequestParam("skills") String str) {
        profile.setProfileId(loggedInUser.getDefaultUserId());
        addressRepository.save(profile.getAddress());
        profile.setAvatarPicture(fileUtil.saveFile(avatar, FileUtil.LOGO));
        profile.setUrlName(loggedInUser.getUser().getUsername());
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
        return "redirect:/user";
    }

    @GetMapping(value = "/user/profile/edit")
    public String showProfileUpdate(Model model) {
        model.addAttribute("user", userRepository.getOne(loggedInUser.getDefaultUserId()));
        Profile profile = profileRepository.getOne(loggedInUser.getDefaultUserId());
        if (profile.getEducations().isEmpty()) {
            profile.addEducation(new Education());
        }
        model.addAttribute("profile", profile);
        model.addAttribute("skills", skillRepository.findAll());
        model.addAttribute("provinces", provinceRepository.findAll());
        return "user-edit-profile";
    }

    @PostMapping(value = "/user/profile/edit")
    public String processProfileEdit(@ModelAttribute Profile profile) {
        // TODO: process POST request

        return "redirect:/user";
    }

    @GetMapping(value = "/user/job")
    public String showUserJob(Model model) {
        model.addAttribute("user", userRepository.getOne(loggedInUser.getDefaultUserId()));
        return "user-job";
    }

    @GetMapping(value = "/user/subcribe")
    public String showUserFollow(Model model) {
        model.addAttribute("user", userRepository.getOne(loggedInUser.getDefaultUserId()));
        return "user-follow";
    }

    @GetMapping(value = "/user/cv")
    public String showUserCV(Model model) {
        model.addAttribute("user", userRepository.getOne(loggedInUser.getDefaultUserId()));
        return "user-cv";
    }

    @PostMapping(value = "/user/upload-cv")
    public String uploadCv(@RequestBody MultipartFile pdf) {
        if (pdf == null || pdf.isEmpty()) {
            return "redirect:/user/cv";
        }
        Cv cv = new Cv();
        cv.setOriginalFilename(pdf.getOriginalFilename());
        cv.setProfile(userRepository.getOne(loggedInUser.getDefaultUserId()).getProfile());
        cv.setUniqueFilename(fileUtil.saveFile(pdf, FileUtil.PDF));
        cvRepository.save(cv);
        return "redirect:/user/cv";
    }

    @GetMapping(value = "/user/cv/delete")
    public String deleteCv(@RequestParam("cv") Cv cv) {
        if (cv.getProfile().getProfileId() == loggedInUser.getDefaultUserId()) {
            cvRepository.delete(cv);
        }
        return "redirect:/user/cv";
    }

    @ResponseBody
    @GetMapping(value = "/user/check_password")
    public String chheckOldPassword(@RequestParam("old_password") String password) {
        User user = userRepository.getOne(loggedInUser.getDefaultUserId());
        if (BCrypt.checkpw(password, user.getPassword())) {
            return "true";
        }
        return "false";
    }

    @PostMapping(value = "/user/change_password")
    public String changeUserPassword(@RequestParam("old_password") String oldPassword,
            @RequestParam("new_password") String newPassword) {
        User user = userRepository.getOne(loggedInUser.getDefaultUserId());
        if (BCrypt.checkpw(oldPassword, user.getPassword())) {
            return "redirect:/user";
        }
        user.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
        userRepository.save(user);
        return "redirect:/user";
    }

}