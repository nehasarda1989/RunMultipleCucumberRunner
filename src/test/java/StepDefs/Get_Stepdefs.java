package StepDefs;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Get_Stepdefs {

	@Then("The book is added")
	public void the_book_is_added() {
		// Write code here that turns the phrase above into concrete actions
		System.out.println("Test 1");
	}

	@When("I remove a book from my reading list")
	public void i_remove_a_book_from_my_reading_list() {
		// Write code here that turns the phrase above into concrete actions
		System.out.println("Test 2");

	}

}
