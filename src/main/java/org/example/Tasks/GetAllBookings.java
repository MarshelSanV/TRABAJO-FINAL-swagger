package org.example.Tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Get;

public class GetAllBookings implements Task {
    private final String endpoint;

    public GetAllBookings(String endpoint) {
        this.endpoint = endpoint;
    }

    public static GetAllBookings fromEndpoint(String endpoint) {
        return Tasks.instrumented(GetAllBookings.class, endpoint);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Get.resource(endpoint)
        );
    }
}
