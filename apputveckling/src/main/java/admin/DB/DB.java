package admin.DB;

import jakarta.annotation.Resource;

import javax.sql.DataSource;
import java.sql.*;

public class DB {


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

    public void CreateTableMembers(){
        try (Connection con = dataSource.getConnection()) {
            String sql = "CREATE TABLE IF NOT EXISTS Member("
                    +"Member_id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "Member_name VARCHAR(255),"
                    + "Member_date datetime,"
                    + "Member_phone int,"
                    + "Member_email VARCHAR(255))";
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
            String sql = "CREATE TABLE IF NOT EXISTS Product("
                    +"Product_id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "Product_price int,"
                    + " Product_amount int,"
                    + "Product_description VARCHAR(255),"
                    + "Product_case VARCHAR(255),"
                    + "Product_image VARCHAR(255))";
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

    public void InsertMember(String name, Date date, int phoneNumber, String email){

        try {
            Connection con = dataSource.getConnection();
            String sql = "INSERT INTO Member (Member_name, Member_date, Member_phone,Member_email) VALUES (?, ?,?,?)";

            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, name);
            preparedStatement.setDate(2, date);
            preparedStatement.setInt(3, phoneNumber);
            preparedStatement.setString(4, email);


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
        SetDB DBsource = new SetDB();
        // Set the DataSource on the DatabaseExample instance
        databaseExample.setDataSource(DBsource.setDB());

        //skapa en ny db om den inte existerar.
        databaseExample.CreateDB();
        databaseExample.CreateTableMembers();
        databaseExample.CreateTableCase();
        databaseExample.CreateTableProduct();
        databaseExample.CreateTableCaseType();

        // Now, you can call fetchData
        //databaseExample.fetchData();
        //databaseExample.getIdByPhonNum(700389406);
        //databaseExample.getIdByEmail("jojo2109@student.miun.se");
        //databaseExample.getNameById(1);
//måste fixas        databaseExample.InsertMember("jag",20,1234567,"jagj2723@gmail.com");
        //databaseExample.GetAllName();
    }
}