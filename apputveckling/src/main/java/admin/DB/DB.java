package admin.DB;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.credential.Password;

import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.*;

@Named
@Default
@ApplicationScoped
public class DB implements Serializable {
    private DB databaseExample;
    @Resource(name = "mysql_web")
    private DataSource dataSource;
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void CreateDB() {
        try (Connection con = dataSource.getConnection()) {
            String sql = "CREATE DATABASE IF NOT EXISTS ProjectDB";
            try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
                preparedStatement.executeUpdate();
            }
            System.out.println("CREATED DATABASE ProjectDB!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void CreateTableClient(){
        try (Connection con = dataSource.getConnection()) {
            String sql = "CREATE TABLE IF NOT EXISTS CLIENT("
                    +"CLIENT_ID INT AUTO_INCREMENT PRIMARY KEY,"
                    + "CLIENT_NAME VARCHAR(255),"
                    + "CLIENT_PHONE VARCHAR(20),"
                    + "CLIENT_EMAIL VARCHAR(255),"
                    + "CLIENT_DATE date," +
                    "PASSWORD VARCHAR(200))";
            try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
                preparedStatement.executeUpdate();
            }
            System.out.println("CREATED Table Member!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void CreateTableProduct(){
        try (Connection con = dataSource.getConnection()) {
            String sql = "CREATE TABLE IF NOT EXISTS PRODUCT("
                    +"PRODUCT_ID INT AUTO_INCREMENT PRIMARY KEY,"
                    + "PRODUCT_PRICE DECIMAL(10,2),"
                    + "PRODUCT_HISTORY_DESC VARCHAR(255),"
                    + "PRODUCT_MAIN_DESC VARCHAR(500),"
                    + "PRODUCT_YEAR INT,"
                    + "PRODUCT_NAME VARCHAR(255))";
            try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
                preparedStatement.executeUpdate();
            }
            System.out.println("CREATED Table Product!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void CreateTableCase(){
        try (Connection con = dataSource.getConnection()) {
            String sql = "CREATE TABLE IF NOT EXISTS Cases("
                    + "Case_id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "Case_member_id int,"
                    + "Case_state VARCHAR(255),"
                    + "Case_date_start datetime,"
                    + "Case_date_end datetime,"
                    + "Case_profit int,"
                    + "Case_description VARCHAR(255),"
                    + "Case_image VARCHAR(255),"
                    + "Case_type VARCHAR(255))";
            try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
                preparedStatement.executeUpdate();
            }
            System.out.println("CREATED Table Case!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void CreateTableCaseType(){
        try (Connection con = dataSource.getConnection()) {
            String sql = "CREATE TABLE IF NOT EXISTS Case_Type("
                    + "Type_id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "Type_description VARCHAR(255))";
            try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
                preparedStatement.executeUpdate();
            }
            System.out.println("CREATED Table Case_Type!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void fetchData() {
        if (dataSource == null) {
            throw new IllegalStateException("DataSource not set. Call setDataSource method first.");
        }

        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM Member";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        // Process the result set
                        String columnValue = resultSet.getString("Member_id");
                        System.out.println(columnValue);
                        // Do something with the column value
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getIdByPhonNum(int phoneNumber) {

        try {
            Connection con = dataSource.getConnection();
            String sql = "SELECT Member_id FROM Member WHERE Member_phone = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            // Set the parameter value
            preparedStatement.setInt(1, phoneNumber);

            ResultSet rs = preparedStatement.executeQuery();

            int medlems_nummer =0;

            if (rs.next()) {
                // Retrieve the result
                medlems_nummer = rs.getInt("Member_id");
                System.out.println(medlems_nummer);
            } else {
                System.out.println("User with ID " + phoneNumber + " not found.");
            }

            // Close resources
            rs.close();
            preparedStatement.close();
            con.close();

            return medlems_nummer;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getIdByEmail(String email) {

        try {
            Connection con = dataSource.getConnection();
            String sql = "SELECT Member_id FROM Member WHERE Member_email = ?";

            PreparedStatement preparedStatement = con.prepareStatement(sql);

            // Set the parameter value
            preparedStatement.setString(1, email);

            ResultSet rs = preparedStatement.executeQuery();

            int medlems_nummer =0;

            if (rs.next()) {
                // Retrieve the result
                medlems_nummer = rs.getInt("Member_id");
                System.out.println(medlems_nummer);
            } else {
                System.out.println("User with ID " + email + " not found.");
            }

            // Close resources
            rs.close();
            preparedStatement.close();
            con.close();

            return medlems_nummer;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public String getNameById(int id) {

        try {
            Connection con = dataSource.getConnection();
            String sql = "SELECT Member_name FROM Member WHERE Member_id = ?";

            PreparedStatement preparedStatement = con.prepareStatement(sql);

            // Set the parameter value
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            String medlems_nummer ="";

            if (rs.next()) {
                // Retrieve the result
                medlems_nummer = rs.getString("Member_name");
                System.out.println(medlems_nummer);
            } else {
                System.out.println("User with ID " + id + " not found.");
            }

            // Close resources
            rs.close();
            preparedStatement.close();
            con.close();

            return medlems_nummer;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int GetIdByName(String name) {

        try {
            Connection con = dataSource.getConnection();
            String sql = "SELECT CLIENT_NAME FROM CLIENT WHERE CLIENT_ID = ?";

            PreparedStatement preparedStatement = con.prepareStatement(sql);

            // Set the parameter value
            preparedStatement.setString(1, name);

            ResultSet rs = preparedStatement.executeQuery();

            int id =0;

            if (rs.next()) {
                // Retrieve the result
                id = rs.getInt("CLIENT_ID");
                //System.out.println(id);
            } /*else {
                System.out.println("User with ID " + name + " not found.");
            }*/

            // Close resources
            rs.close();
            preparedStatement.close();
            con.close();

            return id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public String GetPasswordByName(String name) {

        try {
            Connection con = dataSource.getConnection();
            String sql = "SELECT PASSWORD FROM CLIENT WHERE CLIENT_NAME = ?";

            PreparedStatement preparedStatement = con.prepareStatement(sql);

            // Set the parameter value
            preparedStatement.setString(1, name);

            ResultSet rs = preparedStatement.executeQuery();

            String member_password ="";

            if (rs.next()) {
                // Retrieve the result
                member_password = rs.getString("PASSWORD");
                //System.out.println(id);
            } /*else {
                System.out.println("User with ID " + name + " not found.");
            }*/

            // Close resources
            rs.close();
            preparedStatement.close();
            con.close();

            return member_password;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public String GetNameByName(String name) {

        try {
            Connection con = dataSource.getConnection();
            String sql = "SELECT CLIENT_NAME FROM CLIENT WHERE CLIENT_NAME = ?";

            PreparedStatement preparedStatement = con.prepareStatement(sql);

            // Set the parameter value
            preparedStatement.setString(1, name);

            ResultSet rs = preparedStatement.executeQuery();

            String medlems_nummer ="";

            if (rs.next()) {
                // Retrieve the result
                medlems_nummer = rs.getString("CLIENT_NAME");
                //System.out.println(medlems_nummer);
            } /*else {
                System.out.println("User with ID " + id + " not found.");
            }*/

            // Close resources
            rs.close();
            preparedStatement.close();
            con.close();

            return medlems_nummer;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void InsertMember(String name, String phoneNumber, String email, String password){

        try {
            Connection con = dataSource.getConnection();
            String sql = "INSERT INTO CLIENT (CLIENT_NAME, CLIENT_PHONE, CLIENT_EMAIL,PASSWORD ,CLIENT_DATE) VALUES (?,?,?,?,CURRENT_DATE)";

            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, phoneNumber);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);


            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Rows affected by INSERT: " + rowsAffected);
            preparedStatement.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void GetAllName() {

        String sql = "SELECT Member_name FROM Member ";

        try {
            Connection con = dataSource.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            getNames(rs);

            // Close resources
            rs.close();
            preparedStatement.close();
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void getNames( ResultSet rs) throws SQLException {

        while (rs.next()) {
            // Retrieve the result
            System.out.println(rs.getString("Member_name"));
        }
    }




    public static void main(String[] args) {


        DB databaseExample = new DB();
        //fixar en connection setURL, setUser och setPassword.
        //SetDB DBsource = new SetDB();
        // Set the DataSource on the DatabaseExample instance
        //databaseExample.setDataSource(DBsource.setDB());

        //skapa en ny db om den inte existerar.
        //databaseExample.CreateDB();
        //databaseExample.CreateTableMembers();
        //databaseExample.CreateTableCase();
        //databaseExample.CreateTableProduct();
        //databaseExample.CreateTableCaseType();

        // Now, you can call fetchData
        //databaseExample.fetchData();
        //databaseExample.getIdByPhonNum(700389406);
        //databaseExample.getIdByEmail("jojo2109@student.miun.se");
        //databaseExample.GetIdByName("f");
        //databaseExample.getNameById(1);
//måste fixas        databaseExample.InsertMember("jag",20,1234567,"jagj2723@gmail.com");
        //databaseExample.GetAllName();
    }
}