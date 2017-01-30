package org.launchcode.warrior.controllers;

import javax.servlet.http.HttpSession;

import org.launchcode.warrior.dao.PostDao;
import org.launchcode.warrior.dao.UserDao;
import org.launchcode.warrior.models.User;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractController {

	@Autowired
    protected UserDao userDao;
	
	@Autowired
	protected PostDao postDao;

    public static final String userSessionKey = "user_id";

    protected User getUserFromSession(HttpSession session) {
    	
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        return userId == null ? null : userDao.findByUid(userId);
    }
    
    protected void setUserInSession(HttpSession session, User user) {
    	session.setAttribute(userSessionKey, user.getUid());
    }
	
}
