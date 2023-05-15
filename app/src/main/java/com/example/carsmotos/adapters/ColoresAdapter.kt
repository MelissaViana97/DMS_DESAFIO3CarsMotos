package com.example.carsmotos.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carsmotos.R
import com.example.carsmotos.classes.ColoresModel


class ColoresAdapter : RecyclerView.Adapter<ColoresAdapter.coloresViewHolder>() {
    private var clsList: ArrayList<ColoresModel> = ArrayList()
        private var onClickItem : ((ColoresModel) -> Unit)? = null
    private var onClickDeleteItem : ((ColoresModel) -> Unit)? = null
    fun addItems(items: ArrayList<ColoresModel>){
        this.clsList = items
        notifyDataSetChanged()
    }

    //Al darle click a un valor en la lista
    fun setOnClickItem(callback: (ColoresModel) -> Unit){
        this.onClickItem = callback
    }

    //Al darle click al boton eliminar
    fun setOnClickDeleteItem(callback: (ColoresModel) -> Unit){
        this.onClickDeleteItem = callback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = coloresViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.colores_layout,parent,false)
    )

    override fun getItemCount(): Int {
        return clsList.size
    }

    override fun onBindViewHolder(holder: coloresViewHolder, position: Int) {
        val cls = clsList[position]
        holder.bindView(cls)
        holder.itemView.setOnClickListener{onClickItem?.invoke(cls)}
        holder.btnDeleteColores.setOnClickListener { onClickDeleteItem?.invoke(cls) }

    }

    class coloresViewHolder(var view: View): RecyclerView.ViewHolder(view){
        //Declarando las variables de "marcas_layout"
        private var txtName = view.findViewById<TextView>(R.id.txtColores)
        var btnDeleteColores = view.findViewById<Button>(R.id.btnDeleteColores)

        fun bindView(cls: ColoresModel){
            //Agregando al texto
            txtName.text = cls.descripcion.toString()
        }
    }


}