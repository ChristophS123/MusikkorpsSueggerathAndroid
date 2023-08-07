package de.christoph.musikkorpsminus.menu.mainmenu

import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import de.christoph.musikkorpsminus.BaseActivity
import de.christoph.musikkorpsminus.user.User

abstract class MenuManager(var drawerLayout: DrawerLayout, var activity:BaseActivity, var navigationView:NavigationView) : NavigationView.OnNavigationItemSelectedListener {

    init {
        navigationView.setNavigationItemSelectedListener(this)
    }

    fun toggleMenu() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START)
        else
            drawerLayout.openDrawer(GravityCompat.START)
    }

    abstract fun selectMenuItem(itemID:Int)

}