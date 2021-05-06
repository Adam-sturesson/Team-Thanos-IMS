package com.example.imsthanosapplication

import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore


class TestData {
    companion object {
        var testPath: MutableList<Point> = mutableListOf()
    }
}

class CanvasActivity() : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_canvas)
        val db = FirebaseFirestore.getInstance()
        val displayMetrics = DisplayMetrics()
        val TAG = "DocSnippets"
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        PathObject.height = displayMetrics.heightPixels
        PathObject.width = displayMetrics.widthPixels

        var imageV = findViewById<ImageView>(R.id.imageV)
        val bitmap: Bitmap = Bitmap.createBitmap(PathObject.width, PathObject.height, Bitmap.Config.ARGB_8888)

       // PathObject.startPoint = Point(PathObject.width/2,PathObject.height/2)
       // PathObject.addPoint(PathObject.startPoint)


        val docRef = db.collection("Path")
        docRef.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    PathObject.startPoint = Point(PathObject.width/2,PathObject.height/2)
                    PathObject.addPoint(PathObject.startPoint)
                    val x = document.get("x")
                    val y = document.get("y")
                    TestData.testPath.add(Point(x.hashCode(), y.hashCode()))
               //     TestData.testPath.add(Point(100,175))
                    PathObject.drawPath(bitmap)
                    imageV.background = BitmapDrawable(resources, bitmap)
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }

     /*   //TEST
        TestData.testPath.add(Point(1080/2,1794/2))
        TestData.testPath.add(Point(540,1700))
        TestData.testPath.add(Point(10,300))
        TestData.testPath.add(Point(100,175))
        TestData.testPath.add(Point(260,100))
        TestData.testPath.add(Point(540,300))
        TestData.testPath.add(Point(1080-260,100))
        TestData.testPath.add(Point(980,175))
        TestData.testPath.add(Point(1070,300))
        TestData.testPath.add(Point(540,1700))
        Log.d("hejsan",PathObject.height.toString())
        Log.d("hejsan",PathObject.width.toString())
*/




    }

}