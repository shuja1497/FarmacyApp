import requests
from bs4 import BeautifulSoup
from selenium import webdriver
import csv

from selenium.webdriver import Firefox

# driver=webdriver.Chrome()
driver=Firefox(executable_path='/home/p2/Downloads/geckodriver')

url="https://www.apnikheti.com/en/pn/agriculture/horticulture/vegetable-crops/arum-arvi"
driver.get("https://www.apnikheti.com/en/pn/agriculture/horticulture/vegetable-crops/arum-arvi")



 

list_count=2
parent_count = 7
crop = []
flag=0
label =1
columnname = ["CROP NAME" ,"CROP TYPE","MONTH", "TEMPERATURE - WINTER" , "RAINFALL - WINTER","SOWING TEMPERATURE - WINTER" , "HARVESTING TEMPERATURE - WINTER" , "TEMPERATURE - SUMMER" , "RAINFALL - SUMMER","SOWING TEMPERATURE - SUMMER" , "HARVESTING TEMPERATURE - SUMMER"]
#columnname = ["CROP NAME" , "MONTH"]

while(True):
	try:
		winterTemp = []
		summerTemp = []
		typemonth=[]
		r= requests.get(url)
		r.content
		soup = BeautifulSoup(r.content , "html.parser")
		article = soup.find_all('p')
		
		article1 = soup.find_all('div' ,{"class":"summer-Temp"})
		article2 = soup.find_all('div' ,{"class":"winter-Temp"})

		head = soup.find_all('h1')

		header = []
		for h in head:
			header.append(h.getText())



		# article = article.find_all('p')

		for art in article :
			typemonth=art.getText().split("/")
			break

		for art in article1:
			summerTemp.append(art.getText())
		for art2 in article2:
			winterTemp.append(art2.getText())

		print (header[:1])
		print (typemonth)
		print (winterTemp[:4])
		print (summerTemp[:4])
		wT = winterTemp[:4]
		sT = summerTemp[:4]
		



		
		with open('/home/p2/Documents/Minor 2/Apni Kheti/cropmonth.csv', 'a') as csvfile:
			writer = csv.writer(csvfile, delimiter='|',quotechar='|', quoting=csv.QUOTE_MINIMAL)
			if(label==1):
				writer.writerow(columnname)
				label=0
			writer.writerow(header+typemonth+wT+sT)
			
		
		if (flag==1):
			xpath_parent= '//*[@id="c-menu"]/li['+str(parent_count)+']/a'
			but_click = driver.find_element_by_xpath(xpath_parent)
			but_click.click()
			xpath = '//*[@id="c-menu"]/li['+str(parent_count)+']/ul/li['+str(list_count)+']/a'
			try:
				but_click = driver.find_element_by_xpath(xpath)
				but_click.click()
			except :
				print("andar Exception 1")
				parent_count = parent_count+1
				list_count=0
			url= driver.current_url
			list_count = list_count+1
		else:
			xpath_parent = '//*[@id="hc-menu"]/li['+str(parent_count)+']/a'
			but_click = driver.find_element_by_xpath(xpath_parent)
			but_click.click()
			xpath = '//*[@id="hc-menu"]/li['+str(parent_count)+']/ul/li['+str(list_count)+']/a'
			try:
				but_click = driver.find_element_by_xpath(xpath)
				but_click.click()
			except :
				print("andar Exception 2")
				parent_count = parent_count+1
				list_count=0
			url= driver.current_url
			list_count = list_count+1
		
	except Exception as e:
		print("bahar exception")
		flag=0
		parent_count=1
		print(e)
		pass