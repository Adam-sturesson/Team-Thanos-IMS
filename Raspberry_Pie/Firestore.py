import firebase_admin
from firebase_admin import credentials,firestore
from datetime import datetime, timezone

cred = credentials.Certificate("./ims-thanos-firebase-adminsdk-rxc8t-a522b3e4ae.json")
firebase_admin.initialize_app(cred)
db = firestore.client()

doc_ref = db.collection(u'users').document()

while True:
    #Get position from arduino and send to firebase with some interval
    # x = 1
    # y = 2
    # now = datetime.now()
    doc_ref.set({
        u'x' : x,
        u'y' : y,
        u'time' : now
    })
    break