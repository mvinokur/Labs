var cassandra = require("cassandra-driver");
var fs = require("fs")
var http = require("http");
var client = new cassandra.Client({ contactPoints: ['127.0.0.1'], keyspace: "system", localDataCenter: "datacenter1" });

process.on('unhandledRejection', error => {
    // Will print "unhandledRejection err is not defined"
    console.log('unhandledRejection:', error.message);
});

client.execute("SELECT * FROM system_schema.keyspaces WHERE keyspace_name='games';", function (err, result) {
    if (err) {
        console.error(err);
        return;
    } else {
        if (result.rows.length != 0) {
            client.execute("USE games;");
            process.emit("readyToServerCreate");
            return;
        } else {
            client.execute("CREATE KEYSPACE games WITH REPLICATION={'class':'SimpleStrategy', 'replication_factor':1};", function (err, _res) {
                if (err) {
                    console.error(err);
                    return;
                }
                client.execute("USE games;");
                client.execute("CREATE TABLE GameDB (id double PRIMARY KEY, game TEXT, platform TEXT, price double, release_date double);", function (_err, _res) {
                    if (err) {
                        console.error(err);
                        return;
                    }
                    //fill here
                    client.execute("INSERT INTO GameDB (id, game, platform, price, release_date) VALUES(1, ?, ?, ?, ?);", ["Fallout", "Bethesda Laucnher", 35, 1994]);
                    client.execute("INSERT INTO GameDB (id, game, platform, price, release_date) VALUES(2, ?, ?, ?, ?);", ["Fallout 2", "Bethesda Laucnher", 45, 1996]);
                    client.execute("INSERT INTO GameDB (id, game, platform, price, release_date) VALUES(3, ?, ?, ?, ?);", ["Fallout Nevada", "non", 0, 2003]);
                    client.execute("INSERT INTO GameDB (id, game, platform, price, release_date) VALUES(4, ?, ?, ?, ?);", ["Fallout NEw Vegas", "steam", 40, 2009]);
                    client.execute("INSERT INTO GameDB (id, game, platform, price, release_date) VALUES(5, ?, ?, ?, ?);", ["Halo Combat Envolved", "Xbox Live", 60, 2003]);
                    client.execute("INSERT INTO GameDB (id, game, platform, price, release_date) VALUES(6, ?, ?, ?, ?);", ["Halo 2", "Xbox Live", 60, 2005]);
                    client.execute("INSERT INTO GameDB (id, game, platform, price, release_date) VALUES(7, ?, ?, ?, ?);", ["Dota 2", "Steam", 0, 2011]);
                    client.execute("INSERT INTO GameDB (id, game, platform, price, release_date) VALUES(8, ?, ?, ?, ?);", ["League of legends", "RIOT Launcher", 0, 2009]);
                    client.execute("INSERT INTO GameDB (id, game, platform, price, release_date) VALUES(9, ?, ?, ?, ?);", ["Counter Strike - Condition Zero", "Steam", 25, 2004]);
                    client.execute("INSERT INTO GameDB (id, game, platform, price, release_date) VALUES(10, ?, ?, ?, ?);", ["Call of Duty", "Xbox Live", 45, 2005]);
                    client.execute("INSERT INTO GameDB (id, game, platform, price, release_date) VALUES(11, ?, ?, ?, ?);", ["Horizon Zero Dawn", "PS Network", 60, 2017]);
                    client.execute("INSERT INTO GameDB (id, game, platform, price, release_date) VALUES(12, ?, ?, ?, ?);", ["The last of Us", "PS Netowrk", 60, 2013]);
                    client.execute("INSERT INTO GameDB (id, game, platform, price, release_date) VALUES(13, ?, ?, ?, ?);", ["Fallout 76", "Bethesda Laucnher", 50, 2018]);
                    client.execute("INSERT INTO GameDB (id, game, platform, price, release_date) VALUES(14, ?, ?, ?, ?);", ["Halo 5", "Xbox Live", 60, 2015]);
                    client.execute("INSERT INTO GameDB (id, game, platform, price, release_date) VALUES(15, ?, ?, ?, ?);", ["Stubbs the Zombie", "Xbox Live", 45, 2004]);
                    client.execute("INSERT INTO GameDB (id, game, platform, price, release_date) VALUES(16, ?, ?, ?, ?);", ["Battlefield 1942", "Retail", 55, 2002]);
                    client.execute("INSERT INTO GameDB (id, game, platform, price, release_date) VALUES(17, ?, ?, ?, ?);", ["Team Fortress 2", "Steam", 5, 2008]);
                    client.execute("INSERT INTO GameDB (id, game, platform, price, release_date) VALUES(18, ?, ?, ?, ?);", ["Half-Life", "Steam", 45, 1999]);
                    client.execute("INSERT INTO GameDB (id, game, platform, price, release_date) VALUES(19, ?, ?, ?, ?);", ["Half-Life 2", "Steam", 60, 2004]);
                    client.execute("INSERT INTO GameDB (id, game, platform, price, release_date) VALUES(20, ?, ?, ?, ?);", ["Half-Life 3", "CyberPunkedSteam", 120, 2077]);
                    client.execute("CREATE CUSTOM INDEX ON GameDB (game) USING 'org.apache.cassandra.index.sasi.SASIIndex' WITH OPTIONS = {'mode': 'CONTAINS', 'analyzer_class': 'org.apache.cassandra.index.sasi.analyzer.StandardAnalyzer', 'case_sensitive': 'false'};");
                    client.execute("CREATE CUSTOM INDEX ON GameDB (platform) USING 'org.apache.cassandra.index.sasi.SASIIndex' WITH OPTIONS = {'mode': 'CONTAINS', 'analyzer_class': 'org.apache.cassandra.index.sasi.analyzer.StandardAnalyzer', 'case_sensitive': 'false'};");
                    client.execute("CREATE INDEX ON GameDB (price);");
                    console.log("table GameDB created");
                    process.emit("readyToServerCreate");
                });
            });
        }
    }
});

process.on('readyToServerCreate', () => {
    http.createServer(server_callback).listen(3000);
    console.log("Listen at http://localhost:3000/");
});

var handle_GET = function (request, response) {
    switch (request.url) {
        case "/":
            fs.readFile("./db5.html", function (err, content) {
                if (!err) {
                    response.writeHead(200, { "Content-Type": "text/html; charset=utf-8" });
                    response.end(content, "utf-8")
                } else {
                    response.writeHead(500, { "Content-Type": "text/plain; charset=utf-8" });
                    response.end(err.message, "utf-8");
                    console.log(err);
                }
            });
            break;
        default:
            response.writeHead(404, { "Content-Type": "text/html; charset=utf-8" });
            response.end("<!DOCTYPE html>\n" +
                "<html>\n" +
                "   <head>\n" +
                "       <meta charset='utf-8'>\n" +
                "   </head>\n" +
                "   <body>\n" +
                "404, NOT FOUND: " + request.url +
                " \n</body>\n" +
                "</html>"
            );
    }
}

var handle_POST = function (request, response) {
    if (request.url != "/get_table") {
        response.writeHead(500, { "Content-Type": "text/plain; charset=utf-8" });
        response.end();
    }
    var parse = function (err, res) {
        if (err) {
            console.log(err);
            response.writeHead(404, { "Content-Type": "text/plain; charset=utf-8" });
            response.end();
        } else {
            response.writeHead(200, { "Content-Type": "application/json; charset=utf-8" });
            var db_data = {};
            db_data.table = res.rows;
            response.end(JSON.stringify(db_data));
        }
    }

    var data = '';
    request.on('data', function (chunk) {
        data += chunk;
    });
    request.on('end', function () {
        var filters = JSON.parse(data);
        if (filters.game != "")
            client.execute("SELECT * FROM GameDB WHERE " +
                "game LIKE ? AND " +
                "price>=? AND price<=? ALLOW FILTERING;", ['%'+filters.game+'%', parseInt(filters.price_from), parseInt(filters.price_to)], parse);
        if (filters.game == "")
            client.execute("SELECT * FROM GameDB WHERE " +
                "price>=? AND price<=? ALLOW FILTERING;", [parseInt(filters.price_from), parseInt(filters.price_to)], parse);
    });
}


var server_callback = function (request, response) {
    console.log("request to: " + request.url + " method: " + request.method)
    if (request.method == "GET") {
        handle_GET(request, response);
    } else {
        handle_POST(request, response);
    }
}
