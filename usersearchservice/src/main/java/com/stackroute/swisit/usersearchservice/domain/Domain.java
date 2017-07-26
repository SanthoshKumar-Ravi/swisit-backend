/**....*/
package com.stackroute.swisit.usersearchservice.domain;
/*--------Importing Libraries-------*/
import org.hibernate.validator.constraints.NotEmpty;
import org.neo4j.ogm.annotation.NodeEntity;

/*--------Domain node Domain Class--------*/
@NodeEntity
public class Domain {

    /*-------Domain Class Properties------*/
    @NotEmpty
    private String name;

    /*-------Default Constructor of Domain Domain Class-------*/
    public Domain() {}

    /*-------Parameterized Constructor of Domain Domain Class-------*/
    public Domain(String name) {
        this.name = name;
    }


    /*------------Setter and Getter methods for Properties-----------*/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}