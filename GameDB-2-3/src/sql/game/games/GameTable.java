package sql.game.games;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameTable {
    private ResultSet result;
    private Statement st;
    private PreparedStatement insertSt;
    private PreparedStatement selPlatformSt;
    private PreparedStatement selPriceRangeSt;

    public GameTable(Connection connect) throws SQLException {
        result = null;
        insertSt = connect.prepareStatement("INSERT INTO GameTable VALUES(?, ?, ?, ?, ?);");
        selPlatformSt = connect.prepareStatement("SELECT * FROM GameTable WHERE platform_id=?");
        selPriceRangeSt = connect.prepareStatement("SELECT * FROM GameTable WHERE (price>=? AND price<=?);");
        st = connect.createStatement();
    }

    public void insert(Game s) throws SQLException {
        insertSt.setString(1, s.title);
        insertSt.setInt(2, s.release_date);
        insertSt.setInt(3, s.price);
        insertSt.setInt(4, s.platform_id);
        insertSt.setString(5, s.store);
        insertSt.execute();
    }

    public void insertData() throws SQLException {
        Game data_bridge = new Game();
        //1
        data_bridge.title = "Fallout";
        data_bridge.platform_id = 3;
        data_bridge.price = 40;
        data_bridge.release_date = 1994;
        data_bridge.store = "BethesdaStore";
        insert(data_bridge);
        //2
        data_bridge.title = "Fallout 2";
        data_bridge.platform_id = 3;
        data_bridge.price = 50;
        data_bridge.release_date = 1996;
        data_bridge.store = "BethesdaStore";
        insert(data_bridge);
        //3
        data_bridge.title = "Fallout Nevada";
        data_bridge.platform_id = 3;
        data_bridge.price = 0;
        data_bridge.release_date = 2001;
        data_bridge.store = "non";
        insert(data_bridge);
        //4
        data_bridge.title = "Fallout New Vegas";
        data_bridge.platform_id = 3;
        data_bridge.price = 40;
        data_bridge.release_date = 2009;
        data_bridge.store = "Steam";
        insert(data_bridge);
        //5
        data_bridge.title = "Halo Combat Evolved";
        data_bridge.platform_id = 2;
        data_bridge.price = 60;
        data_bridge.release_date = 2003;
        data_bridge.store = "Xbox Live";
        insert(data_bridge);
        //6
        data_bridge.title = "Halo 2";
        data_bridge.platform_id = 2;
        data_bridge.price = 60;
        data_bridge.release_date = 2005;
        data_bridge.store = "Xbox Live";
        insert(data_bridge);
        //7
        data_bridge.title = "Dota 2";
        data_bridge.platform_id = 3;
        data_bridge.price = 0;
        data_bridge.release_date = 2011;
        data_bridge.store = "Steam";
        insert(data_bridge);
        //8
        data_bridge.title = "League of legends";
        data_bridge.platform_id = 3;
        data_bridge.price = 0;
        data_bridge.release_date = 2009;
        data_bridge.store = "RIOT Launcher";
        insert(data_bridge);
        //9
        data_bridge.title = "Counter-Strike Condition Zero";
        data_bridge.platform_id = 3;
        data_bridge.price = 25;
        data_bridge.release_date = 2004;
        data_bridge.store = "Steam";
        insert(data_bridge);
        //10
        data_bridge.title = "Call of Duty 3";
        data_bridge.platform_id = 2;
        data_bridge.price = 45;
        data_bridge.release_date = 2005;
        data_bridge.store = "Xbox Live";
        insert(data_bridge);
        //11
        data_bridge.title = "Horizon Zero Dawn";
        data_bridge.platform_id = 1;
        data_bridge.price = 60;
        data_bridge.release_date = 2017;
        data_bridge.store = "PS Network";
        insert(data_bridge);
        //12
        data_bridge.title = "The last of Us";
        data_bridge.platform_id = 1;
        data_bridge.price = 60;
        data_bridge.release_date = 2013;
        data_bridge.store = "PS Network";
        insert(data_bridge);
        //13
        data_bridge.title = "Fallout 76";
        data_bridge.platform_id = 3;
        data_bridge.price = 50;
        data_bridge.release_date = 2018;
        data_bridge.store = "BethesdaStore";
        insert(data_bridge);
        //14
        data_bridge.title = "Halo 5";
        data_bridge.platform_id = 2;
        data_bridge.price = 60;
        data_bridge.release_date = 2015;
        data_bridge.store = "Xbox Live";
        insert(data_bridge);
        //15
        data_bridge.title = "Stubbs the Zombie";
        data_bridge.platform_id = 2;
        data_bridge.price = 45;
        data_bridge.release_date = 2004;
        data_bridge.store = "Xbox Live";
        insert(data_bridge);
        //16
        data_bridge.title = "Battlefield 1942";
        data_bridge.platform_id = 3;
        data_bridge.price = 55;
        data_bridge.release_date = 2002;
        data_bridge.store = "Retail Only";
        insert(data_bridge);
        //17
        data_bridge.title = "Team Fortress 2";
        data_bridge.platform_id = 3;
        data_bridge.price = 5;
        data_bridge.release_date = 2008;
        data_bridge.store = "Steam";
        insert(data_bridge);
        //18
        data_bridge.title = "Half-life";
        data_bridge.platform_id = 3;
        data_bridge.price = 45;
        data_bridge.release_date = 1999;
        data_bridge.store = "Retail Only";
        insert(data_bridge);
        //19
        data_bridge.title = "Half-Life 2";
        data_bridge.platform_id = 3;
        data_bridge.price = 60;
        data_bridge.release_date = 2004;
        data_bridge.store = "Steam";
        insert(data_bridge);
        //20
        data_bridge.title = "Half-Life 3";
        data_bridge.platform_id = 3;
        data_bridge.price = 120;
        data_bridge.release_date = 2077;
        data_bridge.store = "CyberPunkedSteam";
        insert(data_bridge);

    }

    public List<Game> selectAll() throws SQLException {
        List<Game> list = new ArrayList<Game>();
        result = st.executeQuery("SELECT * FROM GameTable");
        while (result.next()) {
            Game item = new Game();
            item.title = result.getString("title");
            item.platform_id = result.getInt("platform_id");
            item.price = result.getInt("price");
            item.release_date = result.getInt("release_date");
            item.store = result.getString("store");
            list.add(item);
        }
        return list;
    }

    public List<Game> selectPlatform(String type) throws SQLException {
        List<Game> list = new ArrayList<Game>();
        selPlatformSt.setString(1, type);
        result = selPlatformSt.executeQuery();
        while (result.next()) {
            Game item = new Game();
            item.title = result.getString("title");
            item.platform_id = result.getInt("platform_id");
            item.price = result.getInt("price");
            item.release_date = result.getInt("release_date");
            item.store = result.getString("store");
            list.add(item);
        }
        return list;
    }

    public List<Game> selectPrice(int from, int to) throws SQLException {
        List<Game> list = new ArrayList<Game>();
        selPriceRangeSt.setInt(1, from);
        selPriceRangeSt.setInt(2, to);
        result = selPriceRangeSt.executeQuery();
        while (result.next()) {
            Game item = new Game();
            item.title = result.getString("title");
            item.platform_id = result.getInt("platform_id");
            item.price = result.getInt("price");
            item.release_date = result.getInt("release_date");
            item.store = result.getString("store");
            list.add(item);
        }
        return list;
    }
}
