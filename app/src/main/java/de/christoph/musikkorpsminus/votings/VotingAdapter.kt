package de.christoph.musikkorpsminus.votings

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import de.christoph.musikkorpsminus.R
import de.christoph.musikkorpsminus.VotingActivity
import de.christoph.musikkorpsminus.firebase.FirebaseCloud
import de.christoph.musikkorpsminus.user.User
import kotlinx.android.synthetic.main.item_voting.view.*

class VotingAdapter(val activity:VotingActivity, val context: Context, val votings:ArrayList<Voting>, val user: User) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private class MyViewHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_voting,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(user.admin == 1)
            holder.itemView.cmd_item_voting_delete.visibility = View.VISIBLE
        else
            holder.itemView.cmd_item_voting_delete.visibility = View.GONE
        holder.itemView.tv_item_voting_name.text = votings[position].name
        holder.itemView.tv_item_voting_count.text = votings[position].liked.size.toString()
        if(votings[position].liked.contains(user.id)) {
            holder.itemView.cmd_item_voting_like.setColorFilter(Color.BLUE)
        } else {
            holder.itemView.cmd_item_voting_like.setColorFilter(Color.GRAY)
        }
        holder.itemView.cmd_item_voting_like.setOnClickListener {
            FirebaseCloud().addLike(activity, user, votings[position])
            activity.loadedUser(user)  
        }
        holder.itemView.cmd_item_voting_delete.setOnClickListener {
            FirebaseCloud().deleteVoting(activity, votings[position])
        }
    }

    override fun getItemCount(): Int {
        return votings.size
    }

}