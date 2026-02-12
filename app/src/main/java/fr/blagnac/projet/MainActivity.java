package fr.blagnac.projet;

import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void displayDate(View view) {
        DatePicker selectDate = findViewById(R.id.dp_pour_id);

        int jour = selectDate.getDayOfMonth();
        int mois = selectDate.getMonth() + 1;
        int annee = selectDate.getYear();

        String date = jour + "/" + mois + "/" + annee;

        Toast.makeText(getApplicationContext(),
                "Date sélectionnée : " + date,
                Toast.LENGTH_SHORT).show();
    }



    public void doCancel(View v) {

        EditText Titre = findViewById(R.id.ed_titre_id);
        EditText Note = findViewById(R.id.ed_note_id);
        EditText Corresp = findViewById(R.id.ed_correspondant_id);
        EditText Com = findViewById(R.id.ed_commentaire_id);

        Titre.setText("");
        Note.setText("");
        Corresp.setText("");
        Com.setText("");

        DatePicker selectDate = findViewById(R.id.dp_pour_id);
        Calendar ajd = Calendar.getInstance();

        selectDate.updateDate(
                ajd.get(Calendar.YEAR),
                ajd.get(Calendar.MONTH),
                ajd.get(Calendar.DAY_OF_MONTH)
        );

        Toast.makeText(getApplicationContext(),
                "Champs réinitialisés",
                Toast.LENGTH_SHORT).show();
    }
}