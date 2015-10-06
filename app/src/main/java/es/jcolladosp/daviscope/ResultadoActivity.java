package es.jcolladosp.daviscope;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.ActionBar;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import java.util.Locale;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import es.jcolladosp.daviscope.Util.BaseActivity;

public class ResultadoActivity extends BaseActivity implements View.OnClickListener {
    Button volver;
    ImageButton compartir;

    TextView txPregunta;
    TextView txSiNo;
    TextView txPalabra1;
    TextView txPalabra2;
    TextView txPalabra3;
    AlertDialog levelDialog;
    RelativeLayout fondo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        setListeners();
        findViews();
        generateRandomWords();
        generateBackground();

        Intent intent = getIntent();
        String preguntaObtenida = intent.getExtras().getString("pregunta");
        txPregunta.setText(preguntaObtenida);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Daviscope");


        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_resultado, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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

    public void onClickWhatsApp(View view) {

        PackageManager pm=getPackageManager();
        try {

            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");
            String text = "YOUR TEXT HERE";

            PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            //Check if package exists or not. If not then code
            //in catch block will be called
            waIntent.setPackage("com.whatsapp");

            waIntent.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(waIntent, "Share with"));

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show();
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btOtra) {
            onBackPressed();
        }
        if (v.getId() == R.id.btCompartir) {
            onClickWhatsApp(v);

        }

    }
    private void selectLanguage(){


// Strings to Show In Dialog with Radio Buttons
        final CharSequence[] items = {" English "," Español "," Français "};

        // Creating and Building the Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.action_settings));
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {


                switch(item)
                {
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
    private void generateBackground(){
        fondo = (RelativeLayout) findViewById(R.id.fondoResultado);

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
}
