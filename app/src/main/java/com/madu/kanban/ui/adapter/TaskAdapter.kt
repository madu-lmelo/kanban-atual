package com.madu.kanban.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.madu.kanban.databinding.ItemTaskBinding
import com.madu.kanban.model.Status
import com.madu.kanban.model.Task

class TaskAdapter(
    private val context: Context,
    private val taskList: List<Task>
) : RecyclerView.Adapter<TaskAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun getItemCount() = taskList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val task = taskList[position]
        holder.binding.textDescription.text = task.description

        setIndicators(task, holder)
    }

    private fun setIndicators(task: Task, holder: MyViewHolder){
        when(task.status){
            Status.TODO -> {
                holder.binding.buttonBack.isVisible = false
            }
            Status.DOING -> {
                //configurar vas cores diferentes para as setas
                holder.binding.buttonBack.setColorFilter(ContextCompat.getColor(context, R.color.color_status_todo))
            }

        }
    }

    inner class MyViewHolder(val binding: ItemTaskBinding)
        : RecyclerView.ViewHolder(binding.root)
}