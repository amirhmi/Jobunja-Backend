import javafx.util.Pair;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static boolean isFinished = false;
    private static DataBase db;

    public static void main(String[] args) {
        db = new DataBase();
        while (!isFinished) {
            Pair<String, String> commandParts = getCommandParts();
            String commandName = commandParts.getKey();
            String commandData = commandParts.getValue();

            switch (commandName) {
                case "register":
                    register(commandData);
                    break;
                case "addProject":
                    addProject(commandData);
                    break;
                case "bid":
                    bid(commandData);
                    break;
                case "auction":
                    auction(commandData);
                    isFinished = true;
                    break;
            }
        }
    }

    private static void register(String data) {
        JSONParser parser = new JSONParser();
        try {
            Object user_object = parser.parse(data);
            JSONObject user_json_object = (JSONObject) user_object;
            String username = (String) user_json_object.get("username");
            List<Skill> skills = (List<Skill>) user_json_object.get("skills");
            db.addWorker(new Worker(username, skills));
        } catch (ParseException e) {
            System.out.println("Invalid json input");
        }
    }

    private static void addProject(String data)
    {
        JSONParser parser = new JSONParser();
        try {
            Object user_object = parser.parse(data);
            JSONObject user_json_object = (JSONObject) user_object;
            String title = (String) user_json_object.get("title");
            List<Skill> skills = (List<Skill>) user_json_object.get("skills");
            int budget = (int) user_json_object.get("budget");
            db.addProject(new Project(title, skills, budget));
        } catch (ParseException e) {
            System.out.println("Invalid json input");
        }
    }

    private static void bid(String data)
    {
    }

    private static void auction(String data)
    {

    }

    private static Pair<String, String> getCommandParts() {
        String command = scanner.nextLine();
        int spaceIndex = command.indexOf(" ");
        return new Pair<>(command.substring(0, spaceIndex), command.substring(spaceIndex));
    }
}
