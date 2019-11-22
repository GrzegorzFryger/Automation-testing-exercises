package bdd.jbehave.steps;

import edu.pjwstk.tau.domain.Card;
import edu.pjwstk.tau.domain.CardHistory;
import edu.pjwstk.tau.domain.CardStatus;
import edu.pjwstk.tau.domain.User;
import edu.pjwstk.tau.service.CardServiceCache;
import edu.pjwstk.tau.service.DataServiceProxyImpl;
import edu.pjwstk.tau.service.OperationType;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CardServiceStep {
	private CardServiceCache cardService;
	private List<Card> cardList;
	private List<Card> cardListWithRegex;
	private List<Card> cardListToRemove;


	public CardServiceStep() {
		this.cardService =  new CardServiceCache(new DataServiceProxyImpl());
		cardService.turnOffTimeAssign(OperationType.ADD,OperationType.READ, OperationType.UPDATE);
	}

	@Given("User create cards with description about $description1, $description2, $description3")
	public void createCardsWithdescryption(String description1, String description2, String description3 ) {
		cardService.clean();

		cardList = Arrays.asList(
				Card.builder()
						.id(3)
						.title("test no.3")
						.description(description1)
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
						.description(description2)
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
						.description(description3)
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

	@Given("User create cards with equals: $id, $id2, $id3, $id4")
	public void createCardsWithId( int id, int id2, int id3, int id4) {
		cardService.clean();

		cardList = Arrays.asList(
				Card.builder()
						.id(id)
						.title("test no.3")
						.description("test card")
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
						.id(id2)
						.title("test no.4")
						.description("test card")
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
						.id(id3)
						.title("test no.5")
						.description("test card")
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
						.id(id4)
						.title("test no.6")
						.description("test card")
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

	@Given("save them in database")
	public void saveCardsInDatabase(){
		cardList.forEach(cardService::create);
		cardService.readAll().forEach(System.out::println);
	}

	@Given("user create list with two cards to remove: $id5, $id6")
	public void createListCardToRemove(int id, int id2){
		this.cardListToRemove = cardList.stream()
				.filter(x -> x.getId() == id || x.getId() == id2 )
				.collect(Collectors.toList());
	}

	@When("user wants find card with description about $regex")
	public void findCardByRegex(String regex){
	  cardListWithRegex	= cardService.findByRegexOnDescription(regex);
	}

	@When("user remove cards by list cards to remove")
	public void removeCardsByList(){
	    cardService.delete(cardListToRemove);
	}

	@Then("service returned cards with description contains $word")
	public void cardsFromDbContainsWord(String word){
		cardListWithRegex.forEach(x -> assertTrue(x.getDescription().contains(word)));
	}
	@Then("database not contains list cards to remove")
	public void cardsFromDbContainsWord(){
		assertThat(cardService.readAll(), not(contains(cardListToRemove)));
	}
	@Then("size elements in database is $size")
	public void checkSizeElementsInDb(int size){
		assertEquals(cardService.readAll().size(), size );
	}




}
