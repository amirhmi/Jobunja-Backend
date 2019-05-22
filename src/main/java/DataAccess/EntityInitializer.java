package DataAccess;

import javax.xml.crypto.Data;

public class EntityInitializer {
    public static void createTables() {
        String createUser =
                "CREATE TABLE IF NOT EXISTS User" +
                "(" +
                "  id INTEGER PRIMARY KEY AUTO_INCREMENT," +
                "  firstName VARCHAR(64) NOT NULL," +
                "  lastName VARCHAR(128) NOT NULL," +
                "  userName VARCHAR(128) NOT NULL ," +
                "  password VARCHAR(128) NOT NULL," +
                "  jobTitle VARCHAR(128)," +
                "  profilePicUrl VARCHAR(1024)," +
                "  bio VARCHAR(1024)," +
                "  UNIQUE(userName)" +
                ");";
        String createSkill =
                "CREATE TABLE IF NOT EXISTS Skill" +
                "(" +
                "  skillName VARCHAR(64) NOT NULL," +
                "  PRIMARY KEY (skillName)" +
                ");";
        String createUserSkill =
                "CREATE TABLE IF NOT EXISTS UserSkill" +
                "(" +
                "  userId INTEGER NOT NULL," +
                "  skillName VARCHAR(64) NOT NULL," +
                "  PRIMARY KEY (userId, skillName)," +
                "  FOREIGN KEY (userId) REFERENCES User(id)," +
                "  FOREIGN KEY (skillName) REFERENCES Skill(skillName)" +
                ");";
        String createEndorsement =
                "CREATE TABLE IF NOT EXISTS Endorsement" +
                "(" +
                "  endorserId INTEGER NOT NULL," +
                "  endorsedId INTEGER NOT NULL," +
                "  skillName VARCHAR(64) NOT NULL," +
                "  PRIMARY KEY (endorserId, endorsedId, skillName)," +
                "  FOREIGN KEY (endorserId) REFERENCES User(id)," +
                "  FOREIGN KEY (endorsedId, skillName) REFERENCES UserSkill(userId, skillName) ON DELETE CASCADE" +
                ");";
        String createProject =
                "CREATE TABLE IF NOT EXISTS Project" +
                "(" +
                "  id VARCHAR(512) NOT NULL," +
                "  title VARCHAR(1024) NOT NULL," +
                "  description VARCHAR(1024)," +
                "  imageUrl VARCHAR(1024)," +
                "  budget INT NOT NULL," +
                "  deadline INT NOT NULL," +
                "  creationDate INT," +
                "  ownerId INTEGER," +
                "  winnerId INTEGER," +
                "  PRIMARY KEY (id)," +
                "  FOREIGN KEY (ownerId) REFERENCES User(id)," +
                "  FOREIGN KEY (winnerId) REFERENCES User(id)" +
                ");";
        String createBid =
                "CREATE TABLE IF NOT EXISTS Bid" +
                "(" +
                "  budget INT NOT NULL," +
                "  userId INTEGER NOT NULL," +
                "  projectId VARCHAR(512) NOT NULL," +
                "  PRIMARY KEY (userId, projectId)," +
                "  FOREIGN KEY (userId) REFERENCES User(id)," +
                "  FOREIGN KEY (projectId) REFERENCES Project(id)" +
                ");";
        String createProjectSkill =
                "CREATE TABLE IF NOT EXISTS ProjectSkill" +
                "(" +
                "  point INT NOT NULL," +
                "  skillName VARCHAR(64) NOT NULL," +
                "  projectId VARCHAR(512) NOT NULL," +
                "  PRIMARY KEY (skillName, projectId)," +
                "  FOREIGN KEY (skillName) REFERENCES Skill(skillName)," +
                "  FOREIGN KEY (projectId) REFERENCES Project(id)" +
                ");";
        DataSource.executeSql("ALTER DATABASE tracker CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;");
        DataSource.executeSql(createUser);
        DataSource.executeSql(createProject);
        DataSource.executeSql(createBid);
        DataSource.executeSql(createSkill);
        DataSource.executeSql(createUserSkill);
        DataSource.executeSql(createProjectSkill);
        DataSource.executeSql(createEndorsement);
    }
}
