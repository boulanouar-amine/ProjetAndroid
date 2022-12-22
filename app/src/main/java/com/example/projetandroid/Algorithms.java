package com.example.projetandroid;

import android.annotation.SuppressLint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

@SuppressLint("DefaultLocale")
public class Algorithms {
    public static String knn_Algorithm(Personne cible, List<Personne> list){
        // drug = Le médicament approprié
        String drug = "";

        // une liste des distances Euclidiennes et une autre pour les distances de Manahattan
        ArrayList<Double> distancesEuclidiennes = new ArrayList<Double>();
        ArrayList<Double> distancesManahattan = new ArrayList<Double>();

        //ajouter les distances à les deux listes
        for(Personne p : list){
            distancesManahattan.add(cible.distanceManahattan(p));
            distancesEuclidiennes.add(cible.distanceEuclidienne(p));
        }

        //récupérer le médicament de la personne la plus proche (par rapport à la distance Euclidienne ou de Manahattan) de la personne cible
        int im	=	indexOfMinVal(distancesManahattan);
        int ie	=	indexOfMinVal(distancesEuclidiennes);
        String drugM 	 = list.get(im).getDrug();
        String drugE 	 = list.get(ie).getDrug();
        String drugBayes = bayes_Algorithm(cible,list);

        if(drugE.equalsIgnoreCase(drugM))
            drug = drugE;

        //vérifier si les médicaments sont les mêmes, sinon variable drug reste null
        while(!drug.equalsIgnoreCase(drugBayes)){
            distancesManahattan.remove(im);
            distancesEuclidiennes.remove(ie);

            im	=	indexOfMinVal(distancesManahattan);
            ie	=	indexOfMinVal(distancesEuclidiennes);

            drugM 	 = list.get(im).getDrug();
            drugE 	 = list.get(ie).getDrug();

            if(drugE.equalsIgnoreCase(drugM))
                drug = drugM;
        }
        return drug;
    }

    private static int indexOfMinVal(List<Double> distances){
        //une simple méthode qui retourne l'indice de la distance la plus petite dans la liste "distances"
        double 	minVal 	 = distances.get(0);
        int 	minIndex = 0;

        for(int i=1;i<distances.size();i++)
            if(distances.get(i) < minVal){
                minVal 	 = distances.get(i);
                minIndex = i;
            }

        return minIndex;
    }

    public static String bayes_Algorithm(Personne cible, List<Personne> list){

        ArrayList<HashMap<String,  Double>> probabilities = new ArrayList<HashMap<String,  Double>>();

        ArrayList<Personne> drugA = new ArrayList<Personne>();
        ArrayList<Personne> drugB = new ArrayList<Personne>();
        ArrayList<Personne> drugC = new ArrayList<Personne>();
        ArrayList<Personne> drugX = new ArrayList<Personne>();
        ArrayList<Personne> drugY = new ArrayList<Personne>();

        for(Personne p : list)
            switch (p.getDrug()) {
                case "drugY": 	drugY.add(p);		break;
                case "drugX": 	drugX.add(p);		break;
                case "drugC": 	drugC.add(p);		break;
                case "drugB": 	drugB.add(p);		break;
                case "drugA": 	drugA.add(p);		break;
                default		:	break;
            }

        probabilities.add(classification(drugA)); 	// 0=drugA
        probabilities.add(classification(drugB));	// 1=drugB
        probabilities.add(classification(drugC));	// 2=drugC
        probabilities.add(classification(drugX));	// 3=drugX
        probabilities.add(classification(drugY));	// 4=drugY

        probabilities.get(0).put("proba", (double)drugA.size()/list.size() );
        probabilities.get(1).put("proba", (double)drugB.size()/list.size() );
        probabilities.get(2).put("proba", (double)drugC.size()/list.size() );
        probabilities.get(3).put("proba", (double)drugX.size()/list.size() );
        probabilities.get(4).put("proba", (double)drugY.size()/list.size() );

        return  appropriateDrug(cible, probabilities);
    }

    private static HashMap<String,  Double> classification(ArrayList<Personne> drug){
        HashMap<String,  Double> hm = new HashMap<String,  Double>();

        double aux1=0.0,aux2=0.0,aux3=0.0;
        //debut : Age
        for(Personne p : drug)
            switch (p.getAgeCat().toLowerCase()) {
                case "young": 	aux1++;		break;
                case "adult": 	aux2++;		break;
                default		:	break;
            }

        hm.put("young", (double)aux1/drug.size() );
        hm.put("adult", (double)aux2/drug.size() );
        aux1=0.0;	aux2=0.0;	aux3=0.0;
        //fin : Age

        //debut Genre
        for(Personne p : drug)
            switch (p.getGenre().toLowerCase()) {
                case "f": 	aux1++;		break;
                case "m": 	aux2++;		break;
                default		:	break;
            }

        hm.put("f", (double)aux1/drug.size() );
        hm.put("m", (double)aux2/drug.size() );
        aux1=0.0;	aux2=0.0;	aux3=0.0;
        //fin Genre

        //debut BP
        for(Personne p : drug)
            switch (p.getBloodPressure().toLowerCase()) {
                case "low": 	aux1++;		break;
                case "normal": 	aux2++;		break;
                case "high": 	aux3++;		break;
                default		:	break;
            }

        hm.put("bp_low", 	(double)aux1/drug.size() );
        hm.put("bp_normal", (double)aux2/drug.size() );
        hm.put("bp_high", 	(double)aux3/drug.size() );
        aux1=0.0;	aux2=0.0;	aux3=0.0;
        //fin BP

        //debut Cholesterol
        for(Personne p : drug)
            switch (p.getCholesterol().toLowerCase()) {
                case "normal": 	aux1++;		break;
                case "high": 	aux2++;		break;
                default		:	break;
            }

        hm.put("ch_normal", (double)aux1/drug.size() );
        hm.put("ch_high", 	(double)aux2/drug.size() );
        aux1=0.0;	aux2=0.0;	aux3=0.0;
        //fin Cholesterol

        //debut K
        for(Personne p : drug)
            switch (p.getKCat().toLowerCase()) {
                case "low": 	aux1++;		break;
                case "high": 	aux2++;		break;
                default		:	break;
            }

        hm.put("k_low", 	(double)aux1/drug.size() );
        hm.put("k_high", 	(double)aux2/drug.size() );
        aux1=0.0;	aux2=0.0;	aux3=0.0;
        //fin K

        //debut Na
        for(Personne p : drug)
            switch (p.getNaCat().toLowerCase()) {
                case "low": 	aux1++;		break;
                case "high": 	aux2++;		break;
                default		:	break;
            }

        hm.put("na_low", 	(double)aux1/drug.size() );
        hm.put("na_high", 	(double)aux2/drug.size() );
        aux1=0.0;	aux2=0.0;	aux3=0.0;
        //fin Na

        return hm;
    }

    private static String appropriateDrug (Personne cible ,ArrayList<HashMap<String,  Double>> probabilities) {
        HashMap<String,  Double> drugs = new HashMap<String, Double>();
        drugs.put("drugA", calcProb(cible, probabilities.get(0)));
        drugs.put("drugB", calcProb(cible, probabilities.get(1)));
        drugs.put("drugC", calcProb(cible, probabilities.get(2)));
        drugs.put("drugX", calcProb(cible, probabilities.get(3)));
        drugs.put("drugY", calcProb(cible, probabilities.get(4)));

        double maxVal = drugs.get("drugA");
        String drug = "drugA";

        for(String key : drugs.keySet())
            if(maxVal < drugs.get(key)){
                maxVal = drugs.get(key);
                drug = key;
            }

        return drug;
    }

    private static double calcProb(Personne cible, HashMap<String,  Double> hm){
        double probability;

        String ageCat 	= cible.getAgeCat().toLowerCase();
        String sexeCat 	= cible.getGenre().toLowerCase();
        String bpCat 	= "bp_"+cible.getBloodPressure().toLowerCase();
        String chCat 	= "ch_"+cible.getCholesterol().toLowerCase();
        String naCat 	= "na_"+cible.getNaCat().toLowerCase();
        String kCat 	= "k_"+cible.getKCat().toLowerCase();

        probability = hm.get(ageCat)*hm.get(sexeCat)*hm.get(bpCat)*hm.get(chCat)*hm.get(naCat)*hm.get(kCat)*hm.get("proba");


        return probability;
    }

}
