package eu.accesa.tau.port.polls_app.tests;

import org.testng.annotations.Test;

import java.io.IOException;

public class PrepareTestDataTest extends BaseTest {
    @Test(description = "Deletes existing data and creates default test data", groups = "data")
    public void prepareTestData() throws IOException {
        cleanDatabaseAndCreateDefaultTestData();
    }
}
