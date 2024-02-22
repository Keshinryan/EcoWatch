package com.eco.ecowatch

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.eco.ecowatch.Fragment.User.BeritaFragment
import com.eco.ecowatch.Fragment.User.EdukasiFragment
import com.eco.ecowatch.Fragment.User.HomeFragment
import com.eco.ecowatch.Fragment.User.PrediksiFragment
import com.eco.ecowatch.Fragment.User.SettingsFragment
import com.eco.ecowatch.databinding.ActivityMainBinding
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var sharedpreferences: SharedPreferences? = null
    val SHARED_PREFS = "shared_prefs"

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        FrameLayout(HomeFragment())


        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_home ->{
                    FrameLayout(HomeFragment())
                }
                R.id.bottom_berita ->{
                    FrameLayout(BeritaFragment())
                }
                R.id.bottom_edukasi ->{
                    FrameLayout(EdukasiFragment())
                }
                R.id.bottom_prediksi ->{
                    FrameLayout(PrediksiFragment())
                }
                R.id.bottom_settings ->{
                    FrameLayout(SettingsFragment())
                }
            }
            true
        }
    }
    fun FrameLayout(fg:Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout,fg)
            .commit()
    }
}