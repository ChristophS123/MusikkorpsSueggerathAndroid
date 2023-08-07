package de.christoph.musikkorpsminus.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import de.christoph.musikkorpsminus.R

class Utils {

    fun setUpToolbar(activity: AppCompatActivity, toolbar: Toolbar, title:String) {
        activity.setSupportActionBar(toolbar)
        val actionBar = activity.supportActionBar
        if(actionBar != null) {
            actionBar.title = title
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_pressed)
        }
        toolbar.setNavigationOnClickListener {
            activity.onBackPressed()
        }
    }

}