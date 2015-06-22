package cz.jiripinkas.jba.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import cz.jiripinkas.jba.entity.Blog;
import cz.jiripinkas.jba.entity.Item;
import cz.jiripinkas.jba.entity.User;
import cz.jiripinkas.jba.exception.RssException;
import cz.jiripinkas.jba.repository.BlogRepository;
import cz.jiripinkas.jba.repository.ItemRepository;
import cz.jiripinkas.jba.repository.UserRepository;

@Service
public class BlogService {

	@Autowired
	private BlogRepository blogRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RssService rssService;
	
	@Autowired
	private ItemRepository itemRepository;
	
	public void saveItem(Blog blog) throws RssException{
		List<Item> items = rssService.getItems(blog.getUrl());
		for(Item item: items){
			Item saveItem = itemRepository.findByBlogAndLink(blog, item.getLink());
			if(saveItem == null){
				item.setBlog(blog);
				itemRepository.save(item);
			}
		}
	}
	
	@Scheduled(fixedDelay=3600000)
	public void reloadBlog() throws RssException{
		List<Blog> blogs = blogRepository.findAll();
		for(Blog blog : blogs){
			saveItem(blog);
		}
	}
	
	public void save(Blog blog, String name) throws RssException {
		User user = userRepository.findByName(name);		
		blog.setUser(user);
		blogRepository.save(blog);	
		saveItem(blog);
	}

	public void delete(int id) {
		blogRepository.delete(id);
		
	}

	public Blog findOne(int id) {
		return blogRepository.findOne(id);
		
	}

	@PreAuthorize("#blog.user.name == authentication.name or hasRole('ROLE_ADMIN')")
	public void delete(@P("blog") Blog blog) {
		blogRepository.delete(blog);
		
	}
	
}
