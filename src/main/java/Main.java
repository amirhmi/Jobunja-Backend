import javafx.util.Pair;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static HttpClientGet http_client_get = new HttpClientGet();

    public static void main(String[] args) {
        http_client_get.HttpGetRequest();

    }
}
