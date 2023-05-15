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

class ColoresCRUD : AppCompatActivity() {

    //Database variables
    private var dbHelper : HelperDB? = null
    private var db: SQLiteDatabase? = null
    private var managerColores: Colores? = null
    private var cursosColores: Cursor? = null

    //Coloewa CRUD Activity Variables
    private lateinit var txtColoresAdmin: TextView
    private lateinit var btnAddColores: Button
    private lateinit var btnUpdateColores: Button
    private lateinit var btnCancelColores: Button
    private var opc: String? = null
    private var id: String? = null
    private var nombre: String? = null


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_colores_activity)

        //Declarando la base de datos
        dbHelper = HelperDB(this)
        db = dbHelper!!.writableDatabase
        managerColores = Colores(this) //ESTE ES DE VITAL IMPORTANCIA NO OLVIDAR SEGUN LA LECTURA A LA TABLA QUE SE HARA
        //Declarando componentes de la actividad Main
        txtColoresAdmin = findViewById(R.id.txtColoresAdmin)
        btnAddColores = findViewById(R.id.btnAddColores)
        btnUpdateColores = findViewById(R.id.btnUpdateColores)
       // btnCancelColores = findViewById(R.id.btnCancelColores

        //Recogiendo los datos traidos de la actividad anterior
            val datos: Bundle? = intent.getExtras()
        if (datos != null) {
            //Recibimos los datos de la actividad anterior
            opc = intent.getStringExtra("opc").toString()
            Log.d("COLORES-CRUD",opc.toString())

            //Activamos y desactivamos botones segun el valor que haya sido enviado como "opc"
            if(opc == "agregar"){
                //No se puede actualizar un valor que no existe aun
                btnUpdateColores.isEnabled = false
                btnUpdateColores.isVisible = false
                btnUpdateColores.isClickable = false
            } else if (opc == "editar"){
                //No se puede agregar un nuevo valor del que se esta actualizando
                btnAddColores.isEnabled = false
                btnAddColores.isVisible = false
                btnAddColores.isClickable = false
                //Importamos lo que se envia desde la otra actividad
                id = intent.getStringExtra("idcolores").toString()
                nombre = intent.getStringExtra("nombrecolores").toString()
                //Actualizamos el valor mostrado en el textbox
                txtColoresAdmin.text = nombre

                //Toast.makeText(this, "ID: $id // Marca: $nombre", Toast.LENGTH_LONG).show()

            }

        } else { //Hubo un error encontrando los datos del usuario
            Toast.makeText(this, "Hubo un error cargando la OPC enviada en el MarcasActivity", Toast.LENGTH_SHORT).show()
            finish()
        }


        //AGREGAR MARCA
        btnAddColores.setOnClickListener{
            //Guardamos en una variables lo que este en txtMarca
            val nombreColores : String = txtColoresAdmin.text.toString()

            //Validamos que el campo no este vacio
            if(nombreColores.isEmpty() || nombreColores == null){
                Toast.makeText(this, "Digite el nombre del color", Toast.LENGTH_LONG).show()
            } else {
                managerColores!!.addNewColor(
                    nombreColores
                )
                Toast.makeText(this, "Color agregada", Toast.LENGTH_LONG).show()
                val intent = Intent(this,ColoresActivity::class.java)
                startActivity(intent)
            }
        }

        //EDITAR MARCA
        btnUpdateColores.setOnClickListener{
            //Guardamos en una variables lo que este en txtMarca
            val nombreColores : String = txtColoresAdmin.text.toString()
            val idStr : String = id.toString()
            val idInt : Int = idStr.toInt()
            Log.d("UPDATE-MARCA",nombreColores)
            Log.d("UP" +
                    "DATE-MARCA",idInt.toString())
            //Validamos que el campo no este vacio
            if(nombreColores.isEmpty() || nombreColores == null){
                Toast.makeText(this, "Digite el color", Toast.LENGTH_LONG).show()
            } else {
                managerColores!!.updateColor(
                    id!!.toInt(),
                    nombreColores
                )

                    Toast.makeText(this, "Color  actualizado", Toast.LENGTH_LONG).show()
                intent = Intent(this, MarcasActivity::class.java)
                startActivity(intent)
            }
        }

        //CANCELAR
        btnCancelColores.setOnClickListener{
            finish()
        }

    }

}