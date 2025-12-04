package com.madu.kanban.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.madu.kanban.R
import com.madu.kanban.databinding.ItemTaskBinding
import com.madu.kanban.model.Status
import com.madu.kanban.model.Task

class TaskAdapter(
    private val taskSelected: (Task, Int) -> Unit,
    private val context: Context
) : ListAdapter<Task, TaskAdapter.MyViewHolder>(DIFF_CALLBACK) {

    companion object {

        val SELECT_BACK = 1
        val SELECT_REMOVER = 2
        val SELECT_EDIT = 3
        val SELECT_DETAILS = 4
        val SELECT_NEXT = 5

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val task = getItem(position)

        holder.binding.textDescription.text = task.description
        setIndicators(task, holder)
    }

    private fun setIndicators(task: Task, holder: MyViewHolder) {

        holder.binding.buttonForward.isVisible = true
        holder.binding.buttonBack.isVisible = true

        when (task.status) {
            Status.TODO -> {
                holder.binding.buttonBack.isVisible = false
                holder.binding.buttonForward.setOnClickListener { taskSelected(task, SELECT_NEXT) }
            }

            Status.DOING -> {
                holder.binding.buttonBack.setColorFilter(ContextCompat.getColor(context, R.color.color_status_todo))
                holder.binding.buttonForward.setColorFilter(ContextCompat.getColor(context, R.color.color_status_done))

                holder.binding.buttonForward.setOnClickListener { taskSelected(task, SELECT_NEXT) }
                holder.binding.buttonBack.setOnClickListener { taskSelected(task, SELECT_BACK) }
            }

            Status.DONE -> {
                holder.binding.buttonForward.isVisible = false
                holder.binding.buttonBack.setOnClickListener { taskSelected(task, SELECT_BACK) }
            }
        }

        holder.binding.buttonDelete.setOnClickListener { taskSelected(task, SELECT_REMOVER) }
        holder.binding.buttonEditar.setOnClickListener { taskSelected(task, SELECT_EDIT) }
        holder.binding.buttonDetails.setOnClickListener { taskSelected(task, SELECT_DETAILS) }
    }

    inner class MyViewHolder(val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root)
}
