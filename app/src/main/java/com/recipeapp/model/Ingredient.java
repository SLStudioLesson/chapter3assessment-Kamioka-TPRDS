package com.recipeapp.model;

public class Ingredient {
    private String name;

    //Ingredientクラスのコンストラクタ
    //材料名で初期化する
    public Ingredient(String name) {
        this.name = name;
    }

    //材料名のgetter
    public String getName() {
        return name;
    }
}
