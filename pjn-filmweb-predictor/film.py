
NONE = "NONE"
ARRAY_PROP_SIZE = 5;


def extracTitle(soup):
    titles = soup.findAll("h1", {"class": "filmTitle"})
    for h1 in titles:
        a = h1.find('a')
        if a and a.getText():
            return str(a.getText()).replace("'", "").replace('"', '')
    return "No title found"


def extractVotes(soup):
    ratingCountSpan = soup.find("span", {"itemprop": "ratingCount"})
    if ratingCountSpan and ratingCountSpan.getText():
        return "".join(str(ratingCountSpan.getText()).split())
    return -1


def extractPeopleWantToSee(soup):
    #communityWrapper = soup.find("div", {"class": "communityRateInfoWrapper"})
    #if communityWrapper and communityWrapper.getText():
    #    text = communityWrapper.getText()
    #    numbers = [s for s in text.split() if s.isdigit()]
    #    number = ''.join(numbers)
    #    return int(number)
    return 0


def extractStars(soup):
    stars = soup.find("span", {"itemprop": "ratingValue"})
    if stars and stars.getText():
        return float(str(stars.getText()).replace(",", "."))
    return -1


def extractDirector(soup):
    directorLi = soup.find("li", {"itemprop": "director"})
    if directorLi:
        directorA = directorLi.find('a')
        if directorA and directorA.getText():
            return str(directorA.getText()).replace("'", "").replace('"', '')
    return NONE


def extractScreenwriter(soup):
    return ""


def extractActors(soup):
    actors = []
    castTable = soup.find("table", {"class": "filmCastCast"})
    if not castTable:
        return actors

    rows = castTable.findAll('tr')
    if not rows:
        return actors

    for row in rows:
        cols = row.findAll('td')
        if cols and len(cols) > 1:
            nameCol = cols[1]
            a = nameCol.find('a')
            if a and a.getText():
                actors.append(str(a.getText()).replace("'", "").replace('"', ''))
    return actors


def extractActorsStars(soup):
    stars = []
    container = soup.find("div", {"class": "pkc-wrapper"})
    if container:
        divs = container.findAll('div', {"class": "element"})
        if divs:
            for div in divs:
                span = div.find('span', {"class": "s-20"})
                if span and span.getText():
                    stars.append(float(str(span.getText()).replace(",", ".")))

    return stars


def extractAwards(soup):
    thAwards = soup.find("th", {"class": "thAwards"})


# if thAwards:
# return [NONE, NONE, NONE, NONE, NONE]

def extractBoxoffice(soup):
    return 0


class Film(object):
    id = 0
    url = ""
    title = ""
    votes = 0
    peopleWantToSee = 0
    stars = 0
    director = ""
    screenwriter = ""
    actors = [NONE, NONE, NONE, NONE, NONE]
    actorsStars = []
    actorsStarsAvg = 0.0
    awards = [NONE, NONE, NONE, NONE, NONE]
    boxoffice = "";

    def __init__(self, url, soup):
        if url and soup:
            self.url = url
            self.title = extracTitle(soup)
            self.votes = extractVotes(soup)
            self.peopleWantToSee = extractPeopleWantToSee(soup)
            self.stars = extractStars(soup)
            self.actorsStars = extractActorsStars(soup)
            self.director = extractDirector(soup)
            self.screenwriter = extractScreenwriter(soup)
            self.actors = extractActors(soup)
            self.awards = extractAwards(soup)
            self.boxoffice = extractBoxoffice(soup)
            if len(self.actorsStars) > 0:
                self.actorsStarsAvg = sum(self.actorsStars) / float(len(self.actorsStars))

    def __str__(self):
        return "Film: " + self.title \
               + " stars: " + str(self.stars) \
               + " director: " + self.director \
               + " votes: " + str(self.votes) \
               + " actors: " + str(self.actors) \
               + " actorsStarsAvg: " + str(self.actorsStarsAvg)

def createFromDb(touple):
    film = Film(False, False)
    film.id = touple[0]
    film.url = touple[1]
    film.title = touple[2]
    film.votes = int(touple[3])
    film.peopleWantToSee = int(touple[4])
    film.stars = float(touple[5])
    film.director = touple[6]
    film.screenwriter = ""
    film.actors = touple[7]
    film.awards = [NONE, NONE, NONE, NONE, NONE]
    film.actorsStars = touple[8]
    film.boxoffice = 0
    if len(film.actorsStars) > 0:
        film.actorsStarsAvg = sum(film.actorsStars) / float(len(film.actorsStars))
    else:
        film.actorsStarsAvg = 0
    return film
