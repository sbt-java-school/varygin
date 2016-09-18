package lesson14.home.utils;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by LL on 18.09.2016.
 */
public class JobDaoImpl implements JobDao {
    private int count;

    public JobDaoImpl(int count) {
        this.count = count;
    }

    @Override
    public Queue<Task> getQueue() {
        Queue<Task> queue = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            queue.add(new Job(i));
        }
        return queue;
    }
}
