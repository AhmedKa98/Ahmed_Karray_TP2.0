package TP.mobile;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etGlycemie;     
    private SeekBar sbAge;           
    private TextView tvAge;          
    private TextView msg;            
    private RadioButton rbtOui;      
    private RadioButton rbtNon;      
    private Button btnConsulter;     

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();                       
        
        
        sbAge.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("Information", "onProgressChanged " + progress);
                tvAge.setText("Votre âge : " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        btnConsulter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculer(v);
            }
        });
    }

    private void init() {
        etGlycemie   = findViewById(R.id.etGlycemie);
        sbAge        = findViewById(R.id.sbAge);
        tvAge        = findViewById(R.id.tvAge);
        msg          = findViewById(R.id.msg);
        rbtOui       = findViewById(R.id.rbtOui);
        rbtNon       = findViewById(R.id.rbtNon);
        btnConsulter = findViewById(R.id.btnConsulter);
    }

    public void calculer(View view) {
        String saisie = etGlycemie.getText().toString().trim();
        if (saisie.isEmpty()) {
            Toast.makeText(this, "Veuillez saisir la valeur mesurée", Toast.LENGTH_SHORT).show();
            return;
        }

        double valeur;
        try {
            valeur = Double.parseDouble(saisie.replace(',', '.'));
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Valeur invalide", Toast.LENGTH_SHORT).show();
            return;
        }

        int age = sbAge.getProgress();
        boolean aJeun = rbtOui.isChecked();
        String resultat;

        if (aJeun) {
            double min, max;
            if (age >= 13) {
                min = 5.0;  max = 7.2;
            } else if (age >= 6) {
                min = 5.0;  max = 10.0;
            } else {
                min = 5.5;  max = 10.0;
            }

            if (valeur < min) {
                resultat = "Niveau de glycémie est trop bas";
            } else if (valeur > max) {
                resultat = "Niveau de glycémie est trop élevé";
            } else {
                resultat = "Niveau de glycémie est normal";
            }
        } else {
            // Après repas : normal si < 10,5
            if (valeur < 10.5) {
                resultat = "Niveau de glycémie est normal";
            } else {
                resultat = "Niveau de glycémie est trop élevé";
            }
        }

        msg.setText(resultat);
    }
}
