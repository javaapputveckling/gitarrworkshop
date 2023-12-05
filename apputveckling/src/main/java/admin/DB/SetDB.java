package admin.DB;

import com.mysql.cj.jdbc.MysqlDataSource;

public class SetDB {
    public MysqlDataSource setDB() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setURL("jdbc:mysql://localhost:3306/Projectdb");
        mysqlDataSource.setUser("root2");
        mysqlDataSource.setPassword("1234");
        return mysqlDataSource;
    }
}
