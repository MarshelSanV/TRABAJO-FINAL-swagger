package org.example.StepDefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.rest.SerenityRest;
import org.example.Tasks.DeletePet;
import org.example.Tasks.GetPet;
import org.example.Tasks.PostPet;
import org.example.Tasks.PutPet;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.equalTo;

public class PetStepDefinitions {

    @Before
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
    }

    @Dado("que el actor establece el endpoint de PetStore")
    public void queElActorEstableceElEndpointDePetStore() {
        OnStage.theActorCalled("Tatiana").whoCan(CallAnApi.at("https://petstore.swagger.io/v2"));
    }

    @Cuando("el actor registra una mascota con nombre {string} y estado {string}")
    public void elActorRegistraUnaMascotaConNombreYEstado(String nombre, String estado) {
        // Generar un ID único dinámico por ejecución para evitar dependencia de datos
        String idMascota = String.valueOf((long) (Math.random() * 1000000000L));
        theActorInTheSpotlight().remember("petId", idMascota);
        theActorInTheSpotlight().attemptsTo(PostPet.fromPage(idMascota, nombre, estado));
    }

    @Y("la respuesta debe contener el nombre {string} y el estado {string}")
    public void laRespuestaDebeContenerElNombreYElEstado(String nombre, String estado) {
        SerenityRest.restAssuredThat(response -> response
                .body("name", equalTo(nombre))
                .body("status", equalTo(estado))
        );
    }

    @Y("existe una mascota registrada con nombre {string} y estado {string}")
    public void existeUnaMascotaRegistradaConNombreYEstado(String nombre, String estado) {
        String idMascota = String.valueOf((long) (Math.random() * 1000000000L));
        theActorInTheSpotlight().remember("petId", idMascota);
        theActorInTheSpotlight().attemptsTo(PostPet.fromPage(idMascota, nombre, estado));
    }

    @Cuando("el actor consulta la mascota por su ID")
    public void elActorConsultaLaMascotaPorSuID() {
        String idMascota = theActorInTheSpotlight().recall("petId");
        theActorInTheSpotlight().attemptsTo(GetPet.fromPage(idMascota));
    }

    @Cuando("el actor actualiza el estado de la mascota a {string} con el nombre {string}")
    public void elActorActualizaElEstadoDeLaMascotaAConElNombre(String estado, String nombre) {
        String idMascota = theActorInTheSpotlight().recall("petId");
        theActorInTheSpotlight().attemptsTo(PutPet.fromPage(idMascota, nombre, estado));
    }

    @Cuando("el actor elimina la mascota")
    public void elActorEliminaLaMascota() {
        String idMascota = theActorInTheSpotlight().recall("petId");
        theActorInTheSpotlight().attemptsTo(DeletePet.fromPage(idMascota));
    }
}
