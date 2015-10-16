package es.jcolladosp.daviscope;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import es.jcolladosp.daviscope.Util.BaseActivity;

public class PreguntaActivity extends BaseActivity implements View.OnClickListener {

    Button aceptar;
    EditText edpregunta;
    RelativeLayout fondo;
    public String pregunta;
    AlertDialog levelDialog;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta);


        setListeners();
        findViews();
        generateBackground();

        mp = MediaPlayer.create(this, R.raw.sonido);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setCustomView(R.layout.actionbar_custom);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);



        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pregunta, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            selectLanguage();
            return true;
        }
        if (id == R.id.ajuda) {
            Intent send = new Intent(Intent.ACTION_SENDTO);
            String uriText = "mailto:" + Uri.encode("jousselin.new.antique@gmail.com") +
                    "?subject=" + Uri.encode(getResources().getString(R.string.email) + "Daviscope") +
                    "&body=" + Uri.encode("");
            Uri uri = Uri.parse(uriText);

            send.setData(uri);
            startActivityForResult(Intent.createChooser(send, getResources().getString(R.string.email)), 1);

            return true;
        }
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        if (id == R.id.develop) {
            developAlert();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    private void setListeners() {
        aceptar = (Button) findViewById(R.id.btAceptar);
        aceptar.setOnClickListener(this);

         }
    private void findViews(){
        edpregunta = (EditText) findViewById(R.id.edPregunta);

    }

    private void developAlert(){

        TextView tv  = new TextView(this);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        tv.setText(Html.fromHtml("Idea original: David Jousselin " + "<br />" + "<a href=\"mailto:jousselin.new.antique@gmail.com\">Enviar Email</a>" +
                "<br />" + "<br />" + "Desarrollo: Jose Collado" + "<br />" + "<a href=\"mailto:jose528@gmail.com\">Enviar Email</a>" + "<br />" + "<a href=https://github.com/jcolladosp>GitHub</a>"));
        tv.setTextColor(getResources().getColor(R.color.icons));
        tv.setTextSize(20);
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle(getResources().getString(R.string.develop));

        builder.setView(tv);
        builder.setPositiveButton("OK", null);

        builder.show();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void generateBackground(){
        fondo = (RelativeLayout) findViewById(R.id.fondoPregunta);

         ArrayList<Integer> numbers =generateRandomNumbers(1, 4);
        switch(numbers.get(0))
        {
            case 0:
                fondo.setBackground(getResources().getDrawable(R.drawable.fondo1));
                break;
            case 1:
                fondo.setBackground(getResources().getDrawable(R.drawable.fondo2));

                break;
            case 2:
                fondo.setBackground(getResources().getDrawable(R.drawable.fondo3));
                break;
            case 3:
                fondo.setBackground(getResources().getDrawable(R.drawable.fondo4));
                break;


        }

    }

    public ArrayList generateRandomNumbers(int nnumbers,int maxnumber){
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        Random randomGenerator = new Random();
        while (numbers.size() < nnumbers) {

            int random = randomGenerator .nextInt(maxnumber);
            if (!numbers.contains(random)) {
                numbers.add(random);
            }
        }
        return numbers;
    }

    private void selectLanguage(){
        final CharSequence[] items = {" English "," Español "," Français "};

        // Creating and Building the Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.action_settings));
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {


                switch (item) {
                    case 0:
                        setLocale("en");
                        break;
                    case 1:
                        setLocale("es");

                        break;
                    case 2:
                        setLocale("fr");
                        break;


                }
                levelDialog.dismiss();
            }
        });
        levelDialog = builder.create();
        levelDialog.show();
    }
    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, PreguntaActivity.class);
        startActivity(refresh);
        finish();
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btAceptar) {

            mp.start();
            pregunta = edpregunta.getText().toString();

            Intent i = new Intent(getApplicationContext(), ResultadoActivity.class);
            i.putExtra("pregunta", pregunta);
            startActivity(i);
            edpregunta.setText("");

        }

    }
}
