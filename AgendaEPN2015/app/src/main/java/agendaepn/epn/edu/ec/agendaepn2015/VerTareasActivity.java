/*============PROYECTO SEMINARIO IB ANDROID============
=======================================================
INTEGRANTES:            ANDRADE HECTOR
                        MENSÍAS DANIEL
CURSO: SEMINARIO IB - GR1
=======================================================
CLASE: VerTareasActivity

DESCRIPCION: Esta clase me permite ver las actividades realizando una consulta a la base de datos
con el nombre de la tarea que hemos seleccionado previamente en el listview del layout principal,
obtiene un intent con el cual se puede realizar dicha consulta.
 */
package agendaepn.epn.edu.ec.agendaepn2015;

import android.app.ListActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class VerTareasActivity extends ListActivity {
    String materiaSel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView listaBebidas = getListView();
        Bundle parametro = getIntent().getExtras();
        materiaSel = parametro.getString("solomateria");
        ArrayAdapter<String> adaptador = new ArrayAdapter<String> (
                this,
                android.R.layout.simple_list_item_1,
                consutarTareas(materiaSel));

        TextView textView = new TextView(this);
        //Establezco un encabezado de la lista de tareas por medio de codigo html
        textView.setText(Html.fromHtml("<h2 style=\"position:absolute;top: 50%; left: 50%;\">" +
                "<font color='#FFFFFF'>Lista de tareas:</font></h2>"));

        textView.setBackgroundResource(R.color.colorPrimary);

        listaBebidas.addHeaderView(textView);

        listaBebidas.setAdapter(adaptador);

    }
    //Metodo que consulta a la base de datos las tareas que se tienen para una cierta asignatura
    public String [] consutarTareas(String materia)
    {
        BaseDeDatos bdd = new BaseDeDatos(this);
        String[] resultados = convertirResultado(bdd.obtenerTareasPorMateria(materia));
        return resultados;
    }

    public String[] convertirResultado(String[][] tareasBase)
    {
        String[] tareasConvertidas = new String[tareasBase.length];

        for(int i=Integer.valueOf(tareasBase.length)-1; i>=0;i--)
        {
            tareasConvertidas[i]="\n"+tareasBase[i][0]+") "+tareasBase[i][1]+"\n"+"Enviado:"+
                    tareasBase[i][2]+"\n"+"Entregar:"+tareasBase[i][3]+"\n";
        }
        return tareasConvertidas;
    }
}
