package lesson17.home.builder;

import lesson17.home.model.SalaryPayment;

import java.math.BigDecimal;
import java.util.List;

public class SalaryReportBuilder implements ReportBuilder<SalaryPayment> {
    public String build(List<SalaryPayment> objects) {
        // create a StringBuilder holding a resulting html
        StringBuilder htmlReport = new StringBuilder();
        htmlReport.append("<html><body><table><tr><td>Employee</td><td>Salary</td></tr>");
        BigDecimal totals = new BigDecimal(0);
        for (SalaryPayment object : objects) {
            // process each row of query results
            htmlReport.append("<tr>"); // add row start tag
            htmlReport.append("<td>").append(object.getName()).append("</td>"); // appending employee name
            htmlReport.append("<td>").append(object.getSalary()).append("</td>"); // appending employee salary for period
            htmlReport.append("</tr>"); // add row end tag
            totals = totals.add(object.getSalary()); // add salary to totals
        }
        htmlReport.append("<tr><td>Total</td><td>").append(totals).append("</td></tr>");
        htmlReport.append("</table></body></html>");
        return htmlReport.toString();
    }
}
