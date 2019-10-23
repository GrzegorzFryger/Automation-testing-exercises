package edu.pjwstk.tau.service;

import java.util.EnumSet;

public enum OperationType {
	ADD,UPDATE,READ;

	public static final EnumSet<OperationType> ALL_OPERATION = EnumSet.allOf(OperationType.class);


}
