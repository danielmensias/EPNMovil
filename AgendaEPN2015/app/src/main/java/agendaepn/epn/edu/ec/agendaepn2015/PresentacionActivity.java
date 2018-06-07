/*============PROYECTO SEMINARIO IB ANDROID============
=======================================================
INTEGRANTES:            ANDRADE HECTOR
                        MENSÍAS DANIEL
CURSO: SEMINARIO IB - GR1
=======================================================
CLASE: PresentacionActivity

DESCRIPCION: Muestra una pantalla de inicio de la aplicacion la cual
simula que la misma se esta cargando
 */
package agendaepn.epn.edu.ec.agendaepn2015;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;


public class PresentacionActivity extends Activity {

    public static final int segundos = 5;
    public static final int milisegundos = segundos*1000;
    private ProgressBar pbprgreso;
    public static final int delay =2;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        //Para quitar el título al activity y mostrar pantalla completa
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_presentacion);


        pbprgreso = (ProgressBar) findViewById(R.id.barraProgreso);
        pbprgreso.setMax(maximoProgreso());
        empezarAnimacion();
    }

    public void empezarAnimacion()
    {
        new CountDownTimer(milisegundos,1000)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                pbprgreso.setProgress(establecerProgreso(millisUntilFinished));
            }

            @Override
        public void onFinish()
            {
                Toast.makeText(getApplicationContext(), "Iniciando Agenda EPN...",
                        Toast.LENGTH_SHORT).show();
                Intent nuevoActivity = new Intent(PresentacionActivity.this, PrincipalActivity.class);
                startActivity(nuevoActivity);
            }
        }.start();

    }
    public int establecerProgreso(long miliseconds)
    {
        return (int) ((milisegundos-miliseconds)/1000);
    }
    public int maximoProgreso()
    {
        return segundos-delay;
    }
}
