package com.ChatApplication.ChatApp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ChatApplication.ChatApp.Database.LoginUserDetails;
import com.ChatApplication.ChatApp.Database.MessageDetails;
import com.ChatApplication.ChatApp.Database.MessageDetailsTemp;
import com.ChatApplication.ChatApp.Database.UserDetails;
import com.ChatApplication.ChatApp.Service.DatabaseService;

@RestController
@CrossOrigin
public class Controller {

	@Autowired
	DatabaseService db;
	
	@PostMapping("/signin")
	public ResponseEntity adduser(@RequestBody UserDetails userdetails) {
		UserDetails saveduser=db.adduser(userdetails);
		return ResponseEntity.created(null).build();
	}
	
	@PostMapping("/login")
	public boolean checkuser(@RequestBody LoginUserDetails loginuserdetails ) {
		return db.checkuser(loginuserdetails);
	}
	
	@GetMapping("/user/{sender}/newchat/{receiver}")
	public boolean newchat(@PathVariable long sender,@PathVariable long receiver) {
		return db.newchat(sender,receiver);
	}
	
	@GetMapping("/user/{sender}")
	public UserDetails newchat(@PathVariable long sender) {
		return db.showuser(sender);
	}
	
	@PostMapping("/user/{sender}/chat/{receiver}")
	public void chat(@PathVariable long sender,@PathVariable long receiver,@RequestBody MessageDetailsTemp msg) {
		db.chat(sender,receiver,msg);
	}
	
	@GetMapping("/user/{sender}/msg/{receiver}")
	public List<MessageDetailsTemp> showmessages(@PathVariable long sender,@PathVariable long receiver) {
		return db.showmessages(sender,receiver);
	}
	
	@DeleteMapping("/user/{sender}")
	public void deleteuser(@PathVariable long sender) {
		db.deleted(sender);
	}
	
	@PatchMapping("/user/{sender}")
	public void updateuserdetail(@PathVariable long sender,@RequestBody UserDetails userdetails ) {
		System.out.println("aaaaaa");
		db.updateuserdetail(sender,userdetails);
	}
}
