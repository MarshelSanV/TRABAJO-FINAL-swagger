package org.example.Tasks;

import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Put;
import net.serenitybdd.rest.SerenityRest;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class PutPet implements Task {
    private final String id;
    private final String name;
    private final String imagen;
    private final String raza;
    private final String status;

    public PutPet(String id, String name, String imagen, String raza, String status) {
        this.id = id;
        this.name = name;
        this.imagen = imagen;
        this.raza = raza;
        this.status = status;
    }

    public static Performable fromPage(String id, String name, String imagen, String raza, String status) {
        return instrumented(PutPet.class, id, name, imagen, raza, status);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        String jsonBody = """
                {
                  "id": %s,
                  "category": {
                    "id": 1,
                    "name": "perritos"
                  },
                  "name": "%s",
                  "photoUrls": [
                    "%s"
                  ],
                  "tags": [
                    {
                      "id": 1,
                      "name": "%s"
                    }
                  ],
                  "status": "%s"
                }""".formatted(id, name, imagen, raza, status);

        actor.attemptsTo(
                Put.to("/pet")
                        .with(request -> request
                                .contentType(ContentType.JSON)
                                .header("Accept", "application/json")
                                .body(jsonBody)
                                .log().all()
                        )
        );

        SerenityRest.lastResponse().body().prettyPrint();
    }
}
