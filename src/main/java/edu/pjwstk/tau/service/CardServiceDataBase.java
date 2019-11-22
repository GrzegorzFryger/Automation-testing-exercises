package edu.pjwstk.tau.service;

import edu.pjwstk.tau.domain.Card;
import edu.pjwstk.tau.domain.CardHistory;
import edu.pjwstk.tau.repository.CardHistoryRepository;
import edu.pjwstk.tau.repository.CardRepository;
import edu.pjwstk.tau.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CardServiceDataBase extends CardServiceAbstract implements CardService {

	private CardRepository cardRepository;
	private UserRepository userRepository;
	private CardHistoryRepository cardHistoryRepository;

	public CardServiceDataBase(CardRepository cardRepository, UserRepository userRepository,
	                           DateServiceProxy dateService, CardHistoryRepository cardHistoryRepository) {
		super(dateService);
		this.cardRepository = cardRepository;
		this.userRepository = userRepository;
		this.cardHistoryRepository = cardHistoryRepository;
	}


	@Override
	public Card create(Card object){

		if(cardRepository.findById(object.getId()).isPresent()) {
			throw new IllegalArgumentException();
		}
		userRepository.findById(object.getOwner().getId())
				.orElseGet(()-> userRepository.save(object.getOwner()));

		object.setCardHistory(CardHistory.builder().build());

		this.assignDateCreation(object);

		return cardRepository.save(object);
	}

	@Override
	public Card update(Card object) {

		Card card =	cardRepository.findById(object.getId()).orElseThrow(NullPointerException::new);

		card.setDescription(object.getDescription());
		card.setTitle(object.getTitle());
		card.setCardStatus(object.getCardStatus());
		card.setOwner(object.getOwner());

		this.assignTimeModification(card);

		return cardRepository.save(card);
	}

	@Override
	public boolean delete(int id) {
		try {
			Card card = cardRepository.findById(id).orElseThrow(NullPointerException::new);
			cardRepository.delete(
					card
			);
			return true;
		}catch (NullPointerException IllegalArgumentException  ) {
			return false;
		}
	}

	@Override
	public Optional<Card> read(int id) {

		Optional<Card> card = cardRepository.findById(id);

		if(card.isPresent()){
			assignTimeRead(card.get());
			return card;
		}else return card;

	}

	@Override
	public List<Card> readAll() {

		return  StreamSupport.stream(cardRepository.findAll().spliterator(), false)
				.peek(this::assignTimeRead).collect(Collectors.toList());
	}

	@Override
	public List<Card> findByRegexOnDescription(String regex) {

		return 	StreamSupport.stream(cardRepository.findAll().spliterator(), false)
				.filter(card -> createPredicateFromString(regex).test(card.getDescription()))
				.collect(Collectors.toList());
	}

	@Override
	public boolean delete(List<Card> carToRemove) {

		if (carToRemove.isEmpty()) {
			return false;
		} else {
			carToRemove.forEach(x -> cardRepository.delete(x));
			return true;
		}
	}

	@Override
	protected void assignDateCreation(Card card) {
		super.assignDateCreation(card);
	}

	@Override
	protected void assignTimeModification(Card card) {
		super.assignTimeModification(card);
	}

	@Override
	protected void assignTimeRead(Card card) {
		super.assignTimeRead(card);
	}
}
