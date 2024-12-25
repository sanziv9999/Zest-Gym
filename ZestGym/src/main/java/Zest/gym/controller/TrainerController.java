package Zest.gym.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;



import Zest.gym.model.AttendanceSheet;
import Zest.gym.model.Trainer;
import Zest.gym.model.video;
import Zest.gym.repository.AttendanceRepository;
import Zest.gym.repository.trainerRepository;
import Zest.gym.repository.videoRepository;
import jakarta.servlet.http.HttpSession;



@Controller
public class TrainerController {
	
	@Autowired
	private AttendanceRepository aRepo;
	
	@Autowired
	private trainerRepository tRepo; 
	

	@Autowired
	private videoRepository vRepo;
	
	
	@GetMapping("t-index")
	public String trainerIndex() {
		return "Trainer/Tindex.html";
	}
	
	@GetMapping("t-class-timetable")
	public String trainerClassTimetable() {
		return "Trainer/Tclass-timetable.html";
	}
	@GetMapping("t-editProfile")
	public String trainerEditProfile() {
		return "Trainer/TeditProfile.html";
	}
	@GetMapping("t-profile")
	public String trainerProfile(HttpSession session, @ModelAttribute Trainer t, Model model) {
		
		String email =  (String) session.getAttribute("email");
		Optional<Trainer> tList = tRepo.findByEmail(email);
		model.addAttribute("tList", tList);
		return "Trainer/Tprofile.html";
	}
	
	@GetMapping("/editProfile/{id}")
	public String editProfile(@PathVariable int id, Model model) {
		Optional<Trainer> tList = tRepo.findById(id);
		model.addAttribute("tList", tList);
		return "Trainer/TeditProfile.html";
	}
	
	
	@PostMapping("/saveEditedprofile")
	public String postMethodName(HttpSession session, 
	                              @RequestParam int id,
	                              @RequestParam String name, 
	                              @RequestParam String address, 
	                              @RequestParam String email, 
	                              @RequestParam String contact,  
	                              Model model) {
	    
	    Optional<Trainer> t = tRepo.findById(id);
	    
	    if (t.isPresent()) {  // Ensure the Optional contains a value
	        Trainer trainer = t.get();  // Retrieve the actual Trainer object
	        trainer.setName(name);
	        trainer.setAddress(address);
	        trainer.setContact(contact);
	        trainer.setEmail(email);
	        tRepo.save(trainer);  // Save the updated trainer object
	        
	        // Get the email from the session and find the updated trainer details
	        String semail = (String) session.getAttribute("email");
	        Optional<Trainer> tList = tRepo.findByEmail(semail);
	        
	        model.addAttribute("tList", tList);
	        return "Trainer/Tprofile.html";  // Return the profile page with updated info
	    }

	    // If the trainer with the given id was not found
	    String semail = (String) session.getAttribute("email");
	    Optional<Trainer> tList = tRepo.findByEmail(semail);
	    model.addAttribute("tList", tList);
	    return "Trainer/Tprofile.html";  // Return the profile page if no trainer was found
	}

	@PostMapping("/saveProfile")
	public String saveProfile(@RequestParam("image") MultipartFile Fimage, 
	                          @RequestParam("id") int id, HttpSession session, 
	                          Model model) {
	    Optional<Trainer> trainerOpt = tRepo.findById(id);
	    
	    if (trainerOpt.isPresent()) {
	        Trainer trainer = trainerOpt.get();
	        
	        String uploadDir = Paths.get("src", "main", "resources", "static", "assets").toString();
	        if (!Fimage.isEmpty()) {
	            try {
	                // Get the original filename
	                String imageName = StringUtils.cleanPath(Fimage.getOriginalFilename());
	                
	                // Define the path to save the image (e.g., in the "uploads" directory)
	                Path imagePath = Paths.get(uploadDir, imageName);

	                // Create the directory if it doesn't exist
	                Files.createDirectories(imagePath.getParent());

	                // Save the image file to the defined path
	                Fimage.transferTo(imagePath);

	                // Save the image name (or relative path) to the trainer object
	                trainer.setImage(imageName);  // You can serve images from the static folder using this URL
	            } catch (IOException e) {
	                // If there was an error uploading, show an error message and redirect back
	                model.addAttribute("error", "Failed to upload image.");
	                String semail = (String) session.getAttribute("email");
	        	    Optional<Trainer> tList = tRepo.findByEmail(semail);
	        	    model.addAttribute("tList", tList);
	                return "Trainer/Tprofile.html";  
	            }
	        }

	        // Save the trainer data (including the updated image path)
	        tRepo.save(trainer);
	        
	        String semail = (String) session.getAttribute("email");
		    Optional<Trainer> tList = tRepo.findByEmail(semail);
		    model.addAttribute("tList", tList);
	        return "Trainer/Tprofile.html";   
	    }

	    // If the trainer with the given id was not found, return an error page or the form
	    model.addAttribute("error", "Trainer not found");
	    String semail = (String) session.getAttribute("email");
	    Optional<Trainer> tList = tRepo.findByEmail(semail);
	    model.addAttribute("tList", tList);
        return "Trainer/Tprofile.html";   
	}

	
	
	
	
	
	@GetMapping("t-video")
	public String trainerVideo(@ModelAttribute video v, Model model) {
		List<video> vList = vRepo.findAll();
		model.addAttribute("vList", vList);
		return "Trainer/Tvideo.html";
	}
	
	@GetMapping("t-activities")
	public String trainerActivities() {
		return "Trainer/Tactivities.html";
	}
	
	
	
	@GetMapping("t-attendance")
	public String trainerAttendancepage() {
		return "Trainer/Tattendance.html";
	}
	
	@PostMapping("trainerAttendance")
	public String postMethodName(@ModelAttribute AttendanceSheet a, HttpSession session) {
		if(session != null) {
		String email = (String) session.getAttribute("email");
//		String email = "a@gmail.com";
		a.setDate(LocalDate.now());
		a.setEmail(email);
		aRepo.save(a);
		}
		return "Trainer/Tattendance.html";
	}
	
	@GetMapping("/addTVideo")
	public String addVideo() {
	
		
		return "Admin/addVideo.html";
		
		
	}
	
	
	@PostMapping("/addTVideo")
	public String addVideoData(@ModelAttribute video v, Model model) {
		
		vRepo.save(v);
		List<video> vList = vRepo.findAll();
		model.addAttribute("vList", vList);
		return "Trainer/Tvideo.html";
	}

}
