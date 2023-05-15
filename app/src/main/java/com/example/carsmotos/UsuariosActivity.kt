package com.example.carsmotos

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carsmotos.adapters.UsuariosAdapter
import com.example.carsmotos.classes.UsuariosModel
import com.example.carsmotos.classes.db.HelperDB
import com.example.carsmotos.model.Usuarios

class UsuariosActivity: AppCompatActivity() {

    //Variables de la Base de Datos
    private var managerUsuarios: Usuarios? = null
    private var dbHelper : HelperDB? = null
    private var db: SQLiteDatabase? = null

    //Variables del formulario
   // private lateinit var btnAgregarMarca: FloatingActionButton
    private lateinit var listUsuarios: RecyclerView
    private var adapter: UsuariosAdapter? = null
    private var usu: UsuariosModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.usuarios_activity)

        //Inicializando componentes y la lista
        inicializarView()
        inicializarRecyclerView()

        //Declarando la base de datos
        dbHelper = HelperDB(this)
        db = dbHelper!!.writableDatabase
        //Buscando los valores para la lista de todas las MARCAS
        managerUsuarios = Usuarios(this)
        val usuList = managerUsuarios!!.showAllUsuarios()

        //Desplegando la informacion en el RecyclerView
        adapter?.addItems(usuList)

        //Al darle click a algun boton de la lista para editar datos
        adapter?.setOnClickItem {
            //Toast.makeText(this, "Id: ${it.id} //Marca: ${it.nombre}",Toast.LENGTH_SHORT).show()
            //Enviamos los valores de importancia al CRUD Marcas
            val idusuarios = it.id.toString()
            val nombreusuarios = it.nombre
            usu = it
            val opc = "editar"
            val intent = Intent(this, MarcasCRUD::class.java)
            intent.putExtra("opc",opc)
            intent.putExtra("idusuarios",idusuarios)
            intent.putExtra("nombreusuarios",nombreusuarios)
            startActivity(intent)

        }



        //Al darle click a eliminar un valor
        adapter?.setOnClickDeleteItem {
            managerUsuarios!!.deleteUser(it.id)
            Toast.makeText(this, "usuario eliminada", Toast.LENGTH_LONG).show()
            recreate()
        }


    }

    private fun inicializarRecyclerView(){
        listUsuarios.layoutManager = LinearLayoutManager(this)
        adapter = UsuariosAdapter()
        listUsuarios.adapter = adapter
    }
    private fun inicializarView(){
        //Declarando objetos en el formulario
        //btnAgregarUsuarios = findViewById(R.id.btnAgregarMarca)
        listUsuarios = findViewById(R.id.listUsuarios)
    }



}