import javax.swing.*;
import java.sql.*;

/**
 * @ClassName : DatabaseOperation  //类名
 * @Description : some operations to use database  //描述
 * @Author : 15154 //作者
 * @Date: 2022/9/19  17:06
 */


public class DatabaseOperation {
    /***
     * @description:  用于系统初始化创建用户表，创建一次就可以了
     * @param:
     * @return: void
     * @author 15154
     * @date: 2022/9/19 20:22
     */
    public static void createUsersTable(){
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\15154\\Desktop\\couple-project\\DatabaseName.db");
            statement = connection.createStatement();
            String sql = "CREATE TABLE Users " +
                    "(Name Text PRIMARY KEY  NOT NULL," +
                    " Password  Text NOT NULL)";
            statement.executeUpdate(sql);
            statement.close();
            connection.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    /***
     * @description:  向数据库中添加注册的用户
     * @param: name
    password
     *@return: void
     * @author 15154
     * @date: 2022/9/19 19:48
     */
    public static void InsertIntoUsers(String name,String password){
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\15154\\Desktop\\couple-project\\DatabaseName.db");
            connection.setAutoCommit(false);
            String sql = "insert into Users(name,password) values (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.commit();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }


    public static void modifyPassword(String name,char[] newPasswordCh){
        String newPassword = String.valueOf(newPasswordCh);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\15154\\Desktop\\couple-project\\DatabaseName.db");
            connection.setAutoCommit(false);
            String sql = "update Users set Password = ? where Name = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, newPassword);
            preparedStatement.setObject(2, name);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.commit();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }


    public static boolean checkUserExisted(String name,char[] passwordCh){
        String password = String.valueOf(passwordCh);
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\15154\\Desktop\\couple-project\\DatabaseName.db");
            connection.setAutoCommit(false);
            String sql = "select * from Users";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                if(resultSet.getString(1).equals(name) && resultSet.getString(2).equals(password)) {
                    connection.close();
                    preparedStatement.close();
                    return true;
                }
            }
            connection.close();
            preparedStatement.close();
            return false;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static String findUserPassword(String name){
        Connection connection = null;
        String str= "";
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\15154\\Desktop\\couple-project\\DatabaseName.db");
            connection.setAutoCommit(false);
            String sql = "select * from Users";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                if(resultSet.getString(1).equals(name)) {
                    str = resultSet.getString(2);
                    break;
                }
            }
            connection.close();
            preparedStatement.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return str;
    }



}
