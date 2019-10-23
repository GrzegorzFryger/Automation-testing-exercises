package edu.pjwstk.tau.domain;

import java.util.stream.Collectors;

public interface DeepClone<T extends Cloneable > {

	T deepClone();

}
