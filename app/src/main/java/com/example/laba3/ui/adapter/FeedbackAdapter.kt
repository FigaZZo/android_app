package com.example.laba3.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.laba3.R
import com.example.laba3.entity.Feedback

class FeedbackAdapter: Adapter<FeedbackViewHolder>() {
    private val feedbacks = mutableListOf<Feedback>()

    fun getFeedbacks() = List<Feedback>(feedbacks.size) {
        feedbacks[it]
    }

    fun setFeedbacks(feedbacks: Collection<Feedback>) {
        this.feedbacks.clear()
        this.feedbacks.addAll(feedbacks)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedbackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.feedback_template,
            parent,
            false
        )
        return FeedbackViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeedbackViewHolder, position: Int) {
        val feedback = feedbacks[position]
        val view = holder.textView
        view.text = feedback.text
    }

    override fun getItemCount() = feedbacks.size
}

class FeedbackViewHolder(view: View): ViewHolder(view) {
    var textView: TextView = view.findViewById(R.id.textViewFeedbackTemplate)
        private set
}