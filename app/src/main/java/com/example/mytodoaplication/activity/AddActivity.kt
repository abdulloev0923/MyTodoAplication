package com.example.mytodoaplication.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.mytodoaplication.R
import com.example.mytodoaplication.data.Todo
import com.example.mytodoaplication.data.TodoDatabase
import com.example.mytodoaplication.databinding.ActivityAddBinding
import kotlinx.coroutines.launch

class AddActivity : AppCompatActivity() {
    lateinit var binding:ActivityAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }

    private fun addUser() {
        val title = binding.etTitle.text.toString()
        val description = binding.etDescription.text.toString()

        lifecycleScope.launch {
            val todo = Todo(
                title = title, description = description
            )
            TodoDatabase(this@AddActivity).getTodoDao().addTodo(todo)
        }

        val intent = Intent(this@AddActivity, MainActivity::class.java)
        startActivity(intent)

        Toast.makeText(this, "Successful added", Toast.LENGTH_SHORT).show()

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.save_action ->{
                addUser()
            }
            R.id.back ->{
                val intent = Intent(this@AddActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }


}