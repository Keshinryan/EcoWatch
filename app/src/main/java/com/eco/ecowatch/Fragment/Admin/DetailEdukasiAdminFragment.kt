package com.eco.ecowatch.Fragment.Admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eco.ecowatch.DataClass.Edukasi
import com.eco.ecowatch.Fragment.User.EdukasiFragment
import com.eco.ecowatch.R
import com.eco.ecowatch.databinding.FragmentDetailEdukasiAdminBinding

class DetailEdukasiAdminFragment : Fragment() {
    private var _binding: FragmentDetailEdukasiAdminBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailEdukasiAdminBinding.inflate(inflater, container, false)
        val view = binding.root
        var eList: Edukasi? = null

        var arguments = arguments

        if (arguments != null && arguments.containsKey(EdukasiFragment.NEXT_SCREEN)) {
            eList = arguments.getSerializable(EdukasiFragment.NEXT_SCREEN) as? Edukasi
        }
        if (eList != null) {
            binding.judulEdukasi.text = eList.Nama
            binding.DeskripsiEdukasi.text = eList.Deskripsi
        }

        binding.edukasiBack.setOnClickListener {
            val fragment = EdukasiAdminFragment()
            val bundle = Bundle()
            bundle.putSerializable(EdukasiAdminFragment.NEXT_SCREEN, NEXT_SCREEN)
            fragment.arguments = bundle

            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        return view
    }

    companion object {
        val NEXT_SCREEN = "details_screen"
    }
}