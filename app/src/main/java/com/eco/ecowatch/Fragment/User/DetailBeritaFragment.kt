package com.eco.ecowatch.Fragment.User

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eco.ecowatch.DataClass.Berita
import com.eco.ecowatch.R
import com.eco.ecowatch.databinding.FragmentDetailBeritaBinding

class DetailBeritaFragment : Fragment() {
    private var _binding: FragmentDetailBeritaBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBeritaBinding.inflate(inflater, container, false)
        val view = binding.root
        var bList:Berita ?=null

        var arguments=arguments

        if(arguments != null && arguments.containsKey(BeritaFragment.NEXT_SCREEN)){
            bList= arguments.getSerializable(BeritaFragment.NEXT_SCREEN)as? Berita
        }
        if (bList!=null){
            binding.judulBerita.text=bList.Judul
            binding.DeskripsiBerita.text=bList.Deskripsi
        }

        binding.beritaBack.setOnClickListener {
            val fragment = BeritaFragment()
            val bundle = Bundle()
            bundle.putSerializable(BeritaFragment.NEXT_SCREEN, NEXT_SCREEN)
            fragment.arguments = bundle

            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        return view
    }
    companion object{
        val NEXT_SCREEN="details_screen"
    }
}