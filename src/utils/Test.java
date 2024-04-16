package Utils;

import java.sql.Connection;

public class Test {
    public static void main(String[] args) {
        DataSource dataSource1=DataSource.getInstance();
        DataSource dataSource2=DataSource.getInstance();
        System.out.println(dataSource1);
        System.out.println(dataSource2);

        Connection con1=DataSource.getInstance().getCon();
        Connection con2=DataSource.getInstance().getCon();

        System.out.println(con1);
        System.out.println(con2);
    }
}
