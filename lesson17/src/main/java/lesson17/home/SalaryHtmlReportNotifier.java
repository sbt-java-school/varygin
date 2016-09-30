package lesson17.home;

import lesson17.home.builder.ReportBuilder;
import lesson17.home.dao.SalaryPaymentDao;
import lesson17.home.model.SalaryPayment;
import lesson17.home.sender.Sender;
import lesson17.home.utils.DateRange;

import java.util.List;

public class SalaryHtmlReportNotifier {
    private SalaryPaymentDao salaryPaymentDao;
    private ReportBuilder reportBuilder;
    private Sender sender;

    public SalaryHtmlReportNotifier(SalaryPaymentDao salaryPaymentDao, ReportBuilder reportBuilder, Sender sender) {
        this.salaryPaymentDao = salaryPaymentDao;
        this.reportBuilder = reportBuilder;
        this.sender = sender;
    }

    public void generateAndSendHtmlSalaryReport(String departmentId, DateRange range, String recipients) {
        List<SalaryPayment> salaryPayments = salaryPaymentDao.salaryPaymentsGetByParam(departmentId, range);
        String report = reportBuilder.build(salaryPayments);
        sender.send(recipients, report);
    }
}
