package org.santiago.repository;

import org.santiago.model.PersonAttributes;

import java.util.List;


public interface PersonAttributesRepository {
    List<PersonAttributes> findAllPersons();
    String requestData();
    PersonAttributes setInformationToFile(String newPersonInfo);

    PersonAttributes addPerson(PersonAttributes newPerson);

}
