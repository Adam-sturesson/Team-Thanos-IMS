package com.example.imsthanosapplication

import android.graphics.*
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.PathShape

object PathObject {
    var height = 0
    var width = 0
    var startPoint:Point = Point()
    var path: MutableList<Point> = mutableListOf()

    fun addPoint(point:Point){
        path.add(point)
    }

    fun drawPath(bitmap: Bitmap){
        val canvas = Canvas(bitmap)
        var shapeDrawable: ShapeDrawable
        var path = Path()
        for (i in 0..PathCompanion.listOfPoints.size - 2){
            path.moveTo(PathCompanion.listOfPoints[i].x.toFloat(), PathCompanion.listOfPoints[i].y.toFloat())
            path.lineTo(PathCompanion.listOfPoints[i+1].x.toFloat(), PathCompanion.listOfPoints[i+1].y.toFloat())
        }

        var pathShape = PathShape(path,this.width.toFloat(),this.height.toFloat())

        shapeDrawable = ShapeDrawable(pathShape)
        shapeDrawable.setBounds(50,50,this.width-50,this.height-50)
        shapeDrawable.paint.color = Color.parseColor("#009191")
        shapeDrawable.paint.style = Paint.Style.FILL_AND_STROKE
        shapeDrawable.paint.strokeWidth = 10F
        shapeDrawable.draw(canvas)
    }

}