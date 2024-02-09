package com.bharath.springcloud.couponservice.controllers;



import com.bharath.springcloud.couponservice.model.Coupon;
import com.bharath.springcloud.couponservice.repos.CouponRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;


@Controller
public class CouponController {

    @Autowired
    private CouponRepo repo;

    @GetMapping("/showCreateCoupon") // esta ruta esta en los html
    public String showCreateCoupon() {
        return "createCoupon";
    }

    @PostMapping("/saveCoupon")  // esta ruta esta en los html
    public String save(Coupon coupon) {
        repo.save(coupon);
        return "createResponse";
    }

    @GetMapping("/showGetCoupon")
    //@PreAuthorize("hasRole('ADMIN')") //Lo agregó en sección "level security method"
    //@RolesAllowed("ADMIN") //Lo mismo q @PreAuthorize("hasRole('ADMIN')") pero en versiones anteriores
    //Secure("ADMIN) //Lo mismo q @PreAuthorize("hasRole('ADMIN')") pero en versiones anteriores
    public String showGetCoupon() {
        return "getCoupon";
    }

    @PostMapping("/getCoupon")
    public ModelAndView getCoupon(String code) {
        ModelAndView mav = new ModelAndView("couponDetails");
        mav.addObject(repo.findByCode(code));
        return mav;
    }

}
