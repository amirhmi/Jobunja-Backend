import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonParser {
    public static ObjectMapper mapper = new ObjectMapper();
    public static User parseUser(String json) throws IOException {
        return mapper.readValue(json, User.class);
    }
    public static Project parseProject(String json) throws IOException {
        return mapper.readValue(json, Project.class);
    }
}
