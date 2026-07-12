package org.example.Tasks;

import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.rest.interactions.Put;
import net.serenitybdd.rest.SerenityRest;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class PutBooking implements Task {
    private final String firstname;
    private final String lastname;
    private final String totalprice;
    private final String depositpaid;
    private final String checkin;
    private final String checkout;
    private final String additionalneeds;
    private final String idBook;
    private final String token;

    public PutBooking(String firstname, String lastname, String totalprice, String depositpaid, String checkin, String checkout, String additionalneeds) {
        // Clean surrounding quotes captured from cucumber steps
        this.firstname = firstname.replace("\"", "");
        this.lastname = lastname.replace("\"", "");
        this.totalprice = totalprice.replace("\"", "");
        this.depositpaid = depositpaid.replace("\"", "");
        this.checkin = checkin.replace("\"", "");
        this.checkout = checkout.replace("\"", "");
        this.additionalneeds = additionalneeds.replace("\"", "");
        this.idBook = OnStage.theActorInTheSpotlight().recall("bookingId");
        this.token = OnStage.theActorInTheSpotlight().recall("token");
    }

    public static Performable fromPage(String firstname, String lastname, String totalprice, String depositpaid, String checkin, String checkout, String additionalneeds) {
        return instrumented(PutBooking.class, firstname, lastname, totalprice, depositpaid, checkin, checkout, additionalneeds);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Put.to("/booking/" + idBook)
                .with(requestSpecification -> requestSpecification
                        .contentType(ContentType.JSON)
                        .header("Accept", "application/json")
                        .header("Cookie", "token=" + token)
                        .body(
                            """
                            {
                            "firstname": "%s",
                            "lastname": "%s",
                            "totalprice": %s,
                            "depositpaid": %s,
                            "bookingdates": {
                                "checkin": "%s",
                                "checkout": "%s"
                            },
                            "additionalneeds": "%s"
                            }""".formatted(firstname, lastname, totalprice, depositpaid, checkin, checkout, additionalneeds)
                        ).log().all()));

        SerenityRest.lastResponse().body().prettyPrint();
    }
}
