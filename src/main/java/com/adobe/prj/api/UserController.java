package com.adobe.prj.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.adobe.prj.entity.User;
import com.adobe.prj.service.UserService;

@RestController
@RequestMapping("api/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping()
    public @ResponseBody List<User> getUsers() {
        return userService.getUsers();
    }
	
	@GetMapping("/{id}")
	public @ResponseBody User getUser(@PathVariable("id") int id) {
		return userService.getUser(id);
	}
	
	@PostMapping()
	public @ResponseBody User addUser(@RequestBody User u) {
	  return userService.addUser(u);
	}
	
	@DeleteMapping("/{id}")
	public @ResponseBody void deleteUser(@PathVariable("id") int id) {
		userService.deleteUser(id);
	}
	
	@PutMapping("/{id}")
	public @ResponseBody User updateUser(@RequestBody User u) {
		  return userService.addUser(u);
	}
		
	
}
