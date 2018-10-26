package org.fpoly.nhom2.controller;

import org.springframework.web.bind.annotation.GetMapping;


/**
 * AdminController
 */
public class AdminController {

    @GetMapping(value="/admin")
    public String showAdminDashboard() {
        return "admin-home";
    }
}