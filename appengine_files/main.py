#!/usr/bin/env python
#
# Copyright 2007 Google Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
import webapp2
import os

curdir = os.getcwd()



"""
filedir2 = os.oath.abspath(os.path.join(curdir, 'templates/Offices.xml'))
file2 = open(filedir, 'r').read()

filedir3 = os.path.abspath(os.path.join(curdir, 'templates/Procedures.xml'))
file3 = open(filedir3, 'r').read()
"""

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

app = webapp2.WSGIApplication([
    ('/', MainHandler),
    ('/home', HomeHandler),
    ('/catData', Category_Data),
    ('/officeData', Office_Data),
], debug=True)


