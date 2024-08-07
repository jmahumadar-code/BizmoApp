package com.bizmobiz.bizmoweb.web.rest;

import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bizmobiz.bizmoweb.domain.Country;
import com.bizmobiz.bizmoweb.repository.CountryRepository;
import com.bizmobiz.bizmoweb.util.Constant;

@Controller
@RequestMapping(path = "/api")
public class CountryController {
	
	@Autowired
	private CountryRepository countryRepository;

	@GetMapping(path = "/country")
    public @ResponseBody Iterable<Country> findAll() throws URISyntaxException, Exception {
		return countryRepository.findByIsEnabled(Constant.COUNTRY_IS_ENABLED);
    }
}
