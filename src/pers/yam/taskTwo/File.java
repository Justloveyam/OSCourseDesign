package pers.yam.taskTwo;

public class File {
    private String name;
    private String type;  //文件类型(即文件后缀名)
    private int size;
    private int permission;  //1-高权限文件；2-普通权限文件
    private String superiorDirectory;  //文件所在目录
    private StringBuilder content;  //文件内容
    private int head;  //与内存空间地址一致

    public File(String name, String type, int size, int permission) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.permission = permission;
        this.superiorDirectory = "";
        this.content = new StringBuilder();
        this.head = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public String getSuperiorDirectory() {
        return superiorDirectory;
    }

    public void setSuperiorDirectory(String superiorDirectory) {
        this.superiorDirectory = superiorDirectory;
    }

    public int getHead() {
        return head;
    }

    public void setHead(int head) {
        this.head = head;
    }

    public StringBuilder getContent() {
        return content;
    }

    public void setContent(StringBuilder content) {
        this.content = content;
    }
}
