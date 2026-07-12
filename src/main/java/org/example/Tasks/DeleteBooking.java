package org.example.Tasks;

import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.rest.interactions.Delete;
import net.serenitybdd.rest.SerenityRest;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class DeleteBooking implements Task {
    private final String idBook, token;

    public DeleteBooking() {
        // Recuperamos los datos de la sesión usando el actor en el spotlight
        this.idBook = OnStage.theActorInTheSpotlight().recall("bookingId");
        this.token = OnStage.theActorInTheSpotlight().recall("token");
    }

    public static Performable fromPage() {
        return instrumented(DeleteBooking.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Delete.from("/booking/" + idBook)
                .with(requestSpecification -> requestSpecification
                        .contentType(ContentType.JSON)
                        .header("Cookie", "token=" + token)
                        .log().all()
                )
        );

        SerenityRest.lastResponse().body().prettyPrint();
    }
}
