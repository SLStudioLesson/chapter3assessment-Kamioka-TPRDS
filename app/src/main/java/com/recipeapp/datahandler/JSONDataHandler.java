package com.recipeapp.datahandler;

import java.util.ArrayList;

import com.recipeapp.model.Recipe;

public class JSONDataHandler implements DataHandler {
    // private String filePath;
    
    // public JSONDataHandler() {
    //     this.filePath = "app/src/main/resources/recipes.csv";
    // }

    // public JSONDataHandler(String filePath) {
    //     this.filePath = filePath;
    // }

    @Override
    public String getMode() {
        return "JSON";
    }

    @Override
    public ArrayList<Recipe> readData() {
        return null;
    }

    @Override
    public void writeData(Recipe recipe) {

    }

    @Override
    public ArrayList<Recipe> searchData(String keyword) {
        return null;
    }
}
