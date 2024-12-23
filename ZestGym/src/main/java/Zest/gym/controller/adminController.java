package Zest.gym.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import Zest.gym.model.Trainer;
import Zest.gym.model.membershipDetails;
import Zest.gym.model.video;
import Zest.gym.repository.membershipDetailsRepository;
import Zest.gym.repository.trainerRepository;
import Zest.gym.repository.videoRepository;

@Controller
public class adminController {
	@Autowired
	private membershipDetailsRepository mRepo;
	
	@Autowired
	private trainerRepository tRepo;
	
	@Autowired
	private videoRepository vRepo;
	
	
	
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
	        @RequestParam("image") MultipartFile Fimage, @RequestParam("name") String name, @RequestParam("email") String email,  @RequestParam("contact") String contact, @RequestParam("address") String address ) throws IOException {

		
		
		trainer.setName(name);
        trainer.setEmail(email);
        trainer.setContact(contact);
        trainer.setAddress(address);
        trainer.setImage(Fimage.getOriginalFilename());
        tRepo.save(trainer);
	    // Check if a file is uploaded
	    if (!Fimage.isEmpty()) {
	    	
	    	 try {
	                File saveDir = new ClassPathResource("static/assets").getFile();
	                Path companyLogoPath = Paths.get(saveDir.getAbsolutePath() + File.separator + Fimage.getOriginalFilename());
	                Files.copy(Fimage.getInputStream(), companyLogoPath, StandardCopyOption.REPLACE_EXISTING);
	                System.out.println("Company Logo saved to: " + companyLogoPath);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	    
	    }
	    
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
	

}
