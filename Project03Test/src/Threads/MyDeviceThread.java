package Threads;

public abstract class MyDeviceThread extends Thread{

    protected static int deviceIndex = 1;

    protected int ID;
    public MyDeviceThread() {
        this.ID=deviceIndex++;
    }
}
