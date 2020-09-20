package com.adobe.prj.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.adobe.prj.entity.User;
import com.adobe.prj.service.UserService;
import com.adobe.prj.validation.ValidJson;

import static com.adobe.prj.validation.SchemaLocations.USERSCHEMA;

@RestController
@RequestMapping("api/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	// fetch all users
	@GetMapping()
    public @ResponseBody List<User> getUsers() {
        return userService.getUsers();
    }
	
	// user with id = 'id'
	@GetMapping("/{id}")
	public @ResponseBody User getUser(@PathVariable("id") int id) {
		return userService.getUser(id);
	}
	
	// add a user
	@PostMapping()
	public @ResponseBody User addUser(@ValidJson(USERSCHEMA) User u) {
	  return userService.addUser(u);
	}
	
	// delete a user
	@DeleteMapping("/{id}")
	public @ResponseBody void deleteUser(@PathVariable("id") int id) {
		userService.deleteUser(id);
	}
	
	// update a user
	@PutMapping("/{id}")
	public @ResponseBody User updateUser(@ValidJson(USERSCHEMA) User u) {
		  return userService.addUser(u);
	}
		
	
}
