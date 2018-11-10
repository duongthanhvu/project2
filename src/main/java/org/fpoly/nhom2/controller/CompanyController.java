package org.fpoly.nhom2.controller;

import java.util.List;

import org.fpoly.nhom2.entiry.Address;
import org.fpoly.nhom2.entiry.Category;
import org.fpoly.nhom2.entiry.Company;
import org.fpoly.nhom2.entiry.ProvinceOrCity;
import org.fpoly.nhom2.entiry.Review;
import org.fpoly.nhom2.entiry.Tag;
import org.fpoly.nhom2.repository.*;
import org.fpoly.nhom2.service.FollowService;
import org.fpoly.nhom2.service.LoggedInUser;
import org.fpoly.nhom2.service.ViewCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * CompanyController
 */
@Controller
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private POCRepository pOCRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private LoggedInUser loggedInUser;
    @Autowired
    private ViewCountService viewCountService;

    @GetMapping(value = { "/company/{urlName}", "/company/{urlName}/about" })
    public String showCompanyPage(Model model, @PathVariable("urlName") String urlName) {
        Company company = companyRepository.findFirstByUrlName(urlName);
        model.addAttribute("company", company);
        viewCountService.increaseViewCount(company.getViewCount());
        return "company-about";
    }

    @GetMapping(value = { "/company/{urlName}/product", "/company/{urlName}/product/list" })
    public String showProduct(Model model, @PathVariable("urlName") String urlName,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort) {
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by("productId").ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by("productId").descending();
        }
        Pageable pageable = PageRequest.of(page, size, sortable);
        Company company = companyRepository.findFirstByUrlName(urlName);
        model.addAttribute("company", company);
        model.addAttribute("page", productRepository.findByCompany(company, pageable));
        return "company-product";
    }

    @GetMapping(value = { "/company/{urlName}/career", "/company/{urlName}/career/list" })
    public String showCareer(Model model, @PathVariable("urlName") String urlName,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort) {
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by("jobId").ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by("jobId").descending();
        }
        Pageable pageable = PageRequest.of(page, size, sortable);
        Company company = companyRepository.findFirstByUrlName(urlName);
        model.addAttribute("company", company);
        model.addAttribute("page", jobRepository.findByCompany(company, pageable));
        return "company-career";
    }

    @GetMapping(value = { "/company/{urlName}/post", "/company/{urlName}/post/list" })
    public String showPost(Model model, @PathVariable("urlName") String urlName,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort) {
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by("postId").ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by("postId").descending();
        }
        Pageable pageable = PageRequest.of(page, size, sortable);
        Company company = companyRepository.findFirstByUrlName(urlName);
        model.addAttribute("company", company);
        model.addAttribute("page", postRepository.findByCompany(company, pageable));
        return "company-post";
    }

    @GetMapping(value = "/company/{urlName}/review")
    public String getCompanyReview(Model model, @PathVariable("urlName") String urlName) {
        Company company = companyRepository.findFirstByUrlName(urlName);
        model.addAttribute("company", company);
        model.addAttribute("review", new Review());
        List<Review> reviews = reviewRepository.findByCompany(company);
        model.addAttribute("reviews", reviews);
        int[] stars = { 0, 0, 0, 0, 0 };
        double sum = 0;
        for (Review review : reviews) {
            switch (review.getRating()) {
            case 1:
                stars[0]++;
                sum = sum + 1;
                break;
            case 2:
                stars[1]++;
                sum = sum + 2;
                break;
            case 3:
                stars[2]++;
                sum = sum + 3;
                break;
            case 4:
                stars[3]++;
                sum = sum + 4;
                break;
            default:
                stars[4]++;
                sum = sum + 5;
                break;
            }
        }
        if (reviews.size() != 0) {
            sum = sum / reviews.size();
        }
        model.addAttribute("ratingAvg", sum);
        model.addAttribute("stars", stars);
        return "company-review";
    }

    @PostMapping(value = "/company/{urlName}/review")
    public String processReview(@ModelAttribute("review") Review review, @PathVariable("urlName") String urlName) {
        review.setUser(loggedInUser.getUser());
        review.setCompany(companyRepository.findFirstByUrlName(urlName));
        reviewRepository.save(review);
        return "redirect:/company/" + urlName + "/review";
    }

    @GetMapping(value = "/company")
    public String showCompanyList(Model model) {
        model.addAttribute("companies", companyRepository.findAll());
        model.addAttribute("provinces", pOCRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        return "company-list";
    }

    @ResponseBody
    @GetMapping(value = "/api/company/search")
    public List<Company> searchForCompany(@RequestParam String q) {
        if (q.equals("")) {
            return null;
        }
        return companyRepository.findTop10ByNameContainingIgnoreCase(q);
    }

    @GetMapping(value = "/company/search")
    public String searchForCompany(Model model,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort,
            @RequestParam(name = "location", required = false) Integer provinceId,
            @RequestParam(name = "category", required = false) Integer categoryId,
            @RequestParam(name = "keyword", required = false) String keyword) {
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by("companyId").ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by("companyId").descending();
        }
        Pageable pageable = PageRequest.of(page, size, sortable);
        model.addAttribute("page", companyRepository.searchCompany(categoryId, provinceId, keyword, keyword, pageable));
        model.addAttribute("provinces", pOCRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        return "company-search-result";
    }

    @ResponseBody
    @GetMapping(value = "/test/company")
    public List<Company> getMethodName(@RequestParam Integer catid, @RequestParam Integer locid,
            @RequestParam String name, @RequestParam String description) {

        return null;
    }

}