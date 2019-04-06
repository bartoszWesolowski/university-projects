import sys
import urllib
from bs4 import BeautifulSoup
import codecs

sys.stdout = codecs.getwriter("iso-8859-1")(sys.stdout, 'xmlcharrefreplace')

filmwebDomen = "http://www.filmweb.pl"

def getPageBody(url):
    f = urllib.urlopen(url)
    return f.read()

def getSoup(url):
    html = getPageBody(url)
    return BeautifulSoup(html, 'html.parser')


# laczy domene filmwebu z suffixem na przyklad 'http://www.filmweb.pl' + '/film/Piekna+i+Bestia-2017-736788'
def getFilmwebPage(sufix):
    return filmwebDomen + sufix;


def getFilmSoup(urlSuffix):
    return getSoup(getFilmwebPage(urlSuffix))
def printer(param):
    print param

# Sciaga url do filmow korzystajac z wyszuwiwarki filmweb
#1 param - liczba stron jakie ma odwiedzic
#2 param - numer strony od ktorej ma zaczac
#3param - zbior stron ktore wczesniej zostaly odwiedzony
#4 param - funkcja ktora dostaje za parametr znaleziony url, by default jest to funkcja printujaca
def downloadFimsFromSearch(numberOfSearchPagesToCheck, firstSearchPageToCheck, alreadyChecked=set(),  filmSaverFucn=printer):
    for i in range(firstSearchPageToCheck, numberOfSearchPagesToCheck):
        page = searchPage(i)
        print "looking for films on page " + page
        soup = getSoup(page)
        for link in soup.findAll('a'):
            # print link
            if link.get('class') and 'filmTitle' in link.get('class'):
                href = link.get('href')
                print "checking url: " + href
                if href and href not in alreadyChecked:
                    print "saving url: " + href
                    filmSaverFucn(href)
                    alreadyChecked.add(href)
                else:
                    print href + " already checked"


# zwraca suffix do strony ntej strony wyszukwiarki
def searchPage(number):
    template = "/search/film?q=&type=&startYear=&endYear=&countryIds=&genreIds=&startRate=&endRate=&startCount=&endCount=&sort=COUNT&sortAscending=false&c=portal&page="
    return getFilmwebPage(template + str(number));


def extractPageTouple(url):
    url = getFilmwebPage(url)
    soup = getSoup(url)
