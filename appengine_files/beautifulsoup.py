raw_data="""<?xml version ="1.0" encoding="UTF-8"?>
	<Categories>
		<Category>Recent!</Category>
		<Category>Important Procedures</Category>
		<Category>For Clubs and Chapters</Category>
		<Category>Freshers</Category>
	</Categories>
"""

import re
import json
from bs4 import BeautifulSoup
from collections import defaultdict
soup = BeautifulSoup(raw_data)
data = soup.prettify()
head = soup.contents[0].contents[0]
cat_tag = soup.find_all('category')
result=[]
for i in cat_tag:
	result.append(str(i.get_text()))
result1={str(i) : result[i] for i in range(0,len(result))}
json_array=[result1]
json_data=json.dumps(json_array,sort_keys=True)
print json_data



