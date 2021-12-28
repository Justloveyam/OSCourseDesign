package pers.yam.taskTwo;

public class Partition {
    private int head;
    private int size;
    private boolean isFree;  //记录内存块状态

    public Partition(int head, int size) {
        this.head = head;
        this.size = size;
        this.isFree = true;
    }

    public void setHead(int head) {
        this.head = head;
    }

    public int getHead() {
        return head;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public boolean isFree() {
        return isFree;
    }
}
