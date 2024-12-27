package Zest.gym.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import Zest.gym.model.AttendanceSheet;
import Zest.gym.model.User;
import Zest.gym.model.MembershipOwned;
import Zest.gym.model.Schedule;
import Zest.gym.repository.AttendanceRepository;
import Zest.gym.repository.MembershipOwnedRepository;
import Zest.gym.repository.ScheduleRepository;
import Zest.gym.repository.UserRepository;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;





@Controller
public class LoginSignupContoller {
	@Autowired
	private UserRepository uRepo;
	
	@Autowired
	private AttendanceRepository aRepo;
	
	@Autowired
	private MembershipOwnedRepository mRepo;
	
	@Autowired
	private ScheduleRepository sRepo;
	
	
	
	
	@GetMapping("/")
	public String LandingPage() {
		return "User/landing.html";
	}
	
	
	@GetMapping("/login")
	public String LoginPage() {
		return "User/login.html";
	}
	
	@GetMapping("/register")
	public String RegistarionPage() {
		return "User/register.html";
	}
	
	@PostMapping("/register")
	public String userRegistration(@ModelAttribute User u, Model model) {
		Optional<User> existingUser = uRepo.findByEmail(u.getEmail());
		if(existingUser.isPresent()) {
			model.addAttribute("errormessage", "Email already exist! Try new one");
			return "User/index.html";
		}
		
		String hashPwd = DigestUtils.sha3_256Hex(u.getPassword());
		u.setPassword(hashPwd);
		uRepo.save(u);
		model.addAttribute("message", "Signup successful!!");
		return "User/index.html";
	}
	
	@PostMapping("/login")
	public String loginUser(@ModelAttribute User u, Model model, HttpSession session) {
		if (uRepo.existsByEmailAndPassword(u.getEmail(), DigestUtils.sha3_256Hex(u.getPassword()))) {
			String role = uRepo.findRoleByEmail(u.getEmail());
			String username = uRepo.findUsernameByEmail(u.getEmail());
			if (username != null && role.equals("user")) {
				model.addAttribute("email", u.getEmail());
				model.addAttribute("message", "Login successful! Please click on dashborad.");
				session.setAttribute("email", u.getEmail());
				session.setAttribute("username", username);
				System.out.println(session.getAttribute("email"));
				session.setMaxInactiveInterval(1800);
				return "User/index.html";
			}else {
				model.addAttribute("email", u.getEmail());
				model.addAttribute("message", "Login successful! Please click on dashborad.");
				session.setAttribute("email", u.getEmail());
				session.setAttribute("username", username);
				System.out.println(session.getAttribute("email"));
				System.out.println(session.getAttribute("username"));
				session.setMaxInactiveInterval(1800);
				return "Trainer/Tindex.html";
			}
			

		}
		model.addAttribute("errormessage", "Username or password incorrect. Please try again!");
		return "User/login.html";
	}
	
	
	@GetMapping("/forgotPwd")
	public String forgotPassword() {
		return "User/forgotPwd.html";
	}
	
	@PostMapping("/forgotPwd")
	public String forgotPasswordEmail(Model model,HttpSession session, @RequestParam("email") String email){
		int otp = new MailSender().sendOtp(email);
		session.setAttribute("otp", otp);
		session.setAttribute("email", email);
		System.out.println(otp + email);
		return "User/otp.html";
	}
	
	
	 @PostMapping("/checkOtp") public String otpChecker(HttpSession
			  session, @RequestParam("otpcode")Integer otpcode){
			  if(session.getAttribute("otp")!=null) {
				  String email = (String) session.getAttribute("email");
			  int otp = (int) session.getAttribute("otp"); 
			  if(otp == otpcode) { 
				  String status="matched"; 
				  session.setAttribute("status", status);
				  System.out.println(session.getAttribute("status"));
				  session.setAttribute("email", email);
				  System.out.println(email);
				  return "User/resetPwd.html";
				  }
			  else{ 
					  String status="mismatch"; 
					  session.setAttribute("status", status);
					  System.out.println(session.getAttribute("status"));
					  return "User/otp.html" ; 
			  }
			  }
			  
			  return "User/forgetPwd.html" ; 
	 }
	 
	 
	 @PostMapping("/newPassword")
	  public String newPassword(@ModelAttribute User u, HttpSession session, @RequestParam("newPassword") String newPassword){
		  String email =  (String) session.getAttribute("email");
		  if(email != null) {
			  	String hashPwd = DigestUtils.sha3_256Hex(newPassword);
				u.setPassword(hashPwd);
			  	uRepo.updatePasswordByEmail(email, hashPwd);
				new MailSender().sendPasswordChangeMessage(email);
				System.out.println("password changed successfully" + hashPwd);
				session.invalidate();
		  }
		  return "User/landing.html";
		  
		  
	  }
	 
	 @GetMapping("/about-us")
	 public String aboutUs() {
	 	return "User/about-us.html";
	 }
	 
	 @GetMapping("/team")
	 public String team() {
	 	return "User/team.html";
	 }
	 
	 
	 @GetMapping("/gallery")
	 public String gallery() {
	 	return "User/gallery.html";
	 }
	 
	 @GetMapping("/blog")
	 public String blog() {
	 	return "User/blog.html";
	 }
	 
	 @GetMapping("/404")
	 public String error() {
	 	return "User/404.html";
	 	
	 }
	 
	 @GetMapping("/contact")
	 public String contact() {
	 	return "User/contact.html";
	 	
	 }
	 
	 @GetMapping("/profile")
	 public String profile() {
		 
		 
	 	return "User/profile.html";
	 	
	 }
	 
	 @GetMapping("/index")
	 public String index() {
	 	return "User/index.html";
	 }
	 
	 
	 @GetMapping("/membership")
	 public String membershipPage() {
	 	return "User/membership.html";
	 }
	 
	 @GetMapping("/membershipForm")
	 public String membershipForm() {
	 	return "User/membershipForm.html";
	 }
	 
	 @PostMapping("/membershipForm")
	 public String membershipFormData(@ModelAttribute MembershipOwned m) {
		mRepo.save(m);
	 	return "User/membershipForm.html";
	 }
	 
	 //Activities
	 @GetMapping("/class-timetable")
	 public String getClassTimetable(Model model) {
	     List<Schedule> schedules = sRepo.findSchedulesByDaysAndTimeSlots();

	     // Filter the schedules for each day and time slot
	     Map<String, Schedule> filteredSchedules = new HashMap<>();
	     for (Schedule schedule : schedules) {
	         String key = schedule.getDay() + "-" + schedule.getTimeSlot();
	         filteredSchedules.put(key, schedule);
	     }

	     model.addAttribute("filteredSchedules", filteredSchedules);

	     // Log the schedules for debugging
	     if (schedules != null && !schedules.isEmpty()) {
	         System.out.println("Schedules for specific days and time slots:");
	         for (Schedule schedule : schedules) {
	             System.out.println("Day: " + schedule.getDay() +
	                                ", Time Slot: " + schedule.getTimeSlot() +
	                                ", Trainer: " + schedule.getTrainer() +
	                                ", Activity: " + schedule.getActivity());
	         }
	     } else {
	         System.out.println("No schedules found for the specified days and time slots.");
	     }

	     return "User/class-timetable";
	 }

	 @GetMapping("/bmi-calculator")
	 public String bmiCalculator() {
	 	return "User/bmi-calculator.html";
	 }
	 
	 @GetMapping("/activities")
	 public String activities() {
	 	return "User/activities.html";
	 }
	 
	 //Workout
	 @GetMapping("/video")
	 public String video() {
	 	return "User/video.html";
	 }
	 
	 @GetMapping("/diet")
	 public String deit() {
	 	return "User/diet.html";
	 }
	 
	 @GetMapping("attendance")
		public String trainerAttendancepage() {
			return "User/userAttendance.html";
		}
		
	@PostMapping("attendance")
	public String postMethodName(@ModelAttribute AttendanceSheet a, HttpSession session) {
		if(session != null) {
		String email = (String) session.getAttribute("email");
		a.setDate(LocalDate.now());
		a.setEmail(email);
		aRepo.save(a);
		}
		return "User/userAttendance.html";
	}
		
	 
	 
	 

	 
	 
	 
	 
	 
	 
	 
	 

	
	
	
	
	
	
	
	
	
	

}
