package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import testBase.BaseClass;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.awt.*;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class ExtentReportManager implements ITestListener {
    public ExtentSparkReporter sparkReporter; // takes care of the UI of the report
    public ExtentReports extent; // populate common info on the report
    public ExtentTest test; // creating testcase entries in the report and update the status of the test methods

    String reportName;

    @Override
    public void onStart(ITestContext testcontext) {

       /* SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd_HHmmss");
        Date date = new Date();
        String time = sdf.format(date); */

        String timeStamp = new SimpleDateFormat("yyyy.MM.dd_HHmmss").format(new Date());
        reportName = "Test-Report-" + timeStamp + ".html";

        sparkReporter = new ExtentSparkReporter(".//reports/" + reportName);

        sparkReporter.config().setDocumentTitle("Automation Test Report");
        sparkReporter.config().setReportName("Functional Testing");
        //sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setTheme(Theme.STANDARD);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        extent.setSystemInfo("User-Agent", System.getProperty("user.name"));
        extent.setSystemInfo("Application Name", "tutorialsninja.com");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Learning", "ExtentReports with Listeners");

        //Read the os and browser name from testNG xml files.
        String osname = testcontext.getCurrentXmlTest().getParameter("os");
        extent.setSystemInfo("Operating System", osname);

        String browserName = testcontext.getCurrentXmlTest().getParameter("browser");
        extent.setSystemInfo("Browser", browserName);

        //get the Groups that have been executed from the xml
        List<String> includedGroups = testcontext.getCurrentXmlTest().getIncludedGroups();
        if (!includedGroups.isEmpty()) {
            extent.setSystemInfo("Groups executed", includedGroups.toString());

        }

    }

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("onTestStart");
    }

    public void onTestSuccess(ITestResult result) {
        test = extent.createTest(result.getTestClass().getName()); // gets the testcase class name
        test.assignCategory(result.getMethod().getGroups()); // to display the groups the testcase belongs
        test.log(Status.PASS, result.getName() + " got successfully executed");

    }

    public void onTestFailure(ITestResult result) {
        test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());

        test.log(Status.FAIL, "Test Failed is : " + result.getTestClass().getName());
        test.log(Status.INFO, "Test Failed cause is : " + result.getThrowable().getMessage());

        try {
            String imgPath = new BaseClass().captureScreen(result.getName());
            test.addScreenCaptureFromBase64String(imgPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onTestSkipped(ITestResult result) {
        test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.SKIP, result.getName() + " got skipped");
        test.log(Status.INFO, result.getThrowable().getMessage());
    }

    public void onFinish(ITestContext context) {
        extent.flush();

        // Sender and receiver details
        final String senderEmail = "revathy.amazing20@gmail.com";
        final String senderPassword = "wonderfulthought123$";
//        final String recipientEmail = "revathy.amazing20@gmail.com";
//        String smtpHost = "smtp.gmail.com"; // For Gmail SMTP

        // Setup mail server properties
//        Properties properties = new Properties();
//        properties.put("mail.smtp.host", smtpHost);
//        properties.put("mail.smtp.port", "587"); // Use 465 for SSL
//        properties.put("mail.smtp.auth", "true");
//        properties.put("mail.smtp.starttls.enable", "true");

        // Authenticate sender
//        Session session = Session.getInstance(properties, new Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(senderEmail, senderPassword);
//            }
//        });

        try {

            // Opens the report on the browser
            String pathofExtentReport = System.getProperty("user.dir") + "/reports/" + reportName;
            File extentReport = new File(pathofExtentReport);
            URL url = extentReport.getAbsoluteFile().toURI().toURL();

            if (extentReport.exists()) {
                Desktop.getDesktop().browse(extentReport.toURI());
            }

            // Create email message
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(senderEmail));
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
//            message.setSubject("Test Execution Report - ExtentReport");

            //Email body
//            MimeBodyPart messageBodyPart = new MimeBodyPart();
//            messageBodyPart.setText("Please find the attached ExtentReport for the latest test execution.");

            // Attach Extent Report
//            MimeBodyPart attachmentPart = new MimeBodyPart();
//            //  String filePath = "path/to/ExtentReport.html"; // Update with your ExtentReport file path
//            attachmentPart.attachFile(new File(pathofExtentReport));

            // Combine parts
//            Multipart multipart = new MimeMultipart();
//            multipart.addBodyPart(messageBodyPart);
//            multipart.addBodyPart(attachmentPart);
//
//            // Set content and send email
//            message.setContent(multipart);
//            Transport.send(message);
//**********************************************
            ImageHtmlEmail email = new ImageHtmlEmail();
            email.setDataSourceResolver(new DataSourceUrlResolver(url));
            email.setHostName("smtp.googlemail.com");
            email.setSmtpPort(465);
            email.setSSLOnConnect(true);
            email.setAuthentication(senderEmail, senderPassword);
            email.setFrom(senderEmail);
            email.setSubject("Test Execution Report - ExtentReport");
            email.setMsg("Automation Demo test results attached..");
            email.addTo("revathy.amazing20@gmail.com");
            email.attach(url,"extent report","Test Execution Report - ExtentReport");
            //email.send();

            System.out.println("Email sent successfully with ExtentReport attached!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}
