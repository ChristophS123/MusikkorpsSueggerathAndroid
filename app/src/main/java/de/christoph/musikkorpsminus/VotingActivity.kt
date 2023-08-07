package de.christoph.musikkorpsminus

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import de.christoph.musikkorpsminus.firebase.FirebaseCloud
import de.christoph.musikkorpsminus.user.User
import de.christoph.musikkorpsminus.utils.Utils
import de.christoph.musikkorpsminus.votings.Voting
import de.christoph.musikkorpsminus.votings.VotingAdapter
import kotlinx.android.synthetic.main.activity_voting.*

class VotingActivity : BaseActivity() {

    var votings:ArrayList<Voting>? = null
    var user: User? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voting)
        Utils().setUpToolbar(this, tb_voting, "Lied-Abstimmungen")
        showProgressDialog()
        FirebaseCloud().getUser(this)
    }

    fun loadedVotings(votings: ArrayList<Voting>) {
        hideProgressDialog()
        this.votings = votings
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        rv_voting.layoutManager = LinearLayoutManager(this)
        rv_voting.adapter = VotingAdapter(this, this, votings!!, user!!)
    }

    fun loadedUser(user: User) {
        this.user = user
        FirebaseCloud().loadVotings(this)
    }

}