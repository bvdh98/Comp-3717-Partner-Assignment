package com.example.harrison_wark;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BaseParser {
    @SerializedName("results")
    @Expose
    private ArrayList<Parser> results = new ArrayList<>();

    public ArrayList<Parser> getResults() {
        return results;
    }

    public void setResults(ArrayList<Parser> results) {
        this.results = results;
    }
}

