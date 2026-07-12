package org.example.Questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.rest.SerenityRest;

public class ResponseCode {
    public static Question<Integer> getStatus() {
        return actor -> SerenityRest.lastResponse().statusCode();
    }
    public Integer answeredBy(Actor actor){
        return SerenityRest.lastResponse().statusCode();
    }
}

