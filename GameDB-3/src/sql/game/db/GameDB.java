package sql.game.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import sql.game.games.Game;
import sql.game.platform.Platform;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.logger.LocalLog;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class GameDB {

    private ConnectionSource connectionSource;
    private Dao<Game, Integer> GamesDAO;
    private Dao<Platform, Integer> PlatformsDAO;

    public GameDB() throws SQLException {
        try {
            //Loading the sqlite drivers
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            //Should never happen
            throw new SQLException(e);
        }
        System.setProperty(LocalLog.LOCAL_LOG_FILE_PROPERTY, "db.log");
    }

    public void connect() throws SQLException {
        connect("jdbc:sqlite:.\\db\\GameDB.db");
    }

    public void connect(String filename) throws SQLException {
        connectionSource = new JdbcConnectionSource(filename);

        PlatformsDAO = DaoManager.createDao(connectionSource, Platform.class);
        TableUtils.createTableIfNotExists(connectionSource, Platform.class);
        GamesDAO = DaoManager.createDao(connectionSource, Game.class);
        if(!GamesDAO.isTableExists()) {
            TableUtils.createTable(connectionSource, Game.class);
            insertGameData();
        }
    }

    public List<Game> selectAll() throws SQLException{
        List<Game> list = new ArrayList<Game>();
        QueryBuilder<Game, Integer> GameQB = GamesDAO.queryBuilder();
        QueryBuilder<Platform, Integer> PlatformQB = PlatformsDAO.queryBuilder();
        GameQB.join(PlatformQB);
        PreparedQuery<Game> preparedQuery= GameQB.prepare();
        CloseableIterator<Game> it = GamesDAO.iterator(preparedQuery);
        while (it.hasNext()){
            Game data_bridge = it.current();
            System.out.println(data_bridge.platform.owner + "| " + data_bridge.platform.device + " | " + data_bridge.title + " | " + data_bridge.release_date + " | " + data_bridge.price + " | " + data_bridge.store);
            it.moveToNext();
            list.add(data_bridge);
        }
        return list;
    }

    public List<Game> selectStore(String store) throws SQLException {
        List<Game> list = new ArrayList<Game>();
        QueryBuilder<Game, Integer> GameQB = GamesDAO.queryBuilder();
        GameQB.where().like("store", store);
        CloseableIterator<Game> it = GamesDAO.iterator(GameQB.prepare());
        while (it.hasNext()){
            Game data_bridge = it.current();
            list.add(data_bridge);
            it.moveToNext();
        }
        return list;
    }

    public List<Game> selectOwner(String owner) throws SQLException {
        List<Game> list = new ArrayList<Game>();
        QueryBuilder<Game, Integer> GameQB = GamesDAO.queryBuilder();
        QueryBuilder<Platform, Integer> PlatformQB = PlatformsDAO.queryBuilder();
        PlatformQB.where().like("owner", owner);
        GameQB.join(PlatformQB);
        CloseableIterator<Game> it = GamesDAO.iterator(GameQB.prepare());
        while (it.hasNext()){
            Game data_bridge = it.current();
            list.add(data_bridge);
            it.moveToNext();
        }
        return list;
    }

    public List<Game> selectPrice(int from, int to) throws SQLException {
        List<Game> list = new ArrayList<Game>();
        QueryBuilder<Game, Integer> GameQB = GamesDAO.queryBuilder();
        GameQB.where().between("price", from, to);
        CloseableIterator<Game> it = GamesDAO.iterator(GameQB.prepare());
        while (it.hasNext()){
            Game data_bridge = it.current();
            list.add(data_bridge);
            it.moveToNext();
        }
        return list;
    }

    private void insertGameData() throws SQLException {
        Platform Platform1 = new Platform();
        Platform Platform2 = new Platform();
        Platform Platform3 = new Platform();
        Platform1.device = "PS";
        Platform1.owner = "Sony";
        Platform2.device = "Xbox";
        Platform2.owner = "Microsoft";
        Platform3.device = "PC";
        Platform3.owner = "x86/x65";

        Game data_bridge = new Game();

        //1
        data_bridge.title = "Fallout";
        data_bridge.platform = Platform3;
        data_bridge.price = 40;
        data_bridge.release_date = 1994;
        data_bridge.store = "BethesdaStore";
        GamesDAO.create(data_bridge);
        //2
        data_bridge.title = "Fallout 2";
        data_bridge.platform = Platform3;
        data_bridge.price = 50;
        data_bridge.release_date = 1996;
        data_bridge.store = "BethesdaStore";
        GamesDAO.create(data_bridge);
        //3
        data_bridge.title = "Fallout Nevada";
        data_bridge.platform = Platform3;
        data_bridge.price = 0;
        data_bridge.release_date = 2001;
        data_bridge.store = "Open-source";
        GamesDAO.create(data_bridge);
        //4
        data_bridge.title = "Fallout New Vegas";
        data_bridge.platform = Platform3;
        data_bridge.price = 40;
        data_bridge.release_date = 2009;
        data_bridge.store = "Steam";
        GamesDAO.create(data_bridge);
        //5
        data_bridge.title = "Halo Combat Evolved";
        data_bridge.platform = Platform2;
        data_bridge.price = 60;
        data_bridge.release_date = 2003;
        data_bridge.store = "Xbox Live";
        GamesDAO.create(data_bridge);
        //6
        data_bridge.title = "Halo 2";
        data_bridge.platform = Platform2;
        data_bridge.price = 60;
        data_bridge.release_date = 2005;
        data_bridge.store = "Xbox Live";
        GamesDAO.create(data_bridge);
        //7
        data_bridge.title = "Dota 2";
        data_bridge.platform = Platform3;
        data_bridge.price = 0;
        data_bridge.release_date = 2011;
        data_bridge.store = "Steam";
        GamesDAO.create(data_bridge);
        //8
        data_bridge.title = "League of legends";
        data_bridge.platform = Platform3;
        data_bridge.price = 0;
        data_bridge.release_date = 2009;
        data_bridge.store = "RIOT Launcher";
        GamesDAO.create(data_bridge);
        //9
        data_bridge.title = "Counter-Strike Condition Zero";
        data_bridge.platform = Platform3;
        data_bridge.price = 25;
        data_bridge.release_date = 2004;
        data_bridge.store = "Steam";
        GamesDAO.create(data_bridge);
        //10
        data_bridge.title = "Call of Duty 3";
        data_bridge.platform = Platform2;
        data_bridge.price = 45;
        data_bridge.release_date = 2005;
        data_bridge.store = "Xbox Live";
        GamesDAO.create(data_bridge);
        //11
        data_bridge.title = "Horizon Zero Dawn";
        data_bridge.platform = Platform1;
        data_bridge.price = 60;
        data_bridge.release_date = 2017;
        data_bridge.store = "PS Network";
        GamesDAO.create(data_bridge);
        //12
        data_bridge.title = "The last of Us";
        data_bridge.platform = Platform1;
        data_bridge.price = 60;
        data_bridge.release_date = 2013;
        data_bridge.store = "PS Network";
        GamesDAO.create(data_bridge);
        //13
        data_bridge.title = "Fallout 76";
        data_bridge.platform = Platform3;
        data_bridge.price = 50;
        data_bridge.release_date = 2018;
        data_bridge.store = "BethesdaStore";
        GamesDAO.create(data_bridge);
        //14
        data_bridge.title = "Halo 5";
        data_bridge.platform = Platform2;
        data_bridge.price = 60;
        data_bridge.release_date = 2015;
        data_bridge.store = "Xbox Live";
        GamesDAO.create(data_bridge);
        //15
        data_bridge.title = "Stubbs the Zombie";
        data_bridge.platform = Platform2;
        data_bridge.price = 45;
        data_bridge.release_date = 2004;
        data_bridge.store = "Xbox Live";
        GamesDAO.create(data_bridge);
        //16
        data_bridge.title = "Battlefield 1942";
        data_bridge.platform = Platform3;
        data_bridge.price = 55;
        data_bridge.release_date = 2002;
        data_bridge.store = "Retail Only";
        GamesDAO.create(data_bridge);
        //17
        data_bridge.title = "Team Fortress 2";
        data_bridge.platform = Platform3;
        data_bridge.price = 5;
        data_bridge.release_date = 2008;
        data_bridge.store = "Steam";
        GamesDAO.create(data_bridge);
        //18
        data_bridge.title = "Half-life";
        data_bridge.platform = Platform3;
        data_bridge.price = 45;
        data_bridge.release_date = 1999;
        data_bridge.store = "Retail Only";
        GamesDAO.create(data_bridge);
        //19
        data_bridge.title = "Half-Life 2";
        data_bridge.platform = Platform3;
        data_bridge.price = 60;
        data_bridge.release_date = 2004;
        data_bridge.store = "Steam";
        GamesDAO.create(data_bridge);
        //20
        data_bridge.title = "Half-Life 3";
        data_bridge.platform = Platform3;
        data_bridge.price = 120;
        data_bridge.release_date = 2077;
        data_bridge.store = "CyberPunkedSteam";

    }

}
