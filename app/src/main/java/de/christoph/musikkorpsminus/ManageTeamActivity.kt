package de.christoph.musikkorpsminus

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import de.christoph.musikkorpsminus.firebase.FirebaseCloud
import de.christoph.musikkorpsminus.manageteam.ManageTeamAdapter
import de.christoph.musikkorpsminus.user.User
import de.christoph.musikkorpsminus.utils.Utils
import kotlinx.android.synthetic.main.activity_manage_team.*

class ManageTeamActivity : BaseActivity() {

    var users:ArrayList<User>?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_team)
        Utils().setUpToolbar(this, tb_manage_team, "Vereinsmitglieder verwalten")
        FirebaseCloud().loadUsers(this)
    }

    fun loadedUsers(users: ArrayList<User>) {
        this.users = users
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        rv_manage_team.layoutManager = LinearLayoutManager(this)
        rv_manage_team.adapter = ManageTeamAdapter(this, this, users!!)
    }

}