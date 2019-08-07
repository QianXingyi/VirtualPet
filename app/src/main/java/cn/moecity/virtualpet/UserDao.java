package cn.moecity.virtualpet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private Connection conn = null;
    private Statement st = null;
    private ResultSet rs = null;
    PreparedStatement pst=null;

    public List<User> fetchAll() {
        List<User> ss = new ArrayList<User>();
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "url", "user",
                    "password");
        } catch (ClassNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        String sql = "select * from userTable";
        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                ss.add(new User(rs.getInt("uID"),
                        rs.getString("uName"),
                        rs.getString("uPhone"),
                        rs.getString("uPass"),
                        rs.getInt("uMoney"),
                        rs.getString("phoneID")
                ));
            }
            return ss;
        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }


    public List<User> findByPhone(String phone) {
        List<User> ss = new ArrayList<User>();


        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
             conn = DriverManager.getConnection(
                     "url", "user",
                     "password");
        } catch (ClassNotFoundException e) {
            System.out.println("加载驱动程序出错");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }

        String sql = "select * from userTable where uPhone like'" + phone + "%'";

        try {

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                ss.add(new User(rs.getInt("uID"),
                        rs.getString("uName"),
                        rs.getString("uPhone"),
                        rs.getString("uPass"),
                        rs.getInt("uMoney"),
                        rs.getString("phoneID")
                ));
            }
            return ss;
        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }

    //保存
    public void save(User t) {
        int result = 0;

        String sql = "insert into teacher(uName,uPhone,uPass,uMoney) values( ? , ? , ? , ? )";
        System.out.println(sql);
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "url", "user",
                    "password");
            pst = conn.prepareStatement(sql);
            pst.setString(1, t.getuName());
            pst.setString(2, t.getuPhone());
            pst.setString(3, t.getuPass());
            pst.setInt(4, t.getuMoney());
            result = pst.executeUpdate();
            if (result > 0)
                System.out.println("save success!");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("save failed!");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void update(User t) {
        int result = 0;
        //占位符，只是占位
        String sql = "update teacher set uName=?,uPhone=?,uPass=?,uMoney=? where uID=?";
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "url", "user",
                    "password");
            pst = conn.prepareStatement(sql);
            pst.setString(1, t.getuName());
            pst.setString(2, t.getuPhone());
            pst.setString(3, t.getuPass());
            pst.setInt(4, t.getuMoney());
            pst.setInt(5, t.getuID());
            result = pst.executeUpdate();
            if (result > 0)
                System.out.println("update success!");
        } catch (Exception e) {
            System.out.println("update failed!");
        }

    }

    public void delete(User t) {
        int result = 0;
        String sql = "delete from userTable where uID=?";
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "url", "user",
                    "password");
            pst = conn.prepareStatement(sql);
            pst.setInt(1, t.getuID());
            result = pst.executeUpdate();
            if (result > 0)
                System.out.println("delete success!");
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("delete failed!");
        }
    }

}

