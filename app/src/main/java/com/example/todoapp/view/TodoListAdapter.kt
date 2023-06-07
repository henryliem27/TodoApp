package com.example.todoapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.model.Todo

class TodoListAdapter(val todoList:ArrayList<Todo>,val adapterOnClick: (Todo)->Unit) :
    RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>() {
    class TodoViewHolder(var view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.todo_item_layout, parent, false)

        return TodoViewHolder(view)

    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        var checktask = holder.view.findViewById<CheckBox>(R.id.checkTask)
        checktask.text = todoList[position].title
        checktask.isChecked = false
        checktask.setOnCheckedChangeListener{ compoundButton, _ ->
            if(compoundButton.isChecked){
                adapterOnClick(todoList[position])}
            }
        var imgEdit = holder.view.findViewById<ImageView>(R.id.imgEdit)
        imgEdit.setOnClickListener{
            val action = TodoListFragmentDirections.actionEditTodo(todoList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }
    }


    fun updateTodoList(newTodoList: List<Todo>) {
        todoList.clear()
        todoList.addAll(newTodoList)
        notifyDataSetChanged()
    }

}