package com.recipeapp.model;

import java.util.ArrayList;

public class Recipe {
    private String name;
    private ArrayList<Ingredient> ingredients;
    
    //Recipeクラスのコンストラクタ
    //レシピ名と材料Listで初期化する
    public Recipe(String name, ArrayList<Ingredient> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    //レシピ名のgetter
    public String getName() {
        return name;
    }

    //材料Listのgetter
    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }
}
