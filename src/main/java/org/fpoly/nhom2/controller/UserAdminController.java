package org.fpoly.nhom2.controller;

import org.fpoly.nhom2.entiry.User;
import org.fpoly.nhom2.repository.RoleRepository;
import org.fpoly.nhom2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * AdminController
 */

@Controller
public class UserAdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @RequestMapping(value = "/admin/user/list", method = RequestMethod.GET)
    public String showuserList(Model model,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort) {
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by("userId").ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by("userId").descending();
        }
        Pageable pageable = PageRequest.of(page, size, sortable);
        model.addAttribute("page", userRepository.findAll(pageable));
        return "admin-user-list";
    }

    @RequestMapping(value = "/admin/user/detail", method = RequestMethod.GET)
    public String showuserDetail(@RequestParam("user") Integer userId) {
        // TODO hiển thị chi tiết tài khoản
        return "admin-user-detail";
    }

    @RequestMapping(value = "/admin/user/add", method = RequestMethod.GET)
    public String showAddUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleRepository.findAll());
        return "admin-add-user";
    }

    @RequestMapping(value = "/admin/user/add", method = RequestMethod.POST)
    public String addNewUserProcess(@ModelAttribute("user") User user) {
        user.setEnabled(true);
        //Mã hóa mật khẩu
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        userRepository.save(user);
        return "redirect:/admin/user/list";
    }

    @RequestMapping(value = "/admin/user/edit", method = RequestMethod.GET)
    public String showEditUser(@RequestParam("user") Integer userId, Model model) {
        model.addAttribute("user", userRepository.getOne(userId));
        model.addAttribute("roles", roleRepository.findAll());
        return "admin-edit-user";
    }

    @RequestMapping(value = "/admin/user/edit", method = RequestMethod.POST)
    public String editUserProcess(@ModelAttribute("user") User user, @RequestParam("new_password") String newPassword) {
        if(!newPassword.equals("")){
            user.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
        }
        userRepository.save(user);
        return "redirect:/admin/user/list";
    }

    @RequestMapping(value = "/admin/user/delete", method = RequestMethod.GET)
    public String requestMethodName(@RequestParam("user") Integer userId) {
        // TODO thực hiện việc xóa address, xóa bài đăng, tin tuyển dụng -> xóa user
        return "redirect:/admin/user/list";
    }

}