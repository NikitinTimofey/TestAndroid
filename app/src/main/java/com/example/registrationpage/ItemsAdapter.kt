package com.example.registrationpage

import android.content.ClipData.Item
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.io.ByteArrayOutputStream

class ItemsAdapter(var items: MutableList<com.example.registrationpage.data.Item>, var context: Context) : RecyclerView.Adapter<ItemsAdapter.MyViewHolder>() {

    private val firestore = FirebaseFirestore.getInstance()

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.item_list_image)
        val title: TextView = view.findViewById(R.id.item_list_title)
        val desc: TextView = view.findViewById(R.id.item_list_desc)
        val price: TextView = view.findViewById(R.id.item_list_price)
        val btn: Button = view.findViewById(R.id.item_list_button)
        val buttonDelete: Button = view.findViewById(R.id.button_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_in_list, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.title.text = items[position].brand
        holder.desc.text = "${items[position].year} г.,  ${items[position].transmission}, ${items[position].engineSize}, ${items[position].fuel}, ${items[position].body}, ${items[position].mileage } км"
        holder.price.text = items[position].price.toString() + "$"

        val base64String = items[position].imageUrl
        if (base64String.isNotEmpty()) {
            val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
            holder.image.setImageBitmap(bitmap)
        } else {
            holder.image.setImageResource(R.drawable.placeholder)
        }

        holder.btn.setOnClickListener {
            val intent = Intent(context, ItemActivity::class.java)
            intent.putExtra("itemDocumentId", items[position].documentId)
            context.startActivity(intent)
        }

        holder.buttonDelete.setOnClickListener {
            val currentUser = FirebaseAuth.getInstance().currentUser?.uid

            if (currentUser == items[position].userId) {
                firestore.collection("Items").document(items[position].documentId)
                    .delete()
                    .addOnSuccessListener {
                        items.removeAt(position)
                        notifyItemRemoved(position)
                        Toast.makeText(context, "Объявление удалено!", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(context, "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(context, "Вы можете удалять только свои объявления", Toast.LENGTH_SHORT).show()
            }
        }
    }
}