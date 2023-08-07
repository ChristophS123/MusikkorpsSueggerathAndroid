package de.christoph.musikkorpsminus

import android.os.Bundle
import de.christoph.musikkorpsminus.firebase.FirebaseCloud
import de.christoph.musikkorpsminus.utils.Utils
import kotlinx.android.synthetic.main.activity_create_voting.*

class CreateVotingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_voting)
        Utils().setUpToolbar(this, tb_create_voting, "Lied-Abstimmung erstellen")
        cmd_create_voting_submit.setOnClickListener {
            if(et_add_song_voting_name.text == null || et_add_song_voting_name.text!!.isEmpty()) {
                showErrorSnackbar("Bitte fülle das Feld aus.")
                return@setOnClickListener
            }
            if(et_add_song_voting_name!!.text.toString() == "") {
                showErrorSnackbar("Bitte fülle das Feld aus.")
                return@setOnClickListener
            }
            showProgressDialog()
            FirebaseCloud().createSongVoting(this@CreateVotingActivity, et_add_song_voting_name.text.toString())
        }
    }

    fun created() {
        hideProgressDialog()
        finish()
    }

}