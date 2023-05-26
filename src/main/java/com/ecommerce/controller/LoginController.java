package com.ecommerce.controller;

import com.ecommerce.dto.AdminDto;
import com.ecommerce.model.Admin;
import com.ecommerce.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    private AdminService adminService;
    @GetMapping("/login")
    public String loginForm(){

        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("adminDto", new AdminDto()); // Add the adminDto object to the model
        return "register"; // Return the view name
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(Model model){
        return "forgot-password";
    }
    @PostMapping("/register-new")
    public String addNewAdmin(@Valid @ModelAttribute("adminDto") AdminDto adminDto,
                              BindingResult result,
                              Model model,
                              HttpSession session) {
        System.out.println("PostMapping register-new");
        model.addAttribute("adminDto", new AdminDto());
        try {
            session.removeAttribute("message");
            if (result.hasErrors()) {
                System.out.println("PostMapping register-new: try block: if result.hasErrors");
                for (ObjectError error : result.getAllErrors()) {
                    System.out.println(error.getDefaultMessage());
                }
                model.addAttribute("adminDto", adminDto);
                result.toString();
                return "register";
            }
            String username = adminDto.getUsername();
            Admin admin = adminService.findByUsername(username);
            if (admin != null) {
                model.addAttribute("adminDto", adminDto);
                System.out.println("Admin not null");
                session.setAttribute("meaage", "Your email has been registered already!");
                return "register";
            }
            if(adminDto.getPassword().equals(adminDto.getRepeatPassword())){
                adminService.save(adminDto);
                System.out.println(("success"));
                session.setAttribute("message", "Registered Successfully!");
                model.addAttribute("adminDto", adminDto);
            }else{
                model.addAttribute("adminDto", adminDto);
                session.setAttribute("message", "Password is not same!");
                System.out.println("password not same");
                return "register";
            }

        } catch (Exception e) {
          session.setAttribute("message", "Service Error, please try again later");
        }

        return "register";
    }
}
