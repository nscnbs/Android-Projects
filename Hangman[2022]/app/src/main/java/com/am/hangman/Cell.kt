package com.am.hangman

import android.content.Context
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout

class Cell : Button {

    constructor(context: Context?, value: String,id: Int) :super(context) {
        this.layoutParams = createLayoutParams()
        this.text = value
        this.id = id
        this.setAutoSizeTextTypeUniformWithConfiguration(1,100,1,TypedValue.COMPLEX_UNIT_DIP)
    }

    private fun createLayoutParams(): ViewGroup.LayoutParams? {
        val layoutParams = GridLayout.LayoutParams()
        layoutParams.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f)
        layoutParams.height = 0
        layoutParams.width = 0

        return layoutParams
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}