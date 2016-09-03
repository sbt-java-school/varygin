package homw.work;

import home.work.MyIterator;
import home.work.MyIteratorImpl;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MockitoTest {

    @BeforeClass
    public static void classSetUp() {
        System.out.println("MockitoTest: Start");
    }

    @Mock
    private List<String> stringsArray;
    @Mock
    private List<Person> personsArray;
    @Mock
    private List<Integer> intArray;

    @Before
    public void setUp() {
        when(stringsArray.get(anyInt())).thenReturn("someString");
        when(stringsArray.size()).thenReturn(10);
        when(personsArray.get(anyInt())).thenReturn(new Person("someName", 55));
        when(personsArray.size()).thenReturn(10);
        when(intArray.get(anyInt())).thenThrow(new NoSuchElementException());
        when(intArray.size()).thenReturn(10);
    }

    @Test
    public void checkIter() {
        MyIterator<String> strngIterator = new MyIteratorImpl<>(stringsArray);
        MyIterator<Person> personIterator = new MyIteratorImpl<>(personsArray);

        assertTrue(strngIterator.hasNext());

        assertEquals("someString", strngIterator.next());
        assertNotEquals("otherString", strngIterator.next());

        Person person = personIterator.next();
        assertNotNull(person);
        assertEquals(55, person.getAge());
        assertNotEquals("otherName", person.getName());
    }
}
