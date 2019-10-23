package edu.pjwstk.tau.service;



import edu.pjwstk.tau.domain.Card;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class CardServiceImpl implements CardService {

	private HashMap<Integer, Card> cardHashMap;
	private DateServiceProxy dateService;
	private EnumSet<OperationType> activeOperationsForTimeAssign;


	public CardServiceImpl(DateServiceProxy dateService) {
		this.dateService = dateService;
		this.cardHashMap = new HashMap<>();
		this.activeOperationsForTimeAssign = OperationType.ALL_OPERATION;
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

	public void turnOffTimeAssign(OperationType ... operationTypes){
		List<OperationType> operationsToRemove = new ArrayList<>(Arrays.asList(operationTypes));
		this.activeOperationsForTimeAssign.removeAll(operationsToRemove);
	}

	public void turnOnTimeAssign(OperationType ... operationTypes){

		EnumSet<OperationType> operationToAdd = Arrays.stream(operationTypes)
				.collect(Collectors.toCollection(()-> EnumSet.noneOf(OperationType.class)));

		activeOperationsForTimeAssign.addAll(operationToAdd);
	}

	private void assignDateCreation(Card card){
		if(activeOperationsForTimeAssign.contains(OperationType.ADD)){
			card.getCardHistory().setAddedTime(dateService.getNow());
		}
	}

	private void assignTimeModification(Card card){
		if(activeOperationsForTimeAssign.contains(OperationType.UPDATE)) {
			card.getCardHistory().setModificationTime(dateService.getNow());
		}
	}

	private void assignTimeRead(Card card) {
		if(activeOperationsForTimeAssign.contains(OperationType.READ)){
			card.getCardHistory().setReadingTime(dateService.getNow());
		}
	}

	private Card createDeepClone(Card object) {
		return object.deepClone();
	}


}
