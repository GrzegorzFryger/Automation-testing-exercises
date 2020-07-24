package edu.pjwstk.tau.service;

import edu.pjwstk.tau.domain.Card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CardServiceAbstract {
	private EnumSet<OperationType> activeOperationsForTimeAssign;
	private DateServiceProxy dateService;

	public CardServiceAbstract(DateServiceProxy dateService) {
		this.dateService = dateService;
		this.activeOperationsForTimeAssign = OperationType.ALL_OPERATION;
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

	protected void assignDateCreation(Card card){
		if(activeOperationsForTimeAssign.contains(OperationType.ADD)){
			card.getCardHistory().setAddedTime(dateService.getNow());
		}
	}

	protected void assignTimeModification(Card card){
		if(activeOperationsForTimeAssign.contains(OperationType.UPDATE)) {
			card.getCardHistory().setModificationTime(dateService.getNow());
		}
	}

	protected void assignTimeRead(Card card) {
		if(activeOperationsForTimeAssign.contains(OperationType.READ)){
			card.getCardHistory().setReadingTime(dateService.getNow());
		}
	}

	protected Predicate<String> createPredicateFromString(String s) {
		return Pattern.compile(s).asPredicate();
	}
}
