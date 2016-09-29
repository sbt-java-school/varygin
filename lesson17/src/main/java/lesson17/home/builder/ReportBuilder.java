package lesson17.home.builder;

import java.util.List;

public interface ReportBuilder<T> {
    String build(List<T> objects);
}
