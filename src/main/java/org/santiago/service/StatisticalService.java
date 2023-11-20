package org.santiago.service;

import org.santiago.model.PersonAttributes;

import java.math.BigDecimal;
import java.util.List;

public interface StatisticalService {
    int countNumberOfPeople();
    BigDecimal getAverageAgeUnemployed();
    BigDecimal getAverageStratumUnemployed();
    BigDecimal getAverageNumberOfChildrenUnemployed();
    BigDecimal getAverageSalaryUnemployed();
    BigDecimal getAverageSalary();
    String getTrendEducationalLevel();
    Double getMedianAge();
    Double getMedianStratum();
    Double getMedianNumberOfChildren();
    Double getPercentageMen();
    Double getPercentageWoman();

    PersonAttributes addPerson(PersonAttributes newPerson);

    List<PersonAttributes> findAllPersons();

    PersonAttributes setInformationToFile(String newPersonInfo);

}
