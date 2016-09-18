package lesson14.home;

import lesson14.home.utils.Job;
import lesson14.home.utils.JobDao;
import lesson14.home.utils.JobDaoImpl;
import lesson14.home.utils.Task;
import org.junit.Test;

import java.sql.Time;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.TemporalUnit;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

/**
 * Test Thread Pool
 */
public class PoolTest {
    @Test(expected = IllegalStateException.class)
    public void testExceptions(){
        ThreadPool pool = new ThreadPool(0);
    }

    public static void main(String[] args) throws Exception {

        /*JobDao jobDao = new JobDaoImpl(10);
        Queue<Task> queue = jobDao.getQueue();
        ThreadPool threadPool = new ThreadPool(1, 1);
        threadPool.addTask(queue);*/

        /*JobDao jobDao = new JobDaoImpl(50);
        Queue<Task> queue = jobDao.getQueue();
        ThreadPool threadPool = new ThreadPool(4, queue, 1);
        threadPool.addTask(queue);*/

        JobDao jobDao = new JobDaoImpl(10);
        Queue<Task> queue = jobDao.getQueue();
        ThreadPool threadPool = new ThreadPool(15, 1);
        threadPool.addTask(queue);
    }
}
