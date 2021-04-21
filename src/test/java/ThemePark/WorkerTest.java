package ThemePark;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class WorkerTest {
    int segmentSize = 20; //size of one grid
         ArrayList<Worker> workers = new ArrayList<Worker>();

    @Test
    public void TestWorker() {
        workers.add(new Worker("cleaner",60,80,segmentSize, segmentSize));
        workers.add(new Worker("maintenance",60,80,segmentSize, segmentSize));


        Assert.assertNotNull(workers);
        int bound = workers.size();
        Assert.assertNotNull(workers.size());

    }

    @Test
    public void TestSalary() {
        int bound = workers.size();
        IntStream.range(0, bound).forEachOrdered(i -> Assert.assertEquals("Workers salary", workers.get(i).getSalary(), 200));

    }

    @Test
    public void TestDirection() {

        for (int i = 0; i < workers.size(); i++) {
            workers.get(i).setDirection(1);
        }

        for (int i = 0; i < workers.size(); i++) {
           Assert.assertNotNull(workers.get(i).getDirection());
        }

    }
}