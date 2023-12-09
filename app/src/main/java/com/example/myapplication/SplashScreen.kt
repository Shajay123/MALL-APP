package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {

    private var navigationTriggered = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val videoView = findViewById<VideoView>(R.id.videoView)
        val videoPath = "android.resource://" + packageName + "/" + R.raw.splash

        videoView.setVideoURI(Uri.parse(videoPath))
        videoView.setOnCompletionListener {
            navigateToNextActivity()
        }
        videoView.start()

        // Simulate a 3-second delay using Handler.postDelayed
        Handler().postDelayed({
            if (!navigationTriggered) {
                navigateToNextActivity()
            }
        }, 5000)
    }

    private fun navigateToNextActivity() {
        if (!navigationTriggered) {
            navigationTriggered = true
            val isLoggedIn = checkIfUserIsLoggedIn()
            val nextActivity = if (isLoggedIn) {
                Intent(this@SplashScreen, MainActivity::class.java)
            } else {
                Intent(this@SplashScreen, SignInActivity::class.java)
            }
            startActivity(nextActivity)
            finish()
        }
    }

    private fun checkIfUserIsLoggedIn(): Boolean {
        return FirebaseAuth.getInstance().currentUser != null
    }
}
