package de.christoph.musikkorpsminus.calendar

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import de.christoph.musikkorpsminus.CalendarActivity
import de.christoph.musikkorpsminus.EventAdminActivity
import de.christoph.musikkorpsminus.R
import de.christoph.musikkorpsminus.firebase.FirebaseCloud
import de.christoph.musikkorpsminus.user.User
import kotlinx.android.synthetic.main.item_event.view.*

class CalendarAdapter(val context: CalendarActivity, private val events:ArrayList<Event>, val user: User) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private class MyViewHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_event,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.tv_item_event_title.text = events[position].name
        holder.itemView.tv_item_event_date.text = "${events[position].day}.${events[position].month}.${events[position].year}: ${events[position].time} Uhr"
        holder.itemView.tv_item_event_date.setTextColor(Color.GRAY)
        holder.itemView.cmd_cancel.text = "Absagen"
        holder.itemView.cmd_cancel.setBackgroundColor(Color.RED)
        holder.itemView.cmd_promise.text = "Zusagen"
        holder.itemView.cmd_promise.setBackgroundColor(Color.GREEN)
        if(events[position].promised.contains(FirebaseAuth.getInstance().currentUser!!.uid)) {
            holder.itemView.cmd_promise.text = "Zugesagt"
            holder.itemView.cmd_promise.setBackgroundColor(Color.GRAY)
        } else if(events[position].cancelled.contains(FirebaseAuth.getInstance().currentUser!!.uid)) {
            holder.itemView.cmd_cancel.text = "Abgesagt"
            holder.itemView.cmd_cancel.setBackgroundColor(Color.GRAY)
        }
        if(events[position].isEventCancelled) {
            holder.itemView.tv_item_event_date.text = "Termin abgesagt"
            holder.itemView.tv_item_event_date.setTextColor(Color.RED)
        }
        holder.itemView.cmd_promise.setOnClickListener {
            if(events[position].promised.contains(user.id)) {
                context.showErrorSnackbar("Du hast bereits zugesagt.")
                return@setOnClickListener
            }
            holder.itemView.cmd_promise.text = "Zugesagt"
            holder.itemView.cmd_promise.setBackgroundColor(Color.GRAY)
            holder.itemView.cmd_cancel.text = "Absagen"
            holder.itemView.cmd_cancel.setBackgroundColor(Color.RED)
            FirebaseCloud().promise(context, user, events[position])
        }
        holder.itemView.cmd_cancel.setOnClickListener {
            if(events[position].cancelled.contains(user.id)) {
                context.showErrorSnackbar("Du hast bereits abgesagt.")
                return@setOnClickListener
            }
            holder.itemView.cmd_promise.text = "Zusagen"
            holder.itemView.cmd_promise.setBackgroundColor(Color.GREEN)
            holder.itemView.cmd_cancel.text = "Abgesagt"
            holder.itemView.cmd_cancel.setBackgroundColor(Color.GRAY)
            FirebaseCloud().cancel(context, user, events[position])
        }
        holder.itemView.setOnClickListener {
            if(user.admin == 0)
                return@setOnClickListener
            var intent:Intent = Intent(context, EventAdminActivity::class.java)
            intent.putExtra("event", events[position])
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return events.size
    }

}