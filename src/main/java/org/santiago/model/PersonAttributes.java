package org.santiago.model;



public record PersonAttributes(String name, String lastName, Integer age, Character gender,
                               boolean employmentStatus, Integer stratum, Integer numberOfChildren,
                               String educationalLevel, Double salary) {

}

