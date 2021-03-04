package com.example.breatheeasy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.breatheeasy.R
import com.example.breatheeasy.additional.TrackingUtility
import com.example.breatheeasy.database.Run
import kotlinx.android.synthetic.main.display_run.view.*
import java.text.SimpleDateFormat
import java.util.*

class RunAdapter : RecyclerView.Adapter<RunAdapter.RunViewHolder>() {

    inner class RunViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView)

    val diffCallback = object : DiffUtil.ItemCallback<Run>(){

        override fun areItemsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list : List<Run>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunViewHolder {
        return RunViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.display_run,
                        parent,
                        false
                )
        )
    }

    override fun onBindViewHolder(holder: RunViewHolder, position: Int) {
        val run = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(run.img).into(ivRunImage)

            val cal = Calendar.getInstance().apply {
                timeInMillis = run.timestamp
            }

            val dateFormatted = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
            tvDate.text = dateFormatted.format(cal.time)

            val avgSpeed = "${run.meanSpeed} k/ph"
            tvAvgSpeed.text = avgSpeed

            val runDistance = "${run.distanceInMeters / 1000f} kms"
            tvDistance.text = runDistance

            tvTime.text = TrackingUtility.getFormattedStopwatchTime(run.timeInMilliseconds)

            val calories = "${run.caloriesBurned} k/cal"
            tvCalories.text = calories
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}