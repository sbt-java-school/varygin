package lesson24.db.components;

import lesson24.db.Model;
import lesson24.db.shema.Unit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public class UnitsDao extends DaoModel implements Model {

    @Autowired
    public UnitsDao(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Optional<Unit> getById(Long id) {
        Optional<?> optionalObj = super.getById(id);
        if (!optionalObj.isPresent()) {
            return Optional.empty();
        }
        return Optional.of((Unit)optionalObj.get());
    }

    @Override
    protected Unit init(Map<String, Object> resultMap) {
        return new Unit(
                ((Number) resultMap.get("id")).longValue(),
                (String) resultMap.get("name"),
                (String) resultMap.get("short_name")
        );
    }

    @Override
    protected String getTable() {
        return "units";
    }
}
