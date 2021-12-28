package pers.yam.taskTwo;

import java.util.LinkedList;

public class Memory {
    private int surplusSize;  //内存分区可用大小
    private LinkedList<Partition> partitions;
    private static final int MIN_SIZE = 5;

    public Memory(int size) {
        this.surplusSize = size;
        this.partitions =new LinkedList<Partition>();
        this.partitions.add(new Partition(0, size));
    }

    /*public Memory bestFit(Memory memory, int size) {
        int bestIndex = -1;  //最佳分区下标
        int minResidue =memory.getSurplusSize();  //最小内存盈余量
        for(int i=0; i<partitions.size(); i++) {
            Partition temp = partitions.get(i);
            if(temp.isFree() && ((temp.getSize()-size)<minResidue)) {
                minResidue = temp.getSize() - size;
                bestIndex = i;
            }
        }
        if(bestIndex!=-1)
            return memory.getMemory(size, bestIndex, memory.partitions.get(bestIndex));
        System.out.println("文件过大！");
        return memory;
    }

    public Memory getMemory(int fileSize, int index, Partition partition) {  //size-文件大小 index-最佳内存分区索引 partition-最佳内存分区
        //将盈余的分区与原分区拆分，使之单独成为一个新内存分区
        if(partition.getSize() - fileSize >= MIN_SIZE) {
            Partition etrPartition = new Partition(partition.getHead()+fileSize, partition.getSize()-fileSize);
            partitions.add(etrPartition);
            partition.setSize(fileSize);
        }
        partition.setFree(false);
        return this;
    }*/

    //采用最佳适应算法获取内存
    /*public Memory getMemory(Memory memory, int fileSize) {
        //最佳适应算法
        int bestIndex = -1;  //最佳内存分区下标
        int minResidue = memory.getSurplusSize();  //最小盈余内存
        for(int i=0; i<memory.getPartitions().size(); i++) {
            Partition temp = memory.getPartitions().get(i);
            if(temp.isFree() && ((temp.getSize()-fileSize)<=minResidue)) {
                minResidue = temp.getSize() - fileSize;
                bestIndex = i;
            }
        }

        Partition temp = memory.getPartitions().get(bestIndex);
        if(bestIndex != -1) {
            //将盈余分区与原分区拆开，使之形成新内存分区
            if(temp.getSize()-fileSize >= MIN_SIZE) {
                Partition etrPartition = new Partition(temp.getHead()+fileSize, temp.getSize()-fileSize);
                memory.getPartitions().add(etrPartition);
                temp.setSize(fileSize);
            }
            temp.setFree(false);
            return memory;
        }
        System.out.println("文件过大，创建失败");
        return memory;
    }*/

    public boolean supplyMemory(int fileSize, File newFile) {
        //最佳适应算法分配内存
        int bestIndex = -1;  //最佳内存块分区索引
        int minResidue = this.surplusSize;  //最小盈余内存
        for(int i=0; i< partitions.size(); i++) {
            if(partitions.get(i).isFree() && ((partitions.get(i).getSize()-fileSize)<=minResidue)) {
                bestIndex = i;
                minResidue = partitions.get(i).getSize() - fileSize;
            }
        }

        if(bestIndex != -1) {
            Partition temp = partitions.get(bestIndex);
            newFile.setHead(temp.getHead());
            Partition etrPartition = new Partition(temp.getHead()+fileSize, temp.getSize()-fileSize);
            partitions.add(etrPartition);
            temp.setSize(fileSize);
            temp.setFree(false);
            return true;
        }
        //System.out.println("文件过大，新建文件失败！");
        return false;
    }

    public boolean releaseMemory(int index) {
        //若此内存非尾分区且其下分区为空闲状态，则将二者合并
        Partition releasePartition = partitions.get(index);
        if(releasePartition.isFree()) {
            return false;
        }
        if(partitions.get(index+1).isFree() && index < partitions.size()-1) {
            releasePartition.setSize(releasePartition.getSize()+partitions.get(index+1).getSize());
            partitions.remove(index+1);
        }else if(partitions.get(index-1).isFree() && index > 0) {  //若此内存非头分区且其上分区为空闲状态，则将二者合并
            Partition temp = partitions.get(index-1);
            temp.setSize(temp.getSize()+ releasePartition.getSize());
            partitions.remove(index);
        }
        releasePartition.setFree(true);
        return true;
    }


    public int getSurplusSize() {
        return surplusSize;
    }

    public void setSurplusSize(int surplusSize) {
        this.surplusSize = surplusSize;
    }

    public LinkedList<Partition> getPartitions() {
        return partitions;
    }

    public void setPartitions(LinkedList<Partition> partitions) {
        this.partitions = partitions;
    }

    public static int getMinSize() {
        return MIN_SIZE;
    }

}
