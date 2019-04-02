package com.tfg.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.model.DigitalAsset;
import com.tfg.services.DigitalAssetService;

@RestController
public class DigitalAssetController {

	@Autowired
	DigitalAssetService service;
	
	@RequestMapping("/getDigitalAssetsByFilter")
    public List<DigitalAsset> getDigitalAssetByFilter(@RequestParam String name, @RequestParam Object valor) {
        /*return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));*/
		return service.getDigitalAssetsByFilter(name, valor);
    }
	
	@RequestMapping("/getDigitalAssetsByFilters")
    public List<DigitalAsset> getDigitalAssetsByFilters(@RequestParam Map<String,Object> allRequestParams ) {
        /*return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));*/
		return service.getDigitalAssetsByFilters(allRequestParams);
    }
	
	@RequestMapping("/getDigitalAssets")
    public List<DigitalAsset> getDigitalAssets() {
        /*return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));*/
		return service.getDigitalAssets();
    }
}
