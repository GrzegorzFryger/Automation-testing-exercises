package edu.pjwstk.tau.service;

import edu.pjwstk.tau.domain.Card;

import java.util.List;

public interface CardService extends GenericService<Card> {

	List<Card> findByRegexOnDescription(String s);
}
