var fs = require("fs")
var http = require("http");
var mongodb = require("mongodb");
var escape = require("mongo-escape")

var client = mongodb.MongoClient("mongodb://localhost:27017", { useUnifiedTopology: true });

var handle_GET = function (request, response) {
    switch (request.url) {
        case "/":
            fs.readFile("./db6.html", function (err, content) {
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
        var collection = client.db('GameDB').collection("GameCollection");
        collection.find({
            game: { $regex: escape.escape(filters.game), $options: 'i' },            
            price: { $gte: parseInt(filters.price_from), $lte: parseInt(filters.price_to) }
        }).toArray(function (err, res) {
            if (err) {
                console.error(err);
                return;
            }
            db_data.table = res;
            response.end(JSON.stringify(db_data));
        })
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

client.connect(function (err, client) {
    if (err) {
        console.error(err);
        return;
    }
    var db = client.db('GameDB');
    db.collections(function (err, cols) {
        if (err) {
            console.error(err);
            return;
        }
        if (cols.length == 0) {
            var collection = db.collection("GameCollection");
            var games = [
                {
                    game: "Fallout",
                    platform: "Steam", price: 24, release_date: 1994
                },   
                {
                    game: "Fallout 2",
                    platform: "Steam", price: 29, release_date: 1996
                },
                {
                    game: "Fallout Nevada",
                    platform: "non", price: 0, release_date: 2003
                },
                {
                    game: "Fallout New Vegas",
                    platform: "Steam", price: 40, release_date: 2009
                },
                {
                    game: "Halo Combat Evolved",
                    platform: "Xbox Live", price: 60, release_date: 2003
                },
                {
                    game: "Halo 2",
                    platform: "Xbox Live", price: 60, release_date: 2005
                },
                {
                    game: "Dota 2",
                    platform: "Steam", price: 0, release_date: 2011
                },
                {
                    game: "League of legends",
                    platform: "RIOT Launcher", price: 0, release_date: 2009
                },
                {
                    game: "Counter Strike - Condition Zero",
                    platform: "Steam", price: 25, release_date: 2005
                },
                {
                    game: "Call of duty 3",
                    platform: "Xbox Live", price: 45, release_date: 2006
                },
                {
                    game: "Horizon Zero Dawn",
                    platform: "PS Network", price: 60, release_date: 2017
                },
                {
                    game: "The last of Us",
                    platform: "PS Network", price: 60, release_date: 2013
                },
                {
                    game: "Fallout 76",
                    platform: "Bethesda Launcher", price: 50, release_date: 2018
                },
                {
                    game: "Halo 5",
                    platform: "Xbox Live", price: 60, release_date: 2015
                },
                {
                    game: "Stubbs the Zombie",
                    platform: "Xbox Live", price: 45, release_date: 2004
                },
                {
                    game: "Battlefield 1942",
                    platform: "Retail", price: 55, release_date: 2003
                },
                {
                    game: "Team Fortress 2",
                    platform: "Steam", price: 5, release_date: 2008
                },
                {
                    game: "Half-Life",
                    platform: "Retail", price: 45, release_date: 1999
                },
                {
                    game: "Half-Life 2",
                    platform: "Steam", price: 60, release_date: 2004
                },
                {
                    game: "Half-life 3",
                    platform: "Steam", price: 120, release_date: 2077
                }                             
            ]
            collection.insertMany(games, function (err, res) {
                if (err) {
                    console.error(err);
                    return;
                }
            })
        }
        http.createServer(server_callback).listen(3000);
        console.log("Listen at http://localhost:3000/");
    });
});
