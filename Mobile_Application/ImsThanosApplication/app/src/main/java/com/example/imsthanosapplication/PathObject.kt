package com.example.imsthanosapplication

import android.graphics.*
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.PathShape
import com.example.imsthanosapplication.ui.PathCompanion

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
        shapeDrawable.setBounds(0,0,this.width,this.height)
        shapeDrawable.paint.color = Color.parseColor("#009191")
        shapeDrawable.paint.style = Paint.Style.FILL_AND_STROKE
        shapeDrawable.paint.strokeWidth = 10F
        shapeDrawable.draw(canvas)
    }

    fun drawObstacles(bitmap: Bitmap){
        val canvas = Canvas(bitmap)
        var shapeDrawable: ShapeDrawable

        for (i in 0 until PathCompanion.listOfObstacles.size) {
            val left = PathCompanion.listOfObstacles[i].x - 10
            val top = PathCompanion.listOfObstacles[i].y - 10
            val right = PathCompanion.listOfObstacles[i].x + 10
            val bottom = PathCompanion.listOfObstacles[i].y + 10
            shapeDrawable = ShapeDrawable(OvalShape())
            shapeDrawable.setBounds( left, top, right, bottom)
            shapeDrawable.paint.color = Color.parseColor("#FF0000")
            shapeDrawable.draw(canvas)
        }
    }

}