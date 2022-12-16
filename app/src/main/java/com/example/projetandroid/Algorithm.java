package com.example.projetandroid;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

public class Algorithm {

    // KNN section

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
    public static TreeSet<Integer> NearestNeighbour(Personne targetPersonne, List<Personne> personnes) {

  
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

            double naDifference = Math.abs((double) targetPersonne.getNa() - (double) p.getNa());
            double kDifference = Math.abs((double) targetPersonne.getK() - (double) p.getK());

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
        
        for(int Knumber = 1; Knumber <= 5; Knumber++) {
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
}
