package eu.accesa.tau.port.polls_app.client;

import eu.accesa.tau.port.polls_app.configuration.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class DBClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(DBClient.class.getName());
    private static Connection conn;
    private static PreparedStatement preparedStatement;
    @Autowired
    private Environment environment;

    private void disconnect() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void closeStatement() {
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeAllAndDisconnect() {
        closeStatement();
        disconnect();
    }

    public PreparedStatement getPreparedStatement(String query) throws SQLException {
        connect();
        LOGGER.debug("Preparing statement: " + query);
        preparedStatement = conn.prepareStatement(query);
        return preparedStatement;
    }

    private void connect() throws SQLException {
        conn = DriverManager.getConnection(environment.getDbBaseUrl(), environment.getDbUser(), environment.getDbPassword());
    }
}
