from flask import Flask, request, jsonify
import numpy as np
import pickle
import base64
from keras.models import load_model
from keras.preprocessing import image as image_utils
from sklearn.preprocessing import StandardScaler
import KNN_Prac3
from sklearn.linear_model import LogisticRegression
from sklearn.externals import joblib

app = Flask(__name__)

label_map = {'24': 17, '25': 18, '26': 19, '27': 20, '20': 13,
             '21': 14, '22': 15, '23': 16, '28': 21, '29': 22, '1': 1, '0': 0,
             '3': 23, '2': 12, '5': 33, 'c_4': 32, '7': 35, '6': 34, '9': 37, '33': 27,
             '32': 26, '37': 31, '36': 30, '35': 29, '34': 28, '19': 11, '18': 10, '31': 25,
             '30': 24, '15': 7, '14': 6, '17': 9, '16': 8, '11': 3, '10': 2, '13': 5, '12': 4, '8': 36}


def prediction():
    # building the path
    # testing for a single image
    test_image = image_utils.load_img('image.jpeg', target_size=(32, 32))
    test_image = image_utils.img_to_array(test_image)
    test_image = np.expand_dims(test_image, axis=0)
    result = model.predict_on_batch(test_image)
    # print(result)
    predicted_label = list(label_map.keys())[list(label_map.values()).index(np.argmax(result))]
    return predicted_label


def prediction_crops(x_test):
    print ("In function")
    x_test_f = x_test
    sc = StandardScaler()
    xx_test = sc.fit_transform(x_test_f)

    predicted_index = loaded_model.kneighbors(xx_test, n_neighbors=8)[1]

    predicted_index_month = []

    for i in predicted_index[0]:
        predicted_index_month.append(KNN_Prac3.X[i][0])

    diff_month = []

    for i in predicted_index_month:
        diff = i - xx_test[0][0]
        diff_month.append(diff)
        diff = 0

    indx_sorted = np.argsort(diff_month)

    final_predicted_month = []

    for i in indx_sorted:
        final_predicted_month.append(predicted_index[0][i])

    final_predicted_month = final_predicted_month[0:5]

    top_5_crops = []

    for i in final_predicted_month:
        top_5_crops.append(KNN_Prac3.Y[i])

    return top_5_crops


def getResultsForAlertsystem(jsonData):
    print('in results')

    mday = jsonData['mday']
    mon = jsonData['mon']
    maxtempm = jsonData['maxtempm']
    mintempm = jsonData['mintempm']
    maxhumidity = jsonData['maxhumidity']
    minhumidity = jsonData['minhumidity']
    maxpressurem = jsonData['maxpressurem']
    minpressurem = jsonData['minpressurem']
    maxwspdm = jsonData['maxwspdm']
    precipm = jsonData['precipm']
    meantempm = jsonData['meantempm']
    meanpressurem = jsonData['meanpressurem']
    humidity = jsonData['humidity']

    test = []
    test.append(mon)
    test.append(mday)
    test.append(meantempm)
    test.append(meanpressurem)
    test.append(humidity)
    test.append(maxtempm)
    test.append(mintempm)
    test.append(maxhumidity)
    test.append(minhumidity)
    test.append(maxpressurem)
    test.append(minpressurem)
    test.append(maxwspdm)
    test.append(precipm)

    print(test)

    try:
        # pickle.dump(model , open('rainalert.pkl' ,wb))
        model_alert = joblib.load('rainalert.pkl')
        print ("rain alert model loaded")
        print('here')
        alert = model_alert.predict(test)[0]
        return alert
    except Exception:
        print (Exception)
        pass


@app.route('/alertCheck/', methods=["POST"])
def alertChecking():
    try:
        if request.method == 'POST':
            jsonData = request.get_json()
            result = getResultsForAlertsystem(jsonData)
            print (result)
            # return jsonify({'day1': 1})
            return jsonify({'day1': int(result)})
    except Exception:
        pass
    return "{'day1':'value'}"


@app.route('/<month>/<min_temp>/<max_temp>/<rainfall>')
def return_crops(month, min_temp, max_temp, rainfall):
    val = request.data
    m = month.encode('utf-8')
    min_t = min_temp.encode('utf-8')
    max_t = max_temp.encode('utf-8')
    r = rainfall.encode('utf-8')
    x_test = [[m, min_t, max_t, r]]
    pred = prediction_crops(x_test)
    print (pred)
    return jsonify({'crop1': pred[0], "crop2": pred[1], "crop3": pred[2], "crop4": pred[3], "crop5": pred[4]})


@app.route('/', methods=['GET', 'POST'])
def start():
    if request.method == 'POST':
        strng = request.values
        imageInstring = strng['image']
        imgdata = base64.b64decode(imageInstring)

        with open("image.jpeg", "wb") as fh:
            fh.write(imgdata)

        pred = prediction()
        return pred
    else:
        return "<h1>use post method</h1>"


@app.route('/hello/<username>/<first>/<second>')
def hello(username, first, second):
    a = username + first + second
    return a


if __name__ == '__main__':
    print ("hellooo")
    model = load_model('plantdisease_withVal_allepoch64.h5')
    print ("crops disease model loaded")

    loaded_model = pickle.load(open("knn_model.sav", 'rb'))
    print ("crops info model loaded")

    app.run(port=8080, use_reloader=True)
