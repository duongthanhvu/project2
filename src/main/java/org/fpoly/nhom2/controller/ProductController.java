package org.fpoly.nhom2.controller;

import org.fpoly.nhom2.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ProductController
 */
@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping(value = "/product")
    public String showProduct(Model model,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort) {
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by("productId").ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by("productId").descending();
        }
        Pageable pageable = PageRequest.of(page, size, sortable);
        model.addAttribute("page", productRepository.findAll(pageable));
        return "product";
    }
}