package es.jcolladosp.daviscope;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import es.jcolladosp.daviscope.Util.BaseActivity;

public class PreguntaActivity extends BaseActivity implements View.OnClickListener {

    Button aceptar;
    EditText edpregunta;
    public String pregunta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta);


        setListeners();
        findViews();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pregunta, menu);
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btAceptar) {

            pregunta = edpregunta.getText().toString();

            Intent i = new Intent(getApplicationContext(), ResultadoActivity.class);
            i.putExtra("pregunta", pregunta);
            startActivity(i);
        }

    }
}
