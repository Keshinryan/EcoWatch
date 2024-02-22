package com.eco.ecowatch.Fragment.User

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eco.ecowatch.Adapter.EdukasiAdapter
import com.eco.ecowatch.DataClass.Edukasi
import com.eco.ecowatch.R
import com.eco.ecowatch.databinding.FragmentEdukasiBinding

class EdukasiFragment : Fragment() {
    private var _binding: FragmentEdukasiBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEdukasiBinding.inflate(inflater, container, false)
        val rootView = binding.root

        val adapter = EdukasiAdapter(requireContext())

        adapter.setOnClickListener(object : EdukasiAdapter.OnClickListener{
            override fun onClick(position: Int, model: Edukasi) {
                val fragment= DetailEdukasiFragment()
                val bundle= Bundle()
                bundle.putSerializable(NEXT_SCREEN,model)
                fragment.arguments=bundle

                val transaction =requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.frame_layout,fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }

            override fun onDeleteClick(position: Int, model: Edukasi) {
                TODO("Not yet implemented")
            }
        })

        adapter.LinkData()

        val rv: RecyclerView = rootView.findViewById(R.id.EdukasirecycleView)

        // Set up RecyclerView with LinearLayoutManager and DividerItemDecoration
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.addItemDecoration(DividerItemDecoration(context, 0))

        return rootView
    }

    companion object{
        val NEXT_SCREEN="details_screen"
    }
}