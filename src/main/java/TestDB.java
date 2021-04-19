import java.sql.*;

public class TestDB {
    public static Connection connection;
    public static Statement statement;
    public static ResultSet resultSet;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        setConnection();
        createDb();
        writeDB();
        closeDB();
    }

    public TestDB() throws SQLException, ClassNotFoundException {
        setConnection();
        createDb();
        writeDB();
    }

    public static void setConnection() throws ClassNotFoundException, SQLException {
        connection = null;
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:main.db");
    }

    public static void createDb() throws SQLException {
        statement = connection.createStatement();
        statement.execute(
                "CREATE TABLE if not exists 'user'" +
                        "('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'name' text, 'phone' INT, 'password' text);");
    }

    public static void writeDB() throws SQLException {
        statement.execute("INSERT INTO users ('name', 'phone','password') VALUES ('Petya', 12345,'qqq')");
        statement.execute("INSERT INTO users ('name', 'phone','password') VALUES ('Petya2', 12345,'www')");
        statement.execute("INSERT INTO users ('name', 'phone','password') VALUES ('Petya3', 12345,'zzz')");
        statement.execute("INSERT INTO users ('name', 'phone','password') VALUES ('Petya4', 12345,'ddd')");
        statement.execute("INSERT INTO users ('name', 'phone','password') VALUES ('Petya5', 12345,'aaa')");
        statement.execute("INSERT INTO users ('name', 'phone','password') VALUES ('Petya6', 12345,'eee')");
        statement.execute("INSERT INTO users ('name', 'phone','password') VALUES ('Petya7', 12345,'sss')");
    }

    public void reqUser(String name, int phone, String pass) throws SQLException {
        statement.execute("INSERT INTO users ('name', 'phone','password') VALUES" + "(" + name + "," + phone + "," + pass + ")");
    }

    public String getPassByName(String name) throws SQLException {
        String sql = "SELECT password FROM users where name =?";
        PreparedStatement st = connection.prepareStatement(sql);
        st.setString(1, name);
        resultSet = st.executeQuery(sql);
        //resultSet = statement.executeQuery("SELECT password FROM users where name =" + name);
        while (resultSet.next()) {
            return resultSet.getString("password");
        }
        return "Не найден пользователь с таким логином";
    }

    public String login(String name, String pass) throws SQLException {
        String sql = "SELECT t.password FROM users as t where t.name =?";
        PreparedStatement st = connection.prepareStatement(sql);
        st.setString(1, name);
        resultSet = st.executeQuery(sql);
        //resultSet = statement.executeQuery("SELECT password FROM users where name =" + name);
        while (resultSet.next()) {
            return resultSet.getString("password");
        }
        return "Не найден пользователь с таким логином";
    }

    public static void readDB() throws SQLException {
        resultSet = statement.executeQuery("SELECT * FROM users");
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int phone = resultSet.getInt("phone");
            System.out.println(id + " " + name + " " + phone);

            System.out.println(new User(name, phone));
        }
    }

    public static void closeDB() throws SQLException {
        resultSet.close();
        statement.close();
        connection.close();
    }
}

class User {
    String name;
    int phone;

    public User(String name, int phone) {
        this.name = name;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", phone=" + phone +
                '}';
    }
}
