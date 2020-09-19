package com.adobe.prj.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.adobe.prj.entity.AdminDto;
import com.adobe.prj.exception.CustomException;
import com.adobe.prj.model.AuthenticationRequest;
import com.adobe.prj.model.AuthenticationResponse;
import com.adobe.prj.service.AdminService;
import com.adobe.prj.util.JwtUtil;


@RestController
class AdminController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@Autowired
	private AdminService adminService;

	@RequestMapping({ "/hello" })
	public String firstPage() {
		return "Hello World";
	}

	// API to login and generate JWT upon verification of credentials
	@RequestMapping(value = "api/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new CustomException("Incorrect username or password");
		}
		
		final UserDetails userDetails = adminService.loadUserByUsername(authenticationRequest.getUsername());
		final String jwt = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
	
	// API to register a new admin
	@RequestMapping(value = "api/register", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody AdminDto admin) throws Exception {
		return ResponseEntity.ok(adminService.save(admin));
	}
	
}
