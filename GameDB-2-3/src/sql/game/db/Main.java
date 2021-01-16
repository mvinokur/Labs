package sql.game.db;


import java.sql.SQLException;
import sql.game.games.*;
import sql.game.platform.*;

public class Main {

    public static void main(String[] args) throws SQLException{
        GameDB base = new GameDB();
        base.connect();
        PlatformTable b_tab = base.getPlatformTable();
        GameTable s_tab = base.getGameTable();
        for(Game item : s_tab.selectAll()){
            System.out.println("("+item.platform_id+") "+item.title+"  "+item.price+"  "+item.release_date);
        }
        System.out.println();
        for(Game item : s_tab.selectPlatform("2")){
            System.out.println("("+item.platform_id+") "+item.title+"  "+item.price+"  "+item.release_date);
        }
        System.out.println();
        for(Game item : s_tab.selectPrice(60, 150)){
            System.out.println("("+item.platform_id+") "+item.title+"  "+item.price+"  "+item.release_date);
        }
        System.out.println();
        base.joinSelect();
    }

}


