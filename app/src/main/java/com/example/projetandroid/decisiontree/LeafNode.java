package com.example.projetandroid.decisiontree;

import androidx.annotation.NonNull;

import java.util.List;

public class LeafNode extends Node {

    private final String classLabel;

    public LeafNode(String classLabel) {
       this.classLabel = classLabel;
    }

    public String getClassLabel() {
        return classLabel;
    }

    @NonNull
    @Override
    public String toString() {
        return classLabel;

    }

    public String predict(List<String> data, List<String> features) {
        return classLabel;
    }

}
