package com.cjh.collection;

import java.io.*;

/**
 * @ClassName TransientTest
 * @Description
 * @Author Administrator
 * @Date 2022/7/28 17:12
 * @Version 1.0
 */
public class TransientTest {
    public static void main(String[] args) {
//        User user = new User();
//        user.setUserName("user name");
//        System.out.println(user.toString());

        OtherUser otherUser = new OtherUser();


        try {
            ObjectOutputStream ops = new ObjectOutputStream(new FileOutputStream("c:/user1.txt"));
            ops.writeObject(otherUser);
            ops.flush();
            ops.close();
        }catch (Exception e) {
            e.printStackTrace();
        }

        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream("c:/user1.txt"));
            otherUser = (OtherUser) is.readObject();
            is.close();
            System.out.println(otherUser.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class User implements Serializable {
    private transient String password = "2134";
    private String userName = "userName";

    public static Integer age = 10;

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User{" +
                "age" + age + '\'' +
                "password='" + password + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}

class OtherUser implements Externalizable {
    private  transient  String content = "can serialize";

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(content);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        content = (String) in.readObject();
    }

    @Override
    public String toString() {
        return "OtherUser{" +
                "content='" + content + '\'' +
                '}';
    }
}