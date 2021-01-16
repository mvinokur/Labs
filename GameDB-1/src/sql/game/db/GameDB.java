package sql.game.db;

import java.sql.*;

public class GameDB {
    public static final String DB_URL = "jdbc:h2:/c:/Users/super/IdeaProjects/GameDB/db/GameDB";
    public static final String DB_Driver = "org.h2.Driver";

    public static void main(String[] args){
        try{
            Class.forName(DB_Driver);
            Connection connection = DriverManager.getConnection(DB_URL);
            System.out.println("Соединение выполнено.");
            Statement st = connection.createStatement();

            try{
                st.execute("CREATE TABLE GameDB( " +
                        "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                        "title VARCHAR(255) NOT NULL," +
                        "price INT NOT NULL," +
                        "year VARCHAR(255) NOT NULL," +
                        "primaryStore VARCHAR(255) NOT NULL," +
                        ");");
                System.out.println("Таблица успешно создана.");
            }
            catch (SQLException e){
                e.printStackTrace();
                System.out.println("Проблема при создании таблицы.");
            }
            if(!st.executeQuery("SELECT * from GameDB").next()){
                st.execute("INSERT into GameDB values(1, 'Fallout', '10', '1994', 'Bethesda Launcher');");
                st.execute("INSERT into GameDB values(2, 'Fallout 2', '10', '1998', 'Bethesda Launcher');");
                st.execute("INSERT into GameDB values(3, 'Fallout Nevada', '20', '2007', 'non');");
                st.execute("INSERT into GameDB values(4, 'Fallout New Vegas', '20', '2009', 'Steam');");
                st.execute("INSERT into GameDB values(5, 'Halo Combat Evolved', '35', '2015', 'Xbox Live');");
                st.execute("INSERT into GameDB values(6, 'Halo 2', '40', '1994', 'Xbox Live');");
                st.execute("INSERT into GameDB values(7, 'Dota 2', '25', '2001', 'Steam');");
                st.execute("INSERT into GameDB values(8, 'League of Legends', '25', '2003', 'RIOT Launcher');");
                st.execute("INSERT into GameDB values(9, 'Counter-Strike Condition Zero', '25', '2005', 'Steam');");
                st.execute("INSERT into GameDB values(10, 'Call of Duty 3', '25', '2007', 'Xbox Live');");
                st.execute("INSERT into GameDB values(11, 'Horizon Zero Dawn', '30', '2008', 'PS Network');");
                st.execute("INSERT into GameDB values(12, 'The last of Us', '10', '2004', 'PS Network');");
                st.execute("INSERT into GameDB values(13, 'Fallout 76', '20', '2010', 'Bethesda Launcher');");
                st.execute("INSERT into GameDB values(14, 'Halo 5', '40', '2015', 'Xbox Live');");
                st.execute("INSERT into GameDB values(15, 'Stubbs the Zombie', '35', '2013', 'Xbox Live');");
                st.execute("INSERT into GameDB values(16, 'Battlefield 1942', '40', '2003', 'Retail');");
                st.execute("INSERT into GameDB values(17, 'Team Fortress 2', '40', '2014', 'Steam');");
                st.execute("INSERT into GameDB values(18, 'Half-Life', '40', '2014', 'Steam');");
                st.execute("INSERT into GameDB values(19, 'Half-Life 2', '40', '2005', 'Steam');");
                st.execute("INSERT into GameDB values(20, 'Half-Life 3', '40', '2077', 'Steam');");

            }

            //ResultSet rs = st.executeQuery("SELECT * from GameDB");
            ResultSet rs = st.executeQuery("SELECT * from GameDB WHERE (year > 2004)");

            while (rs.next()){
                System.out.println(
                        "Игра: " +rs.getString("title") + " | Цена: "+ rs.getString("price") + "$, Год выпуска: " + rs.getString("year") + ", Магазин: " + rs.getString("primaryStore")
                );
            }

            connection.close();
            System.out.println("Соединение было закрыто.");
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
            System.out.println("JDBC драйвер не найlен!");
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка SQL!");
        }
    }
}