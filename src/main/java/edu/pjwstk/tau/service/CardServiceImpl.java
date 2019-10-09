package edu.pjwstk.tau.service;



import edu.pjwstk.tau.domain.Card;
import edu.pjwstk.tau.domain.User;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CardServiceImpl implements CardService {

	private HashMap<Integer, Card> cardHashMap;


	public CardServiceImpl() {
		this.cardHashMap = new HashMap<>();

	}

	@Override
	public Card create(Card object){

		if(cardHashMap.get(object.getId()) != null) {
			throw new IllegalArgumentException();
		}
		cardHashMap.put(object.getId(),createCardObject(object));
		return cardHashMap.get(object.getId());
	}

	@Override
	public Card update(Card object) {
		Card card = cardHashMap.replace(object.getId(),object);

		if(card == null ){
			throw new NullPointerException();
		}

		return cardHashMap.get(object.getId());
	}

	@Override
	public boolean delete(int id) {
		boolean isRemoved;
		Card card = cardHashMap.remove(id);

		if( card != null){
			isRemoved = true;
		} else {

			throw new NullPointerException();
		}

		return isRemoved;
	}

	@Override
	public Optional<Card> read(int id) {

		return Optional.of(cardHashMap.get(id));
	}

	@Override
	public List<Card> readAll() {

		return cardHashMap.keySet().stream()
				.map((x) -> createCardObject(cardHashMap.get(x)) )
				.collect(Collectors.toList());

	}

	private Card createCardObject(Card object) {
		return new Card.Builder()
				.id(object.getId())
				.title(object.getTitle())
				.description(object.getDescription())
				.cardStatus(object.getCardStatus())
				.owner(new User.Builder()
						.id(object.getOwner().getId())
						.email(object.getOwner().getEmail())
						.name(object.getOwner().getName())
						.surname(object.getOwner().getSurname())
						.password(object.getOwner().getPassword())
						.build()
				)
				.build();
	}
}
