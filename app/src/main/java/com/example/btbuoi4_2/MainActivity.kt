package com.example.btbuoi4_2

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FirebaseRecyclerAdapter<Product, ProductViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        setupRecyclerView()

        findViewById<Button>(R.id.addButton).setOnClickListener {
            var productName = findViewById<EditText>(R.id.Pname).text.toString()
            var productPrice = findViewById<EditText>(R.id.Pprice).text.toString()
            var productDescription = findViewById<EditText>(R.id.Pdescription).text.toString()
            if (productName.isNotEmpty() && productPrice.isNotEmpty() && productDescription.isNotEmpty()) {
                addProduct(Product(productName, productPrice.toDouble(), productDescription))
                Toast.makeText(this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecyclerView() {
        val options = FirebaseRecyclerOptions.Builder<Product>()
            .setQuery(FirebaseDatabase.getInstance().getReference("products"), Product::class.java)
            .build()

        adapter = object : FirebaseRecyclerAdapter<Product, ProductViewHolder>(options) {
            override fun onBindViewHolder(holder: ProductViewHolder, position: Int, model: Product) {
                holder.bind(model)

            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
                return ProductViewHolder(view)
            }
        }

        recyclerView.adapter = adapter
        adapter.startListening()
    }

    private fun addProduct(product: Product) {
        val database = FirebaseDatabase.getInstance().getReference("products")
        val productId = database.push().key
        productId?.let {
            database.child(it).setValue(product)
        }
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}