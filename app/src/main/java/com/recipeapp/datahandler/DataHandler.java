package com.recipeapp.datahandler;

import java.io.IOException;
import java.util.ArrayList;

import com.recipeapp.model.Recipe;

//ファイル操作のinterface
//csvとjsonクラスに実装する
public interface DataHandler {
    //クラス毎に読み込むファイルを返す
    String getMode();

    //レシピのlistを読み込む
    ArrayList<Recipe> readData() throws IOException;

    //レシピをファイルに書き込む
    void writeData(Recipe recipe) throws IOException;

    //レシピのlistの中からキーワードで検索する
    ArrayList<Recipe> searchData(String keyword) throws IOException;
}
