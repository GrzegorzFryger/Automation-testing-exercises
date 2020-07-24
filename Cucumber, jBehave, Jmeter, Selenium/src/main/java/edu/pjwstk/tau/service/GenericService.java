package edu.pjwstk.tau.service;

import java.util.List;
import java.util.Optional;

public interface GenericService <T> {
	T create(T object) throws IllegalArgumentException;
	T update(T object);
	boolean delete(int id);
	Optional<T> read(int id);
	List<T> readAll();
}
