package lesson17.home.dao;

import lesson17.home.model.SalaryPayment;
import lesson17.home.utils.DateRange;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaryPaymentJdbcDao implements SalaryPaymentDao {
    private Connection connection;

    public SalaryPaymentJdbcDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<SalaryPayment> salaryPaymentsGetByParam(String departmentId, DateRange range) {
        // prepare statement with sql
        try {
            List<SalaryPayment> salaryPayments = new ArrayList<>();
            PreparedStatement ps = connection.prepareStatement(getQuery().toString());
            // inject parameters to sql
            ps.setString(0, departmentId);
            ps.setDate(1, new java.sql.Date(range.getDateFrom().getTime()));
            ps.setDate(2, new java.sql.Date(range.getDateTo().getTime()));
            // execute query and get the results
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                salaryPayments.add(new SalaryPayment(results.getLong("emp_id"), results.getString("amp_name"), results.getBigDecimal("salary")));
            }
            return salaryPayments;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private StringBuilder getQuery() {
        StringBuilder queryString = new StringBuilder();
        queryString.append("select ")
                .append("emp.id as emp_id, emp.name as amp_name, sum(salary) as salary")
                .append(" from ")
                .append("employee emp")
                .append(" left join ")
                .append("salary_payments sp on emp.id = sp.employee_id")
                .append(" where ")
                .append("emp.department_id = ? and sp.date >= ? and sp.date <= ?")
                .append(" group by ")
                .append("emp.id, emp.name");
        return queryString;
    }
}
