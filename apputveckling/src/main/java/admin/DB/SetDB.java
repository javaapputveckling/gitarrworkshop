package admin.DB;

import com.mysql.cj.jdbc.MysqlDataSource;

public class SetDB {
    public MysqlDataSource setDB() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setURL("jdbc:mysql://localhost:3306/ProjectDB");
        mysqlDataSource.setUser("root");
        mysqlDataSource.setPassword("malek123");

        return mysqlDataSource;
    }
}
