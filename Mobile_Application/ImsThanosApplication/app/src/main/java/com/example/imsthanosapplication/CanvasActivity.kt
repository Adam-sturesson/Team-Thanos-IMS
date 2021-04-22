package com.example.imsthanosapplication

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.PathShape
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class CanvasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_canvas)

        var imageV = findViewById<ImageView>(R.id.imageV)

        val bitmap: Bitmap = Bitmap.createBitmap(700, 1000, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        var shapeDrawable: ShapeDrawable

        var path = Path()
        path.moveTo(0F,12F)
        path.lineTo(100F,12F)
        path.moveTo(100F,12F)
        path.lineTo(100F,100F)

        var pathShape = PathShape(path,500F,500F)
        shapeDrawable = ShapeDrawable(pathShape)
        shapeDrawable.setBounds(100,500,600,800)
        shapeDrawable.paint.color = Color.parseColor("#009191")
        shapeDrawable.paint.style = Paint.Style.FILL_AND_STROKE
        shapeDrawable.paint.strokeWidth = 10F
        shapeDrawable.draw(canvas)

        // now bitmap holds the updated pixels

        // set bitmap as background to ImageView
        imageV.background = BitmapDrawable(resources, bitmap)

    }
}