package com.example.imsthanosapplication

import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.imsthanosapplication.data.DatabaseHandler


class PathCompanion {
    companion object {
        var listOfPoints: MutableList<Point> = mutableListOf()
    }
}

class CanvasActivity() : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_canvas)

        val routeId = intent.getStringExtra("routeID")
        val db = DatabaseHandler.db
        val displayMetrics = DisplayMetrics()
        val TAG = "DocSnippets"

        windowManager.defaultDisplay.getMetrics(displayMetrics)
        PathObject.height = displayMetrics.heightPixels
        PathObject.width = displayMetrics.widthPixels

        var imageView = findViewById<ImageView>(R.id.path_imageView)
        val bitmap: Bitmap = Bitmap.createBitmap(PathObject.width, PathObject.height, Bitmap.Config.ARGB_8888)

        db.collection("Routes").document(routeId!!).collection("positions").get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    PathObject.startPoint = Point(PathObject.width/2,PathObject.height/2)
                    PathObject.addPoint(PathObject.startPoint)
                    val x = document.get("x")
                    val y = document.get("y")
                    PathCompanion.listOfPoints.add(Point(x.hashCode(), y.hashCode()))
                    PathObject.drawPath(bitmap)
                    imageView.background = BitmapDrawable(resources, bitmap)
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents from database: ", exception)
            }

    }

    override fun onDestroy() {
        super.onDestroy()
        PathCompanion.listOfPoints.clear()
    }

}