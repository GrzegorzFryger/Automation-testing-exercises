package bdd.cucumber.story;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = "pretty", features = "src/test/resources/bdd/cucumber/story",  glue = {"bdd.cucumber.steps"})
public class CardServiceStoryTest {

}