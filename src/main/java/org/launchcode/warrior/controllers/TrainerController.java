package org.launchcode.warrior.controllers;

import java.util.List;

import org.launchcode.warrior.models.Character;
import org.launchcode.warrior.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TrainerController extends AbstractController {

	@RequestMapping(value = "/")
	public String trainerList(Model model){
		List<User> allPlayers = userDao.findAll();
		model.addAttribute("users", allPlayers);
		
		//model.addAttribute("users", userDao.findAll());
		//model.addAttribute("user",users);
		
		return "trainerList";
	}
	
	@RequestMapping(value = "/trainers")
	public String warriorList(Model model) {
		
		model.addAttribute("inputs", postDao.findAll());
		
		return "trainers";
	}
	
}
