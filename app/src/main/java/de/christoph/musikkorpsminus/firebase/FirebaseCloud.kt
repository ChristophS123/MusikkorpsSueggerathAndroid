package de.christoph.musikkorpsminus.firebase

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import de.christoph.musikkorpsminus.*
import de.christoph.musikkorpsminus.calendar.Event
import de.christoph.musikkorpsminus.user.User
import de.christoph.musikkorpsminus.utils.Constants
import de.christoph.musikkorpsminus.votings.Voting
import java.time.LocalDate

class FirebaseCloud {

    var mFireStore = Firebase.firestore

    fun signUpUser(activity: SignUpActivity, user: User) {
        mFireStore.collection(Constants.USERS)
            .document(user.id)
            .set(user)
            .addOnFailureListener {
                onFailure(activity)
            }
            .addOnSuccessListener {
                activity.hideProgressDialog()
                Toast.makeText(activity, "Willkommen " + user.username, Toast.LENGTH_LONG).show()
                activity.startActivity(Intent(activity, MainActivity::class.java))
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getUser(activity: BaseActivity) {
        mFireStore.collection(Constants.USERS)
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .get()
            .addOnSuccessListener { document ->
                if(activity is MainActivity) {
                    var mActivity:MainActivity = activity
                    mActivity.gotUser(document.toObject(User::class.java)!!)
                } else if(activity is CalendarActivity) {
                    activity.loadedUser(document.toObject(User::class.java)!!)
                } else if(activity is VotingActivity)
                    activity.loadedUser(document.toObject(User::class.java)!!)
            }
            .addOnFailureListener {
                onFailure(activity)
            }
    }

    private fun onFailure(activity: BaseActivity) {
        activity.hideProgressDialog()
        activity.showErrorSnackbar("Es ist etwas schief gelaufen.")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadEvents(activity: BaseActivity) {
        mFireStore.collection(Constants.EVENTS)
            .get()
            .addOnFailureListener {
                onFailure(activity)
            }
            .addOnSuccessListener { collection ->
                var events:ArrayList<Event> = ArrayList()
                var oldEvents:ArrayList<Event> = ArrayList()
                var currentDateTime:LocalDate = LocalDate.now()
                for(document in collection.documents) {
                    var event:Event = document.toObject(Event::class.java)!!
                    var eventDateTime:LocalDate = LocalDate.of(event.year, event.month, event.day)
                    if(currentDateTime > eventDateTime) {
                        oldEvents.add(document.toObject(Event::class.java)!!)
                        continue
                    }
                    events.add(event)
                }
                activity.hideProgressDialog()
                if(activity is CalendarActivity)
                    activity.loadedEvents(events)
                else if(activity is OrganisationActivity)
                    activity.loadedEvents(oldEvents)
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addTraining(activity: OrganisationActivity, id:String, date:LocalDate) {
        var training:Event = Event(id, "Proben", date.dayOfMonth, date.monthValue, date.year, "19:00", ArrayList(), ArrayList())
        training.isTraining = true
        mFireStore.collection(Constants.EVENTS)
            .document(training.documentID)
            .set(training)
            .addOnSuccessListener {
                mFireStore.collection(Constants.USERS)
                    .get()
                    .addOnFailureListener { onFailure(activity) }
                    .addOnSuccessListener { documents ->
                        val users:ArrayList<User> = ArrayList()
                        for(i in documents.documents) {
                            val user:User = i.toObject(User::class.java)!!
                            if(user.defaultPromise)
                                users.add(user)
                        }
                        for(i in users) {
                            training.promised.add(i.id)
                        }
                        mFireStore.collection(Constants.EVENTS)
                            .document(training.documentID)
                            .update("promised", training.promised)
                            .addOnFailureListener { onFailure(activity) }
                    }
            }
            .addOnFailureListener {
                onFailure(activity)
            }
    }

    fun promise(activity: CalendarActivity, user: User, event: Event) {
        var mEvent:Event = event
        mEvent.promised.add(user.id)
        if(mEvent.cancelled.contains(user.id)) {
            mEvent.cancelled.remove(user.id)
        }
        mFireStore.collection(Constants.EVENTS)
            .document(mEvent.documentID)
            .update("promised", mEvent.promised)
            .addOnFailureListener { onFailure(activity) }
            .addOnSuccessListener {
                mFireStore.collection(Constants.EVENTS)
                    .document(mEvent.documentID)
                    .update("cancelled", mEvent.cancelled)
                    .addOnFailureListener { onFailure(activity) }
            }
    }

    fun cancel(activity: CalendarActivity, user: User, event: Event) {
        var mEvent:Event = event
        mEvent.cancelled.add(user.id)
        if(mEvent.promised.contains(user.id)) {
            mEvent.promised.remove(user.id)
        }
        mFireStore.collection(Constants.EVENTS)
            .document(mEvent.documentID)
            .update("cancelled", mEvent.cancelled)
            .addOnFailureListener { onFailure(activity) }
            .addOnSuccessListener {
                mFireStore.collection(Constants.EVENTS)
                    .document(mEvent.documentID)
                    .update("promised", mEvent.promised)
                    .addOnFailureListener { onFailure(activity) }
            }
    }

    fun loadUsers(activity: BaseActivity) {
        mFireStore.collection(Constants.USERS)
            .get()
            .addOnFailureListener { onFailure(activity) }
            .addOnSuccessListener { documents ->
                var users:ArrayList<User> = ArrayList()
                for(i in documents.documents) {
                    users.add(i.toObject(User::class.java)!!)
                }
                if(activity is EventAdminActivity)
                    activity.loadedUsers(users)
                else if(activity is ManageTeamActivity)
                    activity.loadedUsers(users)
                else if(activity is RoomViewActivity)
                    activity.loadedUsers(users)
            }
    }

    fun cancelEvent(activity: EventAdminActivity, event: Event?) {
        mFireStore.collection(Constants.EVENTS)
            .document(event!!.documentID)
            .update("eventCancelled", true)
            .addOnFailureListener { onFailure(activity) }
            .addOnSuccessListener {
                activity.hideProgressDialog()
                activity.startActivity(Intent(activity, MainActivity::class.java))
                Toast.makeText(activity, "Termin abgesagt", Toast.LENGTH_LONG).show()
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun tryDeleteEvents(activity: OrganisationActivity, events: java.util.ArrayList<Event>) {
        for(i in events) {
            mFireStore.collection(Constants.EVENTS)
                .document(i.documentID)
                .delete()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createEvent(activity: AddEventActivity, id: String, selectedDate: LocalDate, name:String, time:String) {
        val event:Event = Event(id, name, selectedDate.dayOfMonth, selectedDate.monthValue, selectedDate.year, time)
        mFireStore.collection(Constants.EVENTS)
            .document(event.documentID)
            .set(event)
            .addOnFailureListener { onFailure(activity) }
            .addOnSuccessListener {
                Toast.makeText(activity, "Termin erstellt", Toast.LENGTH_LONG).show()
                activity.finish()
            }
    }

    fun togglePromiseTraining(activity: BaseActivity, user: User?) {
        mFireStore.collection(Constants.USERS)
            .document(user!!.id)
            .update("defaultPromise", user!!.defaultPromise)
            .addOnFailureListener { onFailure(activity) }
            .addOnSuccessListener {
                activity.hideProgressDialog()
            }
    }

    fun changeInstrument(activity: ManageTeamActivity, instrument: String, user: User) {
        var mUser:User = user
        mUser.instrument = instrument
        mFireStore.collection(Constants.USERS)
            .document(user.id)
            .update("instrument", mUser.instrument)
            .addOnFailureListener { onFailure(activity) }
            .addOnSuccessListener {
                activity.hideProgressDialog()
            }
    }

    fun createSongVoting(activity: CreateVotingActivity, name:String) {
        var id:String = "${name}_${System.currentTimeMillis()}"
        var voting:Voting = Voting(id, name)
        voting.liked = ArrayList()
        mFireStore.collection(Constants.VOTINGS)
            .document(voting.id)
            .set(voting)
            .addOnFailureListener { onFailure(activity) }
            .addOnSuccessListener {
                activity.created()
            }
    }

    fun loadVotings(activity: VotingActivity) {
        mFireStore.collection(Constants.VOTINGS)
            .get()
            .addOnFailureListener { onFailure(activity) }
            .addOnSuccessListener { documents ->
                var votings:ArrayList<Voting> = ArrayList()
                for(i in documents.documents)
                    votings.add(i.toObject(Voting::class.java)!!)
                activity.loadedVotings(votings)
            }
    }

    fun addLike(activity: VotingActivity, user: User, voting: Voting) {
        val mVoting:Voting = voting
        if(!mVoting.liked.contains(user.id)) {
            mVoting.liked.add(user.id)
            mFireStore.collection(Constants.VOTINGS)
                .document(mVoting.id)
                .update("liked", mVoting.liked)
                .addOnFailureListener { onFailure(activity) }
        } else {
            mVoting.liked.remove(user.id)
            mFireStore.collection(Constants.VOTINGS)
                .document(mVoting.id)
                .update("liked", mVoting.liked)
                .addOnFailureListener { onFailure(activity) }
        }

    }

    fun deleteVoting(activity: VotingActivity, voting: Voting) {
        mFireStore.collection(Constants.VOTINGS)
            .document(voting.id)
            .delete()
            .addOnFailureListener { onFailure(activity) }
            .addOnSuccessListener {
                 activity.startActivity(Intent(activity, MainActivity::class.java))
            }
    }

    fun updateUserSeat(activity: RoomViewActivity, user:User, newSeat:Int) {
        mFireStore.collection(Constants.USERS)
            .document(user.id)
            .update("chairID", newSeat)
            .addOnFailureListener { onFailure(activity) }
            .addOnSuccessListener {
                activity.finish()
            }
    }

}