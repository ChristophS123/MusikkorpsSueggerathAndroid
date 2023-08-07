package de.christoph.musikkorpsminus

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import de.christoph.musikkorpsminus.calendar.Event
import de.christoph.musikkorpsminus.calendar.EventAdminAdapter
import de.christoph.musikkorpsminus.firebase.FirebaseCloud
import de.christoph.musikkorpsminus.user.User
import de.christoph.musikkorpsminus.utils.Constants
import de.christoph.musikkorpsminus.utils.Utils
import kotlinx.android.synthetic.main.activity_event_admin.*

class EventAdminActivity : BaseActivity() {

    var event:Event? = null
    var users:ArrayList<User>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_admin)
        Utils().setUpToolbar(this, tb_event_admin, "Termin")
        event = intent.extras!!["event"] as Event?
        showProgressDialog()
        FirebaseCloud().loadUsers(this)
        cmd_cancel_event.setOnClickListener {
            showProgressDialog()
            FirebaseCloud().cancelEvent(this@EventAdminActivity, event)
        }
        cmd_select_room_view.setOnClickListener {
            val intent:Intent = Intent(this@EventAdminActivity, RoomViewActivity::class.java)
            intent.putExtra("event", event)
            startActivity(intent)
        }
    }

    fun loadedUsers(users: java.util.ArrayList<User>) {
        hideProgressDialog()
        this.users = users
        setUpRecyclerviews()
    }

    private fun setUpRecyclerviews() {
        setUpAltoSaxophones()
        setUpTenorSaxophones()
        setUpTrombones()
        setUpTrumpets()
        setUpFlutes()
        setUpPercussion()
        setUpBaritone()
        setUpClarinets()
    }

    private fun setUpAltoSaxophones() {
        val currentUsers:ArrayList<User> = ArrayList()
        for(i in users!!) {
            if(i.instrument.equals(Constants.ALTO_SAXOPHONE))
                currentUsers.add(i)
        }
        rv_alto_saxophone.layoutManager = LinearLayoutManager(this)
        rv_alto_saxophone.adapter = EventAdminAdapter(this, currentUsers, event!!)
    }

    private fun setUpTenorSaxophones() {
        val currentUsers:ArrayList<User> = ArrayList()
        for(i in users!!) {
            if(i.instrument.equals(Constants.TENOR_SAXOPHONE))
                currentUsers.add(i)
        }
        rv_tenor_saxophone.layoutManager = LinearLayoutManager(this)
        rv_tenor_saxophone.adapter = EventAdminAdapter(this, currentUsers, event!!)
    }

    private fun setUpTrombones() {
        val currentUsers:ArrayList<User> = ArrayList()
        for(i in users!!) {
            if(i.instrument.equals(Constants.TROMBONE))
                currentUsers.add(i)
        }
        rv_trombones.layoutManager = LinearLayoutManager(this)
        rv_trombones.adapter = EventAdminAdapter(this, currentUsers, event!!)
    }

    private fun setUpTrumpets() {
        val currentUsers:ArrayList<User> = ArrayList()
        for(i in users!!) {
            if(i.instrument.equals(Constants.TRUMPET))
                currentUsers.add(i)
        }
        rv_trumpetes.layoutManager = LinearLayoutManager(this)
        rv_trumpetes.adapter = EventAdminAdapter(this, currentUsers, event!!)
    }

    private fun setUpFlutes() {
        val currentUsers:ArrayList<User> = ArrayList()
        for(i in users!!) {
            if(i.instrument.equals(Constants.FLUTE))
                currentUsers.add(i)
        }
        rv_flute.layoutManager = LinearLayoutManager(this)
        rv_flute.adapter = EventAdminAdapter(this, currentUsers, event!!)
    }

    private fun setUpPercussion() {
        val currentUsers:ArrayList<User> = ArrayList()
        for(i in users!!) {
            if(i.instrument.equals(Constants.PERCUSSION))
                currentUsers.add(i)
        }
        rv_percussion.layoutManager = LinearLayoutManager(this)
        rv_percussion.adapter = EventAdminAdapter(this, currentUsers, event!!)
    }

    private fun setUpBaritone() {
        val currentUsers:ArrayList<User> = ArrayList()
        for(i in users!!) {
            if(i.instrument.equals(Constants.BARITONE))
                currentUsers.add(i)
        }
        rv_baritone.layoutManager = LinearLayoutManager(this)
        rv_baritone.adapter = EventAdminAdapter(this, currentUsers, event!!)
    }

    private fun setUpClarinets() {
        val currentUsers:ArrayList<User> = ArrayList()
        for(i in users!!) {
            if(i.instrument.equals(Constants.CLARINET))
                currentUsers.add(i)
        }
        rv_clarinet.layoutManager = LinearLayoutManager(this)
        rv_clarinet.adapter = EventAdminAdapter(this, currentUsers, event!!)
    }


}