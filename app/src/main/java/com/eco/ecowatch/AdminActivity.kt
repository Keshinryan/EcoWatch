package com.eco.ecowatch

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.eco.ecowatch.Fragment.Admin.BeritaAdminFragment
import com.eco.ecowatch.Fragment.Admin.EdukasiAdminFragment
import com.eco.ecowatch.databinding.ActivityAdminBinding

class AdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding
    var sharedpreferences: SharedPreferences? = null
    val SHARED_PREFS = "shared_prefs"

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAdminBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        FrameLayout(BeritaAdminFragment())

        binding.bottomNavigation.setOnItemSelectedListener {
            sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
            when (it.itemId) {
                R.id.bottom_berita -> {
                    FrameLayout(BeritaAdminFragment())
                }
                R.id.bottom_edukasi -> {
                    FrameLayout(EdukasiAdminFragment())
                }
                R.id.bottom_logout-> {
                    // calling method to edit values in shared prefs.
                    sharedpreferences?.let { prefs ->
                        val editor: SharedPreferences.Editor = prefs.edit()

                        // below line will clear
                        // the data in shared prefs.
                        editor.clear()

                        // below line will apply empty
                        // data to shared prefs.
                        editor.apply()

                        // Restart the application
                        val intent = packageManager.getLaunchIntentForPackage(packageName)
                        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    }
                }
            }
            true
        }
    }

    fun FrameLayout(fg: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, fg)
            .commit()
    }
}
