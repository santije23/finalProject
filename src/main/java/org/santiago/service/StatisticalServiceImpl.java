package org.santiago.service;

import org.santiago.model.PersonAttributes;
import org.santiago.repository.PersonAttributesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StatisticalServiceImpl implements StatisticalService{

    private static final Logger logger = LoggerFactory.getLogger(StatisticalServiceImpl.class);
    private final PersonAttributesRepository personAttributesRepository;

    public StatisticalServiceImpl(PersonAttributesRepository personAttributesRepository) {
        this.personAttributesRepository = personAttributesRepository;
    }

    // El siguiente metodo permite contar el numero de personas registradas en la base de datos
    @Override
    public int countNumberOfPeople() {
        int totalNumberOfPeople = 0;
        for(PersonAttributes Atribute : personAttributesRepository.findAllPersons()) {
            totalNumberOfPeople += 1;
        }
        return totalNumberOfPeople;
    }

    // El siguiente metodo se realizo para calcular respecto al total de personas, el promedio de la edad
    // de los que son desempleados
    @Override
    public BigDecimal getAverageAgeUnemployed() {
        BigDecimal promEdad = BigDecimal.ZERO;

        for(PersonAttributes Atribute : personAttributesRepository.findAllPersons()) {
            if (!Atribute.employmentStatus()) {
                promEdad = promEdad.add(new BigDecimal(Atribute.age()));
            }
        }
        promEdad = promEdad.divide(BigDecimal.valueOf(countNumberOfPeople()), 2, RoundingMode.HALF_UP);
        logger.info("Se realizo una consulta en la BD, atributo edad");
        return promEdad;
    }

    // El siguiente metodo se realizo para calcular respecto al total de personas, el promedio del estrato
    // de los que son desempleados
    @Override
    public BigDecimal getAverageStratumUnemployed() {
        BigDecimal promEstrato = BigDecimal.ZERO;
        for(PersonAttributes Atribute : personAttributesRepository.findAllPersons()) {
            if (!Atribute.employmentStatus()) {
                promEstrato = promEstrato.add(new BigDecimal(Atribute.stratum()));
            }
        }
        promEstrato = promEstrato.divide(BigDecimal.valueOf(countNumberOfPeople()), 2, RoundingMode.HALF_UP);
        logger.info("Se realizo una consulta en la BD, atributo estrato");
        return promEstrato;
    }

    // El siguiente metodo se realizo para calcular respecto al total de personas, el promedio de numero de hijos
    // de los que son desempleados
    @Override
    public BigDecimal getAverageNumberOfChildrenUnemployed() {
        BigDecimal promNumeroDeHijos = BigDecimal.ZERO;
        for(PersonAttributes Atribute : personAttributesRepository.findAllPersons()) {
            if (!Atribute.employmentStatus()) {
                promNumeroDeHijos = promNumeroDeHijos.add(new BigDecimal(Atribute.numberOfChildren()));
            }
        }
        promNumeroDeHijos = promNumeroDeHijos.divide(BigDecimal.valueOf(countNumberOfPeople()), 2, RoundingMode.HALF_UP);
        logger.info("Se realizo una consulta en la BD, atributo numero de hijos");
        return promNumeroDeHijos;
    }

    // El siguiente metodo se realizo para calcular respecto al total de personas, el promedio del salario
    // de los que son desempleados
    @Override
    public BigDecimal getAverageSalaryUnemployed() {
        BigDecimal promSalario = BigDecimal.ZERO;
        for(PersonAttributes Atribute : personAttributesRepository.findAllPersons()) {
            if (!Atribute.employmentStatus()) {
                promSalario = promSalario.add(new BigDecimal(Atribute.salary()));
            }
        }
        promSalario = promSalario.divide(BigDecimal.valueOf(countNumberOfPeople()), 2, RoundingMode.HALF_UP);
        logger.info("Se realizo una consulta en la BD, atributo salario");
        return promSalario;
    }

    // El siguiente metodo se realizo para calcular respecto al total de personas, el promedio del salario
    @Override
    public BigDecimal getAverageSalary() {
        BigDecimal promEstrato = BigDecimal.ZERO;
        for(PersonAttributes Atribute : personAttributesRepository.findAllPersons()) {
            promEstrato = promEstrato.add(new BigDecimal(Atribute.salary()));
        }
        promEstrato = promEstrato.divide(BigDecimal.valueOf(countNumberOfPeople()), 2, RoundingMode.HALF_UP);
        logger.info("Se realizo una consulta en la BD, atributo salario");
        return promEstrato;
    }

    // El siguiente metodo se realizo para calcular respecto al total de personas, la moda del nivel educacional
    @Override
    public String getTrendEducationalLevel() {
        int contarPrimaria=0;
        int contarSecundaria=0;
        int contarTecnico=0;
        int ContarProfecional=0;
        for(PersonAttributes Atribute : personAttributesRepository.findAllPersons()) {
            if (Atribute.educationalLevel().equals("Primaria")){
                contarPrimaria +=1;
            } else if (Atribute.educationalLevel().equals("Secundaria")) {
                contarSecundaria +=1;
            } else if (Atribute.educationalLevel().equals("Tecnico")) {
                contarTecnico +=1;
            } else {
                ContarProfecional += 1;
            }
        }
        String modaNivelEdu="";
        int mayorModa=0;
        if (contarPrimaria>contarSecundaria && contarPrimaria>contarTecnico && contarPrimaria>ContarProfecional){
            modaNivelEdu="Primaria";
            mayorModa = contarPrimaria;
        } else if (contarSecundaria>contarPrimaria && contarSecundaria>contarTecnico && contarSecundaria>ContarProfecional) {
            modaNivelEdu="Secundaria";
            mayorModa = contarSecundaria;
        } else if (contarTecnico>contarPrimaria && contarTecnico>contarSecundaria && contarTecnico>ContarProfecional) {
            modaNivelEdu="Tecnico";
            mayorModa = contarTecnico;
        } else {
            modaNivelEdu="Profecional";
            mayorModa = ContarProfecional;
        }
        String modaNivelEducacional = "Se puede concluir que del total de personas que equivalen a "+countNumberOfPeople()+", "
                + "la moda en cuando el nivel Educativo es "
                + ""+modaNivelEdu+" que equivale a "+mayorModa+" personas";
        logger.info("Se realizo una consulta en la BD, atributo nivel educacional");
        return modaNivelEducacional;
    }

    // El siguiente metodo se realizo para calcular respecto al total de personas, la media de la edad
    @Override
    public Double getMedianAge() {
        List<Integer> listEdad = new ArrayList<>();
        for(PersonAttributes Atribute : personAttributesRepository.findAllPersons()) {
            listEdad.add(Atribute.age());
        }

        Collections.sort(listEdad);

        int nEntre2;
        int nEntre2Mas1;
        Double medianaEdad;
        nEntre2 = (countNumberOfPeople()/2)-1;
        nEntre2Mas1 = (countNumberOfPeople()/2);
        medianaEdad = (listEdad.get(nEntre2)+listEdad.get(nEntre2Mas1))/2D;
        logger.info("Se realizo una consulta en la BD, atributo edad");
        return medianaEdad;
    }

    // El siguiente metodo se realizo para calcular respecto al total de personas, la media del estrato
    @Override
    public Double getMedianStratum() {
        List<Integer> listEstrato = new ArrayList<>();
        for(PersonAttributes Atribute : personAttributesRepository.findAllPersons()) {
            listEstrato.add(Atribute.stratum());
        }

        Collections.sort(listEstrato);

        int nEntre2;
        int nEntre2Mas1;
        Double medianaEstrato;
        nEntre2 = (countNumberOfPeople()/2)-1;
        nEntre2Mas1 = (countNumberOfPeople()/2);
        medianaEstrato = (listEstrato.get(nEntre2)+listEstrato.get(nEntre2Mas1))/2D;
        logger.info("Se realizo una consulta en la BD, atributo estrato");
        return medianaEstrato;
    }

    // El siguiente metodo se realizo para calcular respecto al total de personas, la media del numero de hijos
    @Override
    public Double getMedianNumberOfChildren() {
        List<Integer> listNumHijos = new ArrayList<>();
        for(PersonAttributes Atribute : personAttributesRepository.findAllPersons()) {
            listNumHijos.add(Atribute.numberOfChildren());
        }

        Collections.sort(listNumHijos);

        int nEntre2;
        int nEntre2Mas1;
        Double medianaNumHijos;
        nEntre2 = (countNumberOfPeople()/2)-1;
        nEntre2Mas1 = (countNumberOfPeople()/2);
        medianaNumHijos = (listNumHijos.get(nEntre2)+listNumHijos.get(nEntre2Mas1))/2D;
        logger.info("Se realizo una consulta en la BD, atributo numero de hijos");
        return medianaNumHijos;
    }

    // El siguiente metodo se realizo para calcular respecto al total de personas, el porcentaje de hombres
    @Override
    public Double getPercentageMen() {
        int contarHombres = 0;
        double porcentajeHombres=0;

        for(PersonAttributes Atribute : personAttributesRepository.findAllPersons()) {
            if (Atribute.gender().equals('M')) {
                contarHombres +=1;
            }
        }

        porcentajeHombres = contarHombres/(double)(countNumberOfPeople());
        logger.info("Se realizo una consulta en la BD, atributo genero");
        return porcentajeHombres;
    }

    // El siguiente metodo se realizo para calcular respecto al total de personas, el porcentaje de mujeres
    @Override
    public Double getPercentageWoman() {
        int contarMujeres = 0;
        double porcentajeMujeres=0;

        for(PersonAttributes Atribute : personAttributesRepository.findAllPersons()) {
            if (Atribute.gender().equals('F')) {
                contarMujeres +=1;
            }
        }

        porcentajeMujeres = contarMujeres/(double)(countNumberOfPeople());
        logger.info("Se realizo una consulta en la BD, atributo genero");
        return porcentajeMujeres;
    }

    @Override
    public PersonAttributes addPerson(PersonAttributes newPerson) {
        return this.personAttributesRepository.addPerson( newPerson );
    }

    @Override
    public List<PersonAttributes> findAllPersons() {
        return this.personAttributesRepository.findAllPersons();
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
}
