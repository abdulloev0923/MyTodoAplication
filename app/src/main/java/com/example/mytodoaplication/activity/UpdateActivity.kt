package com.example.mytodoaplication.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.example.mytodoaplication.R
import com.example.mytodoaplication.adapter.TodoAdapter
import com.example.mytodoaplication.data.Todo
import com.example.mytodoaplication.data.TodoDatabase
import com.example.mytodoaplication.databinding.ActivityAddBinding
import com.example.mytodoaplication.databinding.ActivityUpdateBinding
import kotlinx.coroutines.launch

class UpdateActivity : AppCompatActivity() {
    lateinit var binding: ActivityUpdateBinding
    private var todo: Todo? = null
    private var mAdapter: TodoAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        todo = intent.getSerializableExtra("Data"!!) as Todo?

        binding.titleUpdate.setText(todo?.title.toString())
        binding.descriptionUpdate.setText(todo?.description.toString())

    }

    private fun updateUser() {
        val title = binding.titleUpdate.text.toString()
        val description = binding.descriptionUpdate.text.toString()

        lifecycleScope.launch {
            val todos = Todo(title, description)
            todos.id = todo?.id ?: 0
            TodoDatabase(this@UpdateActivity).getTodoDao().updateTodo(todos)

            val intent = Intent(this@UpdateActivity, MainActivity::class.java)
            startActivity(intent)

            Toast.makeText(this@UpdateActivity, "Successful updated", Toast.LENGTH_SHORT).show()
        }

    }

    fun setAdapter(list:List<Todo>){
        mAdapter?.setData(list)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.update_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.update_action ->{
                updateUser()
            }
            R.id.back_main ->{
                val intent = Intent(this@UpdateActivity, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.delete_action -> {


                    val builder = AlertDialog.Builder(this@UpdateActivity)
                    builder.setMessage("Are you sure you want to delete?")
                    builder.setPositiveButton("Yes") { p0, p1 ->
                        lifecycleScope.launch {
                            TodoDatabase(this@UpdateActivity).getTodoDao().deleteTodo(Todo())
                            val list = TodoDatabase(this@UpdateActivity).getTodoDao().getAllTodo()
                            setAdapter(list)
                        }
                        p0.dismiss()

                    }
                    builder.setNegativeButton("No") { p0, p1 ->

                        p0.dismiss()

                    }

                    val dialog = builder.create()
                    dialog.show()
                }
            }


        return super.onOptionsItemSelected(item)
    }


}