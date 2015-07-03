package cz.jiripinkas.jba.controller;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cz.jiripinkas.jba.entity.User;
import cz.jiripinkas.jba.service.TemplateSendEmail;
import cz.jiripinkas.jba.service.UserService;

@Controller
@RequestMapping("/register")
public class RegisterController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private TemplateSendEmail templateSendEmail;
	
	@ModelAttribute("user")
	public User constructUser() {
		return new User();
	}
	
	@RequestMapping
	public String showRegister() {
		return "user-register";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String doRegister(@Valid @ModelAttribute("user") User user, BindingResult result) throws MessagingException {
		if (result.hasErrors()) {
			return "user-register";
		}
		userService.save(user);
		templateSendEmail.sendTemplateMail(user);
		return "redirect:/register.html?success=true";
	}
	
	@RequestMapping(value="/available")
	@ResponseBody
	public String available(@RequestParam String username) {
		Boolean isUserExist = userService.findOne(username) == null;
		return isUserExist.toString();
	}
	
	@RequestMapping(value="/confirm/{id}")
	public String confirm(@PathVariable("id") String id){
		boolean isSuccess = userService.confirmRegistration(id);
		if(isSuccess)
			return "registration-success";
		return "registration-error";
	}
	
}
