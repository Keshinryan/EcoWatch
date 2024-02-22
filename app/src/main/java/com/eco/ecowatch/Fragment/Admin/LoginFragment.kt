package com.eco.ecowatch.Fragment.Admin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.eco.ecowatch.AdminActivity
import com.eco.ecowatch.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    var sharedpreferences: SharedPreferences? = null
    val SHARED_PREFS = "shared_prefs"
    val USERNAME="USERNAME"
    val PASSWORD_KEY="PASSWORD_KEY"
    var username: String? = null
    var password: String? = null
    private lateinit var Username: EditText
    private lateinit var Password: EditText
    private lateinit var Login: Button
    val defaultUname="admin"
    val defaultPass="Admin10!!!"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        Username=binding.lgUsername
        Password=binding.lgPassword
        Login=binding.loginAdmin
        // getting the data which is stored in shared preferences.
        sharedpreferences = requireContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        username = sharedpreferences!!.getString("USERNAME", null)
        password = sharedpreferences!!.getString("PASSWORD_KEY", null)
        // calling on click listener for login button.
        Login.setOnClickListener(View.OnClickListener {
            val enteredUsername = Username.text.toString()
            val enteredPassword = Password.text.toString()
            // to check if the user fields are empty or not.
            if (TextUtils.isEmpty(enteredUsername) && TextUtils.isEmpty(enteredPassword)
            ) {
                // this method will call when email and password fields are empty.
                Toast.makeText(requireContext(), "Please Enter Email and Password", Toast.LENGTH_SHORT).show()
            } else {
                if(enteredUsername!= defaultUname&& enteredPassword != defaultPass){
                    Toast.makeText(requireContext(), "Incorrect Username & Password", Toast.LENGTH_SHORT).show()
                } else if (enteredUsername== defaultUname) {
                    if ( enteredPassword == defaultPass) {
                        // Correct credentials, save to SharedPreferences and start the new activity
                        val editor = sharedpreferences!!.edit()
                        editor.putString(USERNAME, enteredUsername)
                        editor.putString(PASSWORD_KEY, enteredPassword)
                        editor.apply()

                        val i = Intent(requireContext(), AdminActivity::class.java)
                        startActivity(i)
                    }else {
                        // Incorrect credentials
                        Toast.makeText(requireContext(), "Incorrect Password", Toast.LENGTH_SHORT).show()
                    }
                }else {
                    // Incorrect credentials
                    Toast.makeText(requireContext(), "Incorrect Username ", Toast.LENGTH_SHORT).show()
                }

            }
        })
        return view
    }
}