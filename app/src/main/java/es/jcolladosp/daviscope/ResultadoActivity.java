package es.jcolladosp.daviscope;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.ActionBar;

import android.os.Bundle;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import java.util.Locale;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import es.jcolladosp.daviscope.Util.BaseActivity;
import es.jcolladosp.daviscope.Util.Tipografias;

public class ResultadoActivity extends BaseActivity implements View.OnClickListener, Animation.AnimationListener {
    RelativeLayout volver;
    RelativeLayout compartir;
    String preguntaObtenida;
    TextView txPregunta;

    TextView txPalabra1;
    TextView txPalabra2;
    TextView txPalabra3;

    TextView porcen1;
    TextView porcen2;
    TextView porcen3;

    AlertDialog levelDialog;
    RelativeLayout fondo;
    Animation an1;
    Animation an2;
    Animation an3;
    Animation an4;




    int porcenta1;
    int porcenta2;
    int porcenta3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<Integer> numbers =generateRandomNumbers(1, 4);


        setContentView(R.layout.activity_resultado);


        setListeners();
        findViews();
        generateBackground(numbers.get(0));
        generateRandomWords();
        generatePorcen();


        an1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        an2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        an3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

        an4 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in2);

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

    private void startCountAnimation1() {
        ValueAnimator animator = new ValueAnimator();
        animator.setObjectValues(0, porcenta1);
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                porcen1.setText("" + (int) animation.getAnimatedValue()+"%" );
            }
        });
        animator.start();
    }
    private void startCountAnimation2() {
        ValueAnimator animator = new ValueAnimator();
        animator.setObjectValues(0, porcenta2);
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                porcen2.setText("" + (int) animation.getAnimatedValue()+"%");
            }
        });
        animator.start();
    }
    private void startCountAnimation3() {
        ValueAnimator animator = new ValueAnimator();
        animator.setObjectValues(0, porcenta3);
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                porcen3.setText("" + (int) animation.getAnimatedValue()+"%");
            }
        });
        animator.start();
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
        if (animation == an3) {
            porcen1.setVisibility(View.VISIBLE);
            porcen2.setVisibility(View.VISIBLE);
            porcen3.setVisibility(View.VISIBLE);

            porcen1.startAnimation(an4);
            porcen2.startAnimation(an4);
            porcen3.startAnimation(an4);


            startCountAnimation1();
            startCountAnimation2();
            startCountAnimation3();
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
        if (id == R.id.develop) {
            developAlert();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void developAlert(){

        TextView tv  = new TextView(this);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        tv.setText(Html.fromHtml("Idea original: David Jousselin " +"<br />"+ "<a href=\"mailto:jousselin.new.antique@gmail.com\">Enviar Email</a>" +
                "<br />"+"<br />" + "Desarrollo: Jose Collado" +"<br />" +"<a href=\"mailto:jose528@gmail.com\">Enviar Email</a>"  + "<br />" + "<a href=https://github.com/jcolladosp>GitHub</a>"));
        tv.setTextColor(getResources().getColor(R.color.icons));
        tv.setTextSize(20);
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle(getResources().getString(R.string.develop));

        builder.setView(tv);
        builder.setPositiveButton("OK", null);

        builder.show();
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
        volver = (RelativeLayout) findViewById(R.id.lyNueva);
        volver.setOnClickListener(this);


        compartir = (RelativeLayout) findViewById(R.id.lyShare);
        compartir.setOnClickListener(this);

    }

    private void findViews(){
        txPregunta = (TextView) findViewById(R.id.txPregunta);
        txPregunta.setTypeface(Tipografias.getTypeface(this, ""));

        txPalabra1 = (TextView) findViewById(R.id.txPalabra1);
        txPalabra2 = (TextView) findViewById(R.id.txPalabra2);
        txPalabra3 = (TextView) findViewById(R.id.txPalabra3);

        TextView bt1 = (TextView) findViewById(R.id.bt1);
        TextView bt2 = (TextView) findViewById(R.id.bt2);

        porcen1 = (TextView) findViewById(R.id.porcen1);
        porcen2 = (TextView) findViewById(R.id.porcen2);
        porcen3 = (TextView) findViewById(R.id.porcen3);




        txPalabra1.setTypeface(Tipografias.getTypeface(this, ""));
        txPalabra2.setTypeface(Tipografias.getTypeface(this, ""));
        txPalabra3.setTypeface(Tipografias.getTypeface(this, ""));

        porcen1.setTypeface(Tipografias.getTypeface(this, ""));
        porcen2.setTypeface(Tipografias.getTypeface(this, ""));
        porcen3.setTypeface(Tipografias.getTypeface(this, ""));


        bt1.setTypeface(Tipografias.getTypeface(this, ""));
        bt2.setTypeface(Tipografias.getTypeface(this, ""));
    }

    private void generatePorcen(){

        ArrayList<Integer> nporcen = generateRandomNumbers(1,100);
        int porcenta_1 =  nporcen.get(0);

        int xd = 100 - porcenta_1;

        ArrayList<Integer> nporcen2 = generateRandomNumbers(1,xd);

        int porcenta_2 = nporcen2.get(0);

        int porcenta_3 = xd - porcenta_2;



        ArrayList<Integer> nlugares = generateRandomNumbers(3,3);

        ArrayList<Integer> numeros = new ArrayList<Integer>();

        numeros.add(0,porcenta_1);
        numeros.add(1,porcenta_2);
        numeros.add(2,porcenta_3);

        porcenta1 = numeros.get(nlugares.get(0));
        porcenta2 = numeros.get(nlugares.get(1));
        porcenta3 = numeros.get(nlugares.get(2));

    }

    public void onClickShare(View view) {

        DecimalFormat formatter = new DecimalFormat("00");
        String aFormatted1 = formatter.format(porcenta1);
        String aFormatted2 = formatter.format(porcenta2);
        String aFormatted3 = formatter.format(porcenta3);


        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,  getResources().getString(R.string.pregun1)+ " "+ preguntaObtenida +"\n"+"\n" +" "+ getResources().getString(R.string.pregun2) +"\n"+"\n"
               +"-  " + aFormatted1 +"%" + "      " + txPalabra1.getText().toString()+"\n"
               +"-  " + aFormatted2 +"%" + "      " + txPalabra2.getText().toString()+"\n"
               +"-  " + aFormatted3 +"%" + "      " + txPalabra3.getText().toString()+"\n"
               + "\n"+getResources().getString(R.string.pregun3)                +  "http://bit.ly/future_app");
        startActivity(intent);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.lyNueva) {

            onBackPressed();
        }
        if (v.getId() == R.id.lyShare) {

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



