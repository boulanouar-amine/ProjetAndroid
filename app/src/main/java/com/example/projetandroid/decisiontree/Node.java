package com.example.projetandroid.decisiontree;

import java.util.List;

public abstract class Node {

    public abstract String predict(List<String> data, List<String> features);

}
