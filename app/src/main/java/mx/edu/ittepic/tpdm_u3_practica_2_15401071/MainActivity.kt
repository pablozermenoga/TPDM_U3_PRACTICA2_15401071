package mx.edu.ittepic.tpdm_u3_practica_2_15401071

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL

class MainActivity : AppCompatActivity() {
    var descripcion : EditText?= null
    var monto : EditText?= null
    var fechaVencimiento : EditText?= null
    var insertar : Button?= null
    var cargar : Button?= null
    var mostrar : Button?= null
    var mostraRegistros : TextView?= null
    var pagado : CheckBox?= null
    //-------------------------------------
    var varPag = ""
    var jsonDatos = ArrayList<org.json.JSONObject>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        descripcion=findViewById(R.id.descripcion)
        monto=findViewById(R.id.monto)
        fechaVencimiento=findViewById(R.id.fecha)
        insertar=findViewById(R.id.insertar)
        cargar=findViewById(R.id.cargar)
        mostraRegistros=findViewById(R.id.mostraregistros)
        pagado=findViewById(R.id.pagado)

        insertar?.setOnClickListener {
            if(pagado?.isChecked == true){
                varPag= "true"
            }
            else{
                varPag = "false"
            }

            var conexionWeb = ConexionWeb(this)

            conexionWeb.agregarVariables("descripcion", descripcion?.text.toString())
            conexionWeb.agregarVariables("monto", monto?.text.toString())
            conexionWeb.agregarVariables("fechaVencimiento", fechaVencimiento?.text.toString())
            conexionWeb.agregarVariables("pagado", pagado?.text.toString())

            conexionWeb.execute(URL("https://nameless-wave-53644.herokuapp.com/insertar_recibos.php"))
        }
        cargar?.setOnClickListener {
            cargar?.setOnClickListener {
                var conexionWeb = ConexionWeb(this)
                conexionWeb.execute(URL("https://nameless-wave-53644.herokuapp.com/consulta_recibos.php"))
            }
        }

        mostrar?.setOnClickListener {
            val posicion = descripcion?.text.toString().toInt()
            val jsonObject = jsonDatos.get(posicion)

            mostraregistros.setText("Descripción "+jsonObject.getString("descripcion"))
        }
    }

    fun mostrarRespuesta(cadena: String){
        var jsonarray = org.json.JSONArray(cadena)
        var total = jsonarray.length()
        (0..total).forEach {
            jsonDatos.add(jsonarray.getJSONObject(it))
        }

    }
}
