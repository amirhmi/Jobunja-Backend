import javafx.util.Pair;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
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
            List<JSONObject> skills_jobjects = (List<JSONObject>) user_json_object.get("skills");
            List<Skill> skills = new ArrayList<>();
            for (JSONObject jobj : skills_jobjects)
            {
                String skillname = (String)jobj.get("name");
                long skillpoints = (long)jobj.get("points");
                skills.add(new Skill(skillname, (int)skillpoints));
            }
            db.addWorker(new Worker(username, skills));
            System.out.println("user " + username + " has been added successfully");
        } catch (ParseException e) {
            System.out.println("Invalid json input");
        }
    }

    private static void addProject(String data)
    {
        JSONParser parser = new JSONParser();
        try {
            Object project_object = parser.parse(data);
            JSONObject project_json_object = (JSONObject) project_object;
            String title = (String) project_json_object.get("title");
            List<JSONObject> skills_jobjects = (List<JSONObject>) project_json_object.get("skills");
            List<Skill> skills = new ArrayList<>();
            for (JSONObject jobj : skills_jobjects)
            {
                String skillname = (String)jobj.get("name");
                long skillpoints = (long)jobj.get("points");
                skills.add(new Skill(skillname, (int)skillpoints));
            }
            long budget = (long) project_json_object.get("budget");
            db.addProject(new Project(title, skills, (int)budget));
            System.out.println("project " + title + " has been added successfully");
        } catch (ParseException e) {
            System.out.println("Invalid json input");
        }
    }

    private static void bid(String data)
    {
        JSONParser parser = new JSONParser();
        try {
            Object bid_object = parser.parse(data);
            JSONObject user_json_object = (JSONObject) bid_object;
            String username = (String) user_json_object.get("biddingUser");
            String title = (String) user_json_object.get("projectTitle");
            long budget = (long) user_json_object.get("bidAmount");
            Project project = db.findProject(title);
            Worker worker = db.findWorker(username);
            System.out.println("searching for " + title);
            if (project == null)
                System.out.println("No such a project exists");
            else if (worker == null)
                System.out.println("No such a user exists");
            else {
                project.addBid(new Bid(worker, (int) budget));
                System.out.println("bid by " + username + " has been added for project "  + title + " successfully");
            }
        } catch (ParseException e) {
            System.out.println("Invalid json input");
        }
    }

    private static void auction(String data)
    {
        JSONParser parser = new JSONParser();
        try {
            Object auction_object = parser.parse(data);
            JSONObject auction_json_object = (JSONObject) auction_object;
            String title = (String) auction_json_object.get("projectTitle");
            Project project = db.findProject(title);
            if (project == null)
                System.out.println("No such a project exists");
            else {
                Bid winnerBid = project.evaluate();
                if(winnerBid == null) {
                    System.out.println("No suitable user for this project found");
                }
                else {
                    System.out.println("User " + winnerBid.getWorker().getUsername() + " won this auction");
                }
            }
        } catch (ParseException e) {
            System.out.println("Invalid json input");
        }
    }

    private static Pair<String, String> getCommandParts() {
        String command = scanner.nextLine();
        int spaceIndex = command.indexOf(" ");
        return new Pair<>(command.substring(0, spaceIndex), command.substring(spaceIndex));
    }
}
