package es.jcolladosp.daviscope;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import es.jcolladosp.daviscope.Util.BaseActivity;

public class ResultadoActivity extends BaseActivity implements View.OnClickListener {
    Button volver;
    Button compartir;

    TextView txPregunta;
    TextView txSiNo;
    TextView txPalabra1;
    TextView txPalabra2;
    TextView txPalabra3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        setListeners();
        findViews();
        generateRandomWords();

        Intent intent = getIntent();
        String preguntaObtenida = intent.getExtras().getString("pregunta");
        txPregunta.setText(preguntaObtenida);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_resultado, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private ArrayList generateRandomNumbers(int nnumbers,int maxnumber){
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
        ArrayList<Integer> nsino = generateRandomNumbers(1,2);
        if(nsino.get(0)==0){
            txSiNo.setText(getResources().getString(R.string.si));
        }
        else if(nsino.get(0)==1){
            txSiNo.setText(getResources().getString(R.string.no));
        }

        ArrayList<Integer> npalabras = generateRandomNumbers(3,10);

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

        compartir = (Button) findViewById(R.id.btCompartir);
        compartir.setOnClickListener(this);

    }

    private void findViews(){
        txPregunta = (TextView) findViewById(R.id.txPregunta);
        txSiNo = (TextView) findViewById(R.id.txSiNo);
        txPalabra1 = (TextView) findViewById(R.id.txPalabra1);
        txPalabra2 = (TextView) findViewById(R.id.txPalabra2);
        txPalabra3 = (TextView) findViewById(R.id.txPalabra3);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btOtra) {
            onBackPressed();
        }
        if (v.getId() == R.id.btCompartir) {


        }

    }
}
