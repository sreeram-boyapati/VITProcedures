__author__ = 'Sreeram'

from google.appengine.ext import db
from google.appengine.api import users

class query(db.Model):
    query_name = db.StringProperty(required=True)
    office_name = db.StringProperty(required=True)
    proc1 = db.StringProperty(required=False)
    proc2 = db.StringProperty(required=False)
    proc3 = db.StringProperty(required=False)
    proc4 = db.StringProperty(required=False)
    proc5 = db.StringProperty(required=False)
    clubs = db.StringProperty(required=False)
    freshers = db.StringProperty(requried=False)



