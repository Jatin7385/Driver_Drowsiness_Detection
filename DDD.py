import cv2 as cv
from keras.models import model_from_json
from keras.models import load_model
import tensorflow as tf
import numpy as np
from keras.preprocessing import image

# load json and create model
json_file = open('model.json', 'r')
loaded_model_json = json_file.read()
json_file.close()
model = model_from_json(loaded_model_json)
# load weights into new model
model.load_weights("model.h5")
print("Loaded model from disk")


face_cascade_name = "haarcascade_frontalface_default.xml"
eyes_cascade_name = "haarcascade_eye_tree_eyeglasses.xml"
l_eye_cascade_name = "haarcascade_lefteye_2splits.xml"
r_eye_cascade_name = "haarcascade_righteye_2splits.xml"

face_cascade = cv.CascadeClassifier(face_cascade_name)
eye_cascade = cv.CascadeClassifier(eyes_cascade_name)
l_eye_cascade = cv.CascadeClassifier(l_eye_cascade_name)
r_eye_cascade = cv.CascadeClassifier(r_eye_cascade_name)
#Works with gray scale images. So converting into GrayScale images

font = cv.FONT_HERSHEY_COMPLEX_SMALL

cap = cv.VideoCapture(0)

score = 0


while cap.isOpened():
    _,  frame = cap.read()
    height,width = frame.shape[:2]
    gray = cv.cvtColor(frame,cv.COLOR_BGR2GRAY)
    faces = face_cascade.detectMultiScale(gray,1.1,4)

    for (x,y,w,h) in faces:
        roi_color = frame[y:y+h, x:x+w]
        roi_gray = gray[y:y+h, x:x+w]
        l_eye = l_eye_cascade.detectMultiScale(roi_gray)
        r_eye = r_eye_cascade.detectMultiScale(roi_gray)
        cv.rectangle(frame, (x,y), (x+w, y+h), (255,0,0), 3)
        
        rprediction = 0
        lprediction = 0
        
        for (ex,ey,ew,eh) in l_eye:
            #Prediction
            l_eye_frame = frame[ey:ey+eh,ex:ex+ew]
            l_eye_frame = cv.resize(l_eye_frame,(64,64))
            l_eye_frame = l_eye_frame.reshape(64,64,-1)
            l_eye_frame = image.img_to_array(l_eye_frame)
            l_eye_frame = np.expand_dims(l_eye_frame, axis = 0)
            result = model.predict(l_eye_frame)
                      
            cv.rectangle(roi_color, (ex,ey), (ex+ew, ey+eh), (0,255,0), 2)
            
            if result[0][0] == 0:
                lprediction = 0
            else:
                lprediction = 1
            
        for (ex,ey,ew,eh) in r_eye:
            #Prediction
            r_eye_frame = frame[ey:ey+eh,ex:ex+ew]
            r_eye_frame = cv.resize(r_eye_frame,(64,64))
            r_eye_frame = r_eye_frame.reshape(64,64,-1)
            r_eye_frame = image.img_to_array(r_eye_frame)
            r_eye_frame = np.expand_dims(r_eye_frame, axis = 0)
            result = model.predict(r_eye_frame)
            
            cv.rectangle(roi_color, (ex,ey), (ex+ew, ey+eh), (0,255,0), 2)
            
            if result[0][0] == 0:
                rprediction = 0
            else:
                rprediction = 1
        
        if lprediction == 0 and rprediction == 0:
            cv.putText(frame,"Closed",(10,height-20), font, 1,(255,255,255),1,cv.LINE_AA)
            score += 1
        else:
            cv.putText(frame,"Open",(10,height-20), font, 1,(255,255,255),1,cv.LINE_AA)
            score -= 1
        
        if score<0:
            score = 0
        cv.putText(frame,'Score:'+str(score),(100,height-20), font, 1,(255,255,255),1,cv.LINE_AA)
        if score >= 15:
            print("ALERT!!!!")
            
            
            
    cv.imshow('img',frame)
    if cv.waitKey(1) & 0xFF == ord('q'):
        cv.destroyAllWindows()
        cap.release()
        break