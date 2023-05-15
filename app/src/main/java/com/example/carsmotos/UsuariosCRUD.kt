package com.example.carsmotos

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.carsmotos.classes.db.HelperDB
import com.example.carsmotos.model.Colores
import com.example.carsmotos.model.Marcas
import com.example.carsmotos.model.TiposAutomoviles
import com.example.carsmotos.model.Usuarios
import org.w3c.dom.Text

class UsuariosCRUD : AppCompatActivity() {

    //Database variables
    private var dbHelper : HelperDB? = null
    private var db: SQLiteDatabase? = null
    private var managerUsuarios: Marcas? = null
    private var cursorUsuarios: Cursor? = null

    //MarcasCRUD Activity Variables
    private lateinit var txtUsuariosAdmin: TextView
    private lateinit var btnAddUsuarios: Button
    private lateinit var btnUpdateUsuarios: Button
    private lateinit var btnCancelUsuarios: Button
    private var opc: String? = null
    private var id: String? = null
    private var nombre: String? = null


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_usuarios_activity)

        //Declarando la base de datos
        dbHelper = HelperDB(this)
        db = dbHelper!!.writableDatabase
        //managerUsuarios = Usuarios(this) //ESTE ES DE VITAL IMPORTANCIA NO OLVIDAR SEGUN LA LECTURA A LA TABLA QUE SE HARA
        //Declarando componentes de la actividad Main
        txtUsuariosAdmin = findViewById(R.id.txtUsuariosAdmin)
      btnAddUsuarios = findViewById(R.id.btnAddUsuarios)
        btnUpdateUsuarios = findViewById(R.id.btnUpdateUsuarios)
        btnCancelUsuarios = findViewById(R.id.btnCancelUsuarios)

        //Recogiendo los datos traidos de la actividad anterior
        val datos: Bundle? = intent.getExtras()
        if (datos != null) {
            //Recibimos los datos de la actividad anterior
            opc = intent.getStringExtra("opc").toString()
            Log.d("USUARIOS -CRUD",opc.toString())

            //Activamos y desactivamos botones segun el valor que haya sido enviado como "opc"
            if(opc == "agregar"){
                //No se puede actualizar un valor que no existe aun
                btnUpdateUsuarios.isEnabled = false
                btnUpdateUsuarios.isVisible = false
                btnUpdateUsuarios.isClickable = false
            } else if (opc == "editar"){
                //No se puede agregar un nuevo valor del que se esta actualizando
                btnAddUsuarios.isEnabled = false
                btnAddUsuarios.isVisible = false
                btnAddUsuarios.isClickable = false
                //Importamos lo que se envia desde la otra actividad
                id = intent.getStringExtra("idusuarioa").toString()
                nombre = intent.getStringExtra("nombreusuarios").toString()
                //Actualizamos el valor mostrado en el textbox
                txtUsuariosAdmin.text = nombre

                //Toast.makeText(this, "ID: $id // Marca: $nombre", Toast.LENGTH_LONG).show()

            }

        } else { //Hubo un error encontrando los datos del usuario
            Toast.makeText(this, "Hubo un error cargando la OPC enviada en el UsuariosActivity", Toast.LENGTH_SHORT).show()
            finish()
        }




        //EDITAR MARCA
        btnUpdateUsuarios.setOnClickListener{
            //Guardamos en una variables lo que este en txtMarca
            val nombreUsuarios : String = txtUsuariosAdmin.text.toString()
            val idStr : String = id.toString()
            val idInt : Int = idStr.toInt()
            Log.d("UPDATE-USUARIOS",nombreUsuarios)
            Log.d("UP" +"DATE-USUARIOS ",idInt.toString())
            //Validamos que el campo no este vacio
            if(nombreUsuarios.isEmpty() || nombreUsuarios == null){
                Toast.makeText(this, "Digite el nombre del usuario por favor", Toast.LENGTH_LONG).show()
            } else {
                managerUsuarios!!.updateMarca(
                    id!!.toInt(),
                    nombreUsuarios
                )

                Toast.makeText(this, "Marca actualizada", Toast.LENGTH_LONG).show()
                intent = Intent(this, UsuariosActivity::class.java)
                startActivity(intent)
            }
        }

        //CANCELAR
        btnCancelUsuarios.setOnClickListener{
            finish()
        }

    }

}