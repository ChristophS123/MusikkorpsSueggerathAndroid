package de.christoph.musikkorpsminus.calendar

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import de.christoph.musikkorpsminus.R
import de.christoph.musikkorpsminus.user.User
import kotlinx.android.synthetic.main.item_event_admin_user.view.*

class EventAdminAdapter(val context: Context, private val users:ArrayList<User>, private val event:Event) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private class MyViewHolder(view: View): RecyclerView.ViewHolder(view)

    init {
        Log.d("test", event.toString())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_event_admin_user,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.tv_item_event_admin_username.text = users[position].username
        if(event.promised.contains(users[position].id)) {
            holder.itemView.tv_item_event_admin_status.text = "Zugesagt"
            holder.itemView.tv_item_event_admin_status.setTextColor(Color.GREEN)
        } else if(event.cancelled.contains(users[position].id)) {
            holder.itemView.tv_item_event_admin_status.text = "Abgesagt"
            holder.itemView.tv_item_event_admin_status.setTextColor(Color.RED)
        } else {
            holder.itemView.tv_item_event_admin_status.text = "Ausstehend"
            holder.itemView.tv_item_event_admin_status.setTextColor(Color.GRAY)
        }

    }

    override fun getItemCount(): Int {
        return users.size
    }

}