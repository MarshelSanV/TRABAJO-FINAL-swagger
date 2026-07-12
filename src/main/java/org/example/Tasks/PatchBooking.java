package org.example.Tasks;

import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.rest.interactions.Patch;
import net.serenitybdd.rest.SerenityRest;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class PatchBooking implements Task {
    private final String firstname;
    private final String lastname;
    private final String totalprice;
    private final String idBook;
    private final String token;

    public PatchBooking(String firstname, String lastname, String totalprice) {
        // Clean surrounding quotes captured from cucumber steps
        this.firstname = firstname.replace("\"", "");
        this.lastname = lastname.replace("\"", "");
        this.totalprice = totalprice.replace("\"", "");
        this.idBook = OnStage.theActorInTheSpotlight().recall("bookingId");
        this.token = OnStage.theActorInTheSpotlight().recall("token");
    }

    public static Performable fromPage(String firstname, String lastname, String totalprice) {
        return instrumented(PatchBooking.class, firstname, lastname, totalprice);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Patch.to("/booking/" + idBook)
                .with(requestSpecification -> requestSpecification
                        .contentType(ContentType.JSON)
                        .header("Accept", "application/json")
                        .header("Cookie", "token=" + token)
                        .body(
                            """
                            {
                            "firstname": "%s",
                            "lastname": "%s",
                            "totalprice": %s
                            }""".formatted(firstname, lastname, totalprice)
                        ).log().all()));

        SerenityRest.lastResponse().body().prettyPrint();
    }
}
