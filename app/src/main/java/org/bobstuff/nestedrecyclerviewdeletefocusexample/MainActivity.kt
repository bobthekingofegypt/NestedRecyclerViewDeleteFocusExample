package org.bobstuff.nestedrecyclerviewdeletefocusexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.parent_recycler)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)

        val adapter = CustomParentAdapter()
        recyclerView.adapter = adapter
    }

    inner class CustomParentAdapter: RecyclerView.Adapter<CustomParentViewHolder>() {
        override fun getItemCount(): Int {
            return 6
        }

        override fun onBindViewHolder(holder: CustomParentViewHolder, position: Int) {
            holder.titleView.text = "List $position"
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomParentViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
            return CustomParentViewHolder(view)
        }
    }

    inner class CustomParentViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val titleView = view.findViewById<TextView>(R.id.title)!!

        init {
            val recyclerView = view.findViewById<RecyclerView>(R.id.recycler)
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

            val adapter = CustomAdapter()
            adapter.setItems(mutableListOf("one", "two", "three", "four", "five", "six"))
            recyclerView.adapter = adapter


            val button = view.findViewById<Button>(R.id.delete_item)
            button.setOnClickListener {
                adapter.removeItem(1)
            }
        }
    }

    inner class CustomAdapter: RecyclerView.Adapter<CustomViewHolder>() {
        private val items = mutableListOf<String>()

        fun setItems(items: List<String>) {
            this.items.addAll(items)
        }

        fun removeItem(index: Int) {
            items.removeAt(index)
            notifyItemRemoved(index)
        }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            val item = items[position]
            holder.textView.text = item
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_label, parent, false)
            return CustomViewHolder(view)
        }
    }

    inner class CustomViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.item_textview)
    }
}
