package com.example.imsthanosapplication.ui

import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.imsthanosapplication.PathObject
import com.example.imsthanosapplication.R
import com.google.firebase.database.*


class PathCompanion {
    companion object {
        var listOfPoints: MutableList<Point> = mutableListOf()
        var listOfObstacles: MutableList<Point> = mutableListOf()
    }
}

class CanvasActivity() : AppCompatActivity() {

    private val displayMetrics = DisplayMetrics()
    private var database = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_canvas)
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        PathObject.height = displayMetrics.heightPixels
        PathObject.width = displayMetrics.widthPixels
        PathCompanion.listOfPoints.clear()
        PathCompanion.listOfObstacles.clear()
        getDataFromDatabase()
    }

    private fun getDataFromDatabase() {
        database.addValueEventListener(object : ValueEventListener {

            val id = intent.getStringExtra("routeID")
            var imageV = findViewById<ImageView>(R.id.path_imageView)
            val bitmap: Bitmap =
                Bitmap.createBitmap(PathObject.width, PathObject.height, Bitmap.Config.ARGB_8888)

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val data = snapshot.child("Routes").child(id!!).child("mowerPositions").children
                    data.forEach {
                        val mowPos = it.getValue(MowerPosition::class.java)
                        val x = mowPos!!.x
                        val y = mowPos!!.y
                        PathCompanion.listOfPoints.add(Point(x.hashCode(), y.hashCode()))
                        PathObject.drawPath(bitmap)
                    }
                    val obstaclesData =
                        snapshot.child("Routes").child(id!!).child("obstaclePositions").children
                    obstaclesData.forEach {
                        val mowPos = it.getValue(MowerPosition::class.java)
                        val x = mowPos!!.x
                        val y = mowPos!!.y
                        PathCompanion.listOfObstacles.add(Point(x.hashCode(), y.hashCode()))
                        PathObject.drawObstacles(bitmap)
                    }
                    imageV.background = BitmapDrawable(resources, bitmap)
                } else {
                    val toast = Toast.makeText(
                        applicationContext,
                        "Something went wrong, please try again",
                        Toast.LENGTH_SHORT
                    )
                    toast.show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                val toast = Toast.makeText(applicationContext, "Database Error", Toast.LENGTH_SHORT)
                toast.show()
            }
        })
    }
    override fun onDestroy() {
        super.onDestroy()
        PathCompanion.listOfPoints.clear()
        PathCompanion.listOfObstacles.clear()
    }
    data class MowerPosition(
        var x: Int? = null,
        var y: Int? = null
    )
}