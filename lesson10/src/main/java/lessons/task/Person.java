package lessons.task;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

import static org.apache.commons.lang.StringUtils.isNotEmpty;

/**
 * Class Person store information about some person
 */
public class Person implements Serializable {

    private String name;
    private transient int age;
    private Date birthday;
    private Gender gender;
    private Person relation;

    public Person() {
    }

    public Person(String name, Gender gender) {
        this.name = name;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    private void setAge(int age) {
        this.age = age;
    }
    public Date getBirthday() {
        return birthday;
    }
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
        calculateAgeFromDate();
    }
    public Gender getGender() {
        return gender;
    }
    public void setGender(Gender gender) {
        this.gender = gender;
    }
    public Person getRelation() {
        return relation;
    }
    public void setRelation(Person relation) {
        this.relation = relation;
        if (relation.getRelation() == null) {
            relation.setRelation(this);
        }
    }

    private void calculateAgeFromDate() {
        if (birthday != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(birthday);

            LocalDate birth = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            LocalDate now = LocalDate.now();
            setAge(Period.between(birth, now).getYears());
        }
    }

    private static class SerializationProxy implements Serializable {
        private static final long serialVersionUID = 4427747849034425780L;
        public static final String EMPTY_VALUE = "empty";
        private String data;

        SerializationProxy(Person person) {
            data = getCompress(person);
            if (person.relation != null) {
                data += ";" + getCompress(person.relation);
            }
        }

        private String getCompress(Person person) {
            return StringUtils.join(new String[]{
                    ((person.name != null) ? person.name : EMPTY_VALUE),
                    (person.birthday != null ? person.birthday.getTime() + "" : EMPTY_VALUE),
                    (person.gender != null ? person.gender.toString() : EMPTY_VALUE)
            }, ';');
        }

        private void getDecompress(Person result, String[] unCompress, int i) {
            if (isNotEmpty(unCompress[i]) && !EMPTY_VALUE.equals(unCompress[i])) {
                result.setName(unCompress[i]);
            }
            if (isNotEmpty(unCompress[i + 1]) && !EMPTY_VALUE.equals(unCompress[i + 1])) {
                result.setBirthday(new Date(Long.parseLong(unCompress[i + 1])));
            }
            if (isNotEmpty(unCompress[i + 2]) && !EMPTY_VALUE.equals(unCompress[i + 2])) {
                result.setGender(Gender.getByShort(unCompress[i + 2].charAt(0)));
            }
        }

        private Object readResolve() {
            String[] unCompress = StringUtils.split(data, ';');
            Person result = new Person();
            if (unCompress.length > 0) {
                getDecompress(result, unCompress, 0);
                if (unCompress.length > 3) {
                    Person relationPerson = new Person();
                    getDecompress(relationPerson, unCompress, 3);
                    result.setRelation(relationPerson);
                }
            }
            return result;
        }
    }

    Object writeReplace() {
        return new Person.SerializationProxy(this);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + ((name != null) ? name : "") + '\'' +
                ", age=" + age +
                ", gender=" + ((gender != null) ? gender : "") +
                ", relation=" + ((relation != null) ? relation.getName() : "") +
                '}';
    }

}
