

import webapp2
import os
from google.appengine.ext import db

curdir = os.getcwd()

class MainHandler(webapp2.RequestHandler):
    def get(self):
        self.response.headers['Content-Type'] = 'text/plain'
        self.response.write('Hello world!')


class HomeHandler(webapp2.RequestHandler):
    def get(self):
        self.response.headers['Content-Type'] = 'text/html'
        self.response.write("<h1>Hey this is my homepage</h1>")


class Category_Data(webapp2.RequestHandler):
    def get(self):
        filedir = os.path.abspath(os.path.join(curdir, 'templates/Categories.xml'))
        print filedir
        file = open(filedir, 'r').read()

        self.response.headers['Content-Type'] = 'text/xml'
        self.response.write(file)


class Office_Data(webapp2.RequestHandler):
    def get(self):
        filedir = os.path.abspath(os.path.join(curdir, 'templates/Offices.xml'))
        print filedir
        file = open(filedir, 'r').read()
        self.response.headers['Content-Type'] = 'text/xml'
        self.response.write(file)

class Proc_Data(webapp2.RequestHandler):
    def get(self):
        filedir = os.path.abspath(os.path.join(curdir, 'templates/Procedures.xml'))
        print filedir
        file = open(filedir, 'r').read()
        self.response.headers['Content-Type'] = 'text/xml'
        self.response.write(file)

class VersionNumber(webapp2.RequestHandler):
    def get(self):
        self.response.headers['Content-Type'] = 'text/plain'
        self.response.write(1)

class Procedure(db.Model):
#This is the data store schema for new queries/procedures
	query=db.TextProperty()
	office=db.TextProperty()
	club=db.TextProperty()
	fresher=db.TextProperty()
	p1=db.TextProperty()
	p2=db.TextProperty()
	p3=db.TextProperty()
	p4=db.TextProperty()
	p5=db.TextProperty()
	created=db.DateTimeProperty(auto_now_add=True)

class SubmitProc(webapp2.RequestHandler):	
	def get(self,query,office,club,fresher,p1,p2,p3,p4,p5):
		proc1=Procedure()
		proc1.query=query
		proc1.office=office
		proc1.club=club
		proc1.fresher=fresher
		proc1.p1=p1
		proc1.p2=p2
		proc1.p3=p3
		proc1.p4=p4
		proc1.p5=p5
		proc1.put()
		self.response.out.write('<h1>Procedure submitted succesfully</h1>')
	
app = webapp2.WSGIApplication([
    ('/', MainHandler),
    ('/home', HomeHandler),
    ('/catData', Category_Data),
    ('/officeData', Office_Data),
    ('/procData', Proc_Data),
    ('/Version', VersionNumber),
	('/submitproc/(.*)/(.*)/(.*)/(.*)/(.*)/(.*)/(.*)/(.*)/(.*)', SubmitProc)
], debug=True)


