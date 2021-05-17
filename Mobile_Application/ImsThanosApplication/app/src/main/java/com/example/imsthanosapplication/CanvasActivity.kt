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


class TestData {
    companion object {
        var testPath: MutableList<Point> = mutableListOf()
    }
}

class CanvasActivity() : AppCompatActivity() {
    val TAG = "DocSnippets"
//    private lateinit var database : DatabaseReference




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_canvas)
        //  val db = FirebaseFirestore.getInstance()

        var database = FirebaseDatabase.getInstance()
      //  var myRef: DatabaseReference = database.getReference("Routes")

        readDataFromRealtimeDatabase ()
        //  myRef.setValue("Hello, World!")
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
            }

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d(TAG, "Here")
                val value = dataSnapshot.getValue(String::class.java)!!
                Log.d(TAG, "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })*/

}

   private fun readDataFromRealtimeDatabase () {
        val database = FirebaseDatabase.getInstance().reference
        Log.d(TAG, database.toString())
        database.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d(TAG,"Exists1")
                if (snapshot.exists()) {
                    Log.d(TAG,"Exists")
                    val data = snapshot.child("mowerPositions").children
                    Log.d(TAG, "hilloooo")
                    data.forEach {
                        val traveledPathSession = Response(mutableListOf())
                        val traveledPaths = it.children
                        traveledPaths.forEach{ pathData ->
                            val traveledPath = pathData.getValue(mowerPosition::class.java)
                        //    traveledPathSession.traveledPaths.add(traveledPath!!)
                        }
                       // Globals.traveledPathSessionList.add(traveledPathSession)
                    }
                   // traveledPathSessions.value = Globals.traveledPathSessionList
                }
                else{
                    Log.d(TAG, "snapshot does not exist")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                //Toast.makeText(Globals.currentActivity, "Could not read from database", Toast.LENGTH_LONG).show()
            }
        })
    }


    private fun realTime() {

    }/*
    private fun readData() {
        Log.d(TAG, "Du e her")

        myRef.get().addOnSuccessListener {
            Log.d(TAG, "Du kom in i successlist her")
            if (it.exists()){

                val x = it.child("x").value
                val y = it.child("y").value
                //val age = it.child("age").value
                Log.d(TAG,"Success X:" + x.toString())
                Log.d(TAG,"Success Y:" + y.toString())
              //  Toast.makeText(TAG,"Successfuly Read",Toast.LENGTH_SHORT).show()
                //binding.etusername.text.clear()
               // binding.tvFirstName.text = x.toString()
               // binding.tvLastName.text = y.toString()
               // binding.tvAge.text = age.toString()

            }else{

                Log.d(TAG,"Sry")


            }

        }.addOnFailureListener{

            Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()


        }



    }*/




















        data class mowerPosition(
        var x: Int? = null,
        var y: Int? = null
    )
    data class Response(
        var mowerPositions: List<mowerPosition>? = null,
        var exception: Exception? = null
    )

    private fun print(response: Response) {
        response.mowerPositions?.let { products ->
            products.forEach{ product ->
                product.x?.let {
                    Log.d(TAG, it.toString())
                }
            }
            products.forEach{product ->
                product.y?.let {
                    Log.d(TAG, it.toString())
                }
            }
        }

        response.exception?.let { exception ->
            exception.message?.let {
                Log.d(TAG, it)
            }
        }
    }

    interface FirebaseCallback {
        fun onResponse(response: Response)
    }

    class mowerPosRepository(
        private val rootRef: DatabaseReference = FirebaseDatabase.getInstance().reference,
        private val productRef: DatabaseReference = rootRef.child("Routes")
    ) {
        fun getResponseFromRealtimeDatabaseUsingCallback(callback: FirebaseCallback) {
            productRef.get().addOnCompleteListener { task ->
                val response = Response()
                if (task.isSuccessful) {
                    val result = task.result
                    result?.let {
                        response.mowerPositions = result.children.map { snapShot ->
                            snapShot.getValue(mowerPosition::class.java)!!
                        }
                    }
                } else {
                    response.exception = task.exception
                }
                callback.onResponse(response)
            }
        }
    }

}