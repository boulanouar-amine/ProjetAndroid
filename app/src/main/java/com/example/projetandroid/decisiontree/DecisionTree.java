package com.example.projetandroid.decisiontree;

import android.os.Build;

import androidx.annotation.NonNull;

import java.text.DecimalFormat;
import java.util.*;

public class DecisionTree {

    private final Node root;

    private final List<String> features;

     private static List<List<String>> subData1;

    public static List<List<String>> getSubData1() {
        return subData1;
    }

    public DecisionTree(List<List<String>> data, List<String> features) {

        subData1 = data;
        this.features = features;

        root = buildTree(data, features);
    }

    @NonNull
    @Override
    public String toString() {
        return root.toString();
    }

    public static Node buildTree(List<List<String>> data, List<String> features) {

        if (data.isEmpty() || features.isEmpty()) return null;

        boolean allSameClass = true;
        String firstClass = data.get(0).get(data.get(0).size() - 1);

        for (List<String> line : data) {
            if (!line.get(line.size() - 1).equals(firstClass)) {
                allSameClass = false;

                break;
            }
        }

        if (allSameClass) {
            return new LeafNode(firstClass);
        }
        String bestFeature = chooseBestFeature(data, features);
        DecisionNode node = new DecisionNode(bestFeature);

        List<String> remainingFeatures = new ArrayList<>(features);
        remainingFeatures.remove(bestFeature);


        for (String value : getPossibleValues(data, features, bestFeature)) {

            List<List<String>> subData = getSubData(data,features, bestFeature, value);
            if(subData.isEmpty()) {

                node.addChild(value, new LeafNode(getMostCommonClass(data)));
            } else {
                node.addChild(value, buildTree(subData, remainingFeatures));
            }
            subData1 = subData;
            System.out.println("Remaining Features " + remainingFeatures );
            System.out.println("Subdata " + subData);
            System.out.println("removed feature " + bestFeature);

        }
        return node;
    }

    public static String getMostCommonClass(List<List<String>> subData) {
        Map<String, Integer> classCounts = new HashMap<>();

        for (List<String> line : subData) {
            {
                String classLabel = line.get(line.size() - 1);
                if (classCounts.containsKey(classLabel)) {
                    classCounts.put(classLabel, classCounts.get(classLabel) + 1);
                } else {
                    classCounts.put(classLabel, 1);
                }
            }

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Collections.max(classCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
        }
        return null;
    }


    public static String chooseBestFeature(List<List<String>> data, List<String> features) {

        HashMap<String, Double> featureProbability = new HashMap<>();

        String classFeature = features.get(features.size() - 1);

        ArrayList<String> classValues = getPossibleValues(data, features, classFeature);

        HashMap<String, Double> FeatureEntropyTotal = new HashMap<>();

        for (String feature : features.subList(0, features.size() - 1)) {

            ArrayList<String> values = getPossibleValues(data, features, feature);

            HashMap<String, Integer> valueOccurence = new HashMap<>();

            int numberOfData = data.size();

            double magicNumber;

            HashMap<String, Double> valueEntropy = new HashMap<>();


            for (String value : values) {


                valueOccurence.put(value, getNumberOfOccurenceData(data, features, feature, value));


                for (String classValue : classValues) {

                    int numberOfOccurenceByClass = getNumberOfOccurenceByClass(data, features, feature, value, classValue);


                    magicNumber = -(double) numberOfOccurenceByClass / (double) valueOccurence.get(value) * Math.log((double) numberOfOccurenceByClass / (double) valueOccurence.get(value)) / Math.log(2);

                    if (Double.isNaN(magicNumber)) {
                        magicNumber = 0;
                    }
                    if (valueEntropy.containsKey(value)) {
                        valueEntropy.put(value, magicNumber + valueEntropy.get(value));
                    } else
                        valueEntropy.put(value, magicNumber);


                }

                double probability = (double) valueOccurence.get(value) / (double) numberOfData;

                featureProbability.put(value, probability);


            }


            double entropyFeatureTotal = 0;

            for (String value : values) {

                entropyFeatureTotal += featureProbability.get(value) * valueEntropy.get(value);

            }
            FeatureEntropyTotal.put(feature, entropyFeatureTotal);

        }


        double min = Double.MAX_VALUE;
        String bestFeature = null;
        for (String feature : FeatureEntropyTotal.keySet()) {

            if (FeatureEntropyTotal.get(feature) < min) {
                min = FeatureEntropyTotal.get(feature);
                bestFeature = feature;


            }

        }


        return bestFeature;
    }

    private static int getNumberOfOccurenceByClass(List<List<String>> data, List<String> features, String feature, String value, String classValue) {
        int numberOfOccurenceByClass = 0;
        for (List<String> line : data) {
            if (line.get(features.indexOf(feature)).equals(value) && line.get(line.size() - 1).equals(classValue)) {
                numberOfOccurenceByClass++;
            }
        }
        return numberOfOccurenceByClass;
    }


    private static int getNumberOfOccurenceData(List<List<String>> data, List<String> features, String feature, String value) {

        int numberOfOccurence = 0;
        for (List<String> line : data) {
            for (int i = 0; i <= line.size(); i++) {

                if (i == features.indexOf(feature) && line.get(i).equals(value)) numberOfOccurence++;
            }

        }
        return numberOfOccurence;
    }

    public static ArrayList<String> getPossibleValues(List<List<String>> data, List<String> features, String bestFeature) {

        ArrayList<String> possibleValues = new ArrayList<>();


        for (List<String> line : data) {
            for (int i = 0; i <= line.size(); i++) {

                if (i == features.indexOf(bestFeature)) {

                    if (!possibleValues.contains(line.get(i)))
                        possibleValues.add(line.get(i));
                }
            }


        }
        return possibleValues;
    }

    public static List<List<String>> getSubData(List<List<String>> data, List<String> features, String bestFeature, String value) {

        List<List<String>> subData = new ArrayList<>();

        for (List<String> line : data) {
            List<String> list = new LinkedList<>(line);
            for (int i = 0; i <= line.size(); i++) {

                if (i == features.indexOf(bestFeature) && line.get(i).equals(value)) {
                    list.remove(i);
                    subData.add(list);
                }
            }

        }

           return subData;
    }

    public String predict(List<String> data) {


        return root.predict(data,features);
    }


}

