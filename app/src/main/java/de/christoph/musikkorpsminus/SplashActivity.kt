package de.christoph.musikkorpsminus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startMainActivity(3000)
    }

    private fun startMainActivity(delay: Long) {
        Log.d("test", FirebaseAuth.getInstance().currentUser.toString())
        Handler().postDelayed({
            var intent:Intent? = null
            intent = if(FirebaseAuth.getInstance().currentUser == null) {
                Intent(this@SplashActivity, LogInActivity::class.java)
            } else
                Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent!!)
        }, delay)
    }

}