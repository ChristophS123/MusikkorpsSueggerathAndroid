package de.christoph.musikkorpsminus

import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import de.christoph.musikkorpsminus.firebase.FirebaseCloud
import de.christoph.musikkorpsminus.user.User
import de.christoph.musikkorpsminus.utils.Constants
import de.christoph.musikkorpsminus.utils.Utils
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        Utils().setUpToolbar(this, tb_sign_up, "Registrieren")
        cmd_sign_up_submit.setOnClickListener { tryToSignUp() }
    }

    private fun tryToSignUp() {
        if(FirebaseAuth.getInstance().currentUser != null)
            return
        if(!isValidate()) {
            showErrorSnackbar("Bitte fülle alle Felder aus.")
            return
        }
        if(et_sign_up_password.text.toString() != et_sign_up_repeat_password.text.toString()) {
            showErrorSnackbar("Die Passwörter stimmen nicht überein.")
            return
        }
        showProgressDialog()
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(et_sign_up_email.text.toString(), et_sign_up_password.text.toString())
            .addOnFailureListener {
                showErrorSnackbar("Es ist etwas schief gelaufen.")
                hideProgressDialog()
            }
            .addOnSuccessListener {
                var user:User = User(FirebaseAuth.getInstance().currentUser!!.uid)
                user.username = et_sign_up_name.text.toString()
                user.email = et_sign_up_email.text.toString()
                user.instrument = checkForInstrument(et_sign_up_name.text.toString())
                user.chairID = checkForChair(et_sign_up_name.text.toString())
                FirebaseCloud().signUpUser(this, user)
            }
    }

    private fun checkForChair(name: String): Int {
        return when {
            name.toUpperCase().contains("CHRISTOPH SCHMITZ") -> 7
            name.toUpperCase().contains("PETRA") -> 18
            name.toUpperCase().contains("MORITZ") -> 17
            name.toUpperCase().contains("SOPHIA") -> 16
            name.toUpperCase().contains("MARIE") -> 2
            name.toUpperCase().contains("KARIN") -> 1
            name.toUpperCase().contains("IRA") -> 4
            name.toUpperCase().contains("JESSICA") -> 3
            name.toUpperCase().contains("SANDRA") -> 6
            name.toUpperCase().contains("MIRIAM") -> 5
            name.toUpperCase().contains("LISA") -> 14
            name.toUpperCase().contains("ANNIKA") -> 15
            name.toUpperCase().contains("FRANZ") -> 13
            name.toUpperCase().contains("MARTIN") -> 24
            name.toUpperCase().contains("CHRISTOPH") -> 23
            name.toUpperCase().contains("HOLGER S") -> 22
            name.toUpperCase().contains("HOLGER K") -> 20
            name.toUpperCase().contains("HENR") -> 21
            name.toUpperCase().contains("PETER") -> 10
            name.toUpperCase().contains("NOHA") -> 11
            name.toUpperCase().contains("PAUL") -> 12
            name.toUpperCase().contains("HORST") -> 9
            name.toUpperCase().contains("SCHIEFF") -> 8
            name.toUpperCase().contains("RICK") -> 19
            name.toUpperCase().contains("WALTER") -> 25
            name.toUpperCase().contains("REN") -> 26
            name.toUpperCase().contains("RUDI") -> 27
            else -> {0}
        }
    }

    private fun checkForInstrument(name: String): String {
        return when {
            name.toUpperCase().contains("CHRISTOPH SCHMITZ") -> Constants.ALTO_SAXOPHONE
            name.toUpperCase().contains("PETRA") -> Constants.ALTO_SAXOPHONE
            name.toUpperCase().contains("MORITZ") -> Constants.ALTO_SAXOPHONE
            name.toUpperCase().contains("SOPHIA") -> Constants.ALTO_SAXOPHONE
            name.toUpperCase().contains("MARIE") -> Constants.CLARINET
            name.toUpperCase().contains("KARIN") -> Constants.CLARINET
            name.toUpperCase().contains("IRA") -> Constants.CLARINET
            name.toUpperCase().contains("JESSICA") -> Constants.CLARINET
            name.toUpperCase().contains("SANDRA") -> Constants.FLUTE
            name.toUpperCase().contains("MIRIAM") -> Constants.FLUTE
            name.toUpperCase().contains("LISA") -> Constants.TENOR_SAXOPHONE
            name.toUpperCase().contains("ANNIKA") -> Constants.TENOR_SAXOPHONE
            name.toUpperCase().contains("FRANZ") -> Constants.TENOR_SAXOPHONE
            name.toUpperCase().contains("MARTIN") -> Constants.TROMBONE
            name.toUpperCase().contains("CHRISTOPH") -> Constants.TROMBONE
            name.toUpperCase().contains("HOLGER S") -> Constants.TROMBONE
            name.toUpperCase().contains("HOLGER K") -> Constants.BARITONE
            name.toUpperCase().contains("HENR") -> Constants.BARITONE
            name.toUpperCase().contains("PETER") -> Constants.TRUMPET
            name.toUpperCase().contains("NOHA") -> Constants.TRUMPET
            name.toUpperCase().contains("PAUL") -> Constants.TRUMPET
            name.toUpperCase().contains("HORST") -> Constants.TRUMPET
            name.toUpperCase().contains("SCHIEFF") -> Constants.TRUMPET
            name.toUpperCase().contains("RICK") -> Constants.BARITONE
            name.toUpperCase().contains("WALTER") -> Constants.PERCUSSION
            name.toUpperCase().contains("REN") -> Constants.PERCUSSION
            name.toUpperCase().contains("RUDI") -> Constants.PERCUSSION
            else -> {""}
        }
    }

    private fun isValidate(): Boolean {
        if(et_sign_up_name.text.toString().isEmpty() || et_sign_up_name.text.toString() == "")
            return false
        if(et_sign_up_email.text.toString().isEmpty() || et_sign_up_email.text.toString() == "")
            return false
        if(et_sign_up_password.text.toString().isEmpty() || et_sign_up_password.text.toString() == "")
            return false
        if(et_sign_up_repeat_password.text.toString().isEmpty() || et_sign_up_repeat_password.text.toString() == "")
            return false
        return true
    }

}