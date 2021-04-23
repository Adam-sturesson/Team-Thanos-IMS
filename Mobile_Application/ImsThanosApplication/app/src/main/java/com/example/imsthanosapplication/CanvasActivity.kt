package com.example.imsthanosapplication

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.PathShape
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class TestData {
    companion object {
        var testPath: MutableList<Point> = mutableListOf()
    }
}

class CanvasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_canvas)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        PathObject.height = displayMetrics.heightPixels
        PathObject.width = displayMetrics.widthPixels

        var imageV = findViewById<ImageView>(R.id.imageV)
        val bitmap: Bitmap = Bitmap.createBitmap(PathObject.width, PathObject.height, Bitmap.Config.ARGB_8888)

        PathObject.startPoint = Point(PathObject.width/2,PathObject.height/2)
        PathObject.addPoint(PathObject.startPoint)

        //TEST
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

        PathObject.drawPath(bitmap)

        imageV.background = BitmapDrawable(resources, bitmap)

    }
}