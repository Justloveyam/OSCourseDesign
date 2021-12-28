package pers.yam.taskone;

public class Resource {
    private int num;  //当前可用资源数量
    private int maxNum;  //最大资源数量
    private static int useConsumeNum = 0;  //调用consume()函数次数
    private static int useYieldNum = 0;  //调用yield()函数次数

    public Resource() {
        this.num = 0;
        this.maxNum = 5;
    }
    //消耗资源
    public synchronized void consume() {
        //若num=0，则需等待资源补充
        while(num == 0) {
            try{
                System.out.println(Thread.currentThread().getName() + "进入等待状态，等待资源补充...");
                this.wait();  //线程等待并释放锁
            }catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }

        num--;
        useConsumeNum++;
        /*System.out.println(Thread.currentThread().getName() + "消耗一份资源"
                + "，剩余可用资源数量为:" + num + "，useConsumeNum:" + useConsumeNum);*/
        System.out.println(Thread.currentThread().getName() + "消耗一份资源"
                + "，剩余可用资源数量为:" + num);
        this.notifyAll();  //唤醒其它正在等待的线程
    }

    //生产资源
    public synchronized void yield() {
        //若可用资源数已达系统最大资源数，则进程进入阻塞状态
        while(num == maxNum) {
            try{
                System.out.println(Thread.currentThread().getName() + "进入等待状态，等待资源消耗...");
                this.wait();  //释放对象锁并进入阻塞状态
            }catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }

        num++;
        useYieldNum++;
        /*System.out.println(Thread.currentThread().getName() + "生产一份资源"
                + "，当前可用资源数量为:" + num + ", useYieldNum:" + useYieldNum);*/
        System.out.println(Thread.currentThread().getName() + "生产一份资源"
                + "，当前可用资源数量为:" + num);
        this.notifyAll();  //释放对象锁并唤醒阻塞状态的线程
    }

    public static int getUseConsumeNum() {
        return useConsumeNum;
    }

    public static int getUseYieldNum() {
        return useYieldNum;
    }
}
