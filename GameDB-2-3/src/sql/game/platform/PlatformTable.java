package sql.game.platform;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlatformTable {
    private ResultSet result;
    private Statement st;
    private PreparedStatement insertSt;

    public PlatformTable(Connection connect) throws SQLException {
        result = null;
        insertSt = connect.prepareStatement("INSERT INTO PlatformTable (device, owner) VALUES(?, ?);");
        st = connect.createStatement();
    }
    public void insert(Platform b) throws SQLException {
        insertSt.setString(1, b.device);
        insertSt.setString(2, b.owner);
        insertSt.execute();
    }
    //platform_id = 1 = PS
    //platform_id = 2 = Xbox
    //platform_id = 3 = PC
    public void insertData() throws SQLException {
        Platform temp = new Platform();
        temp.device = "PlayStation";
        temp.owner = "Sony";
        insert(temp);

        temp.device = "Xbox";
        temp.owner = "Microsoft";
        insert(temp);

        temp.device = "PC";
        temp.owner = "-";
        insert(temp);
    }

    public List<Platform> selectAll() throws SQLException {
        List<Platform> list = new ArrayList<Platform>();
        result = st.executeQuery("SELECT * FROM PlatformTable");
        while (result.next()) {
            Platform item = new Platform();
            item.device = result.getString("device");
            item.owner = result.getString("owner");
            item.platform_id = result.getInt("platform_id");
            list.add(item);
        }
        return list;
    }
}
