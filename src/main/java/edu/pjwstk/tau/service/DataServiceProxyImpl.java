package edu.pjwstk.tau.service;

import java.time.LocalDateTime;

public class DataServiceProxyImpl implements DateServiceProxy {
	@Override
	public LocalDateTime getNow() {
		return LocalDateTime.now();
	}
}
