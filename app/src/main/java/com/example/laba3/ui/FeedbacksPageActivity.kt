package com.example.laba3.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.RecyclerView
import com.example.laba3.MyApplication
import com.example.laba3.R
import com.example.laba3.ui.adapter.FeedbackAdapter
import com.example.laba3.viewmodel.FeedbackViewmodel
import kotlinx.coroutines.launch

class FeedbacksPageActivity : AppCompatActivity() {
    companion object {
        fun getIntent(context: Context) = Intent(context, FeedbacksPageActivity::class.java)
    }

    private val recyclerViewFeedback: LCEERecyclerView by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.recyclerViewFeedback) }
    private val textViewFilter: TextView by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.textViewFilter) }
    private val linearLayoutFilter: LinearLayout by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.linearLayoutFilter) }
    private val spinnerFunctionality: Spinner by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.spinnerFunctionality) }
    private val spinnerDesign: Spinner by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.spinnerDesign) }
    private val buttonAddFeedback: Button by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.buttonAddFeedback) }
    private val feedbackAdapter = FeedbackAdapter()
    private val viewmodel by viewModels<FeedbackViewmodel> {
        viewModelFactory {
            initializer {
                FeedbackViewmodel((application as MyApplication).feedbackRepository)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_feedbacks_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.feedbacksPage)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lifecycleScope.launch {
            recyclerViewFeedback.recyclerView.adapter = feedbackAdapter
            recyclerViewFeedback.setOnRetryClickListener {
                this@FeedbacksPageActivity.onCreate(null)
            }
            viewmodel.getFeedback().observe(this@FeedbacksPageActivity) {result ->

                when (result) {
                    is Result.Success -> {
                        feedbackAdapter.setFeedbacks(result.data)
                        recyclerViewFeedback.showRecyclerView()
                    }
                    is Result.Error -> {
                        recyclerViewFeedback.showErrorView()
                    }
                }
            }

            spinnerDesign.adapter = ArrayAdapter(
                this@FeedbacksPageActivity,
                R.layout.spinner_item_text,
                resources.getStringArray(R.array.design_filter)
            )
            spinnerFunctionality.adapter = ArrayAdapter(
                this@FeedbacksPageActivity,
                R.layout.spinner_item_text,
                resources.getStringArray(R.array.functionality_filter)
            )

            buttonAddFeedback.setOnClickListener {
                startActivity(AddFeedbackActivity.getIntent(this@FeedbacksPageActivity))
            }
        }
    }
}