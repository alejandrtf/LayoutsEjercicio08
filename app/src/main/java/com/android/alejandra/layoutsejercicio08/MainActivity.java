package com.android.alejandra.layoutsejercicio08;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private TextView bandaAnterior = null;
    private int contador = 0;

    // para guardar el texto de una banda
    private String texto = null;
    // para guardar el background de una banda
    private Drawable fondo = null;
    // para guardar los colores del texto de una banda
    ColorStateList colorTexto = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Método que se llama al pulsar click sobre un color y muestra un Toast con
     * el nombre del color
     *
     * @param v Vista pulsada
     */
    public void mostrarToast(View v) {

        TextView pulsado = (TextView) v;

        Toast toast = Toast.makeText(getBaseContext(), pulsado.getText()
                .toString(), Toast.LENGTH_SHORT);
        toast.show();

    }

    /**
     * Método que se llama al pulsar click sobre un color y que permite
     * intercambiar el nombre de dos colores, al pulsar primero en uno y luego
     * en otro
     *
     * @param v Vista pulsada
     */
    public void intercambiarTexto(View v) {

        TextView pulsada = (TextView) v;

        if (contador == 0) {
            // primera vez que se pulsa una banda
            bandaAnterior = pulsada;
            contador++;
        } else {
            // no es la primera banda que se pulsa
            if (contador == 1) {
                // debo intercambiar los textos
                // guardo el texto de la primera banda en una auxiliar
                texto = bandaAnterior.getText().toString();
                // cambio el texto a la primera banda
                bandaAnterior.setText(pulsada.getText());
                // cambio el texto a la segunda banda
                pulsada.setText(texto);
                // reinicio el contador
                contador = 0;
            }

        }

    }

    /**
     * Método que se llama al pulsar click sobre un color y que permite
     * intercambiar el nombre de dos bandas, su color de fondo y el color de
     * texto, al pulsar primero en uno y luego en otro
     *
     * @param v Vista pulsada
     */
    public void intercambiarTexto_y_Color(View v) {

        TextView pulsada = (TextView) v;

        if (contador == 0) {
            // primera vez que se pulsa una banda
            bandaAnterior = pulsada;
            contador++;
        } else {
            // no es la primera banda que se pulsa
            if (contador == 1) {
                // debo intercambiar los textos y colores
                // guardo el texto de la primera banda en una auxiliar y su
                // color de fondo y de texto
                texto = bandaAnterior.getText().toString();
                fondo = bandaAnterior.getBackground();
                colorTexto = bandaAnterior.getTextColors();
                // cambio el texto a la primera banda y el color de fondo y de
                // texto
                bandaAnterior.setText(pulsada.getText());
                bandaAnterior.setBackground(pulsada.getBackground());
                bandaAnterior.setTextColor(pulsada.getTextColors());
                // cambio el texto a la segunda banda y su color de fondo y de
                // texto
                pulsada.setText(texto);
                pulsada.setBackground(fondo);
                pulsada.setTextColor(colorTexto);


                // reinicio el contador
                contador = 0;
            }

        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        TextView textView;
        Bundle datosTextView; //almacenara el id, color de fondo, color de texto y texto de cada textview
        String baseNombre = "tv"; //base para formar el nombre de cada textview. Serán tv0, tv1,...
        String nombreTextView = ""; //Será tv0, tv1,...


        super.onSaveInstanceState(outState);

        //obtengo el layout que contien los textview
        LinearLayout layout = (LinearLayout) findViewById(R.id.llLayout);
        //averiguo cuantos textview tiene el layout (son sus child)
        int numTextView = layout.getChildCount();

        //recorro cada Textview guardando sus datos
        for (int i = 0; i < numTextView; i++) {
            //extraigo los datos de un textview y los almaceno en un bundle datosTextView
            datosTextView= saveTextViewStateToBundle((TextView)layout.getChildAt(i));
            //genero un nombre que usaré como clave de ese textview a usar en el outstate bundle
            nombreTextView = baseNombre + i;

            //guardo en el bundle los datos del textview
            outState.putBundle(nombreTextView, datosTextView);

        }
    }

    /**Método que recibe un textView y almacena su estado en un Bundle que devuelve
     *
     * @param textView  cuyo estado se quiere guardar
     * @return Bundle  textViewState contiene el estado del textview
     */
    private Bundle  saveTextViewStateToBundle(TextView textView) {

        //extraigo los datos del textview
        int id = textView.getId();
        int colorTexto = textView.getCurrentTextColor();
        String texto = textView.getText().toString();
        int colorFondo = ((ColorDrawable) textView.getBackground()).getColor();

        //guardo en el bundle los datos de ese textview que acabo de extraer
        Bundle textViewState = new Bundle();
        textViewState.putInt("id", id);
        textViewState.putInt("colorTexto", colorTexto);
        textViewState.putInt("colorFondo", colorFondo);
        textViewState.putString("texto", texto);

        //devuelvo el bundle
        return textViewState;


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        Bundle datosTextView; //almacenara el id, color de fondo, color de texto y texto de cada textview
        String baseNombre = "tv"; //base para formar el nombre de cada textview. Serán tv0, tv1,...
        String nombreTextView = ""; //Será tv0, tv1,...


        super.onRestoreInstanceState(savedInstanceState);
        //obtengo el layout que contien los textview
        LinearLayout layout = (LinearLayout) findViewById(R.id.llLayout);
        //averiguo cuantos textview tiene el layout (son sus child)
        int numTextView = layout.getChildCount();

        //recorro cada Textview extrayendo sus datos
        for (int i = 0; i < numTextView; i++) {


            nombreTextView = baseNombre + i;
            //extraigo los datos de un textview del bundle guardado en onsave
            datosTextView = savedInstanceState.getBundle(nombreTextView);

            if (datosTextView != null) {
                //hay datos guardados de ese textview

                restoreTextViewStateFrom(datosTextView);

            }

        }
    }

    /**Método que restaura el estado de un textView: su color de texto, color de fondo, texto desde un bundle que se le
     * pasa
     * @param datosTextView  bundle que contiene la información a restaurar.
     */
    private void restoreTextViewStateFrom(Bundle datosTextView) {
        //extraigo información a restaurar del bundle datosTextView
        int id = datosTextView.getInt("id");
        int colorTexto = datosTextView.getInt("colorTexto");
        int colorFondo = datosTextView.getInt("colorFondo");
        String texto = datosTextView.getString("texto");

        //obtengo el objeto textview que coincide con el id que he extraido del bundle
        TextView textView = (TextView) findViewById(id);
        if (textView != null) {
            //si existe ese textview en el layout, le configuro con los datos extraidos del bundle
            textView.setBackgroundColor(colorFondo);
            textView.setTextColor(colorTexto);
            textView.setText(texto);
        }
    }
}
