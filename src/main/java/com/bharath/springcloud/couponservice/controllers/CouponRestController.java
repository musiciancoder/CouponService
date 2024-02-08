package com.bharath.springcloud.couponservice.controllers;

import com.bharath.springcloud.couponservice.model.Coupon;
import com.bharath.springcloud.couponservice.repos.CouponRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/couponapi")
@CrossOrigin
public class CouponRestController {

    @Autowired
    CouponRepo repo;

    @RequestMapping(value = "/coupons", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')") //esto es para test unitarios de spring security
    public Coupon create (@RequestBody Coupon coupon){
        return repo.save(coupon);
    }

    @RequestMapping(value = "/coupons/{code}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER','ADMIN')") //esto es para test unitarios de spring security
    public Coupon getCoupon (@PathVariable("code") String code){
        return repo.findByCode(code);
    }
}
