package eu.accesa.tau.port.polls_app.listener;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.util.Arrays;
import java.util.UUID;
public class TestListener extends TestListenerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestListener.class.getName());
    private static final String LOG_LINE_SEPARATOR = "-------------------------------------------------------------------";
    private static String getTestStatus(int status) {
        switch (status) {
            case 1:
                return "PASSED";
            case 2:
                return "FAILED";
            case 3:
                return "SKIPPED";
            case 4:
                return "SUCCESS PERCENTAGE FAILURE";
            case 16:
                return "STARTED";
            default:
                return "UNKNOWN RESULT";
        }
    }

    private static void testFinish(ITestResult tr) {
        String status = getTestStatus(tr.getStatus());

        UUID testUID = UUID.randomUUID();
        tr.setAttribute("uuid", testUID);

        Throwable exception = tr.getThrowable();
        if (exception != null) {
            System.err.println("Test " + status + " with the following exception: ");
            exception.printStackTrace();
        }

        LOGGER.info("**** END TEST: " + status + " ****");
        LOGGER.info(LOG_LINE_SEPARATOR);
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        testFinish(tr);
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        testFinish(tr);
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        testFinish(tr);
    }

    @Override
    public void onFinish(ITestContext ctx) {
        long duration = ctx.getStartDate().getTime() - ctx.getEndDate().getTime();
        System.setProperty("TestDurationMs", String.valueOf(duration));
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testMethodName = result.getMethod().getMethodName();
        String testDescription = result.getMethod().getDescription();
        String testGroups = Arrays.toString(result.getMethod().getGroups());
        LOGGER.info(LOG_LINE_SEPARATOR);
        LOGGER.info(String.format("**** STARTING TEST: %s %s  ****", testMethodName, testGroups));

        if (testDescription != null && !testDescription.isEmpty()) {
            LOGGER.info("Description: " + testDescription);
        }
    }
}
