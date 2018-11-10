package org.fpoly.nhom2.controller;

import java.util.ArrayList;
import java.util.List;

import org.fpoly.nhom2.entiry.Category;
import org.fpoly.nhom2.entiry.Company;
import org.fpoly.nhom2.entiry.CompanyCategory;
import org.fpoly.nhom2.repository.AddressRepository;
import org.fpoly.nhom2.repository.CategoryRepository;
import org.fpoly.nhom2.repository.CompanyCategoryRepository;
import org.fpoly.nhom2.repository.CompanyRepository;
import org.fpoly.nhom2.repository.POCRepository;
import org.fpoly.nhom2.service.FileUtil;
import org.fpoly.nhom2.service.ViewCountService;
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
 * AdminController
 */

@Controller
public class CompanyAdminController {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CategoryRepository catRepository;

    @Autowired
    private POCRepository pOCRepository;

    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private ViewCountService viewCountService;

    @Autowired
    private CompanyCategoryRepository compCatRepository;

    @RequestMapping(value = { "/admin/company/list", "/admin/company" }, method = RequestMethod.GET)
    public String showCompanyList(Model model,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort) {
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by("companyId").ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by("companyId").descending();
        }
        Pageable pageable = PageRequest.of(page, size, sortable);
        model.addAttribute("page", companyRepository.findAll(pageable));
        return "admin-company-list";
        //TODO Thêm thông báo khi thêm hoặc chỉnh sửa thành công
    }

    @RequestMapping(value = "/admin/company/detail", method = RequestMethod.GET)
    public String showCompanyDetail(Model model, @RequestParam("company") Integer companyId) {
        model.addAttribute("company", companyRepository.getOne(companyId));
        return "admin-company-detail";
    }

    @RequestMapping(value = "/admin/company/add", method = RequestMethod.GET)
    public String showAddCompnay(Model model) {
        model.addAttribute("provinces", pOCRepository.findAll());
        model.addAttribute("categories", catRepository.findAll());
        model.addAttribute("company", new Company());
        return "admin-add-company";
    }

    @RequestMapping(value = "/admin/company/add", method = RequestMethod.POST)
    public String addNewCompProcess(@ModelAttribute("company") Company company,
            @RequestParam(value = "categories") List<Category> categories,
            @RequestParam(value = "logo-file") MultipartFile logo) {

        company.setLogo(fileUtil.saveFile(logo, FileUtil.LOGO));
        addressRepository.save(company.getAddress());
        company.setStatus(true);
        company.setViewCount(viewCountService.addNew());
        companyRepository.save(company);
        for (Category category : categories) {
            compCatRepository.save(new CompanyCategory(company, category));
        }
        return "redirect:/admin/company/list";
    }

    @RequestMapping(value = "/admin/company/edit", method = RequestMethod.GET)
    public String showEditCompany(@RequestParam("company") Integer companyId, Model model) {
        Company companyToEdit = companyRepository.getOne(companyId);
        model.addAttribute("company", companyToEdit);
        model.addAttribute("provinces", pOCRepository.findAll());
        model.addAttribute("categories", catRepository.findAll());
        List<Integer> selectedCategories = new ArrayList<>();
        for (CompanyCategory comcat : companyToEdit.getCompanyCategories()) {
            selectedCategories.add(comcat.getCategory().getCategoryId());
        }
        model.addAttribute("selectedCategories", selectedCategories);
        return "admin-edit-company";
    }

    @RequestMapping(value = "/admin/company/edit", method = RequestMethod.POST)
    public String editCompanyProcess(@ModelAttribute("company") Company company,
            @RequestParam(value = "categories") List<Category> categories,
            @RequestParam(value = "logo-file") MultipartFile logo) {

        if (!logo.isEmpty()) {
            company.setLogo(fileUtil.saveFile(logo, FileUtil.LOGO));
        }
        addressRepository.save(company.getAddress());
        companyRepository.save(company);
        // TODO update CompanyCategory
        return "redirect:/admin/company/list";
    }

    @RequestMapping(value = "/admin/company/delete", method = RequestMethod.GET)
    public String requestMethodName(@RequestParam("company") Integer companyId) {
        // TODO thực hiện việc xóa address, xóa bài đăng, tin tuyển dụng -> xóa company
        return "redirect:/admin/company/list";
    }

}