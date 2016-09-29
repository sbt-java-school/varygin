package lesson17.home.dao;

import lesson17.home.model.SalaryPayment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SalaryPaymentJdbcDao implements SalaryPaymentDao {
    private Connection connection;

    public SalaryPaymentJdbcDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<SalaryPayment> salaryPaymentsGetByParam(String departmentId, Date dateFrom, Date dateTo) {
        // prepare statement with sql
        try {
            List<SalaryPayment> salaryPayments = new ArrayList<>();
            PreparedStatement ps = connection.prepareStatement(getQuery().toString());
            // inject parameters to sql
            ps.setString(0, departmentId);
            ps.setDate(1, new java.sql.Date(dateFrom.getTime()));
            ps.setDate(2, new java.sql.Date(dateTo.getTime()));
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
        String fields = "emp.id as emp_id, emp.name as amp_name, sum(salary) as salary";
        String from = "employee emp";
        String join = "salary_payments sp on emp.id = sp.employee_id";
        String where = "emp.department_id = ? and sp.date >= ? and sp.date <= ?";
        String group = "emp.id, emp.name";
        StringBuilder queryString = new StringBuilder();
        queryString.append("select ");
        queryString.append(fields);
        queryString.append(" from ");
        queryString.append(from);
        queryString.append(" left join ");
        queryString.append(join);
        queryString.append(" where ");
        queryString.append(where);
        queryString.append(" group by ");
        queryString.append(group);
        return queryString;
    }
}
