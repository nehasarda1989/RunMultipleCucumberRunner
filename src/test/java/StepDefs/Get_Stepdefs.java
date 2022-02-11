package StepDefs;

import java.util.List;
import java.util.Map;

import org.junit.Assert;

import apiEngine.model.requests.AddBooksRequest;
import apiEngine.model.requests.AuthorizationRequest;
import apiEngine.model.requests.ISBN;
import apiEngine.model.requests.RemoveBookRequest;
import apiEngine.model.responses.Book;
import apiEngine.model.responses.Books;
import apiEngine.model.responses.Token;
import apiEngine.model.responses.UserAccount;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Get_Stepdefs {
	private static final String USER_ID = "9b5f49ab-eea9-45f4-9d66-bcf56a531b85";
	private static final String BASE_URL = "https://bookstore.toolsqa.com";

	private static Response response;
	private static Token tokenResponse;
	private static Book book;

	@Given("I am an authorized user")
	public void iAmAnAuthorizedUser() {
		AuthorizationRequest credentials = new AuthorizationRequest("TOOLSQA-Test", "Test@@123");

		RestAssured.baseURI = BASE_URL;
		RequestSpecification request = RestAssured.given();

		request.header("Content-Type", "application/json");
		response = request.body(credentials).post("/Account/v1/GenerateToken");

		// Deserializing the Response body into tokenResponse
		tokenResponse = response.getBody().as(Token.class);
		System.out.println("tokenResponse : " + tokenResponse);
	}

	@Given("A list of books are available")
	public void listOfBooksAreAvailable() {
		RestAssured.baseURI = BASE_URL;
		RequestSpecification request = RestAssured.given();

		response = request.get("/BookStore/v1/Books");

		// Deserializing the Response body into Books class
		Books books = response.getBody().as(Books.class);
		book = books.books.get(0);
	}

	@When("I add a book to my reading list")
	public void addBookInList() {
		ISBN isbn = new ISBN(book.isbn);
		AddBooksRequest addBooksRequest = new AddBooksRequest(USER_ID, isbn);

		RestAssured.baseURI = BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer " + tokenResponse.token).header("Content-Type", "application/json");

		response = request.body(addBooksRequest).post("/BookStore/v1/Books");
	}

	@Then("The book is added")
	public void bookIsAdded() {
		Assert.assertEquals(201, response.getStatusCode());

		UserAccount userAccount = response.getBody().as(UserAccount.class);

		Assert.assertEquals(USER_ID, userAccount.userID);
		Assert.assertEquals(book.isbn, userAccount.books.get(0).isbn);
	}

	@When("I remove a book from my reading list")
	public void removeBookFromList() {
		RestAssured.baseURI = BASE_URL;
		RequestSpecification request = RestAssured.given();

		RemoveBookRequest removeBookRequest = new RemoveBookRequest(USER_ID, book.isbn);

		request.header("Authorization", "Bearer " + tokenResponse.token).header("Content-Type", "application/json");

		response = request.body(removeBookRequest).delete("/BookStore/v1/Book");
	}

	@Then("The book is removed")
	public void bookIsRemoved() {
		Assert.assertEquals(204, response.getStatusCode());

		RestAssured.baseURI = BASE_URL;
		RequestSpecification request = RestAssured.given();

		request.header("Authorization", "Bearer " + tokenResponse.token).header("Content-Type", "application/json");

		response = request.get("/Account/v1/User/" + USER_ID);
		Assert.assertEquals(200, response.getStatusCode());

		UserAccount userAccount = response.getBody().as(UserAccount.class);
		Assert.assertEquals(0, userAccount.books.size());
	}

}
