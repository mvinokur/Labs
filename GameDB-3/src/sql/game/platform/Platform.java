package sql.game.platform;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "PlatformTableEx")
public class Platform{
    @DatabaseField(generatedId = true)
    public int platform_id;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING)
    public String device;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING)
    public String owner;

}

