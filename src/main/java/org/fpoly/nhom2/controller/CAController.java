package org.fpoly.nhom2.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.fpoly.nhom2.entiry.*;
import org.fpoly.nhom2.repository.*;
import org.fpoly.nhom2.service.*;
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
import org.springframework.web.multipart.MultipartFile;

/**
 * CAController
 */
@Controller
public class CAController {

    @Autowired
    private LoggedInUser loggedInUser;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CompanyCategoryRepository companyCategoryRepository;
    @Autowired
    private POCRepository pOCRepository;
    @Autowired
    private CategoryRepository catRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductPictureRepo productPictureRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private BOJRepository bOJRepository;
    @Autowired
    private BenefitRepository benefitRepository;
    @Autowired
    private TOJRepository tojRepository;
    @Autowired
    private PictureOfCompanyRepo pictureOfCompanyRepo;
    @Autowired
    private UrlCreator urlCreator;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private FileUtil fileUtil;
    @Autowired
    private ViewCountService viewCountService;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private AppliedProfileRepository appliedProfileRepository;

    @GetMapping(value = { "/ca/home", "/ca" })
    public String showCAHome(Model model) {
        model.addAttribute("company", companyRepository.getOne(loggedInUser.getDefaultCompanyId()));
        return "ca-home";
    }

    @GetMapping(value = "/ca/switch-default")
    public String switchDefaulCA(@RequestParam int company, HttpServletRequest request) {
        for (CompanyAdmin ca : loggedInUser.getCA()) {
            if (ca.getCompany().getCompanyId() == company) {
                loggedInUser.setDefaultCompanyId(company);
                //Không có dòng dưới thì vẫn chạy ngon trên local nhưng đem lên app engine thì phải có dòng dưới để force session serialization
                //Giải thích ở đây https://stackoverflow.com/a/19272761/3133713
                request.getSession().setAttribute("CURRENT_TIME", System.currentTimeMillis());
                return "redirect:/ca/home";
            }
        }
        return "403";
    }

    /*
     * Xử lý info: Hiển thị chi tiết, Sửa
     */

    @GetMapping(value = "/ca/info")
    public String showInfo(Model model) {
        model.addAttribute("company", companyRepository.getOne(loggedInUser.getDefaultCompanyId()));
        return "ca-info";
    }

    @GetMapping(value = "/ca/info/edit")
    public String showInfoEdit(Model model) {
        Company companyToEdit = companyRepository.getOne(loggedInUser.getDefaultCompanyId());
        model.addAttribute("company", companyToEdit);
        model.addAttribute("provinces", pOCRepository.findAll());
        model.addAttribute("categories", catRepository.findAll());
        List<Integer> selectedCategories = new ArrayList<>();
        for (CompanyCategory comcat : companyToEdit.getCompanyCategories()) {
            selectedCategories.add(comcat.getCategory().getCategoryId());
        }
        model.addAttribute("selectedCategories", selectedCategories);
        return "ca-info-edit";
    }

    @PostMapping(value = "/ca/info/edit")
    public String editCompanyProcess(@ModelAttribute("company") Company company,
            @RequestParam(value = "categories") List<Category> categories,
            @RequestParam(value = "logo-file") MultipartFile logo) {

        if (!logo.isEmpty()) {
            company.setLogo(fileUtil.saveFile(logo, FileUtil.LOGO));
        }
        addressRepository.save(company.getAddress());
        companyRepository.save(company);
        for (CompanyCategory comcat : company.getCompanyCategories()) {
            companyCategoryRepository.delete(comcat);
        }
        for (Category category : categories) {
            companyCategoryRepository.save(new CompanyCategory(company, category));
        }
        return "redirect:/ca/info";
    }

    /*
     * Xử lý product: Hiển thị danh sách, Hiển thị chi tiết, Thêm
     */

    @GetMapping(value = { "/ca/product", "/ca/product/list" })
    public String showProduct(Model model,
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
        Company company = companyRepository.getOne(loggedInUser.getDefaultCompanyId());
        model.addAttribute("company", company);
        model.addAttribute("page", productRepository.findByCompany(company, pageable));
        return "ca-product";
    }

    @GetMapping(value = "/ca/product/add")
    public String showAddProduct(Model model) {
        model.addAttribute("company", companyRepository.getOne(loggedInUser.getDefaultCompanyId()));
        model.addAttribute("product", new Product());
        return "ca-add-product";
    }

    @PostMapping(value = "/ca/product/add")
    public String addProductProcess(@ModelAttribute Product product, @RequestParam List<MultipartFile> product_photo) {
        Company company = new Company();
        company.setCompanyId(loggedInUser.getDefaultCompanyId());
        product.setCompany(company);
        product.setUrlName(urlCreator.createUrl(product.getName()));
        productRepository.save(product);
        for (MultipartFile photo : product_photo) {
            ProductPicture pp = new ProductPicture();
            pp.setFilename(fileUtil.saveFile(photo, FileUtil.PHOTO));
            pp.setProduct(product);
            productPictureRepository.save(pp);
        }
        return "redirect:/ca/product";
    }

    /*
     * Xử lý job: Hiển thị danh sách, Hiển thị chi tiết, Thêm
     */

    @GetMapping(value = { "/ca/job", "/ca/job/list" })
    public String showJob(Model model, @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
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
        Company company = companyRepository.getOne(loggedInUser.getDefaultCompanyId());
        model.addAttribute("company", company);
        model.addAttribute("page", jobRepository.findByCompany(company, pageable));
        return "ca-job";
    }

    @GetMapping(value = "/ca/job/add")
    public String showAddJob(Model model) {
        model.addAttribute("company", companyRepository.getOne(loggedInUser.getDefaultCompanyId()));
        model.addAttribute("job", new Job());
        model.addAttribute("provinces", pOCRepository.findAll());
        model.addAttribute("tags", tagRepository.findAll());
        return "ca-add-job";
    }

    @PostMapping(value = "/ca/job/add")
    public String addJobProcess(@ModelAttribute Job job, @RequestParam String[] benefit, @RequestParam List<Tag> tags) {
        Company company = new Company();
        company.setCompanyId(loggedInUser.getDefaultCompanyId());
        job.setCompany(company);
        job.setUrlTitle(urlCreator.createUrl(job.getTitle()));
        job.setViewCount(viewCountService.addNew());
        jobRepository.save(job);
        for (String ben : benefit) {
            if (!ben.isEmpty()) {
                Benefit b = new Benefit();
                b.setTitle(ben);
                benefitRepository.save(b);
                BenefitOfJob boj = new BenefitOfJob();
                boj.setBenefit(b);
                boj.setJob(job);
                bOJRepository.save(boj);
            }
        }
        for (Tag tag : tags) {
            TagOfJob toj = new TagOfJob();
            toj.setJob(job);
            toj.setTag(tag);
            tojRepository.save(toj);
        }
        return "redirect:/ca/job";
    }

    /*
     * Xử lý post: Hiển thị danh sách, Hiển thị chi tiết, Thêm
     */

    @GetMapping(value = { "/ca/post", "/ca/post/list" })
    public String showPost(Model model, @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
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
        Company company = companyRepository.getOne(loggedInUser.getDefaultCompanyId());
        model.addAttribute("company", company);
        model.addAttribute("page", postRepository.findByCompany(company, pageable));
        return "ca-post";
    }

    @GetMapping(value = "/ca/post/add")
    public String showAddPost(Model model) {
        model.addAttribute("company", companyRepository.getOne(loggedInUser.getDefaultCompanyId()));
        model.addAttribute("post", new Post());
        return "ca-add-post";
    }

    @PostMapping(value = "/ca/post/add")
    public String addPostProcess(@ModelAttribute Post post) {
        Company company = new Company();
        company.setCompanyId(loggedInUser.getDefaultCompanyId());
        post.setCompany(company);
        post.setUrlTitle(urlCreator.createUrl(post.getTitle()));
        post.setViewCount(viewCountService.addNew());
        postRepository.save(post);
        return "redirect:/ca/post";
    }

    @GetMapping(value = "/ca/info/photo")
    public String showCompanyPhoto(Model model) {
        model.addAttribute("company", companyRepository.getOne(loggedInUser.getDefaultCompanyId()));
        return "ca-photo";
    }

    @PostMapping(value = "/ca/info/photo/upload")
    public String uploadNewPhoto(@RequestParam("photo") List<MultipartFile> photos) {
        Company company = companyRepository.getOne(loggedInUser.getDefaultCompanyId());
        for (MultipartFile photo : photos) {
            PoCompany poc = new PoCompany();
            poc.setPhoto(fileUtil.saveFile(photo, FileUtil.PHOTO));
            poc.setCompany(company);
            pictureOfCompanyRepo.save(poc);
        }
        return "redirect:/ca/info/photo";
    }

    @GetMapping(value = "/ca/info/photo/delete")
    public String getMethodName(@RequestParam("photo") Integer poCompanyId) {
        PoCompany po = pictureOfCompanyRepo.getOne(poCompanyId);
        pictureOfCompanyRepo.delete(po);
        return "redirect:/ca/info/photo";
    }

    @GetMapping(value = "/im-company-admin")
    public String userChooseCompany() {
        return "im-company-admin";
    }

    @Autowired
    EmailService mailSender;

    @PostMapping(value = "/claim-company")
    public String submitToAdminForReview(@RequestParam("companyId") Company company) {
        mailSender.sendEmailCapQuyen(loggedInUser.getUser(), company);
        return "submit-being-review";
    }

    @GetMapping(value = "/submit-new-company")
    public String submitNewCompany(Model model) {
        model.addAttribute("provinces", pOCRepository.findAll());
        model.addAttribute("categories", catRepository.findAll());
        model.addAttribute("company", new Company());
        return "submit-new-company";
    }

    @PostMapping(value = "/submit-new-company")
    public String submitNewCompany(@ModelAttribute("company") Company company) {
        mailSender.iWantToSubmitNewCompany(loggedInUser.getUser(), company);
        return "submit-being-review";
    }

    @GetMapping(value = "/ca/search/profile")
    public String searchForCompany(Model model,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort,
            @RequestParam(name = "location", required = false) Integer provinceId,
            @RequestParam(name = "job_level", required = false, defaultValue = "") String jobLevel,
            @RequestParam(name = "skills", required = false) List<Integer> skillIds,
            @RequestParam(name = "school", required = false, defaultValue = "") String schoolName) {
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by("profileId").ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by("profileId").descending();
        }
        /*
         * Wasted 5 hours then finally found the trick from this :)
         * https://stackoverflow.com/questions/45164322/checking-for-null-on-a-
         * collection-in-jpql-queries/51332354#51332354
         */
        int isSkillEmpty = 1;
        if (skillIds != null && !skillIds.isEmpty()) {
            isSkillEmpty = 0;
        }
        Pageable pageable = PageRequest.of(page, size, sortable);
        model.addAttribute("company", companyRepository.getOne(loggedInUser.getDefaultCompanyId()));
        model.addAttribute("page",
                profileRepository.searchProfile(provinceId, schoolName, jobLevel, skillIds, isSkillEmpty, pageable));
        model.addAttribute("provinces", pOCRepository.findAll());
        model.addAttribute("pcount", profileRepository.count());
        return "ca-search-profile-result";
    }

    @GetMapping(value = { "/ca/application", "/ca/application/list" })
    public String showApplication(Model model, @RequestParam("job") Job job,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort) {
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by("apId").ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by("apId").descending();
        }
        Pageable pageable = PageRequest.of(page, size, sortable);
        Company company = companyRepository.getOne(loggedInUser.getDefaultCompanyId());
        model.addAttribute("job", job);
        model.addAttribute("company", company);
        model.addAttribute("page", appliedProfileRepository.findByJob(job, pageable));
        return "ca-application";
    }

    @GetMapping(value = "/ca/view_profile/{urlName}")
    public String viewProfile(Model model, @PathVariable("urlName") String urlName) {
        model.addAttribute("profile", profileRepository.findByUrlName(urlName));
        model.addAttribute("company", companyRepository.getOne(loggedInUser.getDefaultCompanyId()));
        return "ca-view-profile";
    }

}