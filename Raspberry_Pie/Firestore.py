 #This code should be used with every script that you will be using to connect to the Firebase database.
import pyrebase
import time


config = {
  # You can get all these info from the firebase website. It's associated with your account.
  "apiKey": "AAAActQSqd0:APA91bHYXP7Y1PJGv0moMGKLQJQI87fHufCP5mv8tx3pNZxRQgJxRPkFnwwqvRTCBpMsRKIy42Ci3aq3C5d9yM2JxzaO-uEsTlK-_tZHLhXCCrhYlWQ2VqzWQj58oKQA24sk86pUNEUj",
  "authDomain": "ims-thanos.firebaseapp.com",
  "databaseURL": "https://ims-thanos-default-rtdb.europe-west1.firebasedatabase.app/",
  "storageBucket": "ims-thanos.appspot.com"
}

firebase = pyrebase.initialize_app(config)
db = firebase.database()


data = {
  "list": [
    {"x": 15,
     "y": 15,},
     {"x":20,
     "y":20,}
     ]
}


db.child("Routes").child(time.asctime(time.localtime(time.time()))).child("coordinates").push(data)
