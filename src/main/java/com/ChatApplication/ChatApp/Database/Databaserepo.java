package com.ChatApplication.ChatApp.Database;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Databaserepo extends MongoRepository<UserDetails, Long> {

	public UserDetails findById(long id); 
}
