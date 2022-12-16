package com.example.projetandroid;

import java.util.List;

public class Algorithme {

    public static Double NormalizeData(Double value, Double min, Double max){
        return (value - min) / (max - min);
    }

    public static String NearestNeighbour(Personne target, List<Personne> personnes) {

        double maxAgeDifference = 0;
        double maxGenreDifference = 0;
        double maxBloodPressureDifference = 0;
        double maxCholesterolDifference = 0;
        double maxNaDifference = 0;
        double maxKDifference = 0;

        for (Personne p : personnes) {
            double ageDifference = target.getAge() - p.getAge();
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

            if (ageDifference > maxAgeDifference ) {
                maxAgeDifference = ageDifference;
            }
            if (genreDifference > maxGenreDifference) {
                maxGenreDifference = genreDifference;
            }
            if (bloodPressureDifference > maxBloodPressureDifference) {
                maxBloodPressureDifference = bloodPressureDifference;
            }
            if (cholesterolDifference > maxCholesterolDifference) {
                maxCholesterolDifference = cholesterolDifference;
            }
            if (naDifference > maxNaDifference) {
                maxNaDifference = naDifference;
            }
            if (kDifference > maxKDifference) {
                maxKDifference = kDifference;
            }


        }
        return "Drug FFFFF";
    }
}
