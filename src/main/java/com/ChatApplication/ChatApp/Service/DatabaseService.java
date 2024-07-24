package com.ChatApplication.ChatApp.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ChatApplication.ChatApp.Database.Databaserepo;
import com.ChatApplication.ChatApp.Database.LoginUserDetails;
import com.ChatApplication.ChatApp.Database.UserDetails;
import com.ChatApplication.ChatApp.Database.Userchats;
import com.ChatApplication.ChatApp.Exception.PasswordNotMatchedException;
import com.ChatApplication.ChatApp.Exception.UserAlreadyExists;
import com.ChatApplication.ChatApp.Exception.UserNotFoundException;
import com.ChatApplication.ChatApp.Database.MessageDetails;
import com.ChatApplication.ChatApp.Database.MessageDetailsTemp;
@Service
@Transactional
public class DatabaseService {

	@Autowired
	Databaserepo databaserepo;
	
	@Autowired
	AppService appservice;
	
	@Autowired
	MongoTemplate mdb;
	
	public UserDetails adduser(UserDetails userdetails) {
		UserDetails fetcheddetails=databaserepo.findById(userdetails.phone);
		if(fetcheddetails!=null) {
			throw new UserAlreadyExists("users with this mobile number alreays exists");
		}
		return databaserepo.save(userdetails);
	}
	
	public UserDetails showuser(long phone) {
		UserDetails fetched=databaserepo.findById(phone);
		if(fetched==null) {
			throw new UserNotFoundException("users with this mobile number does not exists");
		}
		return fetched;
	}
	

	public boolean checkuser(LoginUserDetails loginuserdetails) {
		UserDetails saveddetails=databaserepo.findById(loginuserdetails.phone());
		if(saveddetails==null) {
			throw new UserNotFoundException("user not found with this mobile number");
		}
		if(!saveddetails.password.equals(loginuserdetails.password())) {
			throw new PasswordNotMatchedException("wrong password");
		}	
		return true;
	}

	public boolean newchat(long sender, long receiver) {
		if(databaserepo.findById(sender)==null) {
			throw new UserNotFoundException("the sender number is not registered");
		}
		if(databaserepo.findById(receiver)==null) {
			throw new UserNotFoundException("the receiver number is not registered");
		}
		String conversationid=appservice.findConversationId(sender, receiver);
		try {
			mdb.createCollection(conversationid);
		}catch(Exception ex){
			throw new UserAlreadyExists("conversation already exists");
		}
		return true;
	}
	
	public boolean chat(long sender, long receiver,MessageDetailsTemp message) {
		UserDetails senderdetails=databaserepo.findById(sender);
		if(senderdetails==null) {
			throw new UserNotFoundException("the sender number is not registered");
		}
		UserDetails reciverdetails=databaserepo.findById(receiver);
		if(reciverdetails==null) {
			throw new UserNotFoundException("the receiver number is not registered");
		}
		String conversationid=appservice.findConversationId(sender, receiver);
		message.receiver=receiver;
		message.read=false;
		mdb.insert(message,conversationid);
		int ind=senderdetails.chat.indexOf(new Userchats(receiver,reciverdetails.name,true));
		if(ind!=-1) {
			senderdetails.chat.remove(ind);
		}
		
		int ind1=senderdetails.chat.indexOf(new Userchats(receiver,reciverdetails.name,false));
       if(ind1!=-1) {
	senderdetails.chat.remove(ind1);
		}
		
		senderdetails.chat.addFirst(new Userchats(receiver,reciverdetails.name,true));
		int ind2=reciverdetails.chat.indexOf(new Userchats(sender,senderdetails.name,true));
      if(ind2!=-1) {
	reciverdetails.chat.remove(ind2);
		}
		
		int ind3=reciverdetails.chat.indexOf(new Userchats(sender,senderdetails.name,false));
      if(ind3!=-1) {
	reciverdetails.chat.remove(ind3);
		}
		reciverdetails.chat.addFirst(new Userchats(sender,senderdetails.name,false));
		System.out.println(ind+" "+ind1);
		System.out.println(ind2+" "+ind3);
		System.out.println(reciverdetails.chat);
		databaserepo.save(senderdetails);
		databaserepo.save(reciverdetails);
		
		return true;
	}

	public List<MessageDetailsTemp> showmessages(long sender, long receiver) {
		UserDetails senderdetails=databaserepo.findById(sender);
		if(senderdetails==null) {
			throw new UserNotFoundException("the sender number is not registered");
		}
		UserDetails reciverdetails=databaserepo.findById(receiver);
		if(reciverdetails==null) {
			throw new UserNotFoundException("the receiver number is not registered");
		}
		String conversationid=appservice.findConversationId(sender, receiver);
		List<MessageDetailsTemp> messages = mdb.findAll(MessageDetailsTemp.class, conversationid);
		messages.forEach(msg ->{
			if(msg.receiver==sender) {
				msg.read=true;
			}
		});
		senderdetails.chat.forEach(msg->{
			if(msg.phone==receiver) {
				msg.setRead(true);
			}
		});
		databaserepo.save(senderdetails);
		
		mdb.remove(new Query(), conversationid);
		mdb.insert(messages,conversationid);
		return messages;
		
	}

	public void deleted(long sender) {
		UserDetails senderdetails=databaserepo.findById(sender);
		if(senderdetails==null) {
			throw new UserNotFoundException("no user to delete");
		}
		databaserepo.deleteById(sender);

	}

	public void updateuserdetail(long sender, UserDetails userdetails) {
		UserDetails senderdetails=databaserepo.findById(sender);
		if(senderdetails==null) {
			throw new UserNotFoundException("no user to delete");
		}
		if(userdetails.name!=null) {
			senderdetails.name=userdetails.name;
		}
		if(userdetails.bio!=null) {
			senderdetails.bio=userdetails.bio;
		}
		if(userdetails.dob!=null) {
			senderdetails.dob=userdetails.dob;
		}
		if(userdetails.gender!=null) {
			senderdetails.gender=userdetails.gender;
		}	
		databaserepo.save(senderdetails);
	}

	
}
