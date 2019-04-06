class FilmCategorization(object):

    reallyBad = 0 #ocena [0, 2)
    bad = 0 # [2, 5)
    middle = 0 # [5, 6)
    good = 0 # [6, 7)
    veryGood = 0 #[7, 8)
    perfect = 0 # [8, 9)
    ideal = 0 # [9, 10]
    map = dict()

    def __init__(self, films):
        for film in films:
            if film.stars not in self.map:
                self.map[film.stars] = []
            self.map[film.stars].append(film.title)
            if film.stars < 2:
                self.reallyBad += 1
            elif film.stars < 5:
                self.bad += 1
            elif film.stars < 6:
                self.middle += 1
            elif film.stars < 7:
                self.good += 1
            elif film.stars < 8:
                self.veryGood += 1
            elif film.stars < 9:
                self.perfect += 1
            else:
                self.ideal += 1

    def printIt(self):
        m = {}
        m["reallyBad [0, 2)" ] = self.reallyBad
        m["bad [2, 5)"] = self.bad
        m["middle [5, 6)"] = self.middle
        m["good [6, 7)"] = self.good
        m["veryGood [7, 8)"] = self.veryGood
        m["perfect [8, 9)"] = self.perfect
        m["ideal [9, 10]"] = self.ideal
        print m