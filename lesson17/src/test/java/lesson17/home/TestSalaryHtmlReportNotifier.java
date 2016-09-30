package lesson17.home;

import lesson17.home.builder.ReportBuilder;
import lesson17.home.builder.SalaryReportBuilder;
import lesson17.home.dao.SalaryPaymentDao;
import lesson17.home.dao.SalaryPaymentJdbcDao;
import lesson17.home.model.SalaryPayment;
import lesson17.home.sender.EmailSender;
import lesson17.home.sender.Sender;
import lesson17.home.utils.DateRange;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest(value = {SalaryPaymentJdbcDao.class, EmailSender.class, SalaryHtmlReportNotifier.class})
public class TestSalaryHtmlReportNotifier {
    private SalaryPaymentDao salaryPaymentDao;
    private ReportBuilder<SalaryPayment> reportBuilder;
    private Sender sender;

    private static MimeMessageHelper mockMimeMessageHelper;
    private static String expectedReportContent;
    private static final String EXPECTED_REPORT_PATH = "src/test/resources/expectedReport.html";


    @BeforeClass
    public static void init() throws Exception {
        Path path = Paths.get(EXPECTED_REPORT_PATH);
        expectedReportContent = new String(Files.readAllBytes(path));
        mockMimeMessageHelper = getMockedMimeMessageHelper();
    }

    @Before
    public void setUp() throws Exception {
        salaryPaymentDao = new SalaryPaymentJdbcDao(getMockedConnection());
        reportBuilder = new SalaryReportBuilder();
        sender = new EmailSender();
    }

    @Test
    public void test() throws Exception {
        DateRange range = new DateRange(new Date(), new Date());
        SalaryHtmlReportNotifier notifier = new SalaryHtmlReportNotifier(salaryPaymentDao, reportBuilder, sender);
        notifier.generateAndSendHtmlSalaryReport("10", range, "somebody@gmail.com");

        assertEquals(getSendedMessageFromMock(), expectedReportContent);
    }

    private String getSendedMessageFromMock() throws MessagingException {
        ArgumentCaptor<String> messageTextArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockMimeMessageHelper).setText(messageTextArgumentCaptor.capture(), anyBoolean());
        return messageTextArgumentCaptor.getValue();
    }

    private Connection getMockedConnection() throws SQLException {
        Connection connection = mock(Connection.class);
        PreparedStatement someFakePreparedStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        when(someFakePreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(connection.prepareStatement(anyObject())).thenReturn(someFakePreparedStatement);

        when(mockResultSet.next()).thenReturn(true, true, false);

        when(mockResultSet.getLong("emp_id")).thenReturn(10L, 11L);
        when(mockResultSet.getString("amp_name")).thenReturn("John Doe", "Jane Dow");
        when(mockResultSet.getBigDecimal("salary")).thenReturn(new BigDecimal(1110), new BigDecimal(50));

        return connection;
    }

    private static MimeMessageHelper getMockedMimeMessageHelper() throws Exception {
        JavaMailSenderImpl mockMailSender = mock(JavaMailSenderImpl.class);
        MimeMessage mockMimeMessage = mock(MimeMessage.class);
        when(mockMailSender.createMimeMessage()).thenReturn(mockMimeMessage);

        whenNew(JavaMailSenderImpl.class).withNoArguments().thenReturn(mockMailSender);

        MimeMessageHelper mockMimeMessageHelper = mock(MimeMessageHelper.class);
        whenNew(MimeMessageHelper.class)
                .withArguments(any(MimeMessage.class), anyBoolean())
                .thenReturn(mockMimeMessageHelper);
        return mockMimeMessageHelper;
    }
}
