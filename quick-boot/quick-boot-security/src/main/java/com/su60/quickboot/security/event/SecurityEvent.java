package com.su60.quickboot.security.event;

import org.springframework.context.ApplicationEvent;

public class SecurityEvent extends ApplicationEvent {
	public SecurityEvent(Object source) {
		super(source);
	}
}
