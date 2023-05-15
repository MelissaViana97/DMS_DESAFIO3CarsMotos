package com.example.carsmotos.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carsmotos.R
import com.example.carsmotos.classes.MarcaModel
import com.example.carsmotos.classes.TipoAutomovilModel

class TipoAutomovilAdapter : RecyclerView.Adapter<TipoAutomovilAdapter.tipoautomovilViewHolder>() {
    private var tmlsList: ArrayList<TipoAutomovilModel> = ArrayList()
    private var onClickItem : ((TipoAutomovilModel) -> Unit)? = null
    private var onClickDeleteItem : ((TipoAutomovilModel) -> Unit)? = null
    fun addItems(items: ArrayList<TipoAutomovilModel>){
        this.tmlsList = items
        notifyDataSetChanged()
    }

    //Al darle click a un valor en la lista
    fun setOnClickItem(callback: (TipoAutomovilModel) -> Unit){
        this.onClickItem = callback
    }

    //Al darle click al boton eliminar
    fun setOnClickDeleteItem(callback: (TipoAutomovilModel) -> Unit){
        this.onClickDeleteItem = callback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = tipoautomovilViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.tipoautomovil_layout,parent,false)
    )

    override fun getItemCount(): Int {
        return tmlsList.size
    }

    override fun onBindViewHolder(holder: tipoautomovilViewHolder, position: Int) {
        val tmls = tmlsList[position]
        holder.bindView(tmls)
        holder.itemView.setOnClickListener{onClickItem?.invoke(tmls)}
            holder.btnDeleteTiposAutomoviles.setOnClickListener { onClickDeleteItem?.invoke(tmls) }

    }

    class tipoautomovilViewHolder(var view: View): RecyclerView.ViewHolder(view){
        //Declarando las variables de "marcas_layout"
        private var txtName = view.findViewById<TextView>(R.id.txtTipoAutomovil)
        var btnDeleteTiposAutomoviles = view.findViewById<Button>(R.id.btnDeleteTipoAutomovil)

        fun bindView(tmls: TipoAutomovilModel){
            //Agregando al texto
            txtName.text = tmls.descripcion.toString()
        }
    }


}