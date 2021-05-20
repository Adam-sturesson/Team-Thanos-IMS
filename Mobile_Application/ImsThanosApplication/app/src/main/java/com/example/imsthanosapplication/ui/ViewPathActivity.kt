package com.example.imsthanosapplication

import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*


class PathCompanion {
    companion object {
        var listOfPoints: MutableList<Point> = mutableListOf()
    }
}

class CanvasActivity() : AppCompatActivity() {
    val TAG = "DocSnippets"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_canvas)
        val id = intent.getStringExtra("routeID")
        val displayMetrics = DisplayMetrics()

        windowManager.defaultDisplay.getMetrics(displayMetrics)
        PathObject.height = displayMetrics.heightPixels
        PathObject.width = displayMetrics.widthPixels
        var imageV = findViewById<ImageView>(R.id.path_imageView)
        val bitmap: Bitmap = Bitmap.createBitmap(PathObject.width, PathObject.height, Bitmap.Config.ARGB_8888)


        var database = FirebaseDatabase.getInstance().reference
        Log.d(TAG, database.toString())
        Log.d(TAG, database.get().toString())
        database.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                Log.d(TAG,"Exists1")
                if (snapshot.exists()) {
                    Log.d(TAG,"Exists")
                    val data = snapshot.child("Routes").child(id!!).child("mowerPositions").children

                    data.forEach {
                        Log.d(TAG, "hilloooo")
                        Log.d(TAG, it.toString())
                        PathObject.startPoint = Point(PathObject.width/2,PathObject.height/2)
                        PathObject.addPoint(PathObject.startPoint)
                        val mowPos = it.getValue(MowerPosition::class.java)
                        val x = mowPos!!.x
                        val y = mowPos!!.y
                        PathCompanion.listOfPoints.add(Point(x.hashCode(), y.hashCode()))
                        PathObject.drawPath(bitmap)
                        imageV.background = BitmapDrawable(resources, bitmap)
                    }
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


    override fun onDestroy() {
      

        super.onDestroy()
        PathCompanion.listOfPoints.clear()
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