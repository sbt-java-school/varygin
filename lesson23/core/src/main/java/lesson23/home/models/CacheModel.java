package lesson23.home.models;

import dao.DBModel;
import dao.TableField;
import exception.BusinessException;
import lesson23.home.proxy.Value;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;


/**
 * Модель для взаимодействия с конкретной таблицей БД.
 *
 * Содержит три поля идентичных трём столбцам таблицы БД.
 * Каждое такое поле должно быть отмечено аннотацией @TableField
 * и иметь публичный getter и setter.
 *
 * Даннач модель сохраняет в качестве значения - обект.
 *
 * @param <T> - тип кешируемого значения
 */
public class CacheModel<T> extends DBModel {
    @TableField
    private String key;
    @TableField
    private Value<T> value;
    @TableField
    private Timestamp update_at;

    public CacheModel(String key) {
        this(key, null);
    }

    public CacheModel(String key, Value<T> value) {
        this(null, key, value);
    }

    public CacheModel(Long id, String key, Value<T> value) {
        super(id, "cache");
        this.key = key;
        this.value = value;
    }

    public Value<T> getValue() {
        return value;
    }

    public void setValue(Value<T> value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public boolean getCacheByKey() {
        String sql = "SELECT * FROM " + table + " WHERE `key` = ?";
        return templateManager.getTemplate().execute(connection -> {
            try (PreparedStatement statement = prepare(connection, sql, key)) {
                ResultSet result = statement.executeQuery();
                if (result.next()) {
                    return setCacheFromDB(result);
                }
                return false;
            }
        });
    }

    private Boolean setCacheFromDB(ResultSet result) throws IOException, SQLException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(result.getBlob(3).getBinaryStream())) {
            setId(result.getLong(1));
            setUpdate_at(result.getTimestamp(4));
            Object object = ois.readObject();
            if (value != null) {
                return true;
            }
            if (object != null && object.getClass().isAssignableFrom(Value.class)) {
                @SuppressWarnings("unchecked")
                Value<T> value = (Value<T>) object;
                setValue(value);
                return true;
            } else {
                throw new BusinessException("Wrong value in DB");
            }
        }
    }

    public Timestamp getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(Timestamp update_at) {
        this.update_at = update_at;
    }
}
