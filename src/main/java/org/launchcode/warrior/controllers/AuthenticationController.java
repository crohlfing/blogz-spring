package org.launchcode.warrior.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.launchcode.warrior.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AuthenticationController extends AbstractController {
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signupForm() {
		return "signup";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(HttpServletRequest request, Model model) {
		String newUser = request.getParameter("username");
		String newPass = request.getParameter("password");
		String newVer = request.getParameter("verify");
		
		//if (Blogz.User.isValidPassword(newPass) && Blogz.User.isValidUsername(newUser) && (newPass == newVer) {
				
		
		if(User.isValidUsername(newUser)) {
		
			if (User.isValidPassword(newPass)){
				if (newPass.equals (newVer)){
					User user = new User(newUser, newPass);
					userDao.save(user);
					HttpSession session = request.getSession();
					setUserInSession(session, user);
				}			
				else {
					String verify_error = "those aren't the same!";
					model.addAttribute("verify_error", verify_error);
					return "redirect:/signup";
				}
			}
			else {
				String password_error = "invalid password.";
				model.addAttribute("password_error", password_error);
				return "redirect:/signup";
			}
		}
		else {
			String username_error = "invalid username.";
			model.addAttribute("username_error", username_error);
			return "redirect:/signup";
		}
			
		
		

		// TODO - implement signup
		
		return "redirect:trainers/creator";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginForm() {
		return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, Model model) {
		String oldUser = request.getParameter("username");
		String oldPass = request.getParameter("password");
		
		User user = userDao.findByUsername(oldUser);
		if ((user != null) && user.isMatchingPassword(oldPass)) {
			HttpSession session = request.getSession();
			setUserInSession(session, user);
			return "redirect:trainers/creator";
		}
		else {
			String error = "thas not it!";
			model.addAttribute("error", error);
			return "login";
		}
		
		
		
		
		// TODO - implement login
		
		
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request){
        request.getSession().invalidate();
		return "redirect:/";
	}
}


