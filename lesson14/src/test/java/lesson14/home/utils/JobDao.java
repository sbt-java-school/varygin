package lesson14.home.utils;

import java.util.Queue;

public interface JobDao {
    public Queue<Task> getQueue();
}
