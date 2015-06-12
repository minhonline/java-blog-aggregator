package cz.jiripinkas.jba.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cz.jiripinkas.jba.entity.Blog;
import cz.jiripinkas.jba.entity.User;
import cz.jiripinkas.jba.service.BlogService;
import cz.jiripinkas.jba.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private BlogService blogService;

	@ModelAttribute("user")
	public User constructUser() {
		return new User();
	}

	@ModelAttribute("blog")
	public Blog constructBlog() {
		return new Blog();
	}

	@RequestMapping("/users")
	public String users(Model model) {
		model.addAttribute("users", userService.findAll());
		return "users";
	}

	@RequestMapping("/user/{id}")
	public String detail(Model model, @PathVariable int id) {
		model.addAttribute("user", userService.findOne(id));
		return "user-detail";
	}

	@RequestMapping("/register")
	public String showRegister() {
		return "user-register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String doRegister(@ModelAttribute("user") User user) {
		userService.save(user);
		return "redirect:/register.html?success=true";
	}

	@RequestMapping("/account")
	public String account(Model model, Principal principal) {
		String userName = principal.getName();
		model.addAttribute("user", userService.findOneWithName(userName));
		return "account-detail";
	}

	@RequestMapping(value = "/account", method = RequestMethod.POST)
	public String doBlog(@ModelAttribute("blog") Blog blog, Principal principal ) {
		blogService.save(blog, principal.getName());
		return "redirect:/account.html";
	}
	
	@RequestMapping(value="/blog/remove/{id}")
	public String removeBlog(@PathVariable("id") int id){
		Blog blog = blogService.findOne(id);
		blogService.delete(blog);
		return "redirect:/account.html";
	}
	
	@RequestMapping(value="/user/remove/{id}")
	public String removeUser(@PathVariable("id") int id){
		userService.delete(id);
		return "redirect:/users.html";
	}
}
