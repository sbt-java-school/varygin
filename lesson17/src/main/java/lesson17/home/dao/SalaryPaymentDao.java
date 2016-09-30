package lesson17.home.dao;

import lesson17.home.model.SalaryPayment;
import lesson17.home.utils.DateRange;

import java.util.List;

public interface SalaryPaymentDao {
    List<SalaryPayment> salaryPaymentsGetByParam(String departmentId, DateRange range);
}
