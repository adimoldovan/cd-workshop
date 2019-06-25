package eu.accesa.tau.port.polls_app.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.accesa.tau.port.polls_app.client.APIClient;
import eu.accesa.tau.port.polls_app.client.DBClient;
import eu.accesa.tau.port.polls_app.model.User;
import eu.accesa.tau.port.polls_app.type.Tables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DBService {
    private static final Logger LOGGER = LoggerFactory.getLogger(APIClient.class.getName());
    @Autowired
    protected DBClient dbClient;

    /**
     * Deletes all data to ensure a clean DB for testing
     */
    public void cleanDB() {
        String[] tables = {"votes", "choices", "polls", "user_roles", "users"};

        for (String t : tables) {
            try {
                String q = String.format("DELETE FROM %s", t);
                PreparedStatement statement = dbClient.getPreparedStatement(q);
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                dbClient.closeAllAndDisconnect();
            }
        }
    }

    /**
     * This method creates default test data to be used in different tests.
     * <ul>
     *     <li>create a default user</li>
     *     <li>create a poll</li>
     * </ul>
     * @throws IOException
     */
    public void createDefaultTestData() throws IOException {
        // create default user
        ObjectMapper om = new ObjectMapper();
        File testDataFile = new File(this.getClass().getClassLoader().getResource("testdata/users.json").getFile());
        User[] defaultUsers = om.readValue(testDataFile, User[].class);
        List<String> queries = new ArrayList<>();

        for (User user : defaultUsers) {
            int next_user_id = getNextAutoIncrementForTable(Tables.USERS);
            String q_user = String.format(
                    "INSERT INTO users (email, name, password, username, created_at, updated_at) VALUES ('%s', '%s', '%s', '%s', %s, %s)",
                    user.getEmail(),
                    user.getName(),
                    user.getPassword(),
                    user.getUsername(),
                    "NOW()",
                    "NOW()"
            );

            queries.add(q_user);

            String q_role = String.format("INSERT INTO user_roles VALUES (%s, 1)", next_user_id);

            queries.add(q_role);
        }

        for (String q : queries) {
            try {
                PreparedStatement ps = dbClient.getPreparedStatement(q);
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public int getNextAutoIncrementForTable(Tables table) {
        String q = "SELECT auto_increment FROM INFORMATION_SCHEMA.TABLES WHERE table_name = ?";
        int nextId = 0;
        try {
            PreparedStatement ps = dbClient.getPreparedStatement(q);
            ps.setString(1, table.getTableName());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                nextId = rs.getInt("auto_increment");
            }

            LOGGER.debug(String.format("Next auto increment for %s: %s", table.getTableName(), nextId));
            return nextId;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbClient.closeAllAndDisconnect();
        }
        return nextId;
    }

    public int getRecordsCountForTable(Tables table) {
        int count = 0;
        try {
            PreparedStatement ps = dbClient.getPreparedStatement("SELECT COUNT(*) AS count FROM " + table.getTableName());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                count = rs.getInt("count");
            }

            LOGGER.debug(String.format("Number of records for %s: %s", table.getTableName(), count));

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbClient.closeAllAndDisconnect();
        }

        return count;
    }
}
