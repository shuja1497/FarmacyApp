import requests
from bs4 import BeautifulSoup
import json

base_url = "http://api.wunderground.com/api/42b39d8413e182d3/history_%s/q/IN/Amritsar.json"

d=21
month=7
y=2008
i=1
while(y!=2012 or d!=31 or month!=12):
	
	if(month==2 and d==30 and y%4==0):
		d=1
		month=month+1
	elif(month==2 and d==29 and y%4!=0):
		d=01
		month=month+1
	if(month==1 or month==3 or month==5 or month==7 or month==8 or month==10 or month==12):
		if(d==32):
			d=01
			month=month+1
	elif(month==4 or month==6 or month==8 or month==9 or month==11):
		if(d==31):
			d=01
			month=month+1
	if(month==13):
		y=y+1
		d=01
		month=01
		continue
	if(len(str(month))==1):
		m=""
		m="0"+str(month)
	else:
		m=str(month)
	if(len(str(d))==1):
		dy=""
		dy="0"+str(d)
	else:
		dy=str(d)
	current_date = str(y)+m+dy

	try:
		r=requests.get(base_url%current_date)
		r.content
		soup=BeautifulSoup(r.content , "html.parser")
		fo = open(str(current_date)+".txt","w")
		fo.write(soup.prettify())
		fo.close()
	except :
		pass
	i=i+1
	d=d+1