var fs = require("fs")
var http = require("http");
const neo4j = require('neo4j-driver');

const driver = neo4j.driver("bolt://localhost", neo4j.auth.basic("neo4j", "pass"));
const session = driver.session();

var handle_GET = function (request, response) {
    switch (request.url) {
        case "/":
            fs.readFile("./db7.html", function (err, content) {
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

    var data = '';
    request.on('data', function (chunk) {
        data += chunk;
    });
    request.on('end', function () {
        var filters = JSON.parse(data);
        var db_data = {};
        console.log(filters)
        session.run("MATCH (game:gamename)-[:MENTIONED_CHARACTERS]->(device) WHERE "+
        "game.gamename CONTAINS $title AND game.price>=$from AND game.price<=$to "+
        "RETURN game, device;",
        {
            title: filters.gamename,          
            from: parseInt(filters.price_from),
            to: parseInt(filters.price_to)
        }).then(res => {
            var table = [];
            res.records.forEach(rec => {
                var row = {};
                row.gamename = rec.get('game').properties.gamename;
                row.platform = rec.get('game').properties.platform;
                row.price = rec.get('game').properties.price;
                row.device_title = rec.get('device').properties.title;
                row.release_date = rec.get('game').properties.release_date;
                table.push(row);
            });
            db_data.table = table;
            console.log(db_data)
            response.end(JSON.stringify(db_data));
        }).catch(err => {
            console.error(err);
        });
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

session.run("MATCH (n) RETURN n").then(res => {
    if (res.records.length == 0) {
        session.run(
            "CREATE (device1:device $device1) " +
            "CREATE (device2:device $device2) " +
            "CREATE (device3:device $device3) " +
            "CREATE (game1:gamename $game1) " +
            "CREATE (game2:gamename $game2) " +
            "CREATE (game3:gamename $game3) " +
            "CREATE (game4:gamename $game4) " +
            "CREATE (game5:gamename $game5) " +
            "CREATE (game6:gamename $game6) " +
            "CREATE (game7:gamename $game7) " +
            "CREATE (game8:gamename $game8) " +
            "CREATE (game9:gamename $game9) " +
            "CREATE (game10:gamename $game10) " +
            "CREATE (game11:gamename $game11) " +
            "CREATE (game12:gamename $game12) " +
            "CREATE (game13:gamename $game13) " +
            "CREATE (game14:gamename $game14) " +
            "CREATE (game15:gamename $game15) " +
            "CREATE (game16:gamename $game16) " +
            "CREATE (game17:gamename $game17) " +
            "CREATE (game18:gamename $game18) " +
            "CREATE (game19:gamename $game19) " +
            "CREATE (game20:gamename $game20) " +
            "CREATE" +
            "(game1)-[:MENTIONED_CHARACTERS {chars: []}]->(device1)," +
            "(game2)-[:MENTIONED_CHARACTERS {chars: []}]->(device1)," +
            "(game3)-[:MENTIONED_CHARACTERS {chars: []}]->(device1)," +
            "(game4)-[:MENTIONED_CHARACTERS {chars: []}]->(device1)," +
            "(game5)-[:MENTIONED_CHARACTERS {chars: []}]->(device1)," +
            "(game6)-[:MENTIONED_CHARACTERS {chars: []}]->(device1)," +
            "(game7)-[:MENTIONED_CHARACTERS {chars: []}]->(device1)," +
            "(game8)-[:MENTIONED_CHARACTERS {chars: []}]->(device1)," +
            "(game9)-[:MENTIONED_CHARACTERS {chars: []}]->(device1)," +
            "(game10)-[:MENTIONED_CHARACTERS {chars: []}]->(device2)," +
            "(game11)-[:MENTIONED_CHARACTERS {chars: []}]->(device3)," +
            "(game12)-[:MENTIONED_CHARACTERS {chars: []}]->(device3)," +
            "(game13)-[:MENTIONED_CHARACTERS {chars: []}]->(device1)," +
            "(game14)-[:MENTIONED_CHARACTERS {chars: []}]->(device2)," +
            "(game15)-[:MENTIONED_CHARACTERS {chars: []}]->(device2)," +
            "(game16)-[:MENTIONED_CHARACTERS {chars: []}]->(device1)," +
            "(game17)-[:MENTIONED_CHARACTERS {chars: []}]->(device1)," +
            "(game18)-[:MENTIONED_CHARACTERS {chars: []}]->(device1)," +
            "(game19)-[:MENTIONED_CHARACTERS {chars: []}]->(device1)," +
            "(game20)-[:MENTIONED_CHARACTERS {chars: []}]->(device1);",
            {
                device1: { title: "PC", company: "null" },
                device2: { title: "Xbox", company: "Microsoft" },
                device3: { title: "PlayStation", company: "Sony" },
                game1: {
                    gamename: "Fallout",
                    platform: "Bethesda Launcher", price: 35, release_date: 1994
                },
                game2: {
                    gamename: "Fallout 2",
                    platform: "Bethesda Launcher", price: 30, release_date: 1996
                },
                game3: {
                    gamename: "Fallout Nevada",
                    platform: "non", price: 0, release_date: 2003
                },
                game4: {
                    gamename: "Fallout New Vegas",
                    platform: "Steam", price: 40, release_date: 2009
                },
                game5: {
                    gamename: "Halo Combat Evolved",
                    platform: "Xbox Live", price: 60, release_date: 2003
                },
                game6: {
                    gamename: "Halo 2",
                    platform: "Xbox Live", price: 60, release_date: 2005
                },
                game7: {
                    gamename: "Dota 2",
                    platform: "Steam", price: 0, release_date: 2011
                },
                game8: {
                    gamename: "League of legends",
                    platform: "RIOT Launcher", price: 0, release_date: 2009
                },
                game9: {
                    gamename: "Counter Strike - Condition Zero",
                    platform: "Steam", price: 25, release_date: 2004
                },
                game10: {
                    gamename: "Call of duty 3",
                    platform: "Xbox Live", price: 45, release_date: 2005
                },
                game11: {
                    gamename: "Horizon Zero Dawn",
                    platform: "PS Network", price: 60, release_date: 2017
                },
                game12: {
                    gamename: "The last of Us",
                    platform: "PS Network", price: 60, release_date: 2013
                },
                game13: {
                    gamename: "Fallout 76",
                    platform: "Bethesda Launcher", price: 50, release_date: 2018
                },
                game14: {
                    gamename: "Halo 5",
                    platform: "Xbox Live", price: 60, release_date: 2015
                },
                game15: {
                    gamename: "Stubbs the Zombie",
                    platform: "Xbox Live", price: 45, release_date: 2004
                },
                game16: {
                    gamename: "Battlefield 1942",
                    platform: "Retail", price: 55, release_date: 2002
                },
                game17: {
                    gamename: "Team Fortress 2",
                    platform: "Steam", price: 5, release_date: 2008
                },
                game18: {
                    gamename: "Half-Life",
                    platform: "Retail", price: 45, release_date: 1999
                },
                game19: {
                    gamename: "Half-Life 2",
                    platform: "Steam", price: 60, release_date: 2004
                },
                game20: {
                    gamename: "Half-life 3",
                    platform: "CyberPunkedSteam", price: 120, release_date: 2077
                }
            }).then(res => {
                process.emit('readyToServerCreate');
            }).catch(err => {
                console.error(err);
            });
    } else {
        process.emit('readyToServerCreate');
    }
}).catch(err => {
    console.error(err);
});

process.on('readyToServerCreate', () => {
    http.createServer(server_callback).listen(3000);
    console.log("Listen at http://localhost:3000/");
});
