package edu.pjwstk.tau.service;

import edu.pjwstk.tau.domain.Card;
import edu.pjwstk.tau.domain.CardStatus;
import edu.pjwstk.tau.domain.User;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import static org.junit.Assert.*;

public class CardServiceImplTest {

	private Card card;
	private CardServiceImpl cardService;
	private List<Card> cardList;

	@Before
	public void setUp() throws Exception {
		cardService = new CardServiceImpl();

		card = new Card.Builder()
				.id(1)
				.title("test0")
				.description("kara do testowania")
				.cardStatus(CardStatus.ACTIVE)
				.owner(new User.Builder()
						.id(1)
						.email("test@test.com")
						.name("test user")
						.surname("test user")
						.password("password")
						.build()
				).build();

	 cardList  = Arrays.asList(
			 new Card.Builder()
					 .id(1)
					 .title("test1")
					 .description("kara do testowania1")
					 .cardStatus(CardStatus.ACTIVE)
					 .owner(new User.Builder()
							 .id(1)
							 .email("test@test.com")
							 .name("test user")
							 .surname("test user")
							 .password("password")
							 .build()
					 ).build(),
			 new Card.Builder()
					 .id(2)
					 .title("test2")
					 .description("kara do testowania2")
					 .cardStatus(CardStatus.ACTIVE)
					 .owner(new User.Builder()
							 .id(1)
							 .email("test@test.com")
							 .name("test user")
							 .surname("test user")
							 .password("password")
							 .build()
					 ).build(),
			 new Card.Builder()
					 .id(3)
					 .title("test3")
					 .description("kara do testowania3")
					 .cardStatus(CardStatus.ACTIVE)
					 .owner(new User.Builder()
							 .id(1)
							 .email("test@test.com")
							 .name("test user")
							 .surname("test user")
							 .password("password")
							 .build()
					 ).build()
		);
	}

	@Test
	public void cardServiceIsImplemented() {
		assertNotNull(new CardServiceImpl());
	}

	@Test
	public void createMethodReturnCreatedObjectCorrectly() {
		assertEquals(card,cardService.create(card));
	}

	@Test(expected = IllegalArgumentException.class)
	public void createMethodThrowExceptionIfObjectExists() {
		cardService.create(card);
		cardService.create(card);
	}

	@Test
	public void readeMethodReturnObjectIfExistsInDb() {
		cardService.create(card);

		assertEquals(card,cardService.read(card.getId()).get());
	}

	@Test(expected = NullPointerException.class)
	public void readeMethodThrowExceptionIfObjectNotFound() {
		cardService.read(card.getId());
	}

	@Test
	public void readAllMethodReturnCollectionAndReturnAllObjects() {
		cardList.forEach(x -> cardService.create(x));

		assertArrayEquals(cardList.toArray(),cardService.readAll().toArray());
	}

	@Test
	public void updateMethodReturnUpdatedObjectAndDoNotChangeOtherObjects() {
		cardList.forEach( x->cardService.create(x));

		Card updateCard = cardService.read(1).get();

		updateCard.setDescription("updated Card");
		updateCard.setCardStatus(CardStatus.DONE);
		cardService.update(updateCard);

		assertEquals(updateCard, cardService.read(updateCard.getId()).get());
		assertEquals(cardList.stream().filter(x-> x.getId() != updateCard.getId()).collect(Collectors.toList()),
				cardService.readAll().stream().filter(x-> x.getId() != updateCard.getId()).collect(Collectors.toList()));

	}

	@Test(expected = NullPointerException.class)
	public void updateMethodThrowExceptionIfObjectNotExists() {
		card.setCardStatus(CardStatus.DONE);
		cardService.update(card);
	}

	@Test
	public void deleteMethodRemoveOnlyOneObject() {
		cardList.forEach( x->cardService.create(x));

		assertTrue(cardService.delete(1));
		assertEquals(2, cardService.readAll().size());

	}


	@Test(expected = NullPointerException.class)
	public void deleteMethodThrowExceptionIfObjectNotExists() {
		cardService.delete(card.getId());
	}


}