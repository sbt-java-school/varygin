package lesson17.home.dao;

import lesson17.home.model.SalaryPayment;

import java.util.Date;
import java.util.List;

public interface SalaryPaymentDao {
    List<SalaryPayment> salaryPaymentsGetByParam(String departmentId, Date dateFrom, Date dateTo);
}
