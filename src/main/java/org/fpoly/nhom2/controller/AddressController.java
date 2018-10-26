package org.fpoly.nhom2.controller;

import java.util.List;

import org.fpoly.nhom2.entiry.CityOrDist;
import org.fpoly.nhom2.entiry.Commune;
import org.fpoly.nhom2.repository.CityOrDistRepository;
import org.fpoly.nhom2.repository.POCRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * AddressController
 * @author Vu Duong
 */
@Controller
public class AddressController {

    @Autowired POCRepository pOCRepository;

    @Autowired CityOrDistRepository districtRepository;

    @ResponseBody
    @RequestMapping(value = "/api/get-district", method = RequestMethod.GET)
    public List<CityOrDist> getDistricts(@RequestParam Integer provinceId) {
        return pOCRepository.getOne(provinceId).getCityOrDists();
    }

    @ResponseBody
    @RequestMapping(value = "/api/get-commune", method = RequestMethod.GET)
    public List<Commune> getCommunes(@RequestParam Integer distId) {
        return districtRepository.getOne(distId).getCommunes();
    }
}