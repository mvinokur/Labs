package sql.game.games;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import sql.game.platform.Platform;

@DatabaseTable(tableName = "GameTableEx")
public class Game{
    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING)
    public String title;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING)
    public String store;

    @DatabaseField(canBeNull = false, dataType = DataType.INTEGER)
    public int release_date;

    @DatabaseField(canBeNull = false, dataType = DataType.INTEGER)
    public int price;

    @DatabaseField(foreign = true, foreignColumnName = "platform_id", foreignAutoRefresh = true, foreignAutoCreate = true)
    public Platform platform;
}