package StepDefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class PreRequisiteStepDef {
	@Given("A list of books are available")
	public void a_list_of_books_are_available() {
		// Write code here that turns the phrase above into concrete actions
		System.out.println("Pre 1");
	}

	@When("I add a book to my reading list")
	public void i_add_a_book_to_my_reading_list() {
		// Write code here that turns the phrase above into concrete actions
		System.out.println("Pre 2");
	}

}
