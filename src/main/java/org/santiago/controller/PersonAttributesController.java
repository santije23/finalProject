package org.santiago.controller;

import org.santiago.model.PersonAttributes;
import org.santiago.repository.PersonAttributesUsingFileRepositoryImpl;
import org.santiago.service.StatisticalService;
import org.santiago.service.StatisticalServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;



@RestController
@RequestMapping("/personatributes/")
//@CrossOrigin(origins = "http://localhost:63342")
@CrossOrigin(origins = "*")
public class PersonAttributesController {

    //PersonAttributesUsingFileRepositoryImpl objetPersonAttributesUsingFilesRepositoryTest = new PersonAttributesUsingFileRepositoryImpl();
    StatisticalService statisticalService = new StatisticalServiceImpl( new PersonAttributesUsingFileRepositoryImpl());
    @GetMapping
    public List<PersonAttributes> listPeople(){

        System.out.println("listando People");
        List<PersonAttributes> peopleList =   statisticalService.findAllPersons();

        //http://localhost:8080/grades/

		/*

		[{"project":"UNIDAD 1","grade":4.5,"submissionDate":"2023-08-01"},
		{"project":"UNIDAD 2","grade":5.0,"submissionDate":"2023-09-01"},
		{"project":"UNIDAD 3","grade":3.6,"submissionDate":"2023-10-01"},
		{"project":"UNIDAD 4","grade":3.6,"submissionDate":"2023-10-10"}]
		*
		* */
        return peopleList;
    }

    @PostMapping
    public ResponseEntity<PersonAttributes> addPeople(@RequestBody PersonAttributes newPeople){
        System.out.println("adding people");
        String name = newPeople.name();
        String lname = newPeople.lastName();
        Integer age = newPeople.age();
        Character gender = newPeople.gender();
        boolean employmentStatus = newPeople.employmentStatus();
        Integer stratum = newPeople.stratum();
        Integer numberOfChildren = newPeople.numberOfChildren();
        String educationalLevel = newPeople.educationalLevel();
        double salarya = newPeople.salary();
        int salary = Double.valueOf(salarya).intValue();

        String newPerson = String.join(",", name, lname, String.valueOf(age), String.valueOf(gender), String.valueOf(employmentStatus), String.valueOf(stratum), String.valueOf(numberOfChildren), educationalLevel, String.valueOf(salary));

        PersonAttributes person = statisticalService.setInformationToFile((newPerson));
        PersonAttributes person2 = statisticalService.addPerson(newPeople);

        return ResponseEntity.status( HttpStatus.OK).body( person);
    }
}