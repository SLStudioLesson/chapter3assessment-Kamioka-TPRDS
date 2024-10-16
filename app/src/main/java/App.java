import com.recipeapp.datahandler.CSVDataHandler;
import com.recipeapp.datahandler.DataHandler;
import com.recipeapp.datahandler.JSONDataHandler;
import com.recipeapp.ui.RecipeUI;
import java.io.*;

public class App {

    public static void main(String[] args) {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Choose the file format:");
            System.out.println("1. CSV");
            System.out.println("2. JSON");
            System.out.print("Select (1/2): ");
            String choice = reader.readLine();

            //ユーザーの入力値に応じてcsvかjsonの処理を行うためのインスタンスを生成する
            DataHandler dataHandler;
            switch (choice) {
                case "1":
                    dataHandler = new CSVDataHandler();
                    break;
                case "2":
                    dataHandler = new JSONDataHandler();
                    break;
                default:
                    dataHandler = new CSVDataHandler();
                    break;
            }

            //dataHandlerを初期値として渡しRecipeUIインスタンスを生成する
            //生成したインスタンスでdisplayMenuメソッドを呼び出し画面に表示する
            RecipeUI recipeui = new RecipeUI(dataHandler);
            recipeui.displayMenu();

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}