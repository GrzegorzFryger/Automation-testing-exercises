package bdd.cucumber.steps;

import edu.pjwstk.tau.domain.Card;
import edu.pjwstk.tau.domain.CardHistory;
import edu.pjwstk.tau.domain.CardStatus;
import edu.pjwstk.tau.domain.User;
import edu.pjwstk.tau.service.CardServiceCache;
import edu.pjwstk.tau.service.DataServiceProxyImpl;
import edu.pjwstk.tau.service.OperationType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CardServiceSteps {

	private CardServiceCache cardService;
	private List<Card> cardList;
	private List<Card> cardListWithRegex;
	private List<Card> cardListToRemove;


	public CardServiceSteps() {
		this.cardService =  new CardServiceCache(new DataServiceProxyImpl());
		cardService.turnOffTimeAssign(OperationType.ADD,OperationType.READ, OperationType.UPDATE);
	}


	@Given("User create cards with description about {string}, {string}, {string}")
	public void user_create_cards_with_description_about(String string, String string2, String string3) {
		cardService.clean();

		cardList = Arrays.asList(
				Card.builder()
						.id(3)
						.title("test no.3")
						.description(string)
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
						.description(string2)
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
						.description(string3)
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
	public void save_them_in_database() {
		cardList.forEach(cardService::create);
		cardService.readAll().forEach(System.out::println);
	}

	@When("user wants find card with descriptions about {string}")
	public void user_wants_find_card_with_descriptions_about(String string) {
		cardListWithRegex	= cardService.findByRegexOnDescription(string);
	}

	@Then("service returned cards with description contains {string}")
	public void service_returned_cards_with_description_contains(String string) {
		cardListWithRegex.forEach(x -> assertTrue(x.getDescription().contains(string)));
	}

	@Given("User create cards with equals: {int} {int} {int} {int}")
	public void user_create_cards_with_equals(Integer int1, Integer int2, Integer int3, Integer int4) {
		cardService.clean();

		cardList = Arrays.asList(
				Card.builder()
						.id(int1)
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
						.id(int2)
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
						.id(int3)
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
						.id(int4)
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

	@Given("user create list with two cards to remove {int} {int}")
	public void user_create_list_with_two_cards_to_remove(Integer int1, Integer int2) {
		this.cardListToRemove = cardList.stream()
				.filter(x -> x.getId() == int1 || x.getId() == int2 )
				.collect(Collectors.toList());
	}

	@When("user remove cards by list cards to remove")
	public void user_remove_cards_by_list_cards_to_remove() {
		cardService.delete(cardListToRemove);
	}

	@Then("database not contains list cards to remove")
	public void database_not_contains_list_cards_to_remove() {
		assertThat(cardService.readAll(), not(contains(cardListToRemove)));
	}

	@Then("size elements in database is {int}")
	public void size_elements_in_database_is(Integer int1) {
		assertEquals(cardService.readAll().size(), int1.intValue() );
	}
}
