package edu.pjwstk.tau.service;

import edu.pjwstk.tau.domain.Card;
import edu.pjwstk.tau.domain.CardHistory;
import edu.pjwstk.tau.domain.CardStatus;
import edu.pjwstk.tau.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CardServiceImplTest {

	@Mock
	private DateServiceProxy dateService;

	private Card firstCard;
	private Card secondCard;
	private CardServiceImpl cardService;
	private List<Card> cardList;
	private LocalDateTime nowTime;

	@Before
	public void setUp() throws Exception {
		cardService = new CardServiceImpl(dateService);
		nowTime = LocalDateTime.now();

		firstCard = Card.builder()
				.id(1)
				.title("test no.1")
				.description("Card for testing, no.1")
				.cardStatus(CardStatus.ACTIVE)
				.owner(User.builder()
						.id(1)
						.email("test@test.com")
						.name("test user")
						.surname("test user")
						.password("password")
						.build()
				)
				.cardHistory(CardHistory.builder().build())
				.build();

		secondCard = Card.builder()
				.id(2)
				.title("test no.2")
				.description("Card for testing, no.2")
				.cardStatus(CardStatus.ACTIVE)
				.owner(User.builder()
						.id(1)
						.email("test@test.com")
						.name("test user")
						.surname("test user")
						.password("password")
						.build()
				)
				.cardHistory(CardHistory.builder().build())
				.build();

		cardList = Arrays.asList(
				Card.builder()
						.id(3)
						.title("test no.3")
						.description("Card for testing, no.3")
						.cardStatus(CardStatus.ACTIVE)
						.owner(User.builder()
								.id(1)
								.email("test@test.com")
								.name("test user")
								.surname("test user")
								.password("password")
								.build()
						)
						.cardHistory(CardHistory.builder().build())
						.build(),
				Card.builder()
						.id(4)
						.title("test no.4")
						.description("Card for testing, no.4")
						.cardStatus(CardStatus.ACTIVE)
						.owner(User.builder()
								.id(1)
								.email("test@test.com")
								.name("test user")
								.surname("test user")
								.password("password")
								.build()
						)
						.cardHistory(CardHistory.builder().build())
						.build(),
				Card.builder()
						.id(5)
						.title("test no.5")
						.description("Card for testing, no.5")
						.cardStatus(CardStatus.ACTIVE)
						.owner(User.builder()
								.id(1)
								.email("test@test.com")
								.name("test user")
								.surname("test user")
								.password("password")
								.build()
						)
						.cardHistory(CardHistory.builder().build())
						.build()
		);
	}

	@Test
	public void Should_AddCardToDb_When_CardWasAddedByService() {
		assertEquals(firstCard, cardService.create(firstCard));
	}

	@Test(expected = IllegalArgumentException.class)
	public void Should_ThrowException_When_CardWithTheSameIdExistInDB() {
		cardService.create(firstCard);
		cardService.create(firstCard);
	}

	@Test
	public void Should_ReturnCard_When_CardWithIdTheSameIdWasInDb() {
		cardService.create(firstCard);

		assertEquals(firstCard, cardService.read(firstCard.getId()).get());
	}

	@Test(expected = NullPointerException.class)
	public void Should_ThrowException_When_CardWasNotFound() {
		cardService.read(firstCard.getId());
	}

	@Test
	public void Should_ReturnCollectionWithAllCards() {
		cardList.forEach(x -> cardService.create(x));

		assertArrayEquals(cardList.toArray(), cardService.readAll().toArray());
	}

	@Test

	public void Should_ModifyCardAndNotChangeOtherCard_When_CardWasUpdate() {
		cardList.forEach(x -> cardService.create(x));

		Card updateCard = cardService.read(cardList.get(1).getId()).get();

		updateCard.setDescription("updated Card");
		updateCard.setCardStatus(CardStatus.DONE);
		cardService.update(updateCard);

		assertEquals(updateCard, cardService.read(updateCard.getId()).get());
		assertEquals(
				cardList.stream()
						.filter(x -> x.getId() != updateCard.getId())
						.collect(Collectors.toList()),

				cardService.readAll()
						.stream()
						.filter(x -> x.getId() != updateCard.getId())
						.collect(Collectors.toList())
		);

	}

	@Test(expected = NullPointerException.class)
	public void Should_ThrowException_When_CartWasNotFound() {
		firstCard.setCardStatus(CardStatus.DONE);
		cardService.update(firstCard);
	}

	@Test
	public void Should_RemoveOnlyOneCard_When_CardWasDeleted() {
		cardList.forEach(x -> cardService.create(x));

		assertTrue(cardService.delete(cardList.get(1).getId()));
		assertEquals(2, cardService.readAll().size());
	}

	@Test(expected = NullPointerException.class)
	public void Should_ThrowException_When_CardWasNotInDb() {
		cardService.delete(firstCard.getId());
	}

	@Test
	public void Should_AssignTime_When_CardWasAdded() {
		when(dateService.getNow()).thenReturn(this.nowTime);
		Card createdCard = cardService.create(firstCard);

		assertEquals(this.nowTime, createdCard.getCardHistory().getAddedTime());
	}

	@Test
	public void Should_AssignTimeOfModification_When_UpdateMethodWasCall() {
		when(dateService.getNow()).thenReturn(this.nowTime);
		cardService.turnOnTimeAssign(OperationType.ADD,OperationType.UPDATE,OperationType.READ);

		Card createdCard = cardService.create(firstCard);
		createdCard.setTitle("Title was changed ");
		cardService.update(createdCard);

		assertEquals(this.nowTime, createdCard.getCardHistory().getModificationTime());
	}

	@Test
	public void Should_AssignTimeOfRead_When_CardWasTakenFromDb(){
		when(dateService.getNow()).thenReturn(this.nowTime);
		cardService.turnOnTimeAssign(OperationType.ADD,OperationType.UPDATE,OperationType.READ);
		cardService.create(firstCard);
		Card cardFromDb =  cardService.read(firstCard.getId()).get();

		assertEquals(this.nowTime, cardFromDb.getCardHistory().getReadingTime());
	}

	@Test
	public void Should_AssignTimeOfRead_When_AllCardsWasTakenFromDb(){
		when(dateService.getNow()).thenReturn(this.nowTime);
		cardService.turnOnTimeAssign(OperationType.ADD,OperationType.UPDATE,OperationType.READ);
		cardList.forEach(cardService::create);

		cardService.readAll().forEach( x ->
				assertEquals(this.nowTime, x.getCardHistory().getReadingTime())
		);
	}

	@Test
	public void Should_DisableTimeAssign_When_CardWasCreated() {
		cardService.turnOffTimeAssign(OperationType.ADD);
		Card createdCard = cardService.create(firstCard);

		assertNull(createdCard.getCardHistory().getAddedTime());
	}

	@Test
	public void Should_DisableTimeAssign_When_CardWasRead() {
		when(dateService.getNow()).thenReturn(this.nowTime);
		cardService.create(firstCard);
		cardService.turnOffTimeAssign(OperationType.READ);

		assertNull(cardService.read(firstCard.getId()).get().getCardHistory().getReadingTime());
	}

	@Test
	public void Should_DisableTimeAssign_When_AllCardsWasRead() {
		when(dateService.getNow()).thenReturn(this.nowTime);
		cardList.forEach(cardService::create);
		cardService.turnOffTimeAssign(OperationType.READ);

		cardService.readAll().forEach( x ->
				assertNull( x.getCardHistory().getReadingTime())
		);
	}

	@Test
	public void Should_DisableTimeAssign_When_CardWasUpdate(){
		when(dateService.getNow()).thenReturn(this.nowTime);
		cardService.create(firstCard);
		Card cardFromDb = cardService.read(firstCard.getId()).get();
		cardFromDb.setTitle("Card Was Updated");

		cardService.turnOffTimeAssign(OperationType.UPDATE);

		assertNull(cardService.update(cardFromDb).getCardHistory().getModificationTime());
	}

	@Test
	public void Should_EnableTimeAssign_When_TurnedOnTimeAssign(){
		when(dateService.getNow()).thenReturn(this.nowTime);

		cardService.turnOffTimeAssign(OperationType.ADD,OperationType.UPDATE,OperationType.READ);
		Card createdFirstCard = cardService.create(firstCard);

		cardService.turnOnTimeAssign(OperationType.ADD);
		Card createdSecondCard = cardService.create(secondCard);


		assertNull(createdFirstCard.getCardHistory().getAddedTime());
		assertEquals(this.nowTime,createdSecondCard.getCardHistory().getAddedTime());
	}




}