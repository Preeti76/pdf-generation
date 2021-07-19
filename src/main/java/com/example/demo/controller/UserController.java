package com.example.demo.controller;

import java.net.URI;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepositoryImpl;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserController {
	@Autowired
	UserRepositoryImpl service;
	
	@PostMapping("/register")
	public ResponseEntity registerUser(@RequestBody User user) {
		
		System.out.println("hii");
		User user1 =service.createUser(user);
		URI location= ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(user1.getId()).toUri();
		return ResponseEntity.created(location).build();
		
	}
	
	
	

	@GetMapping("getAllUser")
	public List<User> getAllUser(){
		
		return service.getUser();
		
		
	}
	
	@GetMapping("getPdf")
	public int getUserPDF(){
		service.generatePdf();
		return Response.SC_OK;
		
		
	}

	@GetMapping("getUser{id}")
	public EntityModel<User> getOne(@PathVariable("id") int id){
		User user=service.getOneUser(id);
		EntityModel model=EntityModel.of(user);
		WebMvcLinkBuilder link=linkTo(methodOn(this.getClass()).getAllUser());
		model.add(link.withRel("all-user"));
//		if(user==null){
//			throw new UserNotFoundException("usernot found with the id"+id);
//		}else {
			return model;
//		}
	}
}
