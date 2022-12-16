package com.example.projetandroid;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Algorithme {

    // Method to find minimum value from the arrayList
    static double minValue(ArrayList<Double> arraylist) {
        double min = arraylist.get(0);
        for(int i = 1; i<arraylist.size(); i++)
            if(arraylist.get(i)<min) min = arraylist.get(i);
        return min;
    }

    // Method to find maximum value from the ArrayList
    static double maxValue(ArrayList<Double> arraylist){
        double max = arraylist.get(0);
        for(int i = 1; i<arraylist.size(); i++)
            if(arraylist.get(i)>max) max = arraylist.get(i);
        return max;
    }

    //method to initialize the arraylist of Personne Age

    public static ArrayList<Double> initializeAge(List<Personne> personnes){
        ArrayList<Double> ageList = new ArrayList<>();
        for (Personne personne : personnes) {
            ageList.add((double) personne.getAge());
        }
        return ageList;
    }

    static ArrayList<Double> normalizeData(ArrayList<Double> arraylist){
        ArrayList<Double> normalizedList = new ArrayList<>();
        for(int i = 0 ; i<arraylist.size() ; i++) {
            double value = ((arraylist.get(i) - minValue(arraylist)) / (maxValue(arraylist) - minValue(arraylist)));
            normalizedList.add(value);
        }
            return normalizedList;
        }
    public static String NearestNeighbour(Personne target, List<Personne> personnes) {

        double minAgeDifference = 10;
        double minGenreDifference = 1;
        double minBloodPressureDifference = 1;
        double minCholesterolDifference = 1;
        double minNaDifference = 1;
        double minKDifference = 1;

        //age has to be normalized between 0 and 1 to be able to compare it with the other attributes
        ArrayList<Double> ageList = initializeAge(personnes);

        ArrayList<Double> normalizedAgeList = normalizeData(ageList);
        ArrayList<Double> ageDifferenceList = normalizeData(ageList);

        for(int i = 0; i<normalizedAgeList.size(); i++){

            double normalizedAge = (target.getAge() - minValue(ageList)) / (maxValue(ageList) - minValue(ageList));

            double ageDifference = Math.abs(normalizedAge - normalizedAgeList.get(i)) ;
            ageDifferenceList.add(ageDifference);
        }


        int i = 0;
        double totalDifference;
        LinkedHashMap<Double,String> totalDifferenceMap = new LinkedHashMap<>();

        for (Personne p : personnes) {
            p.setUsername("Personne " + i);
            i++;

            double genreDifference = target.getGenre().equals(p.getGenre()) ? 0 : 1;
            double bloodPressureDifference;

            // if the blood pressure has 3 states so we have to calculate the difference
            if (target.getBloodPressure().equals(p.getBloodPressure())) {
                bloodPressureDifference = 0;
            } else if (
                    (target.getBloodPressure().equals("HIGH") && p.getBloodPressure().equals("NORMAL")) ||
                            (target.getBloodPressure().equals("NORMAL") && p.getBloodPressure().equals("HIGH")) ||
                            (target.getBloodPressure().equals("NORMAL") && p.getBloodPressure().equals("LOW")) ||
                            (target.getBloodPressure().equals("LOW") && p.getBloodPressure().equals("NORMAL"))
            ) {
                bloodPressureDifference = 0.5;
            } else {
                bloodPressureDifference = 1;
            }
            int cholesterolDifference = target.getCholesterol().equals(p.getCholesterol()) ? 0 : 1;

            float naDifference = Math.abs((float) target.getNa() - (float) p.getNa());
            float kDifference = Math.abs((float) target.getK() - (float) p.getK());

            totalDifference = Math.abs(Math.sqrt(Math.pow(ageDifferenceList.get(i), 2) + Math.pow(genreDifference, 2) + Math.pow(bloodPressureDifference, 2) + Math.pow(cholesterolDifference, 2) + Math.pow(naDifference, 2) + Math.pow(kDifference, 2)));
            totalDifferenceMap.put(totalDifference,p.getUsername());

        }
        System.out.println(totalDifferenceMap);


        return "Drug FFFFF";
    }
}
