package com.example.carsmotos.adapters

import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carsmotos.R
import com.example.carsmotos.classes.UsuariosModel

class UsuariosAdapter : RecyclerView.Adapter<UsuariosAdapter.usuariosViewHolder>() {
    private var usuList: ArrayList<UsuariosModel> = ArrayList()
    private var onClickItem : ((UsuariosModel) -> Unit)? = null
    private var onClickDeleteItem : ((UsuariosModel) -> Unit)? = null
    fun addItems(items: Cursor?){
        this.usuList = items
        notifyDataSetChanged()
    }

    //Al darle click a un valor en la lista
    fun setOnClickItem(callback: (UsuariosModel) -> Unit){
        this.onClickItem = callback
    }

    //Al darle click al boton eliminar
    fun setOnClickDeleteItem(callback: (UsuariosModel) -> Unit){
        this.onClickDeleteItem = callback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = usuariosViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.usuarios_layout,parent,false)
    )

    override fun getItemCount(): Int {
        return usuList.size
    }

    override fun onBindViewHolder(holder: usuariosViewHolder, position: Int) {
        val usu = usuList[position]
        holder.bindView(usu)
        holder.itemView.setOnClickListener{onClickItem?.invoke(usu)}
        holder.btnDeleteUsuarios.setOnClickListener { onClickDeleteItem?.invoke(usu) }

    }

    class usuariosViewHolder(var view: View): RecyclerView.ViewHolder(view){
        //Declarando las variables de "marcas_layout"
        private var txtName = view.findViewById<TextView>(R.id.txtUsuarios)
        var btnDeleteUsuarios = view.findViewById<Button>(R.id.btnDeleteUsuarios)

        fun bindView(usu: UsuariosModel){
            //Agregando al texto
            txtName.text = usu.nombre.toString()
        }
    }


}