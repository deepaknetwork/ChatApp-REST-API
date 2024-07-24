package com.ChatApplication.ChatApp.Service;

import org.springframework.stereotype.Service;

@Service
public class AppService {

	public String findConversationId(long sender,long receiver) {
		String conversationid;
		if(sender>receiver) {
			conversationid=sender+""+receiver;
		}else {
			conversationid=receiver+""+sender;
		}
		return conversationid;
	}
}
