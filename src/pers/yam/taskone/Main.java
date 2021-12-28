package pers.yam.taskone;

public class Main {
    public static void main(String[] args) {
        Resource resource = new Resource();
        Producer producer1 = new Producer(resource, (int)(Math.random()*10)+1000);
        Producer producer2 = new Producer(resource, (int)(Math.random()*2000)+1000);
        Consumer consumer1 = new Consumer(resource, (int)(Math.random()*5000)+1000);
        Consumer consumer2 = new Consumer(resource, (int)(Math.random()*6000)+1000);

        new Thread(consumer1, "消费者1").start();
        new Thread(producer1, "生产者1").start();
        new Thread(producer2, "生产者2").start();
        new Thread(consumer2, "消费者2").start();
    }
}
