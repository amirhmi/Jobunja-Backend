package DataAccess;

public class EntityInitializer {
    public static void createTables() {
        String createUser =
                "CREATE TABLE IF NOT EXISTS User" +
                "(" +
                "  id INT NOT NULL," +
                "  firstName INT NOT NULL," +
                "  lastName INT NOT NULL," +
                "  jobTitle INT," +
                "  profilePicUrl INT," +
                "  bio INT," +
                "  PRIMARY KEY (id)" +
                ");";
        String createSkill =
                "CREATE TABLE IF NOT EXISTS Skill" +
                "(" +
                "  skillName VARCHAR NOT NULL," +
                "  PRIMARY KEY (skillName)" +
                ");";
        String createUserSkill =
                "CREATE TABLE IF NOT EXISTS UserSkill" +
                "(" +
                "  userId INT NOT NULL," +
                "  skillName VARCHAR NOT NULL," +
                "  PRIMARY KEY (userId, skillName)," +
                "  FOREIGN KEY (userId) REFERENCES User(id)," +
                "  FOREIGN KEY (skillName) REFERENCES Skill(skillName)" +
                ");";
        String createEndorsement =
                "CREATE TABLE IF NOT EXISTS Endorsement" +
                "(" +
                "  endorserId INT NOT NULL," +
                "  endorsedId INT NOT NULL," +
                "  skillName VARCHAR NOT NULL," +
                "  PRIMARY KEY (endorserId, endorsedId, skillName)," +
                "  FOREIGN KEY (endorserId) REFERENCES User(id)," +
                "  FOREIGN KEY (endorsedId, skillName) REFERENCES UserSkill(userId, skillName)" +
                ");";
        String createProject =
                "CREATE TABLE IF NOT EXISTS Project" +
                "(" +
                "  id VARCHAR NOT NULL," +
                "  title VARCHAR NOT NULL," +
                "  description VARCHAR," +
                "  imageUrl VARCHAR," +
                "  budget INT NOT NULL," +
                "  deadline DATE NOT NULL," +
                "  creationDate DATE," +
                "  ownerId INT," +
                "  winnerId INT," +
                "  PRIMARY KEY (id)," +
                "  FOREIGN KEY (ownerId) REFERENCES User(id)," +
                "  FOREIGN KEY (winnerId) REFERENCES User(id)" +
                ");";
        String createBid =
                "CREATE TABLE IF NOT EXISTS Bid" +
                "(" +
                "  budget INT NOT NULL," +
                "  userId INT NOT NULL," +
                "  projectId VARCHAR NOT NULL," +
                "  PRIMARY KEY (userId, projectId)," +
                "  FOREIGN KEY (userId) REFERENCES User(id)," +
                "  FOREIGN KEY (projectId) REFERENCES Project(id)" +
                ");";
        String createProjectSkill =
                "CREATE TABLE IF NOT EXISTS ProjectSkill" +
                "(" +
                "  value INT NOT NULL," +
                "  skillName VARCHAR NOT NULL," +
                "  projectId VARCHAR NOT NULL," +
                "  PRIMARY KEY (skillName, projectId)," +
                "  FOREIGN KEY (skillName) REFERENCES Skill(skillName)," +
                "  FOREIGN KEY (projectId) REFERENCES Project(id)" +
                ");";
        DataSource.excuteSql(createUser);
        DataSource.excuteSql(createProject);
        DataSource.excuteSql(createBid);
        DataSource.excuteSql(createSkill);
        DataSource.excuteSql(createUserSkill);
        DataSource.excuteSql(createProjectSkill);
        DataSource.excuteSql(createEndorsement);
    }
}
