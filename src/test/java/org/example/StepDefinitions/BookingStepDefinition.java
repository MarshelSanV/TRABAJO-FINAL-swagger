package org.example.StepDefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import org.example.Questions.ResponseCode;
import org.example.Tasks.GetAllBookings;
import org.example.Tasks.PostBooking;
import org.example.Tasks.PostToken;
import org.example.Tasks.PatchBooking;
import org.example.Tasks.DeleteBooking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.equalTo;

public class BookingStepDefinition {

    public static Logger LOGGER = LoggerFactory.getLogger(BookingStepDefinition.class);

    @Before
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
    }

    @Given("el {actor} establece el endpoint de booking")
    public void elActorEstableceElEndpointDeBooking(Actor actor) {
        actor.whoCan(CallAnApi.at("https://restful-booker.herokuapp.com"));
    }

    @When("el {actor} realiza una solicitud GET")
    public void elActorRealizaUnaSolicitudGET(Actor actor) {
        actor.attemptsTo(GetAllBookings.fromEndpoint("/booking"));
    }

    @Then("el codigo de respuesta debe ser {int}")
    public void elCodigoDeRespuestaDebeSer(int responseCode) {
        theActorInTheSpotlight().should(seeThat("El código de respuesta", ResponseCode.getStatus(), equalTo(responseCode)));
    }

    @When("^el actor crea un booking con el (.*) (.*) (.*) (.*) (.*) (.*) (.*)$")
    public void elActorCreaUnBookingConEl(String firstname, String lastname, String totalprice, String depositpaid, String checkin, String checkout, String additionalneeds) {
        theActorInTheSpotlight().attemptsTo(PostBooking.fromPage(firstname, lastname, totalprice, depositpaid, checkin, checkout, additionalneeds));
    }

    @When("^el actor actualiza un booking con los datos (.*) (.*) (.*)$")
    public void elActorActualizaUnBookingConLosDatos(String firstname, String lastname, String totalprice) {
        theActorInTheSpotlight().attemptsTo(PostToken.generateToken());
        System.out.println("Token generado: " + (String) theActorInTheSpotlight().recall("token"));
        theActorInTheSpotlight().attemptsTo(PatchBooking.fromPage(firstname, lastname, totalprice));
    }

    @When("el actor elimina el booking creado anteriormente")
    public void elActorEliminaElBookingCreadoAnteriormente() {
        theActorInTheSpotlight().attemptsTo(PostToken.generateToken());
        theActorInTheSpotlight().attemptsTo(DeleteBooking.fromPage());
    }

}
