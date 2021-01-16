package sql.game.db;
import java.sql.*;
import sql.game.games.GameTable;
import sql.game.platform.PlatformTable;

public class GameDB {

    private Connection connect;
    private Statement st;

    public GameDB() {
        st = null;
        connect= null;
    }

    public void connect() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        connect("jdbc:sqlite:C:\\Users\\super\\Desktop\\Labs\\GameDB-2-3\\db\\GameDB.db");
    }

    public void connect(String filename) throws SQLException {
        connect= DriverManager.getConnection(filename);
    }

    public GameTable getGameTable() throws SQLException{
        GameTable table = null;
        st = connect.createStatement();
        ResultSet result = st.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='GameTable';");
        if(!result.next()){
            ResultSet result2 = st.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='PlatformTable';");
            if(!result2.next()){
                getPlatformTable();
            }
            System.out.println("Create Table GameTable");
            st.execute("CREATE TABLE GameTable (title char(100) NOT NULL, release_date integer NOT NULL, price integer NOT NULL, platform_id integer, store char(20), FOREIGN KEY (platform_id) REFERENCES PlatformTable(platform_id));");
            table = new GameTable(connect);
            table.insertData();
        }else{
            table = new GameTable(connect);
        }
        return table;
    }

    public PlatformTable getPlatformTable() throws SQLException{
        PlatformTable table = null;
        st = connect.createStatement();
        ResultSet result = st.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='PlatformTable';");
        if(!result.next()){
            System.out.println("Create Table PlatformTable");
            st.execute("CREATE TABLE PlatformTable (platform_id integer PRIMARY KEY, device char(20) NOT NULL, owner char(20) NOT NULL);");
            table = new PlatformTable(connect);
            table.insertData();
        }else{
            table = new PlatformTable(connect);
        }
        return table;
    }

    public void joinSelect() throws SQLException{
        ResultSet res = null;
        st = connect.createStatement();
        res = st.executeQuery("SELECT * FROM GameTable, PlatformTable WHERE GameTable.platform_id=PlatformTable.platform_id");
        while (res.next()){
            System.out.println(res.getString("title")+" | Price "+res.getString("price")+"$ |"+" Release date: "+res.getInt("release_date")+" | "+res.getString("device")+" | "+res.getString("store")+" |" );
        }
    }
}

