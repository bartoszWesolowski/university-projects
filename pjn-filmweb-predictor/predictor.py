import math
import random

from vowpalwabbit import pyvw

class Predictor(object):
    vw = pyvw.vw(quiet=True)
    random = False
    def __init__(self, filmsToLearn, random=False):
        self.random = random
        print "predicator"
        for film in filmsToLearn:
            s = toExample(film)
            print "learning on examle: " + s
            print ""
            ex = self.vw.example(s)
            self.vw.learn(ex)

    def predict(self, film):
        if self.random:
            print "Returning random"
            return FilmPredictionResult(film, 10.0 * random.random())

        s = toExample(film)
        ex = self.vw.example(s)
        print "prediction for examle: " + s + " with prediction " + str(self.vw.predict(ex))
        print ""
        return FilmPredictionResult(film, self.vw.predict(ex))

    def predictFilms(self, films):
        result = []
        for film in films:
            predictionResult = self.predict(film)
            result.append(predictionResult)
        return result


class FilmPredictionResult(object):
    predictedValue = 0
    film = ""
    diff = 0

    def __init__(self, film, predictedValue):
        #print "creating prediction result for film " + film.title + "(stars: " + str(film.stars) + ") with predicted stars: " + str(predictedValue)
        self.film = film
        self.predictedValue = predictedValue
        self.diff = self.predictedValue - film.stars


class PredictionResult(object):
    predictor = None
    films = []
    predictionResults = []
    avgDiff = 0

    def __init__(self, predictor, films):
        self.predictor = predictor
        self.films = films
        self.predictionResults = predictor.predictFilms(self.films)
        if len(self.predictionResults) > 0:
            diffSquereSum = 0
            for res in self.predictionResults:
                diffSquereSum += res.diff * res.diff
            self.avgDiff = math.sqrt(diffSquereSum / float(len(self.predictionResults)))


def toExample(film):
    s = str(film.stars) + " | " + "votes:" + str(film.votes) + " actorRateAvg:" + str(film.actorsStarsAvg) + " | " +  " ".join(film.actors) + " | " + film.director
    return s