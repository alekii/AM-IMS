package org.am.fakers;

public class Faker extends com.github.javafaker.Faker {

    public EntityFaker entity = new EntityFaker();

    public DomainFaker domain = new DomainFaker();
}
