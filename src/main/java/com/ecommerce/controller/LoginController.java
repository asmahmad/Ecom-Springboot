package com.ecommerce.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecommerce.dto.AdminDto;
import com.ecommerce.model.Admin;
import com.ecommerce.service.AdminService;

@Controller
public class LoginController {

    @Autowired
    private AdminService adminService;
    
    @GetMapping("/login")
    public String loginForm(){

        return "login";
    }
    
    @RequestMapping("/index")
    public String home() {
 
    	return "index";
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
    public String addNewAdmin(@Valid @ModelAttribute("adminDto")AdminDto adminDto,
                              BindingResult result,
                              Model model){

         try {

             if(result.hasErrors()){
                 model.addAttribute("adminDto", adminDto);
                 result.toString();
                 return "register";
             }
             String username = adminDto.getUsername();
             Admin admin = adminService.findByUsername(username);
             if(admin != null){
                 model.addAttribute("adminDto", adminDto);
                 System.out.println("admin not null");
                 model.addAttribute("emailError", "Your email has already been registered!");
                 return "register";
             }
             if(adminDto.getPassword().equals(adminDto.getRepeatPassword())){
                 adminService.save(adminDto);
                 System.out.println("success");
                 model.addAttribute("success", "Register successfully!");
                 model.addAttribute("adminDto", adminDto);
             }else{
                 model.addAttribute("adminDto", adminDto);
                 model.addAttribute("passwordError", "Your password maybe wrong! Check again!");
                 System.out.println("password not same");
                 return "register";
             }
         }catch (Exception e){
             e.printStackTrace();
             model.addAttribute("errors", "The server has been wrong!");
         }
         return "register";

    }

    
}
