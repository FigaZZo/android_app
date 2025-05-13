package com.example.laba3.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.laba3.MyApplication
import com.example.laba3.R
import com.example.laba3.databinding.ActivityAddFeedbackBinding
import com.example.laba3.viewmodel.FeedbackAddViewmodel
import kotlinx.coroutines.launch

class AddFeedbackActivity : AppCompatActivity() {
    companion object {
        fun getIntent(context: Context) = Intent(context, AddFeedbackActivity::class.java)
    }

    private lateinit var binding: ActivityAddFeedbackBinding
    private val viewmodel by viewModels<FeedbackAddViewmodel> {
        viewModelFactory {
            initializer {
                FeedbackAddViewmodel((application as MyApplication).feedbackRepository)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddFeedbackBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        viewmodel.getText()?.let {
            binding.editTextText.setText(it)
        }
        viewmodel.getRatingFunc()?.let {
            binding.radioGroupRatingFunc.check(it)
        }
        viewmodel.getRatingDesign()?.let {
            binding.radioGroupRatingDesign.check(it)
        }

        binding.buttonPushFeedback.setOnClickListener {
            val text = binding.editTextText.text.toString()
            val ratingFunc =
                if (binding.radioGroupRatingFunc.checkedRadioButtonId == -1) null
                else binding.radioGroupRatingFunc.checkedRadioButtonId
            val ratingDesign =
                if (binding.radioGroupRatingDesign.checkedRadioButtonId == -1) null
                else binding.radioGroupRatingDesign.checkedRadioButtonId

            Log.d("Dias", "I am here")

            lifecycleScope.launch {
                val result = viewmodel.addNote(text, ratingFunc, ratingDesign)
                when (result) {
                    is Result.Error -> {
                        Toast.makeText(
                            this@AddFeedbackActivity,
                            "Error occured",
                            Toast.LENGTH_SHORT
                        ).show()
                        viewmodel.saveState(text, ratingFunc, ratingDesign)
                        this@AddFeedbackActivity.onCreate(null)
                    }
                    else -> {
                        Toast.makeText(
                            this@AddFeedbackActivity,
                            "Feedback added",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    }
                }
            }
        }
    }
}