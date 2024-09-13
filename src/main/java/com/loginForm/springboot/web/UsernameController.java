package com.loginForm.springboot.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.multipart.MultipartFile;

import com.loginForm.springboot.model.Booking;
import com.loginForm.springboot.model.Username;

import com.loginForm.springboot.repository.UsernameRepository;



import java.io.IOException;

import java.nio.file.Files;

import java.nio.file.Path;

import java.nio.file.Paths;

import java.util.List;

import java.util.UUID;

import javax.servlet.http.HttpSession;

@Controller

public class UsernameController {



    @Autowired

    private UsernameRepository usernameRepository;
 


    private static final String UPLOADED_FOLDER = "src/main/resources/static/img/";



    @GetMapping("/add-user")

    public String showAddUserForm(Model model) {

        model.addAttribute("username", new Username());

        return "add-username";

    }



    @PostMapping("/add-user")
    public String addUser(@RequestParam("username") String username,
                          @RequestParam("location") String location,
                          @RequestParam("mapLocation") String mapLocation,
                          @RequestParam("contactNumber") String contactNumber,
                          @RequestParam("password") String password,
                          @RequestParam("image") MultipartFile image,
                          @RequestParam("openingDays") String openingDays,
                          @RequestParam("vehicles") List<String> vehicles,
                          @RequestParam("service") List<String> services,
                          @RequestParam("openingTime") String openingTime,
                          @RequestParam("closingTime") String closingTime,
                          RedirectAttributes redirectAttributes) throws IOException {

        // Check for username validity
        if ("admin".equalsIgnoreCase(username)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Username already exists or cannot be 'admin'.");
            return "redirect:/add-user";
        }

        String imageName = null;
        if (!image.isEmpty()) {
            String originalFilename = image.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
            imageName = UUID.randomUUID().toString() + extension;
            Path path = Paths.get(UPLOADED_FOLDER + imageName);
            Files.copy(image.getInputStream(), path);
        }

        Username user = new Username();
        user.setUsername(username);
        user.setLocation(location);
        user.setMapLocation(mapLocation);
        user.setContactNumber(contactNumber);
        user.setPassword(password);
        user.setImage(imageName);
        user.setOpeningDays(openingDays);
        user.setVehicles(vehicles);
        user.setServices(services);
        user.setOpeningTime(openingTime);
        user.setClosingTime(closingTime);

        usernameRepository.save(user);
        return "redirect:/users";
    }


    @GetMapping("/edit-user/{id}")

    public String showEditUserForm(@PathVariable("id") Long id, Model model) {

        Username user = usernameRepository.findById(id)

                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID:" + id));

        model.addAttribute("username", user);

        return "edit";

    }



    @PostMapping("/update-user/{id}")

    public String updateUser(@PathVariable("id") Long id,

                             @RequestParam("username") String username,

                             @RequestParam("location") String location,
                             @RequestParam("mapLocation") String mapLocation,

                             @RequestParam("contactNumber") String contactNumber,

                             @RequestParam("password") String password,

                             @RequestParam("image") MultipartFile image, RedirectAttributes redirectAttributes ) throws IOException {
    	
    	
    	
    	
    	// Check if username already exists or is "admin"
        if ("admin".equalsIgnoreCase(username)) {
          
			redirectAttributes.addFlashAttribute("errorMessage", "Username already exists or cannot be 'admin'.");
            return "redirect:/edit-user/{id}";
        }


        Username user = usernameRepository.findById(id)

                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID:" + id));



        if (!image.isEmpty()) {

            // Delete the old image file if needed (optional)

            if (user.getImage() != null) {

                Path oldImagePath = Paths.get(UPLOADED_FOLDER + user.getImage());

                Files.deleteIfExists(oldImagePath);

            }



            // Save the new image

            String originalFilename = image.getOriginalFilename();

            String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));

            String imageName = UUID.randomUUID().toString() + extension;

            Path path = Paths.get(UPLOADED_FOLDER + imageName);

            Files.copy(image.getInputStream(), path);

            user.setImage(imageName);

        }



        user.setUsername(username);

        user.setLocation(location);

        user.setContactNumber(contactNumber);

        user.setPassword(password);
        
        user.setMapLocation(mapLocation);



        usernameRepository.save(user);

        return "redirect:/users";

    }



    @GetMapping("/delete-user/{id}")

    public String deleteUser(@PathVariable("id") Long id) {

        Username user = usernameRepository.findById(id)

                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID:" + id));

        usernameRepository.delete(user);

        return "redirect:/users";

    }



    @GetMapping("/users")

    public String getAllUsers(Model model) {

        List<Username> users = usernameRepository.findAll();

        model.addAttribute("users", users);

        return "user-details";

    }
    @GetMapping("/main")

    public String getAllUserss(@RequestParam(name = "id", required = false) Long id,Model model) {

        List<Username> userss = usernameRepository.findAll();

        model.addAttribute("userss", userss);
        
        if (id != null) {
            Username vendor = usernameRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid booking Id:" + id));
            model.addAttribute("selectedUser", vendor);
        }


        return "main";

    }
   
    
}