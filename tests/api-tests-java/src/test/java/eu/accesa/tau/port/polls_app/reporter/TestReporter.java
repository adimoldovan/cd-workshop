package eu.accesa.tau.port.polls_app.reporter;

import org.testng.*;
import org.testng.xml.XmlSuite;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Inspired by https://github.com/eugenp/tutorials/tree/master/testng
 */
public class TestReporter implements IReporter {
    private static final String ROW_TEMPLATE = "<tr class=\"%s\"><td>%s</td><td>%s</td><td>%s</td></tr>";

    private String summary = "";

    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        String reportTemplate = initReportTemplate();

        Date currentDate = new Date();
        String title = String.format("Test report %s", new SimpleDateFormat("MMM d',' yyyy 'at' hh:mm a").format(currentDate));

        String environment = String.format("os: %s</br>java: %s</br>cleanDB: %s</br>environment: %s",
            System.getProperty("os.name") + " " + System.getProperty("os.arch"),
            System.getProperty("java.runtime.version"),
            System.getProperty("cleanDB"),
            System.getProperty("env"));

        final String body = suites
            .stream()
            .flatMap(suiteToResults())
            .collect(Collectors.joining());

        String reportContent = reportTemplate.replace("${methods}", body);
        reportContent = reportContent.replace("${summary}", summary);
        reportContent = reportContent.replace("${title}", title);
        reportContent = reportContent.replace("${env}", environment);

        saveReportTemplate(System.getProperty("reportPath"), "test-report_" + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(currentDate) + ".html", reportContent);
        saveReportTemplate(System.getProperty("user.home"), "test-report-latest-execution.html", reportContent);
    }

    private Function<ISuite, Stream<? extends String>> suiteToResults() {
        return suite -> suite.getResults().entrySet()
            .stream()
            .flatMap(resultsToRows(suite));
    }

    private Function<Map.Entry<String, ISuiteResult>, Stream<? extends String>> resultsToRows(ISuite suite) {
        return e -> {
            ITestContext testContext = e.getValue().getTestContext();

            Set<ITestResult> failedTests = testContext
                .getFailedTests()
                .getAllResults();
            Set<ITestResult> passedTests = testContext
                .getPassedTests()
                .getAllResults();
            Set<ITestResult> skippedTests = testContext
                .getSkippedTests()
                .getAllResults();

            summary = String.format("<tr>" +
                "<td><h1><span class=\"label label-success\">Passed <span class=\"badge\">%s</span></span></h1></td>" +
                "<td><h1><span class=\"label label-danger\">Failed <span class=\"badge\">%s</span></span></h1></td>" +
                "<td><h1><span class=\"label label-warning\">Skipped <span class=\"badge\">%s</span></span></h1></td></tr>", passedTests.size(), failedTests.size(), skippedTests.size());

            String suiteName = suite.getName();

            return Stream
                .of(failedTests, passedTests, skippedTests)
                .flatMap(results -> generateReportRows(e.getKey(), suiteName, results).stream());
        };
    }

    private List<String> generateReportRows(String testName, String suiteName, Set<ITestResult> allTestResults) {
        return allTestResults.stream()
            .map(testResultToResultRow(testName, suiteName))
            .collect(toList());
    }

    private Function<ITestResult, String> testResultToResultRow(String testName, String suiteName) {
        return testResult -> {
            switch (testResult.getStatus()) {
                case ITestResult.FAILURE:
                    return String.format(ROW_TEMPLATE, "danger", testResult.getName(), "FAILED", "NA");

                case ITestResult.SUCCESS:
                    return String.format(ROW_TEMPLATE, "success", testResult.getName(), "PASSED", String.valueOf(testResult.getEndMillis() - testResult.getStartMillis()));

                case ITestResult.SKIP:
                    return String.format(ROW_TEMPLATE, "warning", testResult.getName(), "SKIPPED", "NA");

                default:
                    return "";
            }
        };
    }

    private String initReportTemplate() {
        String template = null;
        byte[] reportTemplate;
        try {
            reportTemplate = Files.readAllBytes(Paths.get("src/test/resources/reportTemplate.html"));
            template = new String(reportTemplate, "UTF-8");
        } catch (IOException e) {
            System.err.println("Problem initializing template");
            e.printStackTrace();
        }
        return template;
    }

    private void saveReportTemplate(String outputDirectory, String fileName, String reportTemplate) {
        new File(outputDirectory).mkdirs();
        try {
            PrintWriter reportWriter = new PrintWriter(new BufferedWriter(new FileWriter(new File(outputDirectory, fileName))));
            reportWriter.println(reportTemplate);
            reportWriter.flush();
            reportWriter.close();
        } catch (IOException e) {
            System.err.println("Problem saving template");
            e.printStackTrace();
        }
    }
}
