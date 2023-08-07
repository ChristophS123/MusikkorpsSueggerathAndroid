package de.christoph.musikkorpsminus

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import de.christoph.musikkorpsminus.calendar.CalendarAdapter
import de.christoph.musikkorpsminus.calendar.Event
import de.christoph.musikkorpsminus.firebase.FirebaseCloud
import de.christoph.musikkorpsminus.user.User
import de.christoph.musikkorpsminus.utils.Utils
import kotlinx.android.synthetic.main.activity_calendar.*

class CalendarActivity : BaseActivity() {

    private var events:ArrayList<Event>? = null
    private var user:User? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
        Utils().setUpToolbar(this, tb_calendar, "Kalender")
        showProgressDialog()
        FirebaseCloud().getUser(this)
        cb_promise_training.setOnClickListener {
            showProgressDialog()
            user!!.defaultPromise = !user!!.defaultPromise
            FirebaseCloud().togglePromiseTraining(this, user)
        }
        swipe_calendar.setOnRefreshListener {
            FirebaseCloud().loadEvents(this)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadedUser(user: User) {
        this.user = user
        cb_promise_training.isChecked = user.defaultPromise
        FirebaseCloud().loadEvents(this)
    }

    fun loadedEvents(events: java.util.ArrayList<Event>) {
        swipe_calendar.isRefreshing = false
        hideProgressDialog()
        this.events = events
        setUpEventRecyclerViews()
    }

    private fun setUpEventRecyclerViews() {
        var elseEvents:ArrayList<Event> = ArrayList()
        var trainings:ArrayList<Event> = ArrayList()
        for(currentEvent:Event in events!!) {
            if(currentEvent.isTraining) {
                trainings.add(currentEvent)
            } else {
                elseEvents.add(currentEvent)
            }
        }
        rv_training.layoutManager = LinearLayoutManager(this)
        rv_training.setHasFixedSize(true)
        var trainingAdapter:CalendarAdapter = CalendarAdapter(this, trainings, user!!)
        rv_training.adapter = trainingAdapter
        rv_events.layoutManager = LinearLayoutManager(this)
        rv_events.setHasFixedSize(true)
        var eventAdapter:CalendarAdapter = CalendarAdapter(this, elseEvents, user!!)
        rv_events.adapter = eventAdapter
    }

}