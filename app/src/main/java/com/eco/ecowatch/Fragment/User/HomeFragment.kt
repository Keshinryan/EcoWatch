package com.eco.ecowatch.Fragment.User

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eco.ecowatch.Adapter.BeritaAdapter
import com.eco.ecowatch.Adapter.EdukasiAdapter
import com.eco.ecowatch.R
import com.eco.ecowatch.databinding.FragmentHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        val Badapter = BeritaAdapter(requireContext())
        Badapter.LinkData()

        val Brv: RecyclerView = view.findViewById(R.id.HomeBeritarecycleView)
        val LinearLayoutBerita = object : LinearLayoutManager(requireContext()) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        // Set up RecyclerView with LinearLayoutManager and DividerItemDecoration


        Brv.layoutManager = LinearLayoutBerita
        Brv.adapter = Badapter
        Brv.addItemDecoration(DividerItemDecoration(context, 0))

        // Add top margin to the RecyclerView programmatically



        val Eadapter = EdukasiAdapter(requireContext())
        Eadapter.LinkData()

        val Erv: RecyclerView = view.findViewById(R.id.HomeEdukasiRecycleView)

        // Set up RecyclerView with LinearLayoutManager and DividerItemDecoration

        val LinearLayoutEdukasi = object : LinearLayoutManager(requireContext()) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        Erv.layoutManager = LinearLayoutEdukasi
        Erv.adapter = Eadapter
        Erv.addItemDecoration(DividerItemDecoration(context, 0))



        binding.halBerita.setOnClickListener{

            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout, BeritaFragment()).commit()
            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
                .selectedItemId = R.id.bottom_berita

        }

        binding.halEdukasi.setOnClickListener{
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout, EdukasiFragment()).commit()
            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
                .selectedItemId = R.id.bottom_edukasi
        }

        return view
    }
}