package com.example.projetandroid;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

public class Algorithm {

    // helper functions

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

    //method to normalize a Double arraylist between 0 and 1 should be generic later
    static ArrayList<Double> normalizeData(ArrayList<Double> arraylist){
        ArrayList<Double> normalizedList = new ArrayList<>();
        for(int i = 0 ; i<arraylist.size() ; i++) {
            //the value is normalized using the formula (x-min)/(max-min) * (x-new_max)/(new_max-new_min) with new_max=1 and new_min=0
            double value = ((arraylist.get(i) - minValue(arraylist)) / (maxValue(arraylist) - minValue(arraylist)));
            normalizedList.add(value);
        }
            return normalizedList;
        }

        //KNN section
    public static TreeSet<Integer> NearestNeighbour(Personne targetPersonne, List<Personne> personnes , int knumber){

  
        //age has to be normalized between 0 and 1 to be able to compare it with the other attributes
        ArrayList<Double> ageList = initializeAge(personnes);

        ArrayList<Double> normalizedAgeList = normalizeData(ageList);
        ArrayList<Double> ageDifferenceList = normalizeData(ageList);

        for(int i = 0; i<normalizedAgeList.size(); i++){
            
            //Target age has to be normalized between 0 and 1 to be able to compare it with the other ages
            double normalizedTargetAge = (targetPersonne.getAge() - minValue(ageList)) / (maxValue(ageList) - minValue(ageList));

            double ageDifference = Math.abs(normalizedTargetAge - normalizedAgeList.get(i));
            ageDifferenceList.add(ageDifference);
        }


        int ageSync = 0;
        double totalDifference;
        LinkedHashMap<Double,Integer> totalDifferenceMap = new LinkedHashMap<>();

        //went with double for all the attributes out of convenience
        for (Personne p : personnes) {

            //this ageSync is to sync the personnes list with the ageDifferenceList because age normalization is done outside the loop
            ageSync++;

            //genre has M/F values so we have to convert them to 0/1 to be able to compare them with the other attributes
            double genreDifference = targetPersonne.getGenre().equals(p.getGenre()) ? 0 : 1;
            double bloodPressureDifference;

            //Blood pressure has 3 states so we have to calculate the difference between them manually
            if (targetPersonne.getBloodPressure().equals(p.getBloodPressure())) {
                bloodPressureDifference = 0;
            } else if (
                    (targetPersonne.getBloodPressure().equals("HIGH") && p.getBloodPressure().equals("NORMAL")) ||
                            (targetPersonne.getBloodPressure().equals("NORMAL") && p.getBloodPressure().equals("HIGH")) ||
                            (targetPersonne.getBloodPressure().equals("NORMAL") && p.getBloodPressure().equals("LOW")) ||
                            (targetPersonne.getBloodPressure().equals("LOW") && p.getBloodPressure().equals("NORMAL"))
            ) {
                bloodPressureDifference = 0.5;
            } else {
                bloodPressureDifference = 1;
            }

            int cholesterolDifference = targetPersonne.getCholesterol().equals(p.getCholesterol()) ? 0 : 1;

            double naDifference = Math.abs(targetPersonne.getNa() - p.getNa());
            double kDifference = Math.abs(targetPersonne.getK() - p.getK()) * 10 ; // *10 to make it fit with the other attributes

            //total difference is the sum of all the differences between the target and the current personne in the loop
            //using a distance metric formula based on the euclidean distance (square root of the sum of the squares of the differences)
            totalDifference = Math.abs(Math.sqrt( Math.pow(ageDifferenceList.get(ageSync), 2)
                                                + Math.pow(genreDifference, 2)
                                                + Math.pow(bloodPressureDifference, 2)
                                                + Math.pow(cholesterolDifference, 2)
                                                + Math.pow(naDifference, 2)
                                                + Math.pow(kDifference, 2)));

            totalDifferenceMap.put(totalDifference,p.getId());

        }
        //TreeMap to sort the totalDifferenceMap by key (totalDifference)
        TreeMap<Double, Integer> sortedDifferenceMap = new TreeMap<>(totalDifferenceMap);

        //TreeSet to store the 5 (can be changed to a variable later) nearest neighbours of the target
        TreeSet<Integer> nearestNeighbours = new TreeSet<>();
        
        for(int i = 1; i <= knumber; i++) {
           nearestNeighbours.add(sortedDifferenceMap.get(sortedDifferenceMap.firstKey()));
            sortedDifferenceMap.remove(sortedDifferenceMap.firstKey());
        }

        return nearestNeighbours;
    }

    // Method to find the most frequent value in a String arraylist again should be generic later
    public static String mostFrequent(ArrayList<String> arrayList) {
        int maxCount = 0;
        String  mostFrequentElement = "";
        for (int i = 0 ; i < arrayList.size(); i++) {
            int count = 0;
            for (int j = 0; j < arrayList.size(); j++) {
                if (arrayList.get(j).equals(arrayList.get(i)))
                    count++;
            }
            if (count > maxCount) {
                maxCount = count;
                mostFrequentElement = arrayList.get(i);
            }
        }

        return mostFrequentElement;
    }
    
    // Start of Bayes section

    public static void putInMap(Map<String, Integer> map, String key) {
        if (map.containsKey(key)) {
            map.put(key, map.get(key) + 1);
        } else {
            map.put(key, 1);
        }
    }

    public static Double listeAverage(ArrayList<Double> liste) {
        Double somme = 0.0;
        for (Double valeur : liste) {
            somme += valeur;
        }
        return somme / liste.size();
    }

    //method to initialize the arraylist of Personne Na
    public static ArrayList<Double> initializeNa(List<Personne> personnes){
        ArrayList<Double> naList = new ArrayList<>();
        for (Personne personne : personnes) {
            naList.add(personne.getNa());
        }
        return naList;
    }

    //method to initialize the arraylist of Personne K
    //method to initialize the arraylist of Personne Na
    public static ArrayList<Double> initializeK(List<Personne> personnes){
        ArrayList<Double> kList = new ArrayList<>();
        for (Personne personne : personnes) {
            kList.add(personne.getK());
        }
        return kList;
    }

    public static String naiveBayes(Personne targetPersonne, List<Personne> personnes){

        SortedMap<String,Integer> drugMap = new TreeMap<>();

        //Age Map
        SortedMap<String,Integer> AgeMapHigh = new TreeMap<>();
        SortedMap<String,Integer> AgeMapLow = new TreeMap<>();
        //Genre Map
        SortedMap<String,Integer> drugGenreM = new TreeMap<>();
        SortedMap<String,Integer> drugGenreF = new TreeMap<>();
        //Bloodpressure Map
        SortedMap<String,Integer> drugBloodPressureHigh = new TreeMap<>();
        SortedMap<String,Integer> drugBloodPressureNormal = new TreeMap<>();
        SortedMap<String,Integer> drugBloodPressureLow = new TreeMap<>();
        //Cholesterol Map
        SortedMap<String,Integer> drugCholesterolHigh = new TreeMap<>();
        SortedMap<String,Integer> drugCholesterolNormal = new TreeMap<>();
        //Na Map
        SortedMap<String,Integer> drugNaHigh = new TreeMap<>();
        SortedMap<String,Integer> drugNaNormal = new TreeMap<>();
        //K Map
        SortedMap<String,Integer> drugKHigh = new TreeMap<>();
        SortedMap<String,Integer> drugKNormal = new TreeMap<>();


        //Age List
        ArrayList<Double> ageList = initializeAge(personnes);

        ArrayList<Double> naList = initializeNa(personnes);
        ArrayList<Double> kList = initializeK(personnes);

        double ageAverage = listeAverage(ageList);
        int age;
        String drug;
        String genre;
        String bloodPressure;
        String cholesterol;
        double na;
        double naAverage = listeAverage(naList);
        double k;
        double kAverage = listeAverage(kList);


        //getting the number of occurences per Drug
        for (Personne p : personnes) {

            drug = p.getDrug();

            if(!drug.equals("null")) {

                putInMap(drugMap, drug);

                age = p.getAge();

                if (age > ageAverage)
                    putInMap(AgeMapHigh, drug);
                else if(age < ageAverage)
                    putInMap(AgeMapLow, drug);

                genre = p.getGenre();

                if(genre.equals("M"))
                    putInMap(drugGenreM, drug);
                else
                    putInMap(drugGenreF, drug);

                bloodPressure = p.getBloodPressure();

                if(bloodPressure.equals("HIGH"))
                    putInMap(drugBloodPressureHigh, drug);

                else if(bloodPressure.equals("NORMAL"))
                    putInMap(drugBloodPressureNormal, drug);

                else
                    putInMap(drugBloodPressureLow, drug);

                cholesterol = p.getCholesterol();

                if(cholesterol.equals("HIGH"))
                    putInMap(drugCholesterolHigh, drug);
                else
                    putInMap(drugCholesterolNormal, drug);

                na = p.getNa();

                if(na > naAverage)
                    putInMap(drugNaHigh, drug);
                else
                    putInMap(drugNaNormal, drug);

                k = p.getK();

                if(k > kAverage)
                    putInMap(drugKHigh, drug);
                else
                    putInMap(drugKNormal, drug);
            }
        }


        System.out.println("drug number :" + drugMap);

        //Treatement of the target personne
        System.out.println("Average age :" + ageAverage);
        System.out.println("drug age high :" + AgeMapHigh);
        System.out.println("drug age low :" + AgeMapLow);

        System.out.println("drug genre M :" + drugGenreM);
        System.out.println("drug genre F :" + drugGenreF);

        System.out.println("drug blood pressure high :" + drugBloodPressureHigh);
        System.out.println("drug blood pressure normal :" + drugBloodPressureNormal);
        System.out.println("drug blood pressure low :" + drugBloodPressureLow);

        System.out.println("drug cholesterol high :" + drugCholesterolHigh);
        System.out.println("drug cholesterol normal :" + drugCholesterolNormal);

        System.out.println("drug na high :" + drugNaHigh);
        System.out.println("drug na normal :" + drugNaNormal);

        System.out.println("drug k high :" + drugKHigh);
        System.out.println("drug k normal :" + drugKNormal);

        return "Naive bayes";
    }

}
