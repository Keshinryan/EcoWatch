package com.eco.ecowatch.Adapter

import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.eco.ecowatch.DataClass.Berita
import com.eco.ecowatch.R
import org.json.JSONException

class BeritaAdapter(private val context: Context):RecyclerView.Adapter<BeritaAdapter.MyViewHolder>() {
    private var beritaList: List<Berita> = emptyList()
    private val url ="https://ecowatchk10.000webhostapp.com/showBerita.php"
    private var onClickListener: OnClickListener?=null
    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
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
        // Call the Check function and get the appropriate view
        val view = MyViewHolder(parent).Check(parent)

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        val size= beritaList.size
        return size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val curItem = beritaList[position]

        holder.itemView.findViewById<TextView>(R.id.list_judul_nama).text = curItem.Judul
        holder.itemView.findViewById<TextView>(R.id.list_deskripsi).text = curItem.Deskripsi

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
            val url = "https://ecowatchk10.000webhostapp.com/DeleteBerita.php"
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
    fun onDeleteClick(position: Int, model: Berita) {
        // Remove the item from the list
        beritaList.toMutableList().apply {
            removeAt(position)
            beritaList = this
        }
        // Notify the adapter about the item removal
        notifyItemRemoved(position)
    }


    interface OnClickListener {
        fun onClick(position: Int, model: Berita)
        fun onDeleteClick(position: Int, model: Berita)
    }

    fun LinkData() {
        val jsonObjectRequest = object : JsonObjectRequest(
            Method.GET, url, null,
            Response.Listener { response ->
                try {
                    val dataArray = response.getJSONArray("data")
                    val newBeritaList = mutableListOf<Berita>()
                    for (i in 0 until dataArray.length()) {
                        val dataObject = dataArray.getJSONObject(i)
                        val berita = Berita(
                            id = dataObject.getString("id"),
                            Judul = dataObject.getString("Judul"),
                            Deskripsi = dataObject.getString("Deskripsi")
                        )
                        newBeritaList.add(berita)
                    }
                    beritaList = newBeritaList
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