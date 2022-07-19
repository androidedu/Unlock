package com.example.unlock

import android.content.AttributionSource
import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ImageView
import kotlin.math.sqrt

class PicUnlockView: ViewGroup{
    private val dotSize = dp2px(60)
    private var lineSize = 0
    private var space = 0

    constructor(context: Context):super(context){initUI()}
    constructor(context: Context,attrs:AttributeSet):super(context,attrs){initUI()}
    constructor(context: Context,attrs:AttributeSet,style:Int):super(context,attrs,style){initUI()}

    fun initUI(){
        //创建9个点
        initNineDot()
        //6条横线
        initLandscapeLine()
        //6条竖线
        initVerticalLine()
        //添加斜线
        initSlashLine()
    }

    private fun initSlashLine(){
        for (row in 0..1){
            for (column in 0..1) {
                ImageView(context).also {
                    it.setImageResource(R.drawable.line_left)
                    it.tag = 24+row*33+11*column
                    addView(it)
                }
            }
        }
        for (row in 0..1){
            for (column in 0..1) {
                ImageView(context).also {
                    it.setImageResource(R.drawable.line_right)
                    it.tag = 15+row*33+11*column
                    addView(it)
                }
            }
        }
    }

    private fun initVerticalLine(){
        for (row in 0..1){
            for (column in 0..2) {
                ImageView(context).also {
                    it.setImageResource(R.drawable.line_vertical)
                    it.tag = 14+row*33+11*column
                    addView(it)
                }
            }
        }
    }

    fun initLandscapeLine(){
        for (row in 0..2){
            for (column in 0..1) {
                ImageView(context).also {
                    it.setImageResource(R.drawable.line_horizontal)
                    it.tag = 12+row*33+11*column
                    addView(it)
                }
            }
        }
    }

    fun initNineDot(){
        for (i in 1..9){
            ImageView(context).apply {
                setImageResource(R.drawable.dot_normal)
                tag = "$i"
                addView(this)
            }
        }
    }

    //对9个点进行布局
    private fun layoutNineDot(){
        for (row in 0..2){
            for (column in 0..2){
                val left = column*(dotSize+space)
                val top = row*(dotSize+space)
                val right = left + dotSize
                val bottom = top + dotSize

                val index = row*3+column
                val dotView = getChildAt(index)
                dotView.layout(left,top,right,bottom)
            }
        }
    }

    private fun layoutHorizontalLine(){
        for (row in 0..2){
            for (column in 0..1){
                val left = dotSize + column*(space+dotSize)
                val top = dotSize/2 + row*(space+dotSize)
                val right = left + lineSize
                val bottom = top + dp2px(2)

                //找到这根线在父容器里面的索引
                val index = 9 + row*2 + column
                val lineView = getChildAt(index)
                lineView.layout(left,top,right,bottom)
            }
        }
    }

    private fun layoutSlashLine(){
        for (row in 0..1){
            for (column in 0..1){

                val left = dotSize/2.0 + dotSize* sqrt(2.0) /4 + column*(space+dotSize)
                val top = dotSize/2.0+dotSize* sqrt(2.0) /4 + row*(space+dotSize)
                val right = left + space+(1- sqrt(2.0)/2)*dotSize
                val bottom = top + space+(1- sqrt(2.0)/2)*dotSize

                //找到这根线在父容器里面的索引
                val index = 21 + row*2 + column
                val lineView = getChildAt(index)
                lineView.layout(left.toInt(),top.toInt(),right.toInt(),bottom.toInt())
            }
        }

        for (row in 0..1){
            for (column in 0..1){

                val left = dotSize/2.0 + dotSize* sqrt(2.0) /4 + column*(space+dotSize)
                val top = dotSize/2.0+dotSize* sqrt(2.0) /4 + row*(space+dotSize)
                val right = left + space+(1- sqrt(2.0)/2)*dotSize
                val bottom = top + space+(1- sqrt(2.0)/2)*dotSize

                //找到这根线在父容器里面的索引
                val index = 25 + row*2 + column
                val lineView = getChildAt(index)
                lineView.layout(left.toInt(),top.toInt(),right.toInt(),bottom.toInt())
            }
        }
    }

    private fun layoutVerticalLine(){
        for (row in 0..1){
            for (column in 0..2){
                val left = dotSize/2 + column*(space+dotSize)
                val top = dotSize + row*(space+dotSize)
                val right = left + dp2px(2)
                val bottom = top + lineSize

                //找到这根线在父容器里面的索引
                val index = 15 + row*3 + column
                val lineView = getChildAt(index)
                lineView.layout(left,top,right,bottom)
            }
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        //计算两个点之间的尺寸
        space  =  (width - 3*dotSize)/2
        lineSize = space
    }

    //定义自己的规则
    //这个方法里面不要去大量创建或者运算
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        layoutNineDot()
        layoutHorizontalLine()
        layoutVerticalLine()
        layoutSlashLine()
    }

    //dp -> px
    fun dp2px(dp:Int) = (resources.displayMetrics.density*dp).toInt()
}