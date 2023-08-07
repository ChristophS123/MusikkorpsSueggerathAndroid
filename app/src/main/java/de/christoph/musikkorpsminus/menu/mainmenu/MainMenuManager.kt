package de.christoph.musikkorpsminus.menu.mainmenu

import android.content.Intent
import android.view.MenuItem
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import de.christoph.musikkorpsminus.*
import de.christoph.musikkorpsminus.user.User
import kotlinx.android.synthetic.main.activity_main.*

class MainMenuManager constructor (drawerLayout: DrawerLayout, activity: MainActivity, navigationView: NavigationView) : MenuManager(drawerLayout, activity, navigationView) {

    var user:User? = null

    fun setUpNavHeaderName(name:String, nameTextView:TextView) {
        nameTextView.text = name
    }

    override fun selectMenuItem(itemID: Int) {
        var mainActivity:MainActivity = activity as MainActivity
        when(itemID) {
            R.id.nav_signout -> {
                FirebaseAuth.getInstance().signOut()
                mainActivity.startActivity(Intent(mainActivity, LogInActivity::class.java))
            }
            R.id.nav_admin -> {
                if(user!!.admin == 0) {
                    activity.showErrorSnackbar("Du bist kein Organisator")
                    return
                }
                mainActivity.startActivity(Intent(mainActivity, OrganisationActivity::class.java))
            }
            R.id.nav_calendar -> {
                mainActivity.startActivity(Intent(mainActivity, CalendarActivity::class.java))
            }
            R.id.nav_votings -> {
                mainActivity.startActivity(Intent(mainActivity, VotingActivity::class.java))
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        selectMenuItem(item.itemId)
        return true
    }

}