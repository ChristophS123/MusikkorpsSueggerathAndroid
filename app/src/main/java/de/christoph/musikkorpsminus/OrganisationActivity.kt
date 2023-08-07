package de.christoph.musikkorpsminus

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import de.christoph.musikkorpsminus.calendar.Event
import de.christoph.musikkorpsminus.firebase.FirebaseCloud
import de.christoph.musikkorpsminus.utils.Utils
import kotlinx.android.synthetic.main.activity_calendar.*
import kotlinx.android.synthetic.main.activity_organisation.*
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.TemporalAdjusters
import java.util.*

class OrganisationActivity : BaseActivity() {

    var trainings:ArrayList<Event>? = null
    var waiting:Boolean = false

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_organisation)
        Utils().setUpToolbar(this, tb_organisation, "Organisation")
        showProgressDialog()
        FirebaseCloud().loadEvents(this)
        cmd_organisation_generate_training.setOnClickListener { generateFiveTrainings() }
        cmd_organisation_generate_event.setOnClickListener {
            startActivity(Intent(this@OrganisationActivity, AddEventActivity::class.java))
        }
        cmd_organisation_manage_team.setOnClickListener {
            startActivity(Intent(this@OrganisationActivity, ManageTeamActivity::class.java))
        }
        cmd_organisation_create_voting.setOnClickListener {
            startActivity(Intent(this@OrganisationActivity, CreateVotingActivity::class.java))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadedEvents(events: java.util.ArrayList<Event>) {
        var trainings:ArrayList<Event> = ArrayList()
        for(currentEvent:Event in events!!) {
            if(currentEvent.isTraining) {
                trainings.add(currentEvent)
            }
        }
        this.trainings = trainings
        FirebaseCloud().tryDeleteEvents(this, events)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun generateFiveTrainings() {
        if(waiting) {
            showErrorSnackbar("Bitte warte einen Moment")
            return
        }
        showProgressDialog()
        var lastTraining:Event? = null
        for(current in trainings!!)
            lastTraining = current
        var lastTrainingDate:LocalDate? = null
        if(lastTraining != null)
            lastTrainingDate = LocalDate.of(lastTraining!!.year, lastTraining!!.month, lastTraining!!.day)
        val dateTime: LocalDate = LocalDate.now()
        var nextMonday:LocalDate? = null
        nextMonday = if(lastTrainingDate == null) {
            dateTime.with(TemporalAdjusters.next(DayOfWeek.MONDAY))
        } else {
            if(dateTime > lastTrainingDate)
                dateTime.with(TemporalAdjusters.next(DayOfWeek.MONDAY))
            else
                lastTrainingDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY))
        }
        for(i in 0..4) {
            var id:String = "Proben_" + System.currentTimeMillis() + "$i"
            var date:LocalDate = nextMonday
            if(i != 0)
                date = date.plusDays((i * 7).toLong())
            FirebaseCloud().addTraining(this, id, date)
        }
        waiting = true
        hideProgressDialog()
        Handler().postDelayed({
            waiting = false
        }, 5000)
    }

}