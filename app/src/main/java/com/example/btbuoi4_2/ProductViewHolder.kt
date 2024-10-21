package com.example.btbuoi4_2

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val nameTextView: TextView = itemView.findViewById(R.id.productName)
    private val priceTextView: TextView = itemView.findViewById(R.id.productPrice)
    private val DescriptionTextView: TextView = itemView.findViewById(R.id.productDescription)

    fun bind(product: Product) {
        nameTextView.text = product.name
        priceTextView.text = product.price.toString()
        DescriptionTextView.text = product.description.toString()
    }
}