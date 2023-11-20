package org.santiago.repository;

import org.santiago.model.PersonAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class PersonAttributesUsingFileRepositoryImpl implements PersonAttributesRepository{
    private static final Logger logger = LoggerFactory.getLogger( PersonAttributesUsingFileRepositoryImpl.class);
    private List<PersonAttributes> listOfPeople;
    public PersonAttributesUsingFileRepositoryImpl() {
        this.listOfPeople = new ArrayList<>(loadPeople());
    }



    private List<PersonAttributes> loadPeople(){
        logger.info( "Cargando los datos desde archivo" );
        List<String> plainTextPeobleList =  readFileWithPeople();
        return plainTextPeobleList.stream().map( this::buildPeople ).toList();

    }

    private List<String> readFileWithPeople(){

        Path path = Paths.get( "./src/main/resources/BD.txt");
        try (Stream<String> stream = Files.lines( path)) {
            return stream.toList();
        } catch (IOException x) {
            logger.error("IOException: {0}", x);
        }
        return Collections.emptyList();
    }

    public PersonAttributes buildPeople(String plainTextGrade){

        String[] questionArray = plainTextGrade.split(",");

        PersonAttributes personAttributes = new PersonAttributes( questionArray[0], questionArray[1],
                Integer.parseInt(questionArray[2]), questionArray[3].charAt(0),
                Boolean.parseBoolean(questionArray[4]), Integer.parseInt(questionArray[5]),
                Integer.parseInt(questionArray[6]), questionArray[7],
                Double.parseDouble(questionArray[8]));


        return personAttributes;
    }


    @Override
    public List<PersonAttributes> findAllPersons() {
        return listOfPeople;
    }


    @Override
    public String requestData() {
        Scanner sc = new Scanner(System.in);
        String newPersonInfo="";
        String name;
        String lastName;
        int age;
        char gender;
        boolean employmentStatus;
        int stratum;
        int numberOfChildren;
        String educationalLevel;
        int salary;
        System.out.println("Ingrese Nombre:");
        name = sc.next();
        System.out.println("Ingrese Apellido:");
        lastName = sc.next();
        System.out.println("Ingrese Edad:");
        age = sc.nextInt();
        System.out.println("Ingrese género, M para masculino y F para femenino:");
        gender = sc.next().charAt(0);
        System.out.println("Ingrese estado laboral true para laborando ó false para desempleado:");
        employmentStatus = sc.nextBoolean();
        System.out.println("Ingrese el número de estrato:");
        stratum = sc.nextInt();
        System.out.println("Ingrese número de hijos:");
        numberOfChildren = sc.nextInt();
        System.out.println("Ingrese nivel educacional entre: Tecnico, Primaria, Secundaria ó Profesional");
        educationalLevel = sc.next();
        System.out.println("Ingrese Salario actual:");
        salary = sc.nextInt();
        newPersonInfo = name + "," + lastName + "," + age + "," + gender + ","
                + employmentStatus + "," + stratum + "," + numberOfChildren + ","
                + educationalLevel + "," + salary;
        return newPersonInfo;
    }

    @Override
    public PersonAttributes setInformationToFile(String newPersonInfo) {
        try{
            FileWriter escritura = new FileWriter("./src/main/resources/BD.txt",true);
            escritura.write("\n"+newPersonInfo);
            escritura.close();
            logger.info( "Se agregaron nuevos datos al archivo" );

        }catch (IOException exception) {
            exception.printStackTrace(System.out);
            logger.info( "No se pudo agregar los datos al archivo" );
        }
        return null;
    }


    @Override
    public PersonAttributes addPerson(PersonAttributes newPerson) {
        this.listOfPeople.add( newPerson );

        return this.listOfPeople.stream()
                .filter( isTheEmissionsOfTheCountry( newPerson ) )//Busca las emisiones en la lista que corresponda al pais de las emisiones recien creadas
                .findAny()
                .orElse( null );//Si no las encuentra devuelve nulo
    }

    private Predicate<PersonAttributes> isTheEmissionsOfTheCountry(PersonAttributes newPerson) {
        return p -> p.name().equals( newPerson.name() );
    }
}
