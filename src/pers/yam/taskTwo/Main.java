package pers.yam.taskTwo;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        FileSystem fileSystem = new FileSystem();
        Main.initialInterface();

        int choice = console.nextInt();
        String userName = "";
        String password = "";
        StringBuilder fileName = new StringBuilder();
        StringBuilder fileNewName = new StringBuilder();
        StringBuilder fileType = new StringBuilder();
        int fileSize = 0;
        int filePermission = 2;
        boolean isValid = false;

        while(choice != 0) {
            switch (choice) {
                case 1:  //用户注册！
                    isValid =false;
                    while(!isValid) {
                        System.out.println("请输入用户名:");
                        userName = console.next(); //不可以用nextLine()
                        System.out.println("请输入用户密码:");
                        password = console.next();  //不可用nextLine()
                        isValid = fileSystem.signUp(userName, password);
                    }

                    System.out.println("-----------------------------------------------------------");
                    System.out.println("-----------------------------------------------------------");
                    Main.initialInterface();
                    choice = console.nextInt();
                    break;
                case 2:
                    isValid = false;
                    while(!isValid) {
                        System.out.println("请输入用户名:");
                        userName = console.next();
                        System.out.println("请输入密码:");
                        password = console.next();
                        isValid = fileSystem.signIn(userName, password);
                    }
                    System.out.println("-----------------------------------------------------------");
                    System.out.println("-----------------------------------------------------------");

                    Main.loginInterface(fileSystem.getCurrentUser().getUsername());
                    int choice2 = console.nextInt();
                    boolean isExist = true;
                    while(choice2 != 0) {
                        switch (choice2) {
                            case 1:
                                isValid = false;
                                while(!isValid) {
                                    System.out.println("请输入文件夹名:");
                                    fileName.delete(0,fileName.length());
                                    fileName.append(console.next());
                                    isValid = fileSystem.createDirectory(fileName.toString());
                                }
                                System.out.println("-------------------------------------------------------------");
                                System.out.println("-------------------------------------------------------------");

                                Main.loginInterface(fileSystem.getCurrentUser().getUsername());
                                choice2 = console.nextInt();
                                break;
                            case 2:
                                isValid = false;
                                while(!isValid) {
                                    System.out.println("请输入文件名:");
                                    fileName.delete(0, fileName.length());
                                    fileName.append(console.next());
                                    System.out.println("请输入文件类型:");
                                    fileType.delete(0, fileType.length());
                                    fileType.append(console.next());
                                    System.out.println("请输入文件大小<<=32K>:");
                                    fileSize = console.nextInt();
                                    System.out.println("请输入文件权限<1-高级 2-普通>:");
                                    filePermission = console.nextInt();
                                    isValid = fileSystem.createFile(fileName.toString(), fileType.toString(),
                                            fileSize, filePermission);
                                }
                                System.out.println("-------------------------------------------------------------");
                                System.out.println("-------------------------------------------------------------");

                                Main.loginInterface(fileSystem.getCurrentUser().getUsername());
                                choice2 = console.nextInt();
                                break;
                            case 3:
                                System.out.println("请输入文件夹名:");
                                fileName.delete(0, fileName.length());
                                fileName.append(console.next());
                                fileSystem.openDirectory(fileName.toString());

                                System.out.println("-------------------------------------------------------------");
                                System.out.println("-------------------------------------------------------------");

                                Main.loginInterface(fileSystem.getCurrentUser().getUsername());
                                choice2 = console.nextInt();
                                break;
                            case 4:
                                System.out.println("请输入文件名:");
                                fileName.delete(0, fileName.length());
                                fileName.append(console.next());
                                System.out.println("请选择文件操作<1-读文件 2-写文件 3-关闭文件>:");
                                int choice3 = console.nextInt();
                                fileSystem.openFile(fileName.toString(), choice3);

                                System.out.println("-------------------------------------------------------------");
                                System.out.println("-------------------------------------------------------------");

                                Main.loginInterface(fileSystem.getCurrentUser().getUsername());
                                choice2 = console.nextInt();
                                break;
                            case 5:
                                fileName.delete(0, fileName.length());
                                fileName.append(fileSystem.getCurrentUser().getCurrentDirectory().getSuperiorDirectory());
                                fileSystem.backUp(fileName.toString());

                                System.out.println("-------------------------------------------------------------");
                                System.out.println("-------------------------------------------------------------");

                                Main.loginInterface(fileSystem.getCurrentUser().getUsername());
                                choice2 = console.nextInt();
                                break;
                            case 6:
                                System.out.println("请输入操作数<1-重命名文件 2-重命名文件夹>:");
                                int choice4 = console.nextInt();
                                if(choice4 == 1) {
                                    System.out.println("请输入需要修改的文件名:");
                                    fileName.delete(0, fileName.length());
                                    fileName.append(console.next());
                                    System.out.println("请输入新文件名:");
                                    fileNewName.delete(0, fileNewName.length());
                                    fileNewName.append(console.next());
                                    fileSystem.reName(fileName.toString(), fileNewName.toString(), choice4);
                                }else if(choice4 == 2) {
                                    System.out.println("请输入需要修改的文件夹名:");
                                    fileName.delete(0, fileName.length());
                                    fileName.append(console.next());
                                    System.out.println("请输入新文件夹名:");
                                    fileNewName.delete(0, fileNewName.length());
                                    fileNewName.append(console.next());
                                    fileSystem.reName(fileName.toString(), fileNewName.toString(), choice4);
                                }else {
                                    System.out.println("无效的操作数!");
                                }

                                System.out.println("-------------------------------------------------------------");
                                System.out.println("-------------------------------------------------------------");

                                Main.loginInterface(fileSystem.getCurrentUser().getUsername());
                                choice2 = console.nextInt();
                                break;
                            case 7:
                                System.out.println("请输入文件名:");
                                fileName.delete(0, fileName.length());
                                fileName.append(console.next());
                                fileSystem.deleteFile(fileName.toString());

                                System.out.println("-------------------------------------------------------------");
                                System.out.println("-------------------------------------------------------------");

                                Main.loginInterface(fileSystem.getCurrentUser().getUsername());
                                choice2 = console.nextInt();
                                break;
                            case 8:
                                System.out.println("请输入文件夹名:");
                                fileName.delete(0, fileName.length());
                                fileName.append(console.next());
                                fileSystem.deleteDirectory(fileName.toString());

                                System.out.println("-------------------------------------------------------------");
                                System.out.println("-------------------------------------------------------------");

                                Main.loginInterface(fileSystem.getCurrentUser().getUsername());
                                choice2 = console.nextInt();
                                break;
                            case 9:
                                System.out.println("请输入文件名:");
                                fileName.delete(0, fileName.length());
                                fileName.append(console.next());
                                fileSystem.showHead(fileName.toString());

                                System.out.println("-------------------------------------------------------------");
                                System.out.println("-------------------------------------------------------------");

                                Main.loginInterface(fileSystem.getCurrentUser().getUsername());
                                choice2 = console.nextInt();
                                break;
                            case 10:
                                fileSystem.showCurrentAll();

                                System.out.println("-------------------------------------------------------------");
                                System.out.println("-------------------------------------------------------------");

                                Main.loginInterface(fileSystem.getCurrentUser().getUsername());
                                choice2 = console.nextInt();
                                break;
                            case 11:
                                fileSystem.showPartitions();

                                System.out.println("-------------------------------------------------------------");
                                System.out.println("-------------------------------------------------------------");

                                Main.loginInterface(fileSystem.getCurrentUser().getUsername());
                                choice2 = console.nextInt();
                                break;
                            case 12:
                                fileSystem.cancel();

                                System.out.println("-------------------------------------------------------------");
                                System.out.println("-------------------------------------------------------------");
                                choice2 = 0;
                                isExist = false;
                                break;
                            default:
                                System.out.println("无效操作，请重新输入:");
                                choice2 = console.nextInt();
                                break;
                        }
                    }
                    if(isExist)
                        System.out.println("用户退出成功!");

                    System.out.println("-----------------------------------------------------------");
                    System.out.println("-----------------------------------------------------------");

                    Main.initialInterface();
                    choice = console.nextInt();
                    break;
                default:
                    System.out.println("无效选择，请重新输入:");
                    choice = console.nextInt();
                    break;
            }
        }
        System.out.println("系统退出成功!");
    }

    public static void initialInterface() {
        System.out.println("******************欢迎体验多用户文件管理系统********************");
        System.out.println("*                $ 注册用户请输入数字\"1\" $                   *");
        System.out.println("*                $ 登录用户请输入数字\"2\" $                   *");
        System.out.println("*                $ 退出系统请输入数字\"0\" $                   *");
        System.out.println("***********************************************************");
    }

    public static void loginInterface(String userName) {
        System.out.println("*************************<"+ userName +">*****************************");
        System.out.println("               & 新建文件夹请输入数字\"1\"                       ");
        System.out.println("               & 新建文件请输入数字\"2\"                         ");
        System.out.println("               & 打开文件夹请输入数字\"3\"                       ");
        System.out.println("               & 打开文件请输入数字\"4\"                         ");
        //System.out.println("               & 读文件请输入数字\"5\"                           ");
        //System.out.println("               & 写文件请输入数字\"6\"                           ");
        System.out.println("               & 返回上级文件夹请输入数字\"5\"                     ");
        System.out.println("               & 重命名文件请输入数字\"6\"                        ");
        System.out.println("               & 删除文件请输入数字\"7\"                          ");
        System.out.println("               & 删除文件夹请输入数字\"8\"                       ");
        System.out.println("               & 查看文件存储位置请输入数字\"9\"                  ");
        System.out.println("               & 查看当前文件夹所有内容请输入数字\"10\"             ");
        System.out.println("               & 查看磁盘空间使用状况请输入数字\"11\"               ");
        System.out.println("               & 注销用户请输入数字\"12\"                        ");
        System.out.println("               & 退出登录请输入数字\"0\"                         ");
        System.out.println("************************************************************");
    }
}
