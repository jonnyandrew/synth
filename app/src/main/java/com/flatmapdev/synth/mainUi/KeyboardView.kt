package com.flatmapdev.synth.mainUi

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.flatmapdev.synth.R


/**
 * TODO: document your custom view class.
 */
class KeyboardView : View {

    private var _numKeys = DEFAULT_NUM_KEYS
    private var curKeyDown: Int? = null

    private var contentWidth = width - paddingLeft - paddingRight
    private var keyWidth = contentWidth / numKeys

    var keyTouchListener: ((Int?) -> Unit)? = null

    var numKeys: Int
        get() = _numKeys
        set(value) {
            _numKeys = value
            invalidate()
        }

    private val keyAPaint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.CYAN
    }

    private val keyBPaint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.MAGENTA
    }

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.KeyboardView, defStyle, 0
        )

        _numKeys = a.getInteger(
            R.styleable.KeyboardView_numKeys,
            DEFAULT_NUM_KEYS
        )

        a.recycle()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        contentWidth = width - paddingLeft - paddingRight
        keyWidth = contentWidth / numKeys
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        (0 until numKeys).forEach { keyIndex ->
            // Paint keys alternately
            val keyPaint = if (keyIndex % 2 == 0) {
                keyAPaint
            } else {
                keyBPaint
            }

            val left = paddingLeft + (keyIndex * keyWidth).toFloat()
            val right = left + keyWidth - 1
            val bottom = (height - paddingBottom).toFloat()
            val top = paddingTop.toFloat()

            // Draw one key
            canvas.drawRect(left, top, right, bottom, keyPaint)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val keyTouchListener = keyTouchListener ?: return false

        if (event.action == MotionEvent.ACTION_UP) {
            curKeyDown = null
            keyTouchListener(null)
            return true
        }

        // If the touch falls in the padding, ignore it
        if (event.x <= paddingLeft || event.x >= width - paddingRight
            || event.y < paddingTop || event.y >= height - paddingBottom
        ) {
            return true
        }

        val index = ((event.x - paddingLeft) / keyWidth).toInt()
        if (index != curKeyDown) {
            curKeyDown = index
            keyTouchListener(index)
        }

        return true
    }

    companion object {
        private const val DEFAULT_NUM_KEYS = 10
    }
}
