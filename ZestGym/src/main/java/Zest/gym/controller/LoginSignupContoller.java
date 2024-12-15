package Zest.gym.controller;

import java.util.Optional;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import Zest.gym.model.User;
import Zest.gym.repository.userRepository;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;




@Controller
public class LoginSignupContoller {
	@Autowired
	private userRepository uRepo;
	
	
	@GetMapping("/")
	public String LandingPage() {
		return "landing.html";
	}
	
	
	@GetMapping("/login")
	public String LoginPage() {
		return "login.html";
	}
	
	@GetMapping("/register")
	public String RegistarionPage() {
		return "register.html";
	}
	
	@PostMapping("/register")
	public String userRegistration(@ModelAttribute User u, Model model) {
		Optional<User> existingUser = uRepo.findByEmail(u.getEmail());
		if(existingUser.isPresent()) {
			model.addAttribute("errormessage", "Email already exist! Try new one");
			return "index.html";
		}
		
		String hashPwd = DigestUtils.sha3_256Hex(u.getPassword());
		u.setPassword(hashPwd);
		uRepo.save(u);
		model.addAttribute("message", "Signup successful!!");
		return "index.html";
	}
	
	@PostMapping("/login")
	public String loginUser(@ModelAttribute User u, Model model, HttpSession session) {
		if (uRepo.existsByEmailAndPassword(u.getEmail(), DigestUtils.sha3_256Hex(u.getPassword()))) {
			String username = uRepo.findUsernameByEmail(u.getEmail());
			if (username != null) {
				model.addAttribute("email", u.getEmail());
				model.addAttribute("message", "Login successful! Please click on dashborad.");
				session.setAttribute("email", u.getEmail());
				session.setAttribute("username", username);
				System.out.println(session.getAttribute("email"));
				session.setMaxInactiveInterval(1800);
				return "index.html";
			}
			

		}
		model.addAttribute("errormessage", "Username or password incorrect. Please try again!");
		return "login.html";
	}
	
	
	@GetMapping("/forgotPwd")
	public String forgotPassword() {
		return "forgotPwd.html";
	}
	
	@PostMapping("/forgotPwd")
	public String forgotPasswordEmail(Model model,HttpSession session, @RequestParam("email") String email){
		int otp = new mailSender().sendOtp(email);
		session.setAttribute("otp", otp);
		session.setAttribute("email", email);
		System.out.println(otp + email);
		return "otp.html";
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
				  return "resetPwd.html";
				  }
			  else{ 
					  String status="mismatch"; 
					  session.setAttribute("status", status);
					  System.out.println(session.getAttribute("status"));
					  return "otp.html" ; 
			  }
			  }
			  
			  return "forgetPwd.html" ; 
	 }
	 
	 
	 @PostMapping("/newPassword")
	  public String newPassword(@ModelAttribute User u, HttpSession session, @RequestParam("newPassword") String newPassword){
		  String email =  (String) session.getAttribute("email");
		  if(email != null) {
			  	String hashPwd = DigestUtils.sha3_256Hex(newPassword);
				u.setPassword(hashPwd);
			  	uRepo.updatePasswordByEmail(email, hashPwd);
				new mailSender().sendPasswordChangeMessage(email);
				System.out.println("password changed successfully" + hashPwd);
				session.invalidate();
		  }
		  return "landing.html";
		  
		  
	  }
	 

	
	
	
	
	
	
	
	
	
	

}
