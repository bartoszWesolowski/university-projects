# encoding=utf8  
import sys
import db
import filmweb
from film import Film
import predictor
from categorization import FilmCategorization
from film import createFromDb

reload(sys)
sys.setdefaultencoding('utf8')

#pobiera i zapisuje urle do filmow (pomija te juz zapisane w bazie) - bazuje danych zapisanych w tabeli films_urls
def downloadAndSaveFilmHrefs():
    conn = db.getConn()
    alreadySavedUrls = db.getAllFilmsUrls(conn)
    filmweb.downloadFimsFromSearch(int(raw_input('Number of pages to check: ')), int(raw_input('first page: ')), set(alreadySavedUrls), db.filmServerFunc)
    conn.close()

#Przechodzi po wszystkich zapisanych urlach i dla kazdego pobiera dane o filmie (z htmla), dane zapisuje do tabeli films
#nie dwa razy tych samych filmów
def downloadAndSaveFilmInfo():
    conn = db.getConn()
    all = db.getAllFilmsUrls(conn)
    downloaded = db.getDownloadedFilmsUrls(conn)
    print len(all)
    print len(downloaded)
    for url in all - downloaded:
        soup = filmweb.getFilmSoup(url)
        f = Film(url, soup)
        db.saveFilmInfo(conn, f)
    conn.close()

def getAllFilms():
    conn = db.getConn()
    films = db.getAllFilms(conn)
    conn.close()
    return films

def predicatorTest(random=False, dividor=10):
    films = getAllFilms()
    toLearn = []
    toPredict = []
    for film in films:
        if film.id %  dividor == 0:
            toPredict.append(film)
        else:
            toLearn.append(film)

    predicatorObj = predictor.Predictor(toLearn, random)
    predictionResult = predictor.PredictionResult(predicatorObj, toPredict)
    print "\nLearning on " + str(len(toLearn)) + " films"
    print "Tested on " + str(len(toPredict)) + " films"
    print "Average difference: " + str(predictionResult.avgDiff)
    return predictionResult, predicatorObj, toPredict

print "example usage: \n"
print "import e"
print "result, predictor, films  = e.predicatorTest(False, 10)"
print "film = films[0]"
print "test = predictor.predict(film)"
print "test.diff"
#prediction = predicatorTest()
#print "Avg prediction diff: " + str(prediction.avgDiff)

# For dowloading urls of films
# con = getConn()
# visitedPages = getAllFilmsUrls(con)
# print "Already saved films: " + str(len(visitedPages))
# print "search for films: "
# downloadFimsFromSearch(set(visitedPages), int(raw_input('Number of pages to check: ')), int(raw_input('first page: ')), filmServerFunc)
# con.close()


films = getAllFilms()
category = FilmCategorization(films)
stars = category.map.keys()
stars.sort()
print stars
category.printIt()