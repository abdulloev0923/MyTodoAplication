package com.example.mytodoaplication.adapter

import android.text.method.TextKeyListener
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodoaplication.R
import com.example.mytodoaplication.data.Todo
import java.util.*

class TodoAdapter:RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    private var list = mutableListOf<Todo>()
    private var actionEdit:((Todo)->Unit)? = null
    private var actionDelete:((Todo)->Unit)? = null
    class TodoViewHolder(itemview: View):RecyclerView.ViewHolder(itemview) {
        var title:TextView = itemview.findViewById(R.id.tv_title)
        var description:TextView = itemview.findViewById(R.id.tv_description)
        val actionEdit = itemview.findViewById<RelativeLayout>(R.id.relative)
//        val actionDelete = itemview.findViewById<ImageView>(R.id.action_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_list, parent, false)
        return TodoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todoList = list[position]
        holder.title.text = todoList.title
        holder.description.text = todoList.description

        holder.actionEdit.setOnClickListener{actionEdit?.invoke(todoList)}
//        holder.actionDelete.setOnClickListener{actionDelete?.invoke(todoList)}
    }

    fun setOnActionEditListener(callback : (Todo) -> Unit){
        this.actionEdit = callback
    }
    fun setOnActionDeleteListener(callback : (Todo) -> Unit){
        this.actionDelete = callback
    }

    fun setData(data:List<Todo>){
        list.apply {
            clear()
            addAll(data)
            notifyDataSetChanged()
        }
    }



}