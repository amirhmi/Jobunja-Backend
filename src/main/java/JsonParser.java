import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonParser {
    public static ObjectMapper mapper = new ObjectMapper();

    public static List<Project> parseProjectList(String json) throws IOException {
        return mapper.readValue(json,
                mapper.getTypeFactory().constructCollectionType(List.class, Project.class));
    }
    public static List<String> parseNameList(String json) throws IOException {
        List<Skill> names = mapper.readValue(json,
                mapper.getTypeFactory().constructCollectionType(List.class, Skill.class));
        List<String> ret = new ArrayList<>();
        for (Skill skill : names)
            ret.add(skill.getName());
        return ret;
    }
    public static User parseUser(String json) throws IOException {
        return mapper.readValue(json, User.class);
    }
    public static Project parseProject(String json) throws IOException {
        return mapper.readValue(json, Project.class);
    }
}
