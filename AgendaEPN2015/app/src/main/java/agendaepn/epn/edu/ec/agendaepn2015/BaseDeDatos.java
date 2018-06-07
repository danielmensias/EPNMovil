/*============PROYECTO SEMINARIO IB ANDROID============
=======================================================
INTEGRANTES:            ANDRADE HECTOR
                        MENSÍAS DANIEL
CURSO: SEMINARIO IB - GR1
=======================================================
CLASE: BaseDeDatos

DESCRIPCION: Esta clase nos permite crear y administrar nuestra base de datos, con la cual vamos a gestionar
toda la informacion de la aplicacion haciendo uso de diferentes metodos tanto para escribir en la base
de datos como para realizar las consultas hacia la misma
 */
package agendaepn.epn.edu.ec.agendaepn2015;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDeDatos extends SQLiteOpenHelper
{
    //Creo la base de datos, la cual contiene dos tablas: materias y tareas
    public static final String DATABASE_NAME = "Agenda.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLA_MATERIAS = "materias";
    public static final String COLUMNA_ID = "id";
    public static final String COLUMNA_MATERIA = "materia";
    public static final String COLUMNA_HORARIO = "horario";
    public static final String COLUMNA_DIA = "dia";

    public static final String TABLA_TAREAS = "tareas";
    public static final String COLUMNA_DEBER = "deber";
    public static final String COLUMNA_FECHA = "fecha";
    public static final String COLUMNA_HECHO = "hecho";

    //Realizo la sentencia sql para crear las tablas especificando sus atributos
    //tipo de dato, el id autonumerado y que no soporte valores nulos
    private static final String SQL_CREAR_MATERIAS  = "create table "
            + TABLA_MATERIAS + "(" + COLUMNA_ID
            + " integer primary key autoincrement, "
            + COLUMNA_MATERIA
            + " text not null, "
            + COLUMNA_HORARIO
            + " text not null, "
            + COLUMNA_DIA
            + " text not null);";

    private static final String SQL_CREAR_TAREAS  = "create table "
            + TABLA_TAREAS + "(" + COLUMNA_ID
            + " integer primary key autoincrement, "
            + COLUMNA_MATERIA
            + " text not null, "
            + COLUMNA_DEBER
            + " text not null, "
            + COLUMNA_FECHA
            + " text not null, "
            + COLUMNA_HECHO
            + " text not null);";

    public BaseDeDatos(Context context){
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Crear la base de datos
        db.execSQL(SQL_CREAR_MATERIAS);
        db.execSQL(SQL_CREAR_TAREAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Actualizar la base de datos
    }

    //Este metodo me permite agregar materias, las cuales van a ser agregadas una vez sean escaneadas
    //por el lector de codigo qr
    public void agregarMateria(String materia, String horario, String dia)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMNA_MATERIA, materia);
        values.put(COLUMNA_HORARIO, horario);
        values.put(COLUMNA_DIA, dia);
        db.insert(TABLA_MATERIAS, null,values);
        db.close();
    }
    //El metodo agregarTarea nos permite ingresar a la base de datos la tarea para la materia que hayamos
    //seleccionado del conjunto que tenemos
    public void agregarTarea(String materia, String deber, String fecha, String hecho)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMNA_MATERIA, materia);
        values.put(COLUMNA_DEBER, deber);
        values.put(COLUMNA_FECHA, fecha);
        values.put(COLUMNA_HECHO, hecho);
        db.insert(TABLA_TAREAS, null,values);
        db.close();
    }
    //Para una mejor ayuda se realizo esta clase al momento de abrir la aplicacion y tener cargados
    //datos en la base se nos muestran las materias de ese dia
    public String[][] obtenerMateriasPorDia(String dia){

        SQLiteDatabase db = this.getReadableDatabase();

        String[] columnas = {COLUMNA_MATERIA, COLUMNA_HORARIO};
        String seleccion = COLUMNA_DIA + " = ? ";
        String[] argumentos = new String[]{dia};

        Cursor cursor = db.query(
                TABLA_MATERIAS,
                columnas,
                seleccion,
                argumentos,
                null,
                null,
                null,
                null);

        String[][] respuesta = new  String[cursor.getCount()][cursor.getColumnCount()];

        int i = 0;
        while(cursor.moveToNext()){
            respuesta[i][0] = cursor.getString(0);
            respuesta[i][1] = cursor.getString(1);
            i++;
        }

        db.close();

        return respuesta;
    }
    //Obtenemos las tareas para la materia seleccionada haciendo uso de esta clase
    //la cual solo necesita como parametro el nombre de la materia y realiza la busqueda en la base
    public String[][] obtenerTareasPorMateria(String materia){

        SQLiteDatabase db = this.getReadableDatabase();

        String[] columnas = {COLUMNA_ID, COLUMNA_DEBER, COLUMNA_FECHA, COLUMNA_HECHO};
        String seleccion = COLUMNA_MATERIA + " = ? ";
        String[] argumentos = new String[]{materia};

        Cursor cursor = db.query(
                TABLA_TAREAS,
                columnas,
                seleccion,
                argumentos,
                null,
                null,
                null,
                null);

        String[][] respuesta = new  String[cursor.getCount()][cursor.getColumnCount()];

        int i = 0;
        while(cursor.moveToNext()){
            respuesta[i][0] = cursor.getString(0);
            respuesta[i][1] = cursor.getString(1);
            respuesta[i][2] = cursor.getString(2);
            respuesta[i][3] = cursor.getString(3);
            i++;
        }

        db.close();

        return respuesta;
    }
}


