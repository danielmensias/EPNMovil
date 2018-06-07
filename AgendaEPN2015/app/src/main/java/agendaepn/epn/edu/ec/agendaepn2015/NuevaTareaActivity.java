/*============PROYECTO SEMINARIO IB ANDROID============
=======================================================
INTEGRANTES:            ANDRADE HECTOR
                        MENSÍAS DANIEL
CURSO: SEMINARIO IB - GR1
=======================================================
CLASE: NuevaTareaActivity

DESCRIPCION: Esta clase me permite crear las actividades para cada una de las materias listadas
en el activity principal la misma que al ser descrita se guarda en la base de datos para su posterior
consulta
 */
package agendaepn.epn.edu.ec.agendaepn2015;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NuevaTareaActivity extends AppCompatActivity {
    TextView mat;
    String materiaSel;
    public CalendarView fechaEntrega;
    //En el metodo onCreate recibimos el string materiaSel que contiene el nombre de la materia
    //seleccionada en nuestro listview y al cual le vamos a asugnar la tarea nueva que creeemos
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_tarea);
        Bundle parametro = getIntent().getExtras();
        materiaSel = parametro.getString("solomateria");
    }
    //Metodo que me permite escribir la fecha actual al momento de guardar la tarea en la base de datos
    public void escribirFechaActual(View view)
    {
        EditText fecha = (EditText)this.findViewById(R.id.etFechaActual);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fechaActual = sdf.format(Calendar.getInstance().getTime());
        fecha.setText(fechaActual);
    }
    //Metodo que me permite escribir la fecha de entrega para cuando es la tarea ingresada
    public void escribirFechaEntrega(View view)
    {
        fechaEntrega = (CalendarView)this.findViewById(R.id.calendarView);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fechaSeleccionada = sdf.format(new Date(fechaEntrega.getDate()));

        EditText fecha = (EditText)this.findViewById(R.id.etFechaEntrega);
        fecha.setText(fechaSeleccionada);
    }

    public void guardarTarea(View view)
    {
        BaseDeDatos bdd = new BaseDeDatos(this);

        String tarea = ((EditText) this.findViewById(R.id.etTarea)).getText().toString();
        String fechaEnviado = ((EditText)this.findViewById(R.id.etFechaActual)).getText().toString();
        String fechaEntrega =((EditText)this.findViewById(R.id.etFechaEntrega)).getText().toString();

        ((EditText)this.findViewById(R.id.etFechaEntrega)).setText("");

        bdd.agregarTarea(materiaSel, tarea, fechaEnviado, fechaEntrega);
        Toast.makeText(getApplicationContext(), "Tarea ingresada exitosamente!", Toast.LENGTH_SHORT).show();

        ((EditText)this.findViewById(R.id.etTarea)).setText("");
        ((EditText)this.findViewById(R.id.etFechaActual)).setText("");
        ((EditText)this.findViewById(R.id.etFechaEntrega)).setText("");
    }


}
