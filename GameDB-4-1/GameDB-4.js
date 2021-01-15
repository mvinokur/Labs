var http = require("http");
var sqlite3 = require("sqlite3").verbose();
var db = new sqlite3.Database("./GameDB.db");

var server_callback = function (request, response) {
    response.writeHead(200, { "Content-Type": "text/html; charset=utf-8" });
    response.write("<!DOCTYPE html>\n" +
        "<html>\n" +
        " <head>\n" +
        " <meta charset='utf-8'>\n" +
        " </head>\n" +
        " <body>\n"
    );
    //db.all("SELECT * FROM GameDB_4", function(err, rows){
    db.all("SELECT * FROM GameDB_4 WHERE platform='Steam'", function (err, rows) {

    if (err) {
            response.write("<div style='font-size: 30px; color:red'>" + err + "</div>\n");
        } else {
            response.write("<table cellspacing=\"2\" border=\"1\" cellpadding=\"5\">\n");
            for (var i = 0; i < rows.length; i++) {
                response.write("<tr><td>" + rows[i].game + "</td><td align=\"center\">" + rows[i].price + "$</td><td align=\"center\">" + rows[i].release_date + "</td><td align=\"center\">" + rows[i].platform +"</td></tr>\n");
            }
            response.write("</table>");
        }
        response.end(
            " </body>\n" +
            "</html>\n"
        );
    });

}
db.all("SELECT name FROM sqlite_master WHERE type='table' AND name='GameDB_4';", function (err, rows) {
    if (err || rows.length == 0) {
        db.run("CREATE TABLE GameDB_4 (game TEXT NOT NULL, price integer NOT NULL, release_date TEXT NOT NULL, platform TEXT NOT NULL);", function (err) {
            //Заполнение
            var statement = db.prepare("INSERT INTO GameDB_4 VALUES(?, ?, ?, ?);")
            statement.run("Fallout", 35, "1994", "Bethesda Launcher");
            statement.run("Fallout 2", 45, "1996", "Bethesda Launcher");
            statement.run("Fallout Nevada", 0, "2003", "non");
            statement.run("Fallout New Vegas", 40, "2009", "Steam");
            statement.run("Halo Combat Evolved", 60, "2003", "Xbox Live");
            statement.run("Halo 2", 60, "2006", "Xbox Live");
            statement.run("Dota 2", 0, "2011", "Steam");
            statement.run("League of legends", 0, "2009", "RIOT Launcher");
            statement.run("Counter Strike - Condition Zero", 25, "2005", "Steam");
            statement.run("Call of Duty 3", 60, "2006", "Xbox Live");
            statement.run("Horizon Zero Dawn", 60, "2017", "PS Network");
            statement.run("The last of Us", 60, "2013", "PS Network");
            statement.run("Fallout 76", 50, "2018", "Bethesda Launcher");
            statement.run("Halo 5", 60, "2015", "Xbox Live");
            statement.run("Stubbs the Zombie", 45, "2004", "Xbox Live");
            statement.run("Battlefield 1942", 55, "2003", "Retail");
            statement.run("Team Fortress 2", 5, "2008", "Steam");
            statement.run("Half-Life", 45, "1999", "Retail");
            statement.run("Half-Life 2", 60, "2004", "Steam");
            statement.run("Half-Life 3", 120, "2077", "CyberPunkedSteam");
            statement.finalize();
        });
        console.log("db GameDB_4 created"); 
    }
    http.createServer(server_callback).listen(3000);
    console.log("Listen at http://localhost:3000/");
});