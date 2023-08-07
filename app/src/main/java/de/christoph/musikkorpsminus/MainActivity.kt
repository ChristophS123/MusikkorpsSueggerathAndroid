package de.christoph.musikkorpsminus

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import de.christoph.musikkorpsminus.firebase.FirebaseCloud
import de.christoph.musikkorpsminus.menu.mainmenu.MainMenuManager
import de.christoph.musikkorpsminus.user.User
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.nav_header_main.*

class MainActivity : BaseActivity() {

    var user:User? = null
    private lateinit var menuManager: MainMenuManager

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseCloud().getUser(this)
        menuManager = MainMenuManager(dl_main, this, nav_view)
        cmd_main_menu.setOnClickListener { menuManager.toggleMenu() }
    }

    fun gotUser(user: User) { // Is called, when user is loaded
        this.user = user
        menuManager.user = user
        menuManager.setUpNavHeaderName(user!!.username, tv_nav_header_name)
    }

    override fun onBackPressed() { // Is called, when smartphones back button is pressed
        menuManager.toggleMenu()
    }

}