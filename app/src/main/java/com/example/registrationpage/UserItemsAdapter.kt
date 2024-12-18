package com.example.registrationpage

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.registrationpage.data.Item

class UserItemsAdapter(
    private var userItems: List<Item>,
    private val context: Context,
    private val onDeleteItem: (Item, Int) -> Unit
) : RecyclerView.Adapter<UserItemsAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.item_list_image)
        val title: TextView = view.findViewById(R.id.item_list_title)
        val desc: TextView = view.findViewById(R.id.item_list_desc)
        val price: TextView = view.findViewById(R.id.item_list_price)
        val buttonDelete: Button = view.findViewById(R.id.button_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_in_list, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int = userItems.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = userItems[position]
        holder.title.text = item.brand
        holder.desc.text = item.text
        holder.price.text = "${item.price} $"

        if (item.imageUrl.isNotEmpty()) {
            val decodedBytes = Base64.decode(item.imageUrl, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
            holder.image.setImageBitmap(bitmap)
        } else {
            holder.image.setImageResource(R.drawable.placeholder)
        }

        holder.buttonDelete.setOnClickListener {
            onDeleteItem(item, position)
        }
    }

    fun updateList(newList: List<Item>) {
        userItems = newList
        notifyDataSetChanged()
    }
}
