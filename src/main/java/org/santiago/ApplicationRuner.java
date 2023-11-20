package org.santiago;

import org.santiago.repository.PersonAttributesUsingFileRepositoryImpl;
import org.santiago.service.StatisticalService;
import org.santiago.service.StatisticalServiceImpl;

import javax.swing.*;
import java.math.RoundingMode;
import java.util.Scanner;

public class ApplicationRuner {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int i = 1;
        while (i==1){
            PersonAttributesUsingFileRepositoryImpl objetPersonAttributesUsingFilesRepositoryTest = new PersonAttributesUsingFileRepositoryImpl();
            //StatisticalService objectStatisticalService = new StatisticalServiceImpl(objetPersonAttributesUsingFilesRepositoryTest);
            String dato = JOptionPane.showInputDialog(null, "¿Desea ingresar una nueva persona?\n"+"Escriba 1 para agregar ó presione 0 para terminar proceso: \n");
            System.out.flush();
            i = Integer.parseInt(dato);

            if (i==1){
                objetPersonAttributesUsingFilesRepositoryTest.setInformationToFile(objetPersonAttributesUsingFilesRepositoryTest.requestData());
            }
            PersonAttributesUsingFileRepositoryImpl objetPersonAttributesUsingFilesRepositoryTest2 = new PersonAttributesUsingFileRepositoryImpl();
            StatisticalService objectStatisticalService2 = new StatisticalServiceImpl(objetPersonAttributesUsingFilesRepositoryTest2);
            int totalPeople = objectStatisticalService2.countNumberOfPeople();
            System.out.print("El numero de personas registradas es: "+ objectStatisticalService2.countNumberOfPeople()+"\n");
            System.out.print("Del total de personas que equivale a "+totalPeople+" personas, el promedio de edad de las personas desempleadas es de "
                    + ""+objectStatisticalService2.getAverageAgeUnemployed().setScale(0, RoundingMode.HALF_UP)+" años\n");
            System.out.print("Del total de personas que equivale a "+totalPeople+" personas, el promedio de estrato de las personas desempleadas es de "
                    + ""+objectStatisticalService2.getAverageStratumUnemployed().setScale(0, RoundingMode.HALF_UP)+"\n");
            System.out.print("Del total de personas que equivale a "+totalPeople+" personas, el promedio de numero de hijos de las personas desempleadas es de "
                    + ""+objectStatisticalService2.getAverageNumberOfChildrenUnemployed().setScale(0, RoundingMode.HALF_UP)+"\n");
            System.out.print("Del total de personas que equivale a "+totalPeople+" personas, el promedio de salario de las personas desempleadas es de "
                    + ""+objectStatisticalService2.getAverageSalaryUnemployed().setScale(2, RoundingMode.HALF_UP)+" pesos Colombianos\n");
            System.out.print("Del total de personas que equivale a "+totalPeople+" personas, el promedio de salario de las personas es de "
                    + ""+objectStatisticalService2.getAverageSalary().setScale(2, RoundingMode.HALF_UP)+" pesos Colombianos\n");
            System.out.print(objectStatisticalService2.getTrendEducationalLevel()+"\n");
            System.out.print("La mediana de la edad de las personas es "+objectStatisticalService2.getMedianAge()+"\n");
            System.out.print("La mediana del estrato de las personas es "+objectStatisticalService2.getMedianStratum()+"\n");
            System.out.print("La mediana del numero de hijos de las personas es "+objectStatisticalService2.getMedianNumberOfChildren()+"\n");
            System.out.print("El total de personas que equivalen a "+totalPeople+ ", el porcentaje de hombres"
                    + " equivale a el "+(objectStatisticalService2.getPercentageMen()*100)+"%\n");
            System.out.print("El total de personas que equivalen a "+totalPeople+ ", el porcentaje de mujeres"
                    + " equivale a el "+(objectStatisticalService2.getPercentageWoman()*100)+"%\n");

        }
    }
}