package sql.game.db;

import sql.game.games.Game;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        GameDB db= new GameDB();
        db.connect();
        db.selectAll();

        System.out.println(" ");
        for(Game item : db.selectStore("Xbox Live")){
            System.out.println(item.platform.owner+" "+item.store+"  "+item.title+"  "+item.price+"$  "+item.release_date);
        }
        System.out.println(" ");
        for(Game item : db.selectPrice(60, 121)){
            System.out.println(item.platform.owner+" "+item.store+"  "+item.title+"  "+item.price+"$  "+item.release_date);
        }
        System.out.println(" ");
        for(Game item : db.selectOwner("Microsoft")){
            System.out.println(item.platform.owner+" "+item.store+"  "+item.title+"  "+item.price+"$  "+item.release_date);

        }
    }
}



