import psycopg2

from film import createFromDb

hostname = 'localhost'
username = 'postgres'
password = 'test'
database = 'filmweb'


# Simple routine to run a query on a database and print the results:
def doQuery(conn, query):
    cur = conn.cursor()
    print 'executing query %s' % query
    cur.execute(query)
    return cur


def insertIntoFilmsUrls(conn, url):
    query = "INSERT INTO films_urls(url) VALUES ('%s')" % url;
    doQuery(conn, query)
    conn.commit()


def saveFilmInfo(conn, film):
    actors = "'{ \"" + '\",\"'.join(film.actors) + "\"}'"
    actorsStars = "'{ " + ','.join(str(x) for x in film.actorsStars) + "}'"
    query = "INSERT INTO public.films(url, title, votes, peopleWantToSee, stars, director, actors, actorsStars, actorsStarsAvg) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s)" \
            % (
            "'" + film.url + "'", "'" + film.title + "'", str(film.votes), str(film.peopleWantToSee), str(film.stars),
            "'" + film.director + "'", actors, actorsStars, str(film.actorsStarsAvg))
    doQuery(conn, query)
    conn.commit()


def filmServerFunc(url):
    con = getConn()
    print "saving url in db: " + url
    insertIntoFilmsUrls(con, url)
    con.close()


# return set of all films urls saved in db
def getAllFilmsUrls(conn):
    query = 'SELECT url from films_urls'
    cursor = doQuery(conn, query)
    tuples = cursor.fetchall()
    result = set()
    for film in tuples:
        result.add(film[0])
    print result
    return result


def getDownloadedFilmsUrls(conn):
    query = 'SELECT url from films'
    cursor = doQuery(conn, query)
    tuples = cursor.fetchall()
    result = set()
    for film in tuples:
        result.add(film[0])
    print result
    return result

def getAllFilms(conn):
    query = 'SELECT * from films'
    cursor = doQuery(conn, query)
    tuples = cursor.fetchall()
    result = set()
    for film in tuples:
        result.add(createFromDb(film))
    return result


def getConn():
    return psycopg2.connect(host=hostname, user=username, password=password, dbname=database)

# insertIntoFilmsUrls( getConn(), 'film/Gwiezdne+wojny%3A+ostatni+Jedi-2017-671049' )
# getAllFilmsUrls(getConn())
# myConnection.close()
