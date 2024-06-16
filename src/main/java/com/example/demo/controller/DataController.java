package com.example.demo.controller;

import com.example.demo.entity.Customer;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DataController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/abc")
    public String getTheData(
            @RequestParam("fname") String firstname,
            @RequestParam("mname") String middlename,
            @RequestParam("lname") String lastname,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            RedirectAttributes redirectAttributes
    ) {
        Customer user = new Customer();
        user.setFirstName(firstname);
        user.setMiddleName(middlename);
        user.setLastName(lastname);
        user.setEmail(email);
        user.setPassword(password);

        try {
            Customer savedUser = userRepository.save(user);
            redirectAttributes.addAttribute("success", true);
            redirectAttributes.addFlashAttribute("addedEmployee", savedUser); // Pass added employee as flash attribute
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", true);
        }
        return "redirect:/";
    }

    @GetMapping("/employees")
    public String showEmployees(Model model) {
        // Fetch all employees from repository
        Iterable<Customer> employees = userRepository.findAll();
        model.addAttribute("employees", employees);
        return "Employee";
    }
}
