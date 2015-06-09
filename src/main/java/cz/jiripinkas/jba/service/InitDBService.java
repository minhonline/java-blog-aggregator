package cz.jiripinkas.jba.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.jiripinkas.jba.entity.Blog;
import cz.jiripinkas.jba.entity.Item;
import cz.jiripinkas.jba.entity.Role;
import cz.jiripinkas.jba.entity.User;
import cz.jiripinkas.jba.repository.BlogRepository;
import cz.jiripinkas.jba.repository.ItemRepository;
import cz.jiripinkas.jba.repository.RoleRepository;
import cz.jiripinkas.jba.repository.UserRepository;

@Transactional
@Service
public class InitDBService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private BlogRepository blogRepository;

	@PostConstruct
	public void init() {
		Role roleUser = new Role();
		roleUser.setName("ROLE_USER");
		roleRepository.save(roleUser);

		Role roleAdmin = new Role();
		roleUser.setName("ROLE_ADMIN");
		roleRepository.save(roleAdmin);

		User userAdmin = new User();
		userAdmin.setName("admin");
		List<Role> roles = new ArrayList<Role>();
		roles.add(roleAdmin);
		roles.add(roleUser);
		userAdmin.setRoles(roles);
		userRepository.save(userAdmin);

		Blog blogJava = new Blog();
		blogJava.setName("Java");
		blogJava.setUrl("http://vnexpress.net");
		blogJava.setUser(userAdmin);
		blogRepository.save(blogJava);

		Item item1 = new Item();
		item1.setBlog(blogJava);
		item1.setTitle("first");
		item1.setLink("http://tuoitre.vn");
		item1.setPublishedDate(new Date());
		itemRepository.save(item1);

		Item item2 = new Item();
		item2.setBlog(blogJava);
		item2.setTitle("second");
		item2.setLink("http://tuoitre.vn");
		item2.setPublishedDate(new Date());
		itemRepository.save(item2);
	}
}