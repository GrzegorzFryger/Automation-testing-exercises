package edu.pjwstk.tau.service;


import edu.pjwstk.tau.domain.Card;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CardServiceCache extends CardServiceAbstract implements CardService {

	private HashMap<Integer, Card> cardHashMap;


	public CardServiceCache(DateServiceProxy dateService) {
		super(dateService);
		this.cardHashMap = new HashMap<>();
	}

	@Override
	public Card create(Card object){

		if(cardHashMap.get(object.getId()) != null) {
			throw new IllegalArgumentException();
		}

		Card cardToSave = createDeepClone(object);
		assignDateCreation(cardToSave);
		cardHashMap.put(object.getId(), cardToSave);

		return cardHashMap.get(object.getId());
	}

	@Override
	public Card update(Card object) {

		this.assignTimeModification(object);
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
		Optional<Card> card =  Optional.of(cardHashMap.get(id));
		card.ifPresent(this::assignTimeRead);

		return card;
	}

	@Override
	public List<Card> readAll() {
		return cardHashMap.keySet().stream()
				.map((x) -> {
					Card card = createDeepClone(cardHashMap.get(x));
					assignTimeRead(card);
					return card;
				})
				.collect(Collectors.toList());
	}

	@Override
	public List<Card> findByRegexOnDescription(String regex) {

		return cardHashMap.values().stream()
				.filter(card -> createPredicateFromString(regex).test(card.getDescription()))
				.collect(Collectors.toList());
	}

	@Override
	public boolean delete(List<Card> carToRemove) {
		if(carToRemove.isEmpty()) {
			return false;
		} else {
			cardHashMap.entrySet().removeIf(e -> carToRemove.contains(e.getValue()) );
			return true;
		}
	}

	public void clean(){
		cardHashMap.clear();
	}


	private Card createDeepClone(Card object) {
		return object.deepClone();
	}

}
