package com.example.imsthanosapplication

import android.graphics.Bitmap
import android.graphics.Point
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


class TestData {
    companion object {
        var testPath: MutableList<Point> = mutableListOf()
    }
}

class CanvasActivity() : AppCompatActivity() {
    val TAG = "DocSnippets"
//    private lateinit var database : DatabaseReference
   // private lateinit var database: DatabaseReference
    private val database = Firebase.database
    private val myRef = database.getReference("mowerPositions")




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_canvas)
        //  val db = FirebaseFirestore.getInstance()

     //   myRef.setValue("Hello, World!")


    //    readDataFromRealtimeDatabase()
     //   myRef.setValue("Hello, World!")
        val displayMetrics = DisplayMetrics()
      //  readData()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        PathObject.height = displayMetrics.heightPixels
        PathObject.width = displayMetrics.widthPixels
        //myRef.get()
        var imageV = findViewById<ImageView>(R.id.imageV)
        val bitmap: Bitmap =
            Bitmap.createBitmap(PathObject.width, PathObject.height, Bitmap.Config.ARGB_8888)
/*
        val docRef = db.collection("Path")
        docRef.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    PathObject.startPoint = Point(PathObject.width/2,PathObject.height/2)
                    PathObject.addPoint(PathObject.startPoint)
                    val x = document.get("x")
                    val y = document.get("y")
                    TestData.testPath.add(Point(x.hashCode(), y.hashCode()))
                    PathObject.drawPath(bitmap)
                    imageV.background = BitmapDrawable(resources, bitmap)
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }*/


/*
        myRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d(TAG, database.toString())
                Log.d(TAG, myRef.toString())
                Log.d(TAG, "Here")
                //myRef.setValue("Hello, World!")
                val value = dataSnapshot.getValue<String>()
                //val value = dataSnapshot.getValue(MowerPosition::class.java)!!
                Log.d(TAG, "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })*/
        readDataFromRealtimeDatabase ()
}


   private fun readDataFromRealtimeDatabase () {

       var database = FirebaseDatabase.getInstance().getReference("Routes")

        Log.d(TAG, database.toString())
        Log.d(TAG, database.get().toString())


        database.setValue("Hello, World!")

        database.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                Log.d(TAG,"Exists1")
                val value = snapshot.getValue<String>()

                Log.d(TAG, "Value is: $value")
                val mPos = snapshot.getValue<Response>()
                Log.d(TAG, "Value is: $mPos")
                if (snapshot.exists()) {
                    Log.d(TAG,"Exists")
                    val data = snapshot.child("Routes").children

                    Log.d(TAG, "hilloooo")
                    data.forEach {
                        Log.d(TAG, it.toString())
                   //     val traveledPathSession = Response(mutableListOf())
                   //     val traveledPaths = it.children
                   //     traveledPaths.forEach{ pathData ->
                    //        val traveledPath = pathData.getValue(mowerPosition::class.java)
                        //    traveledPathSession.traveledPaths.add(traveledPath!!)
                        }
                       // Globals.traveledPathSessionList.add(traveledPathSession)
                  //  }
                   // traveledPathSessions.value = Globals.traveledPathSessionList
                }
                else{
                    Log.d(TAG, "snapshot does not exist")
                }
            }

            override fun onCancelled(error: DatabaseError) {
               Log.d(TAG,"Canceld")
            }
        })
    }


    data class MowerPosition(
        var x: Int? = null,
        var y: Int? = null
    )
    data class Response(
        var mowerPositions: List<MowerPosition>? = null,
        var exception: Exception? = null
    )



}