package pers.yam.taskone;

public class Consumer implements Runnable{
    private Resource resource;
    private int sleepTime;

    public Consumer(Resource resource, int sleepTime) {
        this.resource = resource;
        this.sleepTime = sleepTime;
    }

    @Override
    public void run() {
        while(true) {
            if(Resource.getUseConsumeNum() >= 99)
                break;
            try{
                Thread.sleep((int)(Math.random()*sleepTime)+1000);
            }catch (Exception e) {
                System.err.println(e.getMessage());
            }
            resource.consume();
        }
    }
}
