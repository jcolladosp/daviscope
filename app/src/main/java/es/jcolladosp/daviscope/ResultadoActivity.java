package es.jcolladosp.daviscope;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;


import android.net.Uri;
import android.os.Build;
import android.support.v7.app.ActionBar;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Random;

import java.util.Locale;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import es.jcolladosp.daviscope.Util.BaseActivity;

public class ResultadoActivity extends BaseActivity implements View.OnClickListener, Animation.AnimationListener {
    Button volver;
    ImageButton compartir;
    String preguntaObtenida;
    TextView txPregunta;

    TextView txPalabra1;
    TextView txPalabra2;
    TextView txPalabra3;
    AlertDialog levelDialog;
    RelativeLayout fondo;
    Animation an1;
    Animation an2;
    Animation an3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<Integer> numbers =generateRandomNumbers(1, 4);


        setContentView(R.layout.activity_resultado);


        setListeners();
        findViews();
        generateBackground(numbers.get(0));
        generateRandomWords();



        an1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        an2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        an3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

        an1.setAnimationListener(this);
        an2.setAnimationListener(this);
        an3.setAnimationListener(this);



        Intent intent = getIntent();
        preguntaObtenida = intent.getExtras().getString("pregunta");
        txPregunta.setText(preguntaObtenida);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setElevation(0);
            actionBar.setCustomView(R.layout.actionbar_custom);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);


        }
        txPalabra1.setVisibility(View.VISIBLE);
        txPalabra1.startAnimation(an1);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_resultado, menu);
        return true;
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

        if (animation == an1) {
            txPalabra2.setVisibility(View.VISIBLE);
            txPalabra2.startAnimation(an2);
        }

        if (animation == an2) {
            txPalabra3.setVisibility(View.VISIBLE);
            txPalabra3.startAnimation(an3);

        }

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

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


        return super.onOptionsItemSelected(item);
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

    private void generateRandomWords(){

        ArrayList<Integer> npalabras = generateRandomNumbers(3,42);

        String selector1 = "palabra"+npalabras.get(0).toString();
        String selector2 = "palabra"+npalabras.get(1).toString();
        String selector3 = "palabra"+npalabras.get(2).toString();

        int IdPal1 = getResources().getIdentifier(selector1, "string", "es.jcolladosp.daviscope");
        int IdPal2 = getResources().getIdentifier(selector2, "string", "es.jcolladosp.daviscope");
        int IdPal3 = getResources().getIdentifier(selector3, "string", "es.jcolladosp.daviscope");

        txPalabra1.setText(getString(IdPal1));
        txPalabra2.setText(getString(IdPal2));
        txPalabra3.setText(getString(IdPal3));


    }


    private void setListeners() {
        volver = (Button) findViewById(R.id.btOtra);
        volver.setOnClickListener(this);

        compartir = (ImageButton) findViewById(R.id.btCompartir);
        compartir.setOnClickListener(this);

    }

    private void findViews(){
        txPregunta = (TextView) findViewById(R.id.txPregunta);

        txPalabra1 = (TextView) findViewById(R.id.txPalabra1);
        txPalabra2 = (TextView) findViewById(R.id.txPalabra2);
        txPalabra3 = (TextView) findViewById(R.id.txPalabra3);


    }

    public void onClickShare(View view) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,  getResources().getString(R.string.pregun1)+ " "+ preguntaObtenida +" "+ getResources().getString(R.string.pregun2) +" "
                + txPalabra1.getText().toString() +", "  + txPalabra2.getText().toString() +", " + txPalabra3.getText().toString() + ". "+getResources().getString(R.string.pregun3)
                +  "http://bit.ly/futureapp");
        startActivity(intent);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btOtra) {

            onBackPressed();
        }
        if (v.getId() == R.id.btCompartir) {

            onClickShare(v);

        }

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
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void generateBackground(int n){
        fondo = (RelativeLayout) findViewById(R.id.fondoResultado);

        switch(n)
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




}



