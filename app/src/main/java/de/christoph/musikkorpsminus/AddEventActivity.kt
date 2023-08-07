package de.christoph.musikkorpsminus

import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import de.christoph.musikkorpsminus.firebase.FirebaseCloud
import de.christoph.musikkorpsminus.utils.Utils
import kotlinx.android.synthetic.main.activity_add_event.*
import java.time.LocalDate
import java.util.*

class AddEventActivity : BaseActivity() {

    var selectedDate:LocalDate? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)
        Utils().setUpToolbar(this, tb_add_event, "Termin hinzufügen")
        cmd_add_event_date.setOnClickListener { selectDate() }
        cmd_add_event_submit.setOnClickListener {
            if(et_add_event_name.text == null || et_add_event_name.text!!.isEmpty()) {
                showErrorSnackbar("Bitte vergebe einen Namen.")
                return@setOnClickListener
            }
            if(selectedDate == null) {
                showErrorSnackbar("Bitte wähle ein Datum aus.")
                return@setOnClickListener
            }
            if(et_add_event_time.text == null || et_add_event_time.text!!.isEmpty()) {
                showErrorSnackbar("Bitte gebe eine Uhrzeit an.")
                return@setOnClickListener
            }
            showProgressDialog()
            var id:String = et_add_event_name.text.toString() + "_" + System.currentTimeMillis()
            FirebaseCloud().createEvent(this, id, selectedDate!!, et_add_event_name.text.toString(), et_add_event_time.text.toString())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun selectDate() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(this,
            { view, year, month, day ->
                selectedDate = LocalDate.of(year, month+1, day)
                cmd_add_event_date.text = "${selectedDate!!.year}.${selectedDate!!.month}.${selectedDate!!.dayOfMonth}"
            }, year, month, day)
        dpd.show()
    }

}