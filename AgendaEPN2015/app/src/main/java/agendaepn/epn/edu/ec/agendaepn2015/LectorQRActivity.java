package agendaepn.epn.edu.ec.agendaepn2015;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.util.Linkify;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class LectorQRActivity extends AppCompatActivity implements View.OnClickListener {
    ListView listam;
    String[] textodividido;
    ArrayAdapter<String> adaptador;
    TextView muestra;
    ImageButton check;
    String prueba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lector_qr);
        muestra = (TextView) findViewById(R.id.textView);
        //muestra.setVisibility(View.INVISIBLE);
        check=(ImageButton)findViewById(R.id.ibtnCheck);
        check.setOnClickListener(this);
        configureButtonReader();
    }
    public void onClick(View v) {
        BaseDeDatos base=new BaseDeDatos(this);
        for(int i=0;i< Integer.valueOf(textodividido.length);i+=3)
        {
            //Toast.makeText(this.getApplicationContext(),textodividido[i]+"/"+textodividido[i+1]+"/"+textodividido[i+2],Toast.LENGTH_SHORT).show();
            base.agregarMateria(String.valueOf(textodividido[i]), String.valueOf(textodividido[i+2]), String.valueOf(textodividido[i+1]));
        }
        //Toast.makeText(this.getApplicationContext(),base.obtenerMateriasPorDia("Jueves")[0][0],Toast.LENGTH_SHORT).show();

        //Define el bundle
        //Bundle parametros = new Bundle();
        //parametros.putString("materias", prueba);
        //Define la actividad
        Intent i = new Intent(this, PrincipalActivity.class);
        //i.putExtras(parametros);
        //Inicia la actividad
        startActivity(i);
    }

    private void configureButtonReader() {
        final ImageButton buttonReader = (ImageButton)findViewById(R.id.imageButton);
        buttonReader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new IntentIntegrator(LectorQRActivity.this).initiateScan();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        final IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        handleResult(scanResult);
    }

    private void handleResult(IntentResult scanResult) {
        if (scanResult != null) {
            updateUITextViews(scanResult.getContents(), scanResult.getFormatName());
        } else {
            Toast.makeText(this, "No se ha leído nada :(", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUITextViews(String scan_result, String scan_result_format) {
        ((TextView)findViewById(R.id.tvFormat)).setText(scan_result_format);
        final TextView tvResult = (TextView)findViewById(R.id.tvResult);
        tvResult.setText(scan_result);
        dividirTexto(scan_result);
        Linkify.addLinks(tvResult, Linkify.ALL);
    }

    public void dividirTexto(String textoscaneado)
    {
        listam = (ListView)findViewById(R.id.lstmaterias);
        textodividido=textoscaneado.split("%");
        adaptador= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, textodividido);
        listam.setAdapter(adaptador);
        prueba=textoscaneado;
        muestra.setText(String.valueOf(adaptador.getCount()));
    }
    public void llenarLista(String textoscaneado)
    {
        prueba=textoscaneado;
        listam = (ListView)findViewById(R.id.lstmaterias);
        textodividido=textoscaneado.split("%");
        adaptador= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, textodividido);
        listam.setAdapter(adaptador);
        TextView texto=(TextView)findViewById(R.id.textView);
        texto.setText(textodividido.toString());
        /*for(int i=0;i<adaptador.getCount();i+=3)
        {
            base.agregarMateria(adaptador.getItem(i),adaptador.getItem(i+1),adaptador.getItem(i+2));
        }*/
    }


}