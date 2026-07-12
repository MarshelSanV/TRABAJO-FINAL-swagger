package org.example.Tasks;

import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.rest.interactions.Post;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class PostBooking implements Task {

    private final String firstname, lastname, totalprice, depositpaid, checkin, checkout, additionalneeds;

    public PostBooking(String firstname, String lastname, String totalprice, String depositpaid, String checkin, String checkout, String additionalneeds) {
        // Clean surrounding quotes captured from cucumber steps
        this.firstname = firstname.replace("\"", "");
        this.lastname = lastname.replace("\"", "");
        this.totalprice = totalprice.replace("\"", "");
        this.depositpaid = depositpaid.replace("\"", "");
        this.checkin = checkin.replace("\"", "");
        this.checkout = checkout.replace("\"", "");
        this.additionalneeds = additionalneeds.replace("\"", "");
    }

    public static Performable fromPage(String firstname, String lastname, String totalprice, String depositpaid, String checkin, String checkout, String additionalneeds) {
        return instrumented(PostBooking.class, firstname, lastname, totalprice, depositpaid, checkin, checkout, additionalneeds);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Post.to("/booking")
                .with(requestSpecification -> requestSpecification
                        .contentType(ContentType.JSON)
                        .header("Accept", "application/json")
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

        if (SerenityRest.lastResponse().statusCode() == 200) {
            OnStage.theActorInTheSpotlight().remember("bookingId", SerenityRest.lastResponse().path("bookingid").toString());
            String valorDelBookingId = actor.recall("bookingId");
            System.out.println("BOOK ID: " + valorDelBookingId);
        }
    }
}
