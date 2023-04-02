package com.example.mytodoaplication.activity

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.constraintlayout.motion.widget.OnSwipe
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodoaplication.R
import com.example.mytodoaplication.Swipe.Swipe
import com.example.mytodoaplication.adapter.TodoAdapter
import com.example.mytodoaplication.data.Todo
import com.example.mytodoaplication.data.TodoDatabase
import com.example.mytodoaplication.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var mAdapter: TodoAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.add.setOnClickListener {
            val intent = Intent(this@MainActivity, AddActivity::class.java)
            startActivity(intent)
        }

        lifecycleScope.launch {
            val todoList = TodoDatabase(this@MainActivity).getTodoDao().getAllTodo()
            mAdapter = TodoAdapter()
            binding.recyclerview.apply {
                layoutManager = GridLayoutManager(this@MainActivity, 2)
                adapter = mAdapter.apply {
                    setAdapter(todoList)
                    mAdapter?.setOnActionEditListener {
                        val intent = Intent(this@MainActivity, UpdateActivity::class.java)
                        intent.putExtra("Data", it)
                        startActivity(intent)
                    }
//                    mAdapter?.setOnActionDeleteListener {
//                        val builder = AlertDialog.Builder(this@MainActivity)
//                        builder.setMessage("Are you sure you want to delete?")
//                        builder.setPositiveButton("Yes"){p0,p1->
//                            lifecycleScope.launch {
//                                TodoDatabase(this@MainActivity).getTodoDao().deleteTodo(it)
//                                val list = TodoDatabase(this@MainActivity).getTodoDao().getAllTodo()
//                                setAdapter(list)
//                            }
//                            p0.dismiss()
//
//                        }
//                        builder.setNegativeButton("No"){ p0, p1->
//
//                            p0.dismiss()
//
//                        }
//
//                       builder.create().show()
//                }


                }
            }
        }

        val swipeToDeleteCallBack = object : Swipe() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                lifecycleScope.launch {
                    val position = viewHolder.adapterPosition
                    val list = TodoDatabase(this@MainActivity).getTodoDao().getAllTodo()
                    TodoDatabase(this@MainActivity).getTodoDao().deleteTodo(list[position])
                    binding.recyclerview.adapter?.notifyItemRemoved(position)
                    setAdapter(list)
                    Log.e("onSwiped", position.toString())
                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallBack)
        itemTouchHelper.attachToRecyclerView(binding.recyclerview)

    }

    fun setAdapter(list: List<Todo>) {
        mAdapter?.setData(list)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.DeleteAll -> {
                val builder = AlertDialog.Builder(this)

                builder.setMessage("Are you sure you want to delete all?")

                builder.setNegativeButton("No") { p0, p1 ->
                    p0.dismiss()
                }
                builder.setPositiveButton("Yes") { p0, p1 ->
                    lifecycleScope.launch {
                        TodoDatabase(this@MainActivity).getTodoDao().deleteAllTodo()
                        val list = TodoDatabase(this@MainActivity).getTodoDao().getAllTodo()
                        setAdapter(list)
                    }
                    p0.dismiss()
                }

                val dialog = builder.create()
                dialog.show()
            }
        }

        return super.onOptionsItemSelected(item)
    }


}
