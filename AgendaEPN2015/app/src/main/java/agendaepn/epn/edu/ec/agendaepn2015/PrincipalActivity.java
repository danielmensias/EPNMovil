/*============PROYECTO SEMINARIO IB ANDROID============
=======================================================
INTEGRANTES:            ANDRADE HECTOR
                        MENSÍAS DANIEL
CURSO: SEMINARIO IB - GR1
=======================================================
CLASE: PrincipalActivity

DESCRIPCION: Es la clase encargada de gestionar cada uno de los activitys que se van a
lanzar dependiendo de los botones que presionemos, es la clase padre la que
envia los intents hacia los de mas layouts y es el encargado de receptar lo que viene
del lector de codigos qr
 */
package agendaepn.epn.edu.ec.agendaepn2015;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.SimpleDateFormat;
import java.util.Date;


public class PrincipalActivity extends AppCompatActivity  {
    ArrayAdapter<String> adaptador;
    String[] materiaslista;
    String cadena;
    BaseDeDatos db;
    String dayactual;
    ListView listaprincipal;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Button acerca = (Button) findViewById(R.id.btnacerca);
        ImageButton entry = (ImageButton) findViewById(R.id.ibtnLanzarQR);
        listaprincipal=(ListView)findViewById(R.id.lstmaterias);
        Date date2=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        dayactual = sdf.format(date2);
        TextView txt = (TextView) findViewById(R.id.datopasado);
        txt.setText(convertirDia(dayactual));
        cargarMateriasDiaHoy(dayactual);

        final DatePicker datePicker1 = (DatePicker) findViewById(R.id.datePicker);
        datePicker1.init(datePicker1.getYear(), datePicker1.getMonth(), datePicker1.getDayOfMonth(), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int month, int day) {
                // Hacemos algo cuando cambia la fecha, la formateamos y la enviamos para que nos
                //cargue las materias que tenemos ese dia
                Date date = new Date(year, month, day - 1);
                SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
                String dayOfWeek = sdf.format(date);
                TextView txt = (TextView) findViewById(R.id.textView2);
                txt.setText(convertirDia(dayOfWeek));
                cargarMateriasDiaHoy(convertirDia(dayOfWeek));
            }
        });

        //Cuando tengamos datos en el listview este metodo me va a permitir lanzar un nuevo layout
        //para realizar dos acciones respecto al elemento seleccionado, pasandole como argumento
        //el nombre de la materia que se escogio
        listaprincipal.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long arg3) {
                view.setSelected(true);
                String mat=adaptador.getItem(position);
                Bundle parametros = new Bundle();
                parametros.putString("materiaseleccionada", mat);
                Intent i = new Intent(getApplicationContext(), Opciones.class);
                i.putExtras(parametros);
                startActivity(i);
            }
        });
        //Llama al layout LectorQR
        entry.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent ListSong = new Intent(getApplicationContext(), LectorQRActivity.class);
                startActivity(ListSong);
            }
        });

        //Nos muestra la informacion en un nuevo layout con el nombre de los desarrolladores
        acerca.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent ListSong = new Intent(getApplicationContext(), AcercaDeActivity.class);
                startActivity(ListSong);
            }
        });

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
    //Este metodo me permite de una manera dinamica actualizar el layout principal dependiendo del dia que sea
    //o por su defecto que se escoja
    public void cargarMateriasDiaHoy(String diaselec) {
        db=new BaseDeDatos(this);
        try {
            String[] result=(convertirMatriz(db.obtenerMateriasPorDia(diaselec)));
            adaptador=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, result);
            listaprincipal=(ListView)findViewById(R.id.lstmaterias);
            listaprincipal.setAdapter(adaptador);
        }
        catch (Exception e)
        {
            Toast.makeText(this.getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    public String[] convertirMatriz(String[][] materiasbase)
    {
        String[] materiasConvertidas=new String[materiasbase.length];

        for(int i=0;i<Integer.valueOf(materiasbase.length);i++)
        {
            materiasConvertidas[i]=materiasbase[i][0]+":"+materiasbase[i][1];
        }
        return materiasConvertidas;
    }
    public String convertirDia(String dia)
    {
        String respuesta="nocalculado";
        String[] diassemana={"Lunes","Martes","Miercoles","Jueves","Viernes","Sabado","Domingo"};
        String[] diaporconvertir={"lunes","martes","miércoles","jueves","viernes","sábado","domingo"};
        for(int i=0;i<7;i++)
        {
            if(dia.equals(diaporconvertir[i]))
            {
                respuesta=diassemana[i];
            }
        }
        return respuesta;
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Principal Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://agendaepn.epn.edu.ec.agendaepn2015/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Principal Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://agendaepn.epn.edu.ec.agendaepn2015/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
