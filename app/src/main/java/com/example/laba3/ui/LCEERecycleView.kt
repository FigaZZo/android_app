package com.example.laba3.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.laba3.R
import com.example.laba3.databinding.*

/**
 * Created by suson on 6/27/20
 * Custom recycler view with integrated error, empty and loading view
 */
class LCEERecyclerView constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)


    private val binding: LceeRecyclerLayoutBinding =
        LceeRecyclerLayoutBinding.inflate(LayoutInflater.from(context), this)

    private val errorBinding: RecyclerErrorLayoutBinding
    private val emptyBinding: RecyclerEmptyLayoutBinding

    // expose the recycler view
    val recyclerView: RecyclerView
        get() = binding.customRecyclerView

    init {

        // inflate the layout
        errorBinding = binding.customErrorView
        emptyBinding = binding.customEmptyView

//        context.theme.obtainStyledAttributes(
//            attrs,
//            R.styleable.LCEERecyclerView,
//            0,
//            0
//        ).apply {
//            try {
//                errorText = "Something went wrong"
//                emptyText =
//                    getString(R.styleable.LCEERecyclerView_emptyText) ?: "Nothing to show"
//                errorIcon = getResourceId(
//                    R.styleable.LCEERecyclerView_errorIcon,
//                    R.drawable.ic_error_loading
//                )
//                emptyIcon =
//                    getResourceId(R.styleable.LCEERecyclerView_emptyIcon, R.drawable.ic_empty_image)
//            } finally {
//                recycle()
//            }
//        }
    }

    fun showEmptyView(msg: String? = null) {
        recyclerView.visibility = View.GONE
        errorBinding.root.visibility = View.GONE

        emptyBinding.root.visibility = View.VISIBLE
    }

    fun showErrorView(msg: String? = null) {
        emptyBinding.root.visibility = View.GONE
        recyclerView.visibility = View.GONE

        errorBinding.root.visibility = View.VISIBLE
    }

    fun showRecyclerView() {
        errorBinding.root.visibility = View.GONE
        emptyBinding.root.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    fun setOnRetryClickListener(callback: () -> Unit) {
        errorBinding.retryButton.setOnClickListener {
            callback()
        }
    }
}