package com.recipeapp.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import com.recipeapp.datahandler.*;
import com.recipeapp.model.*;

public class RecipeUI {
    private BufferedReader reader;
    private DataHandler dataHandler;

    public RecipeUI(DataHandler dataHandler) {
        reader = new BufferedReader(new InputStreamReader(System.in));
        this.dataHandler = dataHandler;
    }

    public void displayMenu() {

        System.out.println("Current mode: " + dataHandler.getMode());

        while (true) {
            try {
                System.out.println();
                System.out.println("Main Menu:");
                System.out.println("1: Display Recipes");
                System.out.println("2: Add New Recipe");
                System.out.println("3: Search Recipe");
                System.out.println("4: Exit Application");
                System.out.print("Please choose an option: ");

                String choice = reader.readLine();

                switch (choice) {
                    case "1":
                        displayRecipes();
                        break;
                    case "2":
                        addNewRecipe();
                        break;
                    case "3":
                        break;
                    case "4":
                        System.out.println("Exiting the application.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please select again.");
                        break;
                }
            } catch (IOException e) {
                System.out.println("Error reading input from user: " + e.getMessage());
            }
        }

    }

    // ファイルの中身を整形して画面に表示するメソッド
    private void displayRecipes() {
        // レシピ名と材料名を受け取り、例外処理を行う
        ArrayList<Recipe> recipes = new ArrayList<>();
        try {
            recipes = new CSVDataHandler().readData();
        } catch (IOException e) {
            System.out.println("Error reading file: 例外のメッセージ");
        }

        // レシピデータが1件も存在しない場合の処理
        if (recipes.isEmpty()) {
            System.out.println("\nNo recipes available.");
            return;
        }

        // 画面に表示する
        System.out.println("\nRecipes:");
        System.out.println("-----------------------------------");

        for (Recipe i : recipes) {
            // レシピ名を表示する
            System.out.print("Recipe Name: ");
            System.out.println(i.getName());

            // 材料Listを区切り文字で結合し表示する
            System.out.print("Main Ingredients: ");
            List<Ingredient> ingredients = new ArrayList<>(i.getIngredients());
            List<String> ingredientNameList = new ArrayList<>();
            for (Ingredient j : ingredients) {
                ingredientNameList.add(j.getName());
            }
            String joinIngredients = String.join(", ", ingredientNameList);
            System.out.println(joinIngredients);

            System.out.println("-----------------------------------");
        }
    }

    // ユーザー入力を基にRecipeクラスのインスタンスを生成し、DataHandlerクラスに渡すメソッド
    private void addNewRecipe() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // レシピ名の入力を受け取る
        System.out.println("\nAdding a new recipe.");
        System.out.print("Enter recipe name: ");
        String inputRecipe = reader.readLine();

        // 材料名の入力を受け取り、Ingredientクラスのインスタンスを生成し、Listに追加する
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        System.out.println("Enter ingredients (type 'done' when finished):");
        String inputIngredient = "";
        while (true) {
            System.out.print("Ingredient: ");
            inputIngredient = reader.readLine();

            //doneが入力されたら入力処理を終了する
            if(inputIngredient.equals("done")) {
                break;
            }
            
            Ingredient ingredient = new Ingredient(inputIngredient);
            ingredients.add(ingredient);
        }

        // レシピ名と材料ListでRecipeクラスのインスタンスを生成する
        Recipe newRecipe = new Recipe(inputRecipe, ingredients);

        // writeDataメソッドにRecipeクラスを引数として渡す
        try {
            CSVDataHandler csvDataHandler = new CSVDataHandler();
            csvDataHandler.writeData(newRecipe);
            System.out.println("Recipe added successfully.");
        } catch (IOException e) {
            System.out.println("Failed to add new recipe: 例外のメッセージ");
        }
    }

    private void searchRecipe() throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


        System.out.println("Enter search query (e.g., 'name=Tomato&ingredient=Garlic'): ");
        String inputData = reader.readLine();

        ArrayList<Recipe> recipes = new ArrayList<>();
        CSVDataHandler csvDataHandler = new CSVDataHandler();
        csvDataHandler.searchData(inputData);

        for (Recipe i: recipes) {
            System.out.println(i.getName());
        }
    }
}
