package com.eco.ecowatch.Adapter

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.eco.ecowatch.DataClass.Berita
import com.eco.ecowatch.DataClass.Edukasi
import com.eco.ecowatch.R
import org.json.JSONException

class EdukasiAdapter (private val context: Context): RecyclerView.Adapter<EdukasiAdapter.MyViewHolder>() {

    private var edukasiList: List<Edukasi> = emptyList()
    private val url ="https://ecowatchk10.000webhostapp.com/showEdukasi.php"
    private var onClickListener: OnClickListener?=null
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var sharedpreferences: SharedPreferences? = null
        val SHARED_PREFS = "shared_prefs"
        var username: String? = null
        fun Check(parent: ViewGroup): View {
            sharedpreferences = parent.context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
            username = sharedpreferences!!.getString("USERNAME", null)
            if (username == null) {
                // Return a different layout if sharedpreferences is null
                return LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
            } else {
                // Return the default layout if sharedpreferences is not null
                return LayoutInflater.from(parent.context).inflate(R.layout.row_admin, parent, false)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = MyViewHolder(parent).Check(parent)

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        val size= edukasiList.size
        return size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val curItem = edukasiList[position]
        holder.itemView.findViewById<TextView>(R.id.list_judul_nama).text= curItem.Nama
        holder.itemView.findViewById<TextView>(R.id.list_deskripsi).text= curItem.Deskripsi
        holder.itemView.setOnClickListener {
            if (onClickListener!=null){
                onClickListener!!.onClick(position,curItem)
            }
        }
        var sharedpreferences: SharedPreferences? = null
        val SHARED_PREFS = "shared_prefs"
        var username: String? = null
        sharedpreferences = holder.itemView.context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        username = sharedpreferences!!.getString("USERNAME", null)
        var button: ImageButton?=null
        if (username=="admin"){
            button=holder.itemView.findViewById<ImageButton>(R.id.delete)
        }
        button?.setOnClickListener {
            onClickListener?.onDeleteClick(position, curItem)
            val url = "https://ecowatchk10.000webhostapp.com/DeleteEdukasi.php"
            val request = object : StringRequest(
                Method.POST, url,
                Response.Listener { response ->
                    Toast.makeText(holder.itemView.context, response.toString(), Toast.LENGTH_SHORT).show()
                },
                Response.ErrorListener { error ->
                    // Handle the error response
                    Toast.makeText(holder.itemView.context, "Error: " + error.message, Toast.LENGTH_SHORT).show()
                }) {
                override fun getParams(): Map<String, String>? {
                    val params = HashMap<String, String>()
                    params["id"] = curItem.id
                    return params
                }
            }
            val queue = Volley.newRequestQueue(holder.itemView.context)
            queue.add(request)
        }
    }
    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }
    fun onDeleteClick(position: Int, model: Edukasi) {
        // Remove the item from the list
        edukasiList.toMutableList().apply {
            removeAt(position)
            edukasiList = this
        }
        // Notify the adapter about the item removal
        notifyItemRemoved(position)
    }

    interface OnClickListener {
        fun onClick(position: Int, model: Edukasi)
        fun onDeleteClick(position: Int, model: Edukasi)
    }

    fun LinkData() {
        val jsonObjectRequest = object : JsonObjectRequest(
            Method.GET, url, null,
            Response.Listener { response ->
                try {
                    val dataArray = response.getJSONArray("data")
                    val newEdukasiList = mutableListOf<Edukasi>()
                    for (i in 0 until dataArray.length()) {
                        val dataObject = dataArray.getJSONObject(i)
                        val edukasi = Edukasi(
                            id = dataObject.getString("id"),
                            Nama = dataObject.getString("Nama"),
                            Deskripsi = dataObject.getString("Deskripsi")
                        )
                        newEdukasiList.add(edukasi)
                    }
                    edukasiList = newEdukasiList
                    notifyDataSetChanged()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }) {
            override fun getBodyContentType(): String {
                return "application/json"
            }

            override fun getBody(): ByteArray {
                return ByteArray(0)
            }
        }
        val queue = Volley.newRequestQueue(context)
        queue.add(jsonObjectRequest)
    }



}