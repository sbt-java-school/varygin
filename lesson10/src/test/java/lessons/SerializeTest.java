package lessons;

import lessons.task.Gender;
import lessons.task.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.*;
import java.util.Arrays;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.runners.Parameterized.Parameter;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class SerializeTest {

    private static final String NAME = "Ivan";
    private static final int AGE = 35;
    public static final String RELATION_NAME = "Natasha";
    public static final String FILE_NAME = "serialize.ser";

    @Parameter
    public String nameExpected;
    @Parameter(1)
    public Gender gender;
    @Parameter(2)
    public int age;
    @Parameter(3)
    public Person person;

    @Parameters
    public static Iterable<Object[]> data() {
        Person ivan = new Person(NAME, Gender.MALE);
        ivan.setBirthday((new GregorianCalendar(1980, 9, 15)).getTime());
        Person relation = new Person(RELATION_NAME, Gender.FEMALE);
        relation.setBirthday((new GregorianCalendar(1985, 9, 15)).getTime());
        ivan.setRelation(relation);

        return Arrays.asList(new Object[][]{
                {NAME, Gender.FEMALE, 0, new Person(NAME, Gender.FEMALE)},
                {null, null, 0, new Person()},
                {NAME, Gender.MALE, AGE, ivan},
        });
    }

    @Test
    public void testSerializeToByteArray() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        assertTrue(write(out, person));

        InputStream in = new ByteArrayInputStream(out.toByteArray());
        checkResult(read(in));
    }

    @Test
    public void testSerializeToFile() throws Exception {
        String path = this.getClass().getClassLoader().getResource("").getPath() + "/" + FILE_NAME;
        OutputStream out = new FileOutputStream(path);
        assertTrue(write(out, person));

        InputStream in = new FileInputStream(path);
        checkResult(read(in));
    }

    private void checkResult(Person result) {
        assertEquals(nameExpected, result.getName());
        assertEquals(gender, result.getGender());
        assertEquals(age, result.getAge());

        Person relation = result.getRelation();
        if (relation != null) {
//            System.out.println(relation);
            assertEquals(RELATION_NAME, relation.getName());
            assertEquals(Gender.FEMALE, relation.getGender());
        }
    }

    private Person read(InputStream in) {
        Person result = null;
        try {
            ObjectInputStream inputStream = new ObjectInputStream(in);
            result = (Person) inputStream.readObject();
//            System.out.println("Read person: " + result);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    private boolean write(OutputStream out, Person writePerson) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(out)){
            outputStream.writeObject(writePerson);
//            System.out.println("Wrote person: " + writePerson);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }
}
