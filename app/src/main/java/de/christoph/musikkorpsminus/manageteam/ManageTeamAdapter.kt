package de.christoph.musikkorpsminus.manageteam

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.christoph.musikkorpsminus.ManageTeamActivity
import de.christoph.musikkorpsminus.R
import de.christoph.musikkorpsminus.RoomViewActivity
import de.christoph.musikkorpsminus.calendar.CalendarAdapter
import de.christoph.musikkorpsminus.firebase.FirebaseCloud
import de.christoph.musikkorpsminus.user.User
import de.christoph.musikkorpsminus.utils.Constants
import kotlinx.android.synthetic.main.dialog_instrument.*
import kotlinx.android.synthetic.main.item_team.view.*

class ManageTeamAdapter(var activity:ManageTeamActivity, var context: Context, val users:ArrayList<User>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private class MyViewHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_team,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.tv_item_team_name.setText(users[position].username)
        if(users[position].instrument == "" || users[position].instrument == null)
            holder.itemView.tv_item_team_instrument.text = "Kein"
        else
            holder.itemView.tv_item_team_instrument.text = users[position].instrument
        holder.itemView.tv_item_team_instrument.setOnClickListener {
            var instrumentDialog = Dialog(context)
            instrumentDialog.setContentView(R.layout.dialog_instrument)
            instrumentDialog.setTitle("Instrument wählen")
            instrumentDialog.show()
            instrumentDialog.cmd_dialog_instrument_alto_saxophone.setOnClickListener { setInstrument(Constants.ALTO_SAXOPHONE, users[position], holder.itemView.tv_item_team_instrument, instrumentDialog) }
            instrumentDialog.cmd_dialog_instrument_tenor_saxophone.setOnClickListener { setInstrument(Constants.TENOR_SAXOPHONE, users[position], holder.itemView.tv_item_team_instrument, instrumentDialog) }
            instrumentDialog.cmd_dialog_instrument_trombone.setOnClickListener { setInstrument(Constants.TROMBONE, users[position], holder.itemView.tv_item_team_instrument, instrumentDialog) }
            instrumentDialog.cmd_dialog_instrument_trumpet.setOnClickListener { setInstrument(Constants.TRUMPET, users[position], holder.itemView.tv_item_team_instrument, instrumentDialog) }
            instrumentDialog.cmd_dialog_instrument_flute.setOnClickListener { setInstrument(Constants.FLUTE, users[position], holder.itemView.tv_item_team_instrument, instrumentDialog) }
            instrumentDialog.cmd_dialog_instrument_percussion.setOnClickListener { setInstrument(Constants.PERCUSSION, users[position], holder.itemView.tv_item_team_instrument, instrumentDialog) }
            instrumentDialog.cmd_dialog_instrument_baritone.setOnClickListener { setInstrument(Constants.BARITONE, users[position], holder.itemView.tv_item_team_instrument, instrumentDialog) }
            instrumentDialog.cmd_dialog_instrument_clarinet.setOnClickListener { setInstrument(Constants.CLARINET, users[position], holder.itemView.tv_item_team_instrument, instrumentDialog) }
        }
        holder.itemView.tv_item_team_seat.setOnClickListener {
            var intent:Intent = Intent(activity, RoomViewActivity::class.java)
            intent.putExtra("selectUser", users[position])
            intent.putExtra("isSelectMode", true)
            activity.startActivity(intent)
        }
    }

    private fun setInstrument(instrument:String, user:User, text:TextView, dialog:Dialog) {
        activity.showProgressDialog()
        text.text = instrument
        dialog.closeOptionsMenu()
        FirebaseCloud().changeInstrument(activity, instrument, user)
    }

    override fun getItemCount(): Int {
        return users.size
    }

}