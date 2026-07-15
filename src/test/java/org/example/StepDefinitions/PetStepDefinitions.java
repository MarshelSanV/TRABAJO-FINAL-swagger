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
import org.example.Questions.ResponseCode;
import org.example.Tasks.DeletePet;
import org.example.Tasks.GetPet;
import org.example.Tasks.PostPet;
import org.example.Tasks.PutPet;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.lessThan;

public class PetStepDefinitions {

    @Before
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
    }

    @Dado("que el actor establece el endpoint de PetStore")
    public void queElActorEstableceElEndpointDePetStore() {
        OnStage.theActorCalled("Tatiana").whoCan(CallAnApi.at("https://petstore.swagger.io/v2"));
    }

    @Cuando("el actor registra una mascota con nombre {string} y imagen {string} y raza {string} y estado {string}")
    public void elActorRegistraUnaMascotaConNombreYImagenYRazaYEstado(String nombre, String imagen, String raza, String estado) {
        String idMascota = String.valueOf((long) (Math.random() * 1000000000L));
        theActorInTheSpotlight().remember("petId", idMascota);
        theActorInTheSpotlight().attemptsTo(PostPet.fromPage(idMascota, nombre, imagen, raza, estado));
    }

    @Y("la respuesta debe contener el nombre {string} y la imagen {string} y la raza {string} y el estado {string}")
    public void laRespuestaDebeContenerElNombreYLaImagenYLaRazaYElEstado(String nombre, String imagen, String raza, String estado) {
        SerenityRest.restAssuredThat(response -> response
                .body("id", notNullValue())
                .body("name", equalTo(nombre))
                .body("photoUrls[0]", equalTo(imagen))
                .body("tags[0].name", equalTo(raza))
                .body("status", equalTo(estado))
                .time(lessThan(5000L))
        );
    }

    @Y("existe una mascota registrada con nombre {string} y imagen {string} y raza {string} y estado {string}")
    public void existeUnaMascotaRegistradaConNombreYImagenYRazaYEstado(String nombre, String imagen, String raza, String estado) {
        String idMascota = String.valueOf((long) (Math.random() * 1000000000L));
        theActorInTheSpotlight().remember("petId", idMascota);
        theActorInTheSpotlight().attemptsTo(PostPet.fromPage(idMascota, nombre, imagen, raza, estado));
    }

    @Cuando("el actor consulta la mascota por su ID")
    public void elActorConsultaLaMascotaPorSuID() {
        String idMascota = theActorInTheSpotlight().recall("petId");
        theActorInTheSpotlight().attemptsTo(GetPet.fromPage(idMascota));
    }

    @Cuando("el actor actualiza el estado de la mascota a {string} con el nombre {string} y imagen {string} y raza {string}")
    public void elActorActualizaElEstadoDeLaMascotaAConElNombreYImagenYRaza(String estado, String nombre, String imagen, String raza) {
        String idMascota = theActorInTheSpotlight().recall("petId");
        theActorInTheSpotlight().attemptsTo(PutPet.fromPage(idMascota, nombre, imagen, raza, estado));
    }

    @Cuando("el actor elimina la mascota")
    public void elActorEliminaLaMascota() {
        String idMascota = theActorInTheSpotlight().recall("petId");
        theActorInTheSpotlight().attemptsTo(DeletePet.fromPage(idMascota));
    }

    @Y("al intentar consultar nuevamente su ID el codigo de respuesta debe ser {int}")
    public void alIntentarConsultarNuevamenteSuIDElCodigoDeRespuestaDebeSer(int expectedStatusCode) {
        String idMascota = theActorInTheSpotlight().recall("petId");
        theActorInTheSpotlight().attemptsTo(GetPet.fromPage(idMascota));
        theActorInTheSpotlight().should(seeThat("Código de error esperado al buscar mascota borrada", 
                ResponseCode.getStatus(), equalTo(expectedStatusCode)));
    }
}
