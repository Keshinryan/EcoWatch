package com.eco.ecowatch.Fragment.Admin

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.eco.ecowatch.Fragment.User.BeritaFragment
import com.eco.ecowatch.R
import com.eco.ecowatch.databinding.FragmentTambahBeritaBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class TambahBeritaFragment : Fragment() {
    private lateinit var Judul: EditText
    private lateinit var Deskripsi: EditText
    private lateinit var tambah: Button
    lateinit var handler: Handler
    private  var _binding: FragmentTambahBeritaBinding?=null
    private val binding get() = _binding!!
    private val url ="https://ecowatchk10.000webhostapp.com/insertBerita.php"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTambahBeritaBinding.inflate(inflater, container, false)
        val view = binding.root
        Judul=binding.tambahJudulBerita
        Deskripsi=binding.tambahDeskripsiBerita
        tambah=binding.tambahBerita
        handler=Handler()
        tambah.setOnClickListener {
            val request= object: StringRequest(
                Method.POST, url,
                Response.Listener<String>{ response ->
                    Judul.setText("")
                    Deskripsi.setText("")
                    handler.postDelayed({
                        Toast.makeText(requireContext(), response.toString(), Toast.LENGTH_SHORT).show()
                    }, 10)
                },
                Response.ErrorListener { error ->
                    handler.postDelayed({
                        // Handle the error response
                        Toast.makeText(requireContext(), "Error: " + error.message, Toast.LENGTH_SHORT).show()
                    }, 10)
                }) {
                override fun getParams(): Map<String, String>? {
                    val params= HashMap<String,String>()
                    params.put("Judul",Judul.text.toString())
                    params.put("Deskripsi",Deskripsi.text.toString())
                    return params
                }
            }
            val queue = Volley.newRequestQueue(requireContext())
            queue.add(request)

            val fragment = BeritaAdminFragment()
            val bundle = Bundle()
            bundle.putSerializable(BeritaAdminFragment.NEXT_SCREEN, NEXT_SCREEN)
            fragment.arguments = bundle

            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
                .selectedItemId = R.id.bottom_berita
        }
        return view
    }
    companion object{
        val NEXT_SCREEN="details_screen"
    }
}