package org.example;

import java.io.Serializable;

//Serializable needed for the Redis
public class PersonDTO implements Serializable {
    private String id;
    private String name;
    private int age;

    public String getId() {
        return id;
    }

    public PersonDTO setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public PersonDTO setName(String name) {
        this.name = name;
        return this;
    }

    public int getAge() {
        return age;
    }

    public PersonDTO setAge(int age) {
        this.age = age;
        return this;
    }
}
