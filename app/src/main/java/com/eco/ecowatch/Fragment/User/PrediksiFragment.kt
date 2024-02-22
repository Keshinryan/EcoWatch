package com.eco.ecowatch.Fragment.User

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.eco.ecowatch.databinding.FragmentPrediksiBinding
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.Charset

class PrediksiFragment : Fragment() {
    private lateinit var suhu: EditText
    private lateinit var lembab: EditText
    private lateinit var k_angin: EditText
    private lateinit var deteksi: Button
    private lateinit var hasil: TextView
    lateinit var handler: Handler
    private  var _binding: FragmentPrediksiBinding?=null
    private val binding get() = _binding!!
    private val url ="https://keshinryan.pythonanywhere.com/predict"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPrediksiBinding.inflate(inflater, container, false)
        val view = binding.root
        suhu = binding.suhu
        lembab = binding.Lembab
        k_angin = binding.kAngin
        deteksi = binding.deteksi
        hasil = binding.hasil
        deteksi.setOnClickListener {
            val jsonObject = JSONObject()

            jsonObject.put("suhu", suhu.text.toString())
            jsonObject.put("lembab", lembab.text.toString())
            jsonObject.put("k_angin", k_angin.text.toString())


            val requestBody = jsonObject.toString()

            val jsonObjectRequest = object : JsonObjectRequest(
                Method.POST, url, jsonObject,
                Response.Listener { response ->
                    try {
                        val data = response.getString("CC")
                        hasil.text = if (data != null) {
                            if (data == "0") {
                                "Berawan"
                            } else if (data == "1") {
                                "Hujan Lebat"
                            } else if (data == "2") {
                                "Hujan Ringan"
                            }else if (data == "3") {
                                "Hujan ekstrem"
                            }else if (data == "4") {
                                "Hujan sangat lebat"
                            }else{
                                "Hujan sedang"
                            }
                        }else{
                            "Prediksi Gagal"
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    handler.postDelayed({
                        Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
                    }, 10)
                }) {
                override fun getBodyContentType(): String {
                    return "application/json"
                }

                override fun getBody(): ByteArray {
                    return requestBody.toByteArray(Charset.defaultCharset())
                }
            }
            val queue = Volley.newRequestQueue(requireContext())
            queue.add(jsonObjectRequest)
        }
        return view
    }
}