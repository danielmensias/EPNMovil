/*============PROYECTO SEMINARIO IB ANDROID============
=======================================================
INTEGRANTES:            ANDRADE HECTOR
                        MENSÍAS DANIEL
CURSO: SEMINARIO IB - GR1
=======================================================
CLASE: Opciones

DESCRIPCION: Esta clase me permite seleccionar entre dos acciones que se pueden realizar
como son consultar las tareas y añadir a tareas a una materia seleccionada previamente en el
listview
 */
package agendaepn.epn.edu.ec.agendaepn2015;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Opciones extends AppCompatActivity {
    String materiaSel;
    TextView mat;
    String[] nombremateria;
    String solomateria;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones);
        ImageButton ver=(ImageButton)findViewById(R.id.btnverTarea);
        ImageButton añadir=(ImageButton)findViewById(R.id.ibtntareaNueva);
        //Creo el bundle en el cual voy a recibir el intent que me envio la clase principal
        //la cual contiene el nombre de la materia que seleccione en el listview
        Bundle parametro = getIntent().getExtras();
        mat=(TextView)findViewById(R.id.txtMateria);
        materiaSel = parametro.getString("materiaseleccionada");
        mat.setText(materiaSel);
        dividirTexto(materiaSel);

        //Llamo a la nueva activity pasandole como dato el nombre de la materia selecionada
        ver.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Bundle parametros = new Bundle();
                parametros.putString("solomateria", solomateria);
                Intent i = new Intent(getApplicationContext(), VerTareasActivity.class);
                i.putExtras(parametros);
                //Inicia la actividad
                startActivity(i);
            }
        });
        //Llamo a la nueva activity pasandole como dato el nombre de la materia selecionada
        añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle parametros = new Bundle();
                parametros.putString("solomateria", solomateria);
                Intent i = new Intent(getApplicationContext(), NuevaTareaActivity.class);
                i.putExtras(parametros);
                //Inicia la actividad
                startActivity(i);
            }
        });

    }
    public void dividirTexto(String materiahorario)
    {
        nombremateria=materiahorario.split(":");
        solomateria=nombremateria[0];
    }
}
