package pers.yam.taskTwo;

import java.util.LinkedList;

public class User {
    private String username;
    private String password;
    private int permission;  //1-高权限用户 2-普通权限用户
    private LinkedList<Directory> directoryLinkedList;
    private Directory currentDirectory;  //当前目录
    private Memory memory;  //用户可用内存分区

    public User(String username, String password, Memory memory) {
        this.username = username;
        this.password = password;
        this.permission = 2;  //默认注册用户都为普通权限用户
        this.directoryLinkedList = new LinkedList<Directory>();
        this.directoryLinkedList.add(new Directory("root"));
        this.currentDirectory = directoryLinkedList.get(0);
        this.memory = memory;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public LinkedList<Directory> getDirectoryLinkedList() {
        return directoryLinkedList;
    }

    public void setDirectoryLinkedList(LinkedList<Directory> directoryLinkedList) {
        this.directoryLinkedList = directoryLinkedList;
    }

    public Directory getCurrentDirectory() {
        return currentDirectory;
    }

    public void setCurrentDirectory(Directory currentDirectory) {
        this.currentDirectory = currentDirectory;
    }

    public Memory getMemory() {
        return memory;
    }

    public void setMemory(Memory memory) {
        this.memory = memory;
    }
}
