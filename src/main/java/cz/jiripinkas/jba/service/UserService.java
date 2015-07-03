package cz.jiripinkas.jba.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import cz.jiripinkas.jba.entity.Blog;
import cz.jiripinkas.jba.entity.Item;
import cz.jiripinkas.jba.entity.Role;
import cz.jiripinkas.jba.entity.User;
import cz.jiripinkas.jba.repository.BlogRepository;
import cz.jiripinkas.jba.repository.ItemRepository;
import cz.jiripinkas.jba.repository.RoleRepository;
import cz.jiripinkas.jba.repository.UserRepository;

@Service
@Transactional
public class UserService extends AbstractService{

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BlogRepository blogRepository;

	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public User findOne(int id) {
		return userRepository.findOne(id);
	}

	public User findOneWithBlog(int id) {
		User user = this.findOne(id);
		List<Blog> blogs = blogRepository.findByUser(user);
		for (Blog blog : blogs) {
			List<Item> items = itemRepository.findByBlog(blog, new PageRequest(0, 20, Direction.DESC, "publishedDate"));
			blog.setItems(items);
		}
		user.setBlogs(blogs);
		return user;
	}

	public User save(User user) {
		String confirmId = UUID.randomUUID().toString();
		System.out.println(confirmId);
		user.setConfirmId(confirmId);
		user.setEnabled(false);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));
		
		List<Role> roles = new ArrayList<Role>();
		roles.add(roleRepository.findByName("ROLE_USER"));
		user.setRoles(roles);
		return userRepository.save(user);		
	}

	public User findOneWithName(String userName) {
		User user = userRepository.findByName(userName);		
		return findOne(user.getId());
	}

	public void delete(int id) {
		userRepository.delete(id);		
	}

	public User findOne(String username) {
		return userRepository.findByName(username);
	}

	public boolean confirmRegistration(String id) {
		User user = userRepository.findByConfirmId(id);
		if(user == null){
			return false;
		}
		user.setEnabled(true);
		userRepository.save(user);
		return true;
	}
	
	
}
