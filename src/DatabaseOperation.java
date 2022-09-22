import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.DriverManager;


/**
 * @ClassName : DatabaseOperation                   // 类名
 * @Description : some operations to use database   // 描述
 * @Author : Chang                                  // 作者
 * @Date: 2022/9/19  17:06
 */


public class DatabaseOperation {
    /***
     * @description:  用于系统初始化创建用户表，创建一次就可以了
     * @param:
     * @return: void
     * @author Chang
     * @date: 2022/9/19 20:22
     */
    public static void createUsersTable() {
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
     * @description:  在生成Maths表的时候需要调用此函数，因为本地先运行过，所以就无需调用了
     * @param:
     * @return: void
     * @author Chang
     * @date: 2022/9/22 10:56
     */
    public static void createMathsTable() {
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\15154\\Desktop\\couple-project\\DatabaseName.db");
            statement = connection.createStatement();
            String sql = "CREATE TABLE Maths " +
                    "(Expression Text PRIMARY KEY  NOT NULL," +
                    " Level  Text NOT NULL," +
                    " Answer REAl NOT NULL" +
                    ")";
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
     * @param: name 用户的名称
    password 用户的密码
     *@return: void
     * @author Chang
     * @date: 2022/9/19 19:48
     */
    public static void InsertIntoUsers(String name,String password) {
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\15154\\Desktop\\couple-project\\DatabaseName.db");
            connection.setAutoCommit(false);
            String sql = "insert into Users(name,password) values (?, ?)";
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

    /***
     * @description:  修改密码，存入到数据库中
     * @param: name 用户的名称
    newPasswordCh 新密码
     * @return: void
     * @author Chang
     * @date: 2022/9/22 10:56
     */
    public static void modifyPassword(String name, char[] newPasswordCh) {
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

    /***
     * @description:  检查用户是否存在，根据用户的名称和密码进行数据库检索
     * @param: name 用户的名称
    passwordCh 用户的密码
     * @return: boolean
     * @author Chang
     * @date: 2022/9/22 10:57
     */
    public static boolean checkUserExisted(String name, char[] passwordCh) {
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

    /***
     * @description:  根据用户的名字，找到用户的原密码
     * @param: name 用户的名称
     * @return: java.lang.String
     * @author 15154
     * @date: 2022/9/22 10:58
     */
    public static String findUserPassword(String name) {
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

    /***
     * @description:  将生成的数学题存到数据库中，虽然本次项目没有用到，但是便于以后项目的拓展：例如查看往年试题
     * @param: problemInformations 保存数学题的题目和答案
    type 数学的类型：小学、初中、高中
     * @return: void
     * @author Chang
     * @date: 2022/9/22 10:59
     */
    public static void storeMathInDatabase(MathGenerate.ProblemInformation []problemInformations, String type) {
        for(int i = 0;i < problemInformations.length;i++) {
            Connection connection = null;
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\15154\\Desktop\\couple-project\\DatabaseName.db");
                connection.setAutoCommit(false);
                String sql = "insert or ignore  into Maths(Expression,Level,Answer) values (?,?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, problemInformations[i].expression);
                preparedStatement.setString(2, type);
                preparedStatement.setDouble(3, problemInformations[i].answer);
                preparedStatement.executeUpdate();
                preparedStatement.close();
                connection.commit();
                connection.close();
            } catch (SQLException | ClassNotFoundException e) {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                System.exit(0);
            }
        }
    }

}
