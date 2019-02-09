import javafx.util.Pair;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static boolean isFinished = false;

    public static void main(String[] args) {
        while (!isFinished) {
            Pair<String, String> commandParts = getCommandParts();
            String commandName = commandParts.getKey();
            String commandData = commandParts.getValue();

            switch (commandName) {
                case "register":
                    regiser(commandData);
                    break;
                case "addProject":
                    System.out.println(commandData);
                    break;
                case "bid":
                    System.out.println(commandData);
                    break;
                case "auction":
                    System.out.println(commandData);
                    isFinished = true;
                    break;
            }
        }
    }

    private static void regiser(String data) {
        JSONParser parser = new JSONParser();
        try {
            Object user_object = parser.parse(data);
            JSONObject user_json_object = (JSONObject) user_object;
            String name = (String) user_json_object.get("username");
            List<Skill> skills = (List<Skill>) user_json_object.get("skills");
            System.out.println(skills.size());

        } catch (ParseException e) {
            System.out.println(data);
        }
    }

    private static Pair<String, String> getCommandParts() {
        String command = scanner.nextLine();
        int spaceIndex = command.indexOf(" ");
        return new Pair<>(command.substring(0, spaceIndex), command.substring(spaceIndex));
    }
}
