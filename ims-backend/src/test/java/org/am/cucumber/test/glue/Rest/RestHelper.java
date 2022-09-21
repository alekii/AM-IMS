package org.am.cucumber.test.glue.Rest;

public enum RestHelper {
    INSTANCE(new Rest());

    private Rest rest;

    RestHelper(Rest instance) {

        this.rest = instance;
    }

    public static Rest getRest() {

        return INSTANCE.rest;
    }
}
