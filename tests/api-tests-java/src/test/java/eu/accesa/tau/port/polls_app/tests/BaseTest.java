package eu.accesa.tau.port.polls_app.tests;

import eu.accesa.tau.port.polls_app.configuration.Environment;
import eu.accesa.tau.port.polls_app.service.DBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;

@ContextConfiguration(locations = "classpath:test-context.xml")
public class BaseTest extends AbstractTestNGSpringContextTests {
    protected static final Logger LOGGER = LoggerFactory.getLogger(BaseTest.class.getName());
    @Autowired
    protected Environment environment;
    @Autowired
    protected DBService dbService;

    @BeforeSuite
    public void beforeSuite() throws Exception {
        super.springTestContextPrepareTestInstance();

        if (environment.isCleanData()) {
            cleanDatabaseAndCreateDefaultTestData();
        }
    }

    @AfterMethod
    public void cleanUp() {
        LOGGER.info("Cleaning up");

    }

    void cleanDatabaseAndCreateDefaultTestData() throws IOException {
        dbService.cleanDB();
        dbService.createDefaultTestData();
    }
}
