package edu.pjwstk.tau.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DataServiceProxyImpl implements DateServiceProxy {
	@Override
	public LocalDateTime getNow() {
		return LocalDateTime.now();
	}
}
