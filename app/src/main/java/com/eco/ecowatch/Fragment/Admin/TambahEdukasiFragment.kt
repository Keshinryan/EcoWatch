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
import com.eco.ecowatch.Fragment.User.EdukasiFragment
import com.eco.ecowatch.R
import com.eco.ecowatch.databinding.FragmentTambahEdukasiBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class TambahEdukasiFragment : Fragment() {
    private lateinit var Nama: EditText
    private lateinit var Deskripsi: EditText
    private lateinit var tambah: Button
    lateinit var handler: Handler
    private  var _binding: FragmentTambahEdukasiBinding?=null
    private val binding get() = _binding!!
    private val url ="https://ecowatchk10.000webhostapp.com/insertEdukasi.php"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTambahEdukasiBinding.inflate(inflater, container, false)
        val view = binding.root
        Nama=binding.tambahNamaEdukasi
        Deskripsi=binding.tambahDeskripsiEdukasi
        tambah=binding.tambahEdukasi
        handler=Handler()
        tambah.setOnClickListener {
            val request= object: StringRequest(
                Method.POST, url,
                Response.Listener<String>{ response ->
                    Nama.setText("")
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
                    params.put("Name",Nama.text.toString())
                    params.put("Deskripsi",Deskripsi.text.toString())
                    return params
                }
            }
            val queue = Volley.newRequestQueue(requireContext())
            queue.add(request)

            val fragment = EdukasiAdminFragment()
            val bundle = Bundle()
            bundle.putSerializable(EdukasiAdminFragment.NEXT_SCREEN, NEXT_SCREEN)
            fragment.arguments = bundle

            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
                .selectedItemId = R.id.bottom_edukasi
        }
        return view
    }
    companion object{
        val NEXT_SCREEN="details_screen"
    }
}