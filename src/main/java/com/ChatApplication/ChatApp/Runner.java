package com.ChatApplication.ChatApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ChatApplication.ChatApp.Database.Databaserepo;

@Component
public class Runner implements CommandLineRunner{

	@Autowired
	Databaserepo databaserepo;
	@Override
	public void run(String... args) throws Exception {
//		databaserepo.save(new UserDetails(1,"deepak",9585010400L));
//		databaserepo.save(new UserDetails(2,"mass",9585010400L));
		
	}

	
}
