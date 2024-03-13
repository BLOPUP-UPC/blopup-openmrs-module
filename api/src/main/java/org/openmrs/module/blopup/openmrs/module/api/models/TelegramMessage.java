package org.openmrs.module.blopup.openmrs.module.api.models;

import org.hibernate.validator.constraints.NotEmpty;
import org.simpleframework.xml.load.Strategy;

public class TelegramMessage {
	
	String chatId;
	
	String message;
	
	public TelegramMessage() {
		
	}
	
	public TelegramMessage(String chatId, String message) {
		this.chatId = chatId;
		this.message = message;
	}
	
	public String getChatId() {
		return chatId;
	}
	
	public String getMessage() {
		return message;
	}
}
