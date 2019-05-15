package DataAccess;

public class EntityInitializer {
    public static void createTables() {
        String createUser =
                "CREATE TABLE IF NOT EXISTS User" +
                "(" +
                "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "  firstName VARCHAR NOT NULL," +
                "  lastName VARCHAR NOT NULL," +
                "  userName VARCHAR NOT NULL," +
                "  password VARCHAR NOT NULL," +
                "  jobTitle VARCHAR," +
                "  profilePicUrl VARCHAR," +
                "  bio VARCHAR" +
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
                "  userId INTEGER NOT NULL," +
                "  skillName VARCHAR NOT NULL," +
                "  PRIMARY KEY (userId, skillName)," +
                "  FOREIGN KEY (userId) REFERENCES User(id)," +
                "  FOREIGN KEY (skillName) REFERENCES Skill(skillName)" +
                ");";
        String createEndorsement =
                "CREATE TABLE IF NOT EXISTS Endorsement" +
                "(" +
                "  endorserId INTEGER NOT NULL," +
                "  endorsedId INTEGER NOT NULL," +
                "  skillName VARCHAR NOT NULL," +
                "  PRIMARY KEY (endorserId, endorsedId, skillName)," +
                "  FOREIGN KEY (endorserId) REFERENCES User(id)," +
                "  FOREIGN KEY (endorsedId, skillName) REFERENCES UserSkill(userId, skillName) ON DELETE CASCADE" +
                ");";
        String createProject =
                "CREATE TABLE IF NOT EXISTS Project" +
                "(" +
                "  id VARCHAR NOT NULL," +
                "  title VARCHAR NOT NULL," +
                "  description VARCHAR," +
                "  imageUrl VARCHAR," +
                "  budget INT NOT NULL," +
                "  deadline INT NOT NULL," +
                "  creationDate INT," +
                "  ownerId VARCHAR," +
                "  winnerId VARCHAR," +
                "  PRIMARY KEY (id)," +
                "  FOREIGN KEY (ownerId) REFERENCES User(id)," +
                "  FOREIGN KEY (winnerId) REFERENCES User(id)" +
                ");";
        String createBid =
                "CREATE TABLE IF NOT EXISTS Bid" +
                "(" +
                "  budget INT NOT NULL," +
                "  userId INTEGER NOT NULL," +
                "  projectId VARCHAR NOT NULL," +
                "  PRIMARY KEY (userId, projectId)," +
                "  FOREIGN KEY (userId) REFERENCES User(id)," +
                "  FOREIGN KEY (projectId) REFERENCES Project(id)" +
                ");";
        String createProjectSkill =
                "CREATE TABLE IF NOT EXISTS ProjectSkill" +
                "(" +
                "  point INT NOT NULL," +
                "  skillName VARCHAR NOT NULL," +
                "  projectId VARCHAR NOT NULL," +
                "  PRIMARY KEY (skillName, projectId)," +
                "  FOREIGN KEY (skillName) REFERENCES Skill(skillName)," +
                "  FOREIGN KEY (projectId) REFERENCES Project(id)" +
                ");";
        DataSource.executeSql(createUser);
        DataSource.executeSql(createProject);
        DataSource.executeSql(createBid);
        DataSource.executeSql(createSkill);
        DataSource.executeSql(createUserSkill);
        DataSource.executeSql(createProjectSkill);
        DataSource.executeSql(createEndorsement);
    }
}
