package Zest.gym.controller;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import Zest.gym.model.AttendanceSheet;
import Zest.gym.model.Diet;
import Zest.gym.model.Trainer;
import Zest.gym.model.membershipDetails;
import Zest.gym.model.schedule;
import Zest.gym.model.video;
import Zest.gym.repository.AttendanceRepository;
import Zest.gym.repository.dietRepository;
import Zest.gym.repository.membershipDetailsRepository;
import Zest.gym.repository.scheduleRepository;
import Zest.gym.repository.trainerRepository;
import Zest.gym.repository.videoRepository;
import jakarta.servlet.http.HttpSession;

@Controller
public class adminController {
	@Autowired
	private membershipDetailsRepository mRepo;
	
	@Autowired
	private trainerRepository tRepo;
	
	@Autowired
	private videoRepository vRepo;
	
	@Autowired
	private dietRepository dRepo;
	

	@Autowired 
	private scheduleRepository sRepo;
	
	
	
	
	
	
	@GetMapping("/adminDash")
	public String admminDashboard() {
		return "Admin/index.html";
	}
	
	
	@GetMapping("/adminLogin")
	public String admminLoginPage() {
		return "Admin/adminLogin.html";
	}
	
	@GetMapping("/addMembership")
	public String addMembership() {
		return "Admin/addMembership.html";
	}
	
	@PostMapping("/addMembership")
	public String addMembershipDetails(@ModelAttribute membershipDetails m) {
		
		mRepo.save(m);
		
		return "Admin/addMembership.html";
	}
	
	@GetMapping("/addTrainer")
	public String addTrainer() {
		return "Admin/addTrainer.html";
	}
	
	@PostMapping("/addTrainer")
	public String addTrainerData(
	        @ModelAttribute Trainer trainer,
	        @RequestParam("Trainerimage") MultipartFile Fimage, @RequestParam("name") String name, @RequestParam("email") String email,  @RequestParam("contact") String contact, @RequestParam("address") String address ) throws IOException {

		
		
		trainer.setName(name);
        trainer.setEmail(email);
        trainer.setContact(contact);
        trainer.setAddress(address);
        
        String uploadDir = Paths.get("src", "main", "resources", "static", "assets").toString();

	    // Process image
	    if (!Fimage.isEmpty()) {
	        try {
	            // Get the original filename
	            String imageName = StringUtils.cleanPath(Fimage.getOriginalFilename());
	            
	            // Create a path for the image file in the upload directory
	            Path imagePath = Paths.get(uploadDir, imageName);

	            // Create the directory if it doesn't exist
	            Files.createDirectories(imagePath.getParent());

	            // Save the image file
	            Fimage.transferTo(imagePath);

	            // Save the image path to the user object (the URL should be relative for serving)
	            trainer.setImage(imageName);  // You can serve images from static folder using this URL
	        } catch (IOException e) {
//	            model.addAttribute("error", "Failed to upload image.");
	            return "redirect:/addTrainer";  // Return to the form if there was an error
	        }
	    }

        tRepo.save(trainer);
	    
	    return "Admin/addTrainer.html";
	}

	@GetMapping("/addVideo")
	public String addVideo() {
		return "Admin/addVideo.html";
	}
	
	
	@PostMapping("/addVideo")
	public String addVideoData(@ModelAttribute video v) {
		
		vRepo.save(v);
		return "Admin/addVideo.html";
	}
	
	
	@GetMapping("/manageAttendance")
	public String manageAttendance() {
		return "Admin/manageAttendance.html";
	}
	
	@GetMapping("/manageDiet")
	public String manageDiet() {
		return "Admin/manageDiet.html";
	}
	@GetMapping("/manageTrainer")
	public String manageTraine() {
		return "Admin/manageTrainer.html";
	}
	
	@GetMapping("/manageUser")
	public String manageTrainer() {
		return "Admin/manageTrainer.html";
	}
	@GetMapping("/manageVideo")
	public String manageVideo() {
		return "Admin/manageVideo.html";
	}
	
	@GetMapping("/addDiet")
	public String addDiet() {
		return "Admin/addDiet.html";
	}
	
	@PostMapping("/addDiet")
	public String addDietData(@ModelAttribute Diet d, @RequestParam("dietImage") MultipartFile image) {
		String uploadDir = Paths.get("src", "main", "resources", "static", "assets").toString();

	    // Process image
	    if (!image.isEmpty()) {
	        try {
	            // Get the original filename
	            String imageName = StringUtils.cleanPath(image.getOriginalFilename());
	            
	            // Create a path for the image file in the upload directory
	            Path imagePath = Paths.get(uploadDir, imageName);

	            // Create the directory if it doesn't exist
	            Files.createDirectories(imagePath.getParent());

	            // Save the image file
	            image.transferTo(imagePath);

	            // Save the image path to the user object (the URL should be relative for serving)
	            d.setImage(imageName);  // You can serve images from static folder using this URL
	        } catch (IOException e) {
//	            model.addAttribute("error", "Failed to upload image.");
	            return "redirect:/addTrainer";  // Return to the form if there was an error
	        }
	    }

	    dRepo.save(d);
		return "Admin/addDiet.html";
	}
	
	
	
	
	
	@GetMapping("/addSchedule")
	 public String addSchedule() {
	 	return "Admin/addSchedule.html";
	 }
	 
	 @PostMapping("/addSchedule")
	 public String addScheduleData(@ModelAttribute schedule s) {
	 	sRepo.save(s);
		return "Admin/addSchedule.html";
	 }
	
	
	

}
