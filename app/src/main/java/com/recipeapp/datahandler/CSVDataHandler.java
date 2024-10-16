package com.recipeapp.datahandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.recipeapp.model.Ingredient;
import com.recipeapp.model.Recipe;

//csvファイルに操作を行うための実装クラス
public class CSVDataHandler implements DataHandler {
    private String filePath;

    public CSVDataHandler() {
        this.filePath = "app/src/main/resources/recipes.csv";
    }

    public CSVDataHandler(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getMode() {
        return "CSV";
    }

    //csvファイルからデータを読み取りArrayListとして返すメソッド
    @Override
    public ArrayList<Recipe> readData() throws IOException{
        ArrayList<Recipe> recipes = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String inputData;
            //csvファイルを全行読み込む
            while ((inputData = reader.readLine()) != null) {
                //最初の区切り文字までの文字列をレシピ名として格納する。
                String recipeName = inputData.substring(0, inputData.indexOf(","));

                //最初の区切り文字以降は区切り文字毎に材料名としてlistに格納する
                ArrayList<Ingredient> ingredients = new ArrayList<>();
                String[] ingredientsName = inputData.substring(inputData.indexOf(",") + 1, inputData.length()).split(", ");
                for (String i : ingredientsName) {
                    Ingredient ingredient = new Ingredient(i);
                    ingredients.add(ingredient);
                }


                //レシピ名と材料listを初期値としてrecipeクラスのインスタンスを生成する
                Recipe recipe = new Recipe(recipeName, ingredients);

                //recipeクラスをリストrecipesに格納する
                recipes.add(recipe);
            }
        }
        return recipes;
    }

    //csvファイルに一行でレシピ名と材料名を追加するメソッド
    @Override
    public void writeData(Recipe recipe) throws IOException{
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            //レシピ名と区切り文字を書き込む
            writer.newLine();
            writer.write(recipe.getName() + ",");
            
            //材料名のリストを区切り文字で結合し書き込む
            ArrayList<Ingredient> ingredients = new ArrayList<>(recipe.getIngredients());
            ArrayList<String> ingredientNameList = new ArrayList<>();
            for (Ingredient i : ingredients) {
                ingredientNameList.add(i.getName());
            }
            String ingredientNameJoin = String.join(", ", ingredientNameList);
            writer.write(ingredientNameJoin);
        }
    }

    @Override
    public ArrayList<Recipe> searchData(String keyword) throws IOException{
        CSVDataHandler csvDataHandler = new CSVDataHandler();
        ArrayList<Recipe> allRecipes = new ArrayList<>(csvDataHandler.readData());

        ArrayList<Recipe> searchRecipe = new ArrayList<>();

        int exeFlag = 0;
        if(keyword.contains("name") && keyword.contains("ingredient")) {
            exeFlag = 1;
        } else if (keyword.contains("name")) {
            exeFlag = 2;
        } else {
            exeFlag = 3;
        }

        String[] searchWord = keyword.split("&");
        switch(exeFlag) {
            case 1:
                for (int j = 0; j < searchWord.length; j++) {
                    if (searchWord[j].indexOf("name") >= 0) {
                        String[] searchWordRecipe = searchWord[j].split("=");
                        for (int i = 0; i < allRecipes.size(); i++) {
                            if(searchWordRecipe[1].equals(allRecipes.get(i).getName())) {
                                String[] searchWordIngredient = searchWord[j].split("=");
                                for (int k = 0; k < allRecipes.size(); k++) {
                                    ArrayList<Ingredient> ingredients = new ArrayList<>(allRecipes.get(i).getIngredients());
                                    for (Ingredient h : ingredients) {
                                        if (searchWordIngredient[1].equals(h.getName())) {
                                            searchRecipe.add(allRecipes.get(i));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                break;

            case 2:
                String[] searchWordRecipe = searchWord[0].split("=");
                for (int i = 0; i < allRecipes.size(); i++) {
                    if(searchWordRecipe[1].equals(allRecipes.get(i).getName())) {
                        searchRecipe.add(allRecipes.get(i));
                    }
                }
                break;

            case 3:
                String[] searchWordIngredient = searchWord[0].split("=");
                for (int i = 0; i < allRecipes.size(); i++) {
                    ArrayList<Ingredient> ingredients = new ArrayList<>(allRecipes.get(i).getIngredients());
                        for (Ingredient j : ingredients) {
                            if (searchWordIngredient[1].equals(j.getName())) {
                            searchRecipe.add(allRecipes.get(i));
                        }
                    }
                }
        }
                
        return searchRecipe;
    }
}
