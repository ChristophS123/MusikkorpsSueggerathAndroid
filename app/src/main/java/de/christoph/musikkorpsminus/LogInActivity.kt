package de.christoph.musikkorpsminus

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import de.christoph.musikkorpsminus.utils.Utils
import kotlinx.android.synthetic.main.activity_log_in.*

class LogInActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        Utils().setUpToolbar(this, tb_sign_in, "Einloggen")
        cmd_sign_in_submit.setOnClickListener { tryToLogin() }
        cmd_sign_in_sign_up.setOnClickListener { startActivity(Intent(this, SignUpActivity::class.java)) }
    }

    private fun tryToLogin() {
        if(FirebaseAuth.getInstance().currentUser != null)
            return
        if(!isValidate()) {
            showErrorSnackbar("Bitte gebe deine Email und dein Passwort ein.")
            return
        }
        FirebaseAuth.getInstance().signInWithEmailAndPassword(et_sign_in_email.text.toString(), et_sign_in_password.text.toString())
            .addOnFailureListener {
                showErrorSnackbar("Deine Email oder dein Passwort ist falsch.")
            }
            .addOnSuccessListener {
                Toast.makeText(this, "Willkommen zurück!", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, MainActivity::class.java))
            }
    }

    private fun isValidate(): Boolean {
        if(et_sign_in_email.text.toString().isEmpty() || et_sign_in_email.text.toString() == "")
            return false
        if(et_sign_in_password.text.toString().isEmpty() ||et_sign_in_password.text.toString() == "")
            return false
        return true
    }

}