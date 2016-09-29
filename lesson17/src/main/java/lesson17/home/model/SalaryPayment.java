package lesson17.home.model;

import java.math.BigDecimal;

public class SalaryPayment {
    private final Long id;
    private final String name;
    private final BigDecimal salary;

    public SalaryPayment(Long id, String name, BigDecimal salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getSalary() {
        return salary;
    }
}
