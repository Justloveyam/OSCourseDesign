package pers.yam.taskTwo;

import java.util.LinkedList;

public class Directory {
    private String name;
    private int size;
    private String superiorDirectory;
    private LinkedList<File> fileLinkedList;
    private LinkedList<Directory> directoryLinkedList;

    public Directory(String name) {
        this.name = name;
        this.size = 0;  //设定空文件夹占用内存为0K
        this.superiorDirectory = "";
        this.fileLinkedList = new LinkedList<File>();
        this.directoryLinkedList = new LinkedList<Directory>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void setSuperiorDirectory(String superiorDirectory) {
        this.superiorDirectory = superiorDirectory;
    }

    public String getSuperiorDirectory() {
        return superiorDirectory;
    }

    public void setFileLinkedList(LinkedList<File> fileLinkedList) {
        this.fileLinkedList = fileLinkedList;
    }

    public LinkedList<File> getFileLinkedList() {
        return fileLinkedList;
    }

    public void setDirectoryLinkedList(LinkedList<Directory> directoryLinkedList) {
        this.directoryLinkedList = directoryLinkedList;
    }

    public LinkedList<Directory> getDirectoryLinkedList() {
        return directoryLinkedList;
    }

}
