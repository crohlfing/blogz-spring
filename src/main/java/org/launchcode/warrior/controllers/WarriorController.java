package org.launchcode.warrior.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.launchcode.warrior.models.Character;
import org.launchcode.warrior.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WarriorController extends AbstractController {

	
	
	//This method takes you to the character creator template
	@RequestMapping(value = "/trainers/creator", method = RequestMethod.GET)
	public String newCharForm() {
		return "creator";
	}
	
	//This method validates the data you submit to the template
	//if it's squeaky clean, it creates the object and saves the data 
	@RequestMapping(value = "/trainers/creator", method = RequestMethod.POST)
	public String newChar(HttpServletRequest request, Model model) {
		
		HttpSession session = request.getSession();
		String name = request.getParameter("name");
		String gender = request.getParameter("gender");
		int strength = Integer.parseInt(request.getParameter("strength"));
		int quickness = Integer.parseInt(request.getParameter("quickness"));
		int hardiness = Integer.parseInt(request.getParameter("hardiness"));
		String weapon = request.getParameter("weapon");
		String armor = request.getParameter("armor");
		String background = request.getParameter("background");
		User player = getUserFromSession(session);
		
		if (name == null || name == "") {
			String error = "you need to come up with a name";
			model.addAttribute("error", error);
			return "creator";
		}
		else if (gender == null || gender == "") {
			String error = "you didn't select a gender";
			model.addAttribute("error", error);
			return "creator";
		}
		else if (strength + hardiness + quickness != 20) {
			String error = "your stats must add up to 20.";
			model.addAttribute("error", error);
			return "creator";
		}
		else if (weapon == null || weapon == "" || armor == null || armor == "") {
			String error = "you didn't select your weapon or armor";
			model.addAttribute("error", error);
			return "creator";
		}
		else if (background == null || background == "") {
			String error = "your background can be short, but you got to write something.";
			model.addAttribute("error", error);
			return "creator";
		}

		Character newChar = new Character(name, gender, strength, quickness, hardiness, weapon, armor, background, player);
		
		postDao.save(newChar);
 
		String username = player.getUsername();
		int uid = newChar.getUid();
		return makeSheet(username, uid, model);
	
		
	}
	
	
	//This is the method to make a character sheet of a created character
	@RequestMapping(value = "/trainers/{username}/{uid}", method = RequestMethod.GET)
	public String makeSheet(@PathVariable String username, @PathVariable int uid, Model model) {
		
		Character newSheet = postDao.findByUid(uid);
		
		model.addAttribute("inputs", newSheet);
		
		
		return "sheet";
	}
	
	//This method directs user to a list of a single user's characters
	@RequestMapping(value = "/trainers/{username}", method = RequestMethod.GET)
	public String userInputs(@PathVariable String username, Model model) {
		
		User user = userDao.findByUsername(username);
		List<Character> userInputs = user.getInputs();
		
		model.addAttribute("inputs", userInputs);
		
		model.addAttribute("player", user.getUsername());
		return "trainersroster";
	}
	

	
}
