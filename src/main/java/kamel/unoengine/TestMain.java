package kamel.unoengine;


public class TestMain {
    public static void main(String[] args) {
        Object lock0 = new Object();
        Object lock1 = new Object();
        Thread t0 = new CustomThread("Thread 0", lock0);
        Thread t1 = new CustomThread("Thread 1", lock1);
        t0.start();
        t1.start();

        try {
            for (int i = 0; i < 20; i++) {
                Thread.sleep(5);
                synchronized (lock0) {
                    lock0.notify();
                }
                Thread.sleep(5);
                synchronized (lock0) {
                    lock0.notify();
                }
                synchronized (lock1) {
                    lock1.notify();
                }
            }
            t0.interrupt();
            t1.interrupt();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

class CustomThread extends Thread {
    private String msg;
    private Object lock;

    public CustomThread(String msg, Object lock) {
        this.msg = msg;
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            while (true) {
                synchronized (lock) {
                    lock.wait();
                    System.out.println(msg);
                }
            }
        } catch (Exception e) {}
    }
}
