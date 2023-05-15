package com.example.carsmotos

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carsmotos.adapters.ColoresAdapter
import com.example.carsmotos.adapters.MarcaAdapter
import com.example.carsmotos.classes.ColoresModel
import com.example.carsmotos.classes.MarcaModel
import com.example.carsmotos.classes.db.HelperDB
import com.example.carsmotos.model.Colores
import com.example.carsmotos.model.Marcas
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.String as String
import kotlin.collections.MutableList as MutableList

class ColoresActivity: AppCompatActivity() {

    //Variables de la Base de Datos
    private var managerColores: Colores? = null
    private var dbHelper : HelperDB? = null
    private var db: SQLiteDatabase? = null

    //Variables del formulario
    private lateinit var btnAgregarColores: FloatingActionButton
    private lateinit var listColores: RecyclerView
    private var adapter: ColoresAdapter? = null
    private var cls: ColoresModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.colores_activity)

        //Inicializando componentes y la lista
        inicializarView()
        inicializarRecyclerView()

        //Declarando la base de datos
        dbHelper = HelperDB(this)
        db = dbHelper!!.writableDatabase
        //Buscando los valores para la lista de todas las MARCAS
        managerColores = Colores(this)
        val clsList = managerColores!!.showAllList()

        //Desplegando la informacion en el RecyclerView
        adapter?.addItems(clsList)

        //Al darle click a algun boton de la lista para editar datos
        adapter?.setOnClickItem {
            //Toast.makeText(this, "Id: ${it.id} //Marca: ${it.nombre}",Toast.LENGTH_SHORT).show()
            //Enviamos los valores de importancia al CRUD Marcas
            val idcolores = it.id.toString()
            val nombrecolores = it.descripcion
            cls = it
            val opc = "editar"
            val intent = Intent(this, ColoresCRUD::class.java)
            intent.putExtra("opc",opc)
            intent.putExtra("idcolores",idcolores)
            intent.putExtra("nombremarca",nombrecolores)
            startActivity(intent)

        }

        //Al agregar un valor
        btnAgregarColores.setOnClickListener {
            //Le envio como "putExtra" la opcion de AGREGAR, porque al inicio de la actividad MarcasCRUD,
            //para que en la actividad MarcaCRUD solo le quito las opciones segun la "opc" recibida
            val opc = "agregar"
            val intent = Intent(this, ColoresCRUD::class.java)
            intent.putExtra("opc",opc)
            startActivity(intent)
        }

        //Al darle click a eliminar un valor
        adapter?.setOnClickDeleteItem {
            managerColores!!.deleteColor(it.id)
            Toast.makeText(this, "Color eliminada", Toast.LENGTH_LONG).show()
            recreate()
        }


    }

    private fun inicializarRecyclerView(){
        listColores.layoutManager = LinearLayoutManager(this)
        adapter = ColoresAdapter()
        listColores.adapter = adapter
    }
    private fun inicializarView(){
        //Declarando objetos en el formulario
        btnAgregarColores = findViewById(R.id.btnAgregarColores)
        listColores= findViewById(R.id.listColores)
    }



}