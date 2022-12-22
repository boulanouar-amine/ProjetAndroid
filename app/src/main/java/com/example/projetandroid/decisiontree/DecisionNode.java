package com.example.projetandroid.decisiontree;

import static com.example.projetandroid.decisiontree.DecisionTree.getMostCommonClass;
import static com.example.projetandroid.decisiontree.DecisionTree.getSubData1;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DecisionNode extends Node {

    private final String feature;
    private final Map<String, Node> children;

    public DecisionNode(String feature) {
        this.feature = feature;
        this.children = new HashMap<>();
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(feature).append(" -> \n");
        for (Map.Entry<String, Node> entry : children.entrySet()) {
            sb.append("  ").append(entry.getKey()).append(" -> ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }

    public void addChild(String value, Node child) {
        this.children.put(value, child);
    }


    public String predict(List<String> data, List<String> features) {

        try {
            int index = features.indexOf(feature);
            String value = data.get(index);
            Node child = children.get(value);
            if (child == null) {
                return getMostCommonClass(getSubData1());
            }

            return child.predict(data, features);
        } catch (Exception e) {
            return getMostCommonClass(getSubData1());
        }

    }

}

