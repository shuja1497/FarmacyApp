import json
import csv

#start date
d=1
month=1
y=2001


i=1
flag=0

#end date in condition
while(y!=2016 or d!=31 or month!=12):
	if(month==2 and d==30 and y%4==0):
		d=1
		month=month+1
	elif(month==2 and d==29 and y%4!=0):
		d=1
		month=month+1
	if(month==1 or month==3 or month==5 or month==7 or month==8 or month==10 or month==12):
		if(d==32):
			d=1
			month=month+1
	elif(month==4 or month==6 or month==8 or month==9 or month==11):
		if(d==31):
			d=1
			month=month+1
	if(month==13):
		y=y+1
		d=1
		month=1
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

	name = str(current_date)+".txt"

	try:
		fo = open(name,"r")

		data = json.loads(fo.read())
		with open('daily_summary_data_2001-2016.csv', 'a') as csvfile:
			writer = csv.writer(csvfile, delimiter=',',quotechar='|', quoting=csv.QUOTE_MINIMAL)
			
			if(flag==0):
				writer.writerow(["year","mon","mday","fog","rain","snow","snowfallm","snowfalli","snowdepthm"
				,"snowdepthi","hail","thunder","tornado","meantempm","meantempi","meandewptm","meandewpti"
				,"meanpressurem","meanpressurei","meanwindspdm","meanwindspdi","meanwdire","meanwdird","meanvism"
				,"meanvisi","humidity","maxtempm","maxtempi","mintempm","mintempi","maxhumidity","minhumidity"
				,"maxdewptm","maxdewpti","mindewptm","mindewpti","maxpressurem","maxpressurei","minpressurem"
				,"minpressurei","maxwspdm","maxwspdi","minwspdm","minwspdi","maxvism","maxvisi","minvism","minvisi"
				,"gdegreedays","heatingdegreedays","coolingdegreedays","precipm","precipi","precipsource"])

				flag=1

			# for daily summary data
			year = data["history"]["dailysummary"][0]["date"]["year"]
			mon = data["history"]["dailysummary"][0]["date"]["mon"]
			mday = data["history"]["dailysummary"][0]["date"]["mday"]
			fog = data["history"]["dailysummary"][0]["fog"]
			rain = data["history"]["dailysummary"][0]["rain"]
			snow = data["history"]["dailysummary"][0]["snow"]
			snowfallm = data["history"]["dailysummary"][0]["snowfallm"]
			snowfalli = data["history"]["dailysummary"][0]["snowfalli"]
			snowdepthm = data["history"]["dailysummary"][0]["snowdepthm"]
			snowdepthi = data["history"]["dailysummary"][0]["snowdepthi"]
			hail = data["history"]["dailysummary"][0]["hail"]
			thunder = data["history"]["dailysummary"][0]["thunder"]
			tornado = data["history"]["dailysummary"][0]["tornado"]
			meantempm = data["history"]["dailysummary"][0]["meantempm"]
			meantempi = data["history"]["dailysummary"][0]["meantempi"]
			meandewptm = data["history"]["dailysummary"][0]["meandewptm"]
			meandewpti = data["history"]["dailysummary"][0]["meandewpti"]
			meanpressurem = data["history"]["dailysummary"][0]["meanpressurem"]
			meanpressurei = data["history"]["dailysummary"][0]["meanpressurei"]
			meanwindspdm = data["history"]["dailysummary"][0]["meanwindspdm"]
			meanwindspdi = data["history"]["dailysummary"][0]["meanwindspdi"]
			meanwdire = data["history"]["dailysummary"][0]["meanwdire"]
			meanwdird = data["history"]["dailysummary"][0]["meanwdird"]
			meanvism = data["history"]["dailysummary"][0]["meanvism"]
			meanvisi = data["history"]["dailysummary"][0]["meanvisi"]
			humidity = data["history"]["dailysummary"][0]["humidity"]
			maxtempm = data["history"]["dailysummary"][0]["maxtempm"]
			maxtempi = data["history"]["dailysummary"][0]["maxtempi"]
			mintempm = data["history"]["dailysummary"][0]["mintempm"]
			mintempi = data["history"]["dailysummary"][0]["mintempi"]
			maxhumidity = data["history"]["dailysummary"][0]["maxhumidity"]
			minhumidity = data["history"]["dailysummary"][0]["minhumidity"]
			maxdewptm = data["history"]["dailysummary"][0]["maxdewptm"]
			maxdewpti = data["history"]["dailysummary"][0]["maxdewpti"]
			mindewptm = data["history"]["dailysummary"][0]["mindewptm"]
			mindewpti = data["history"]["dailysummary"][0]["mindewpti"]
			maxpressurem = data["history"]["dailysummary"][0]["maxpressurem"]
			maxpressurei = data["history"]["dailysummary"][0]["maxpressurei"]
			minpressurem = data["history"]["dailysummary"][0]["minpressurem"]
			minpressurei = data["history"]["dailysummary"][0]["minpressurei"]
			maxwspdm = data["history"]["dailysummary"][0]["maxwspdm"]
			maxwspdi = data["history"]["dailysummary"][0]["maxwspdi"]
			minwspdm = data["history"]["dailysummary"][0]["minwspdm"]
			minwspdi = data["history"]["dailysummary"][0]["minwspdi"]
			maxvism = data["history"]["dailysummary"][0]["maxvism"]
			maxvisi = data["history"]["dailysummary"][0]["maxvisi"]
			minvism = data["history"]["dailysummary"][0]["minvism"]
			minvisi = data["history"]["dailysummary"][0]["minvisi"]
			gdegreedays = data["history"]["dailysummary"][0]["gdegreedays"]
			heatingdegreedays = data["history"]["dailysummary"][0]["heatingdegreedays"]
			coolingdegreedays = data["history"]["dailysummary"][0]["coolingdegreedays"]
			precipm = data["history"]["dailysummary"][0]["precipm"]
			precipi = data["history"]["dailysummary"][0]["precipi"]
			precipsource = data["history"]["dailysummary"][0]["precipsource"]
			writer.writerow([year,mon,mday,fog,rain,snow,snowfallm,snowfalli,snowdepthm,snowdepthi,hail,thunder,tornado
				,meantempm,meantempi,meandewptm,meandewpti,meanpressurem,meanpressurei,meanwindspdm,meanwindspdi,meanwdire
				,meanwdird,meanvism,meanvisi,humidity,maxtempm,maxtempi,mintempm,mintempi,maxhumidity,minhumidity,maxdewptm,maxdewpti
				,mindewptm,mindewpti,maxpressurem,maxpressurei,minpressurem,minpressurei,maxwspdm,maxwspdi,minwspdm,minwspdi,maxvism
				,maxvisi,minvism,minvisi,gdegreedays,heatingdegreedays,coolingdegreedays,precipm,precipi,precipsource])

			


			"""
				#for observation data

			if(flag==0):
				writer.writerow(["year","mon","mday","hour","mi","tempm","tempi","dewptm","dewpti","hum","wspdm","wspdi","wgusti","wdird","wdire","vism","visi"
					,"pressurem","pressurei","windchillm","windchilli","heatindexm","heatindexi","precipm","precipi","conds","icon","fog","rain","snow","hail"
					,"thunder","tornado"])
				flag=1
			for i in range (0,len(data["history"]["observations"])):
				year = data["history"]["observations"][i]["date"]["year"]
				mon = data["history"]["observations"][i]["date"]["mon"]
				mday = data["history"]["observations"][i]["date"]["mday"]
				hour = data["history"]["observations"][i]["date"]["hour"]
				mi = data["history"]["observations"][i]["date"]["min"]
				tempm = data["history"]["observations"][i]["tempm"]
				tempi = data["history"]["observations"][i]["tempi"]
				dewptm = data["history"]["observations"][i]["dewptm"]
				dewpti = data["history"]["observations"][i]["dewpti"]
				hum = data["history"]["observations"][i]["hum"]
				wspdm = data["history"]["observations"][i]["wspdm"]
				wspdi = data["history"]["observations"][i]["wspdi"]
				wgustm = data["history"]["observations"][i]["wgustm"]
				wgusti = data["history"]["observations"][i]["wgusti"]
				wdird = data["history"]["observations"][i]["wdird"]
				wdire = data["history"]["observations"][i]["wdire"]
				vism = data["history"]["observations"][i]["vism"]
				visi = data["history"]["observations"][i]["visi"]
				pressurem = data["history"]["observations"][i]["pressurem"]
				pressurei = data["history"]["observations"][i]["pressurei"]
				windchillm = data["history"]["observations"][i]["windchillm"]
				windchilli = data["history"]["observations"][i]["windchilli"]
				heatindexm = data["history"]["observations"][i]["heatindexm"]
				heatindexi = data["history"]["observations"][i]["heatindexi"]
				precipm = data["history"]["observations"][i]["precipm"]
				precipi = data["history"]["observations"][i]["precipi"]
				conds = data["history"]["observations"][i]["conds"]
				icon = data["history"]["observations"][i]["icon"]
				fog = data["history"]["observations"][i]["fog"]
				rain = data["history"]["observations"][i]["rain"]
				snow = data["history"]["observations"][i]["snow"]
				hail = data["history"]["observations"][i]["hail"]
				thunder = data["history"]["observations"][i]["thunder"]
				tornado = data["history"]["observations"][i]["tornado"]
				metar = data["history"]["observations"][i]["metar"]
				writer.writerow([year,mon,mday,hour,mi,tempm,tempi,dewptm,dewpti,hum,wspdm,wspdi,wgusti,wdird,wdire,vism,visi
					,pressurem,pressurei,windchillm,windchilli,heatindexm,heatindexi,precipm,precipi,conds,icon,fog,rain,snow,hail
					,thunder,tornado])
				
				"""




	except:
		pass
	i=i+1
	d=d+1




