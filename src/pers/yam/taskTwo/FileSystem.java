package pers.yam.taskTwo;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class FileSystem {
    /*private int index;  //记录最近一次寻址地址
    private LinkedList<Memory> memories;*/
    private LinkedList<User> users;
    private User currentUser;
    private HashMap<String, String> accounts;  //存储用户名及用户密码

    public FileSystem() {
        //this.memories = new LinkedList<Memory>();
        this.users = new LinkedList<User>();
        //this.index = 0;
        this.currentUser = null;
        this.accounts = new HashMap<String, String>();
    }

    //注册用户
    public boolean signUp(String name, String password) {
        boolean isValid = true;
        /*for(int i=0; i< users.size(); i++) {
            if(users.get(i).getUsername().equals(name)) {
                isValid = false;
                break;
            }
        }*/
        if(accounts.containsKey(name)) {
            isValid = false;
        }
        if(isValid) {
            //为用户分配32K总内存
            Memory userSpace = new Memory(32);
            //memories.add(userSpace);
            User user = new User(name, password, userSpace);
            accounts.put(user.getUsername(), user.getPassword());
            //将用户添加至系统用户链表中
            users.add(user);
            System.out.println("注册用户成功！");
        }else
            System.out.println("用户名重复，请重新输入！");
        return isValid;
    }

    //登录用户
    public boolean signIn(String name, String password) {
        boolean isValid = false;
        //验证用户登录
        if(accounts.containsKey(name)) {
            if(accounts.get(name).equals(password)) {
                //在users链表中找到该用户并将之赋值给currentUser属性
                for(int i=0; i< users.size(); i++) {
                    if(users.get(i).getUsername().equals(name)) {
                        this.currentUser = users.get(i);
                        break;
                    }
                }
                System.out.println("登录成功!");
                isValid = true;
            }else {
                System.out.println("密码错误，请重新输入!");
            }
        }else {
            System.out.println("用户名无效!");
        }
        return isValid;
    }

    //用户注销
    public void cancel() {
        this.accounts.remove(currentUser.getUsername());
        this.users.remove(currentUser);
        System.out.println("用户注销成功!");
    }

    //新建文件
    public boolean createFile(String fileName, String type, int size, int permission) {
        boolean isValid = true;
        File newFile = new File(fileName, type, size, permission);
        if(newFile.getSize() > currentUser.getMemory().getSurplusSize()) {
            System.out.println("文件过大，新建文件失败!");
        }else {
            //判断是否有同名文件
            for(int i=0; i<currentUser.getCurrentDirectory().getFileLinkedList().size(); i++) {
                if(currentUser.getCurrentDirectory().getFileLinkedList().get(i).getName().equals(fileName)) {
                    isValid = false;
                    break;
                }
            }
            if(isValid) {
                newFile.setSuperiorDirectory(currentUser.getCurrentDirectory().getName());  //设置上级目录
                if(currentUser.getMemory().supplyMemory(newFile.getSize(), newFile)) {  //分配内存并设置文件在内存中的头地址
                    currentUser.getCurrentDirectory().getFileLinkedList().add(newFile);  //将该文件加入至当前目录的文件链表中
                    //设置上级目录文件夹大小
                    currentUser.getCurrentDirectory().setSize(currentUser.getCurrentDirectory().getSize() + size);
                    System.out.println("文件："+ newFile.getName() + "." + newFile.getType() + "创建成功!");
                }else {
                    System.out.println("文件过大，内存空间不足，新建文件失败!");
                }
            }else {
                System.out.println("该文件已存在!");
            }
        }
        return isValid;
    }

    //新建文件夹
    public boolean createDirectory(String directoryName) {
        boolean isValid = true;
        //判断是否有同名文件夹
        for(int i=0; i<currentUser.getCurrentDirectory().getDirectoryLinkedList().size(); i++) {
            if(currentUser.getCurrentDirectory().getDirectoryLinkedList().get(i).getName().equals(directoryName)) {
                isValid = false;
                break;
            }
        }
        if(isValid) {
            Directory newDirectory = new Directory(directoryName);
            newDirectory.setSuperiorDirectory(currentUser.getCurrentDirectory().getName());  //设置其上级目录
            currentUser.getDirectoryLinkedList().add(newDirectory);  //将此目录加入至该目录中的目录链表中
            //将此文件夹放入放入当前文件夹中
            currentUser.getCurrentDirectory().getDirectoryLinkedList().add(newDirectory);
            System.out.println("文件夹：" + newDirectory.getName() + "创建成功!");
        }else {
            System.out.println("该文件夹已存在!");
        }
        return isValid;
    }

    //打开文件
    public void openFile(String fileName, int operation) {
        File aimFile = null;
        for(int i=0; i<currentUser.getCurrentDirectory().getFileLinkedList().size(); i++) {
            if(currentUser.getCurrentDirectory().getFileLinkedList().get(i).getName().equals(fileName)) {
                aimFile = currentUser.getCurrentDirectory().getFileLinkedList().get(i);
                break;
            }
        }
        switch (operation) {
            case 1:
                readFile(aimFile);
                break;
            case 2:
                writeFile(aimFile);
                break;
            case 3:
                closeFile(fileName);
                break;
            default:
                System.out.println("无效操作!");
                break;
        }
    }

    public void closeFile(String fileName) {
        System.out.println(fileName + "文件关闭成功!");
    }

    //打开文件夹
    public void openDirectory(String directoryName) {
        Directory aimDirectory = null;
        for(int i=0; i<currentUser.getCurrentDirectory().getDirectoryLinkedList().size(); i++) {
            if(currentUser.getCurrentDirectory().getDirectoryLinkedList().get(i).getName().equals(directoryName)) {
                aimDirectory = currentUser.getCurrentDirectory().getDirectoryLinkedList().get(i);
                break;
            }
        }
        readFile(aimDirectory);
    }

    //读文件或文件夹操作
    public void readFile(Object object) {
        boolean isFile = object instanceof File;
        if(isFile) {
            File aimFile = (File)object;
            System.out.println("------------文件内容------------");
            System.out.println(aimFile.getContent());
            System.out.println("-------------------------------");
            System.out.println("");
        }else {
            Directory aimDirectory = (Directory) object;
            currentUser.setCurrentDirectory(aimDirectory);
            System.out.println("-----------文件夹内容------------");
            for(int i=0; i<aimDirectory.getDirectoryLinkedList().size(); i++) {
                System.out.println(aimDirectory.getDirectoryLinkedList().get(i).getName());
            }
            for(int i=0; i<aimDirectory.getFileLinkedList().size(); i++) {
                System.out.println(aimDirectory.getFileLinkedList().get(i).getName() + "."
                        + aimDirectory.getFileLinkedList().get(i).getType() + "<权限等级:"
                        + aimDirectory.getFileLinkedList().get(i).getPermission() + ">");
            }
            System.out.println("--------------------------------");
        }
    }

    //写文件操作
    public void writeFile(File aimFile) {
        System.out.println("请输入文件内容：");
        Scanner console = new Scanner(System.in);
        aimFile.getContent().append(console.next()); //文件内容追加
    }

    //文件夹回退操作
    public void backUp(String directoryName) {
        Directory aimDirectory = null;
        for(int i=0; i<currentUser.getDirectoryLinkedList().size(); i++) {
            if(currentUser.getDirectoryLinkedList().get(i).getName().equals(directoryName)) {
                aimDirectory = currentUser.getDirectoryLinkedList().get(i);
                break;
            }
        }
        readFile(aimDirectory);
    }

    public void deleteFile(String fileName) {
        File aimFile = null;
        int aimIndex = 0;
        int aimHead = 0;
        for(int i=0; i<currentUser.getCurrentDirectory().getFileLinkedList().size(); i++) {
            if(currentUser.getCurrentDirectory().getFileLinkedList().get(i).getName().equals(fileName)) {
                aimFile = currentUser.getCurrentDirectory().getFileLinkedList().get(i);
                aimIndex = i;
                break;
            }
        }
        aimHead = aimFile.getHead();
        if(aimFile.getPermission() == 1) {
            if(currentUser.getPermission() == 2) {
                System.out.println("用户权限不足!");
            }else {

                //将此文件从该目录中移除
                currentUser.getCurrentDirectory().getFileLinkedList().remove(aimIndex);
                //将此文件对应的内存分区回收
                for(int i=0; i<currentUser.getMemory().getPartitions().size(); i++) {
                    if(currentUser.getMemory().getPartitions().get(i).getHead() == aimHead) {
                        currentUser.getMemory().releaseMemory(i);
                        break;
                    }
                }
                System.out.println("文件删除成功!");
            }
        }else {
            //将此文件从该目录中移除
            currentUser.getCurrentDirectory().getFileLinkedList().remove(aimIndex);
            //将此文件对应的内存分区回收
            for(int i=0; i<currentUser.getMemory().getPartitions().size(); i++) {
                if(currentUser.getMemory().getPartitions().get(i).getHead() == aimHead) {
                    currentUser.getMemory().releaseMemory(i);
                    break;
                }
            }
            System.out.println("文件删除成功!");
        }
    }

    public void deleteDirectory(String directoryName) {
        Directory aimDirectory = null;
        for(int i=0; i<currentUser.getCurrentDirectory().getDirectoryLinkedList().size(); i++) {
            if(currentUser.getCurrentDirectory().getDirectoryLinkedList().get(i).getName().equals(directoryName)) {
                aimDirectory = currentUser.getCurrentDirectory().getDirectoryLinkedList().get(i);
                break;
            }
        }
        if(aimDirectory.getDirectoryLinkedList().isEmpty() && aimDirectory.getFileLinkedList().isEmpty()) {
            currentUser.getCurrentDirectory().getDirectoryLinkedList().remove(aimDirectory);
            System.out.println("文件夹删除成功!");
        }else {
            System.out.println("该文件夹包含其它文件，无法删除!");
        }
    }

    public void reName(String name, String newName, int operation) {
        //boolean isValid = true;
        if(operation == 1) {
            for(int i=0; i<currentUser.getCurrentDirectory().getFileLinkedList().size(); i++) {
                if(currentUser.getCurrentDirectory().getFileLinkedList().get(i).getName().equals(newName)) {
                    //isValid = false;
                    //break;
                    System.out.println("存在同名文件，修改失败!");
                    return;
                }
            }
        }else if(operation == 2) {
            for(int i=0; i<currentUser.getCurrentDirectory().getDirectoryLinkedList().size(); i++) {
                if(currentUser.getCurrentDirectory().getDirectoryLinkedList().get(i).getName().equals(newName)) {
                    //isValid = false;
                    //break;
                    System.out.println("存在同名文件夹，修改失败!");
                    return;
                }
            }
        }
        switch(operation) {
            case 1:
                File aimFile = null;
                for(int i=0; i<currentUser.getCurrentDirectory().getFileLinkedList().size(); i++) {
                    if(currentUser.getCurrentDirectory().getFileLinkedList().get(i).getName().equals(name)) {
                        aimFile = currentUser.getCurrentDirectory().getFileLinkedList().get(i);
                        break;
                    }
                }
                aimFile.setName(newName);
                System.out.println("文件名修改成功!");
                break;
            case 2:
                Directory aimDirectory = null;
                for(int i=0; i<currentUser.getCurrentDirectory().getDirectoryLinkedList().size(); i++) {
                    if(currentUser.getCurrentDirectory().getDirectoryLinkedList().get(i).getName().equals(name)) {
                        aimDirectory = currentUser.getCurrentDirectory().getDirectoryLinkedList().get(i);
                        break;
                    }
                }
                aimDirectory.setName(newName);
                System.out.println("文件夹名修改成功!");
                break;
        }
    }

    public void showCurrentAll() {
        String aimName = currentUser.getCurrentDirectory().getName();
        System.out.println("------------<" + aimName +">文件夹------------");
        System.out.println("-------------------------------------------------");
        for(int i=0; i<currentUser.getCurrentDirectory().getDirectoryLinkedList().size(); i++) {
            System.out.println(currentUser.getCurrentDirectory().getDirectoryLinkedList().get(i).getName());
        }
        for(int i=0; i<currentUser.getCurrentDirectory().getFileLinkedList().size(); i++) {
            File temp = currentUser.getCurrentDirectory().getFileLinkedList().get(i);
            System.out.println(temp.getName() + "." + temp.getType());
        }
        System.out.println("-------------------------------------------------");
    }


    public void showHead(String fileName) {
        boolean isValid = false;
        for(int i=0; i<currentUser.getCurrentDirectory().getFileLinkedList().size(); i++) {
            File temp = currentUser.getCurrentDirectory().getFileLinkedList().get(i);
            if(temp.getName().equals(fileName)) {
                System.out.println("文件名:" + temp.getName() + "." + temp.getType() +
                        "\tFileSize:" + temp.getSize() + "\tFileAddress:" + temp.getHead());
                isValid = true;
                break;
            }
        }
        if(!isValid)
            System.out.println("文件不存在!");
    }

    public void showPartitions() {
        System.out.println("----------------磁盘空间使用状况---------------------------");
        System.out.println("--区块地址--------------区块大小--------------区块空闲------");
        for(int i=0; i<currentUser.getMemory().getPartitions().size(); i++) {
            Partition temp = currentUser.getMemory().getPartitions().get(i);
            if(temp.getHead() >= 0 && temp.getHead()< 10) {
                System.out.println("  " + temp.getHead() + " \t\t\t\t\t" + temp.getSize() + "   \t\t\t\t" + temp.isFree());
            }else {
                System.out.println("  " + temp.getHead() + "\t\t\t\t\t" + temp.getSize() + "   \t\t\t\t" + temp.isFree());
            }
        }
        System.out.println("---------------------------------------------------------");
    }


    /*public void setZones(LinkedList<Memory> memories) {
        this.memories = memories;
    }

    public LinkedList<Memory> getZones() {
        return memories;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }*/

    public LinkedList<User> getUsers() {
        return users;
    }

    public User getCurrentUser() {
        return currentUser;
    }


}
