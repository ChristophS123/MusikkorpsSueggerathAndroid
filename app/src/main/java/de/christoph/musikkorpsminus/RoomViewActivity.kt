package de.christoph.musikkorpsminus

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import de.christoph.musikkorpsminus.calendar.Event
import de.christoph.musikkorpsminus.firebase.FirebaseCloud
import de.christoph.musikkorpsminus.user.User
import kotlinx.android.synthetic.main.activity_room_view.*

class RoomViewActivity : BaseActivity() {

    private lateinit var currentEvent:Event
    private lateinit var seats:ArrayList<Button?>
    private var users:ArrayList<User>? = null

    var isSelectMode:Boolean = false
    var selectUser:User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_view)
        seats = ArrayList()
        registerSeats()
        if(intent.hasExtra("isSelectMode")) {
            isSelectMode = true
        }
        if(isSelectMode) {
            selectUser = intent.extras!!["selectUser"] as User?
            setSelectClickListeners()
            return
        }
        currentEvent = (intent.extras!!["event"] as Event?)!!
        FirebaseCloud().loadUsers(this)
    }

    private fun setSelectClickListeners() {
        var n = 0
        /*for(i in seats) {
            n++
            i!!.setOnClickListener {
                FirebaseCloud().updateUserSeat(this@RoomViewActivity, selectUser!!, n)
            }
        }*/
        cmd_room_one.setOnClickListener {
            FirebaseCloud().updateUserSeat(this@RoomViewActivity, selectUser!!, 1)
        }
        cmd_room_one2.setOnClickListener {
            FirebaseCloud().updateUserSeat(this@RoomViewActivity, selectUser!!, 2)
        }
        cmd_room_one3.setOnClickListener {
            FirebaseCloud().updateUserSeat(this@RoomViewActivity, selectUser!!, 3)
        }
        cmd_room_one4.setOnClickListener {
            FirebaseCloud().updateUserSeat(this@RoomViewActivity, selectUser!!, 4)
        }
        cmd_room_one5.setOnClickListener {
            FirebaseCloud().updateUserSeat(this@RoomViewActivity, selectUser!!, 5)
        }
        cmd_room_one6.setOnClickListener {
            FirebaseCloud().updateUserSeat(this@RoomViewActivity, selectUser!!, 6)
        }
        cmd_room_one7.setOnClickListener {
            FirebaseCloud().updateUserSeat(this@RoomViewActivity, selectUser!!, 7)
        }
        cmd_room_one8.setOnClickListener {
            FirebaseCloud().updateUserSeat(this@RoomViewActivity, selectUser!!, 8)
        }
        cmd_room_one9.setOnClickListener {
            FirebaseCloud().updateUserSeat(this@RoomViewActivity, selectUser!!, 9)
        }
        cmd_room_one10.setOnClickListener {
            FirebaseCloud().updateUserSeat(this@RoomViewActivity, selectUser!!, 10)
        }
        cmd_room_one11.setOnClickListener {
            FirebaseCloud().updateUserSeat(this@RoomViewActivity, selectUser!!, 11)
        }
        cmd_room_one12.setOnClickListener {
            FirebaseCloud().updateUserSeat(this@RoomViewActivity, selectUser!!, 12)
        }
        cmd_room_one13.setOnClickListener {
            FirebaseCloud().updateUserSeat(this@RoomViewActivity, selectUser!!, 13)
        }
        cmd_room_one14.setOnClickListener {
            FirebaseCloud().updateUserSeat(this@RoomViewActivity, selectUser!!, 14)
        }
        cmd_room_one15.setOnClickListener {
            FirebaseCloud().updateUserSeat(this@RoomViewActivity, selectUser!!, 15)
        }
        cmd_room_one16.setOnClickListener {
            FirebaseCloud().updateUserSeat(this@RoomViewActivity, selectUser!!, 16)
        }
        cmd_room_one17.setOnClickListener {
            FirebaseCloud().updateUserSeat(this@RoomViewActivity, selectUser!!, 17)
        }
        cmd_room_one18.setOnClickListener {
            FirebaseCloud().updateUserSeat(this@RoomViewActivity, selectUser!!, 18)
        }
        cmd_room_one19.setOnClickListener {
            FirebaseCloud().updateUserSeat(this@RoomViewActivity, selectUser!!, 19)
        }
        cmd_room_one20.setOnClickListener {
            FirebaseCloud().updateUserSeat(this@RoomViewActivity, selectUser!!, 20)
        }
        cmd_room_one21.setOnClickListener {
            FirebaseCloud().updateUserSeat(this@RoomViewActivity, selectUser!!, 21)
        }
        cmd_room_one22.setOnClickListener {
            FirebaseCloud().updateUserSeat(this@RoomViewActivity, selectUser!!, 22)
        }
        cmd_room_one23.setOnClickListener {
            FirebaseCloud().updateUserSeat(this@RoomViewActivity, selectUser!!, 23)
        }
        cmd_room_one24  .setOnClickListener {
            FirebaseCloud().updateUserSeat(this@RoomViewActivity, selectUser!!, 24)
        }
        cmd_room_one25.setOnClickListener {
            FirebaseCloud().updateUserSeat(this@RoomViewActivity, selectUser!!, 25)
        }
        cmd_room_one26.setOnClickListener {
            FirebaseCloud().updateUserSeat(this@RoomViewActivity, selectUser!!, 26)
        }
        cmd_room_one27.setOnClickListener {
            FirebaseCloud().updateUserSeat(this@RoomViewActivity, selectUser!!, 27)
        }
    }

    private fun registerSeats() {
        seats.add(null)
        seats.add(cmd_room_one)
        seats.add(cmd_room_one2)
        seats.add(cmd_room_one3)
        seats.add(cmd_room_one4)
        seats.add(cmd_room_one5)
        seats.add(cmd_room_one6)
        seats.add(cmd_room_one7)
        seats.add(cmd_room_one8)
        seats.add(cmd_room_one9)
        seats.add(cmd_room_one10)
        seats.add(cmd_room_one11)
        seats.add(cmd_room_one12)
        seats.add(cmd_room_one13)
        seats.add(cmd_room_one14)
        seats.add(cmd_room_one15)
        seats.add(cmd_room_one16)
        seats.add(cmd_room_one17)
        seats.add(cmd_room_one18)
        seats.add(cmd_room_one19)
        seats.add(cmd_room_one20)
        seats.add(cmd_room_one21)
        seats.add(cmd_room_one22)
        seats.add(cmd_room_one23)
        seats.add(cmd_room_one24)
        seats.add(cmd_room_one25)
        seats.add(cmd_room_one26)
        seats.add(cmd_room_one27)
    }

    fun loadedUsers(users: java.util.ArrayList<User>) {
        this.users = users
        for(i in users) {
            if(i.chairID == 0)
                continue
            if(currentEvent.promised.contains(i.id))
                seats[i.chairID]!!.setBackgroundColor(Color.GREEN)
            else if(currentEvent.cancelled.contains(i.id))
                seats[i.chairID]!!.setBackgroundColor(Color.RED)
            else
                seats[i.chairID]!!.setBackgroundColor(Color.GRAY)
            seats[i.chairID]!!.text = (i.username[0].toString())
        }
    }

}