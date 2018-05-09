
# coding: utf-8

# In[1]:


# Importing the libraries
import numpy as np
import matplotlib.pyplot as plt
import pandas as pd


# In[2]:


#IMPORTING THE DATASET
dataset = pd.read_csv('cropmonth_rain.csv')


# In[3]:


dataset


# In[4]:


X = dataset.iloc[: , 2:].values


# In[5]:


X


# In[6]:


Y = dataset.iloc[: , 0].values


# In[7]:


Y


# In[8]:


from sklearn.preprocessing import StandardScaler
sc = StandardScaler()
X_train = sc.fit_transform(X)


# In[9]:


from sklearn.neighbors import KNeighborsClassifier
classifier = KNeighborsClassifier(n_neighbors = 5, metric = 'minkowski', p = 2)
classifier.fit(X_train, Y)


# In[42]:


x_test = [[5 , 28.5 , 40.6, 3.4]]
                             
from sklearn.preprocessing import StandardScaler
sc = StandardScaler()
X_test = sc.fit_transform(x_test)


# In[52]:


predicted_index = classifier.kneighbors(X_test, n_neighbors=8)[1]


# In[53]:


predicted_index


# In[58]:


predicted_index[0]


# In[65]:


predicted_index_month = []

for i in predicted_index[0]:
    predicted_index_month.append(X[i][0])


# In[66]:


predicted_index_month


# In[67]:


diff_month = []

for i in predicted_index_month :
    diff = i - x_test[0][0]
    diff_month.append(diff)
    diff = 0


# In[68]:


diff_month


# In[72]:


indx_sorted = np.argsort(diff_month)
indx_sorted


# In[73]:


final_predicted_month = []

for i in indx_sorted :
    final_predicted_month.append(predicted_index[0][i])


# In[79]:


final_predicted_month = final_predicted_month[0:5]

final_predicted_month


# In[80]:


top_5_crops = []

for i in final_predicted_month:
    top_5_crops.append(Y[i])


# In[81]:


top_5_crops


# In[82]:


X[9 , :]


# In[83]:


X[89, :]


# In[85]:


X[26, :]


# In[86]:


X[39, :]


# In[87]:


X[43, :]


# In[88]:


import pickle


# In[89]:


filename = 'knn_model.sav'
pickle.dump(classifier, open(filename, 'wb'))


# In[91]:


loaded_model = pickle.load(open(filename, 'rb'))

