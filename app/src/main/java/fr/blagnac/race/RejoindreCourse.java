package fr.blagnac.race;

import java.io.IOException;
import java.net.MalformedURLException;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Dialog;
import android.content.Context;
import android.location.Location;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class RejoindreCourse extends Dialog{
	private final MapsActivity mainActivity;

	public RejoindreCourse(MapsActivity mainAct) {
		super(mainAct);
		this.mainActivity=mainAct;
		setContentView(R.layout.connexion_serveur);
		setTitle("Connexion à un serveur");
		Button ok = (Button) findViewById(R.id.button_ok);
		ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String adresse_serveur = ((EditText) findViewById(R.id.ed_serveur)).getText().toString();
				String nom = ((EditText) findViewById(R.id.ed_nom)).getText().toString();
				mainAct.setAdresse_serveur(adresse_serveur);
				mainAct.setMonNom(nom);
				mainAct.getMyMarker().setTitle(nom);
				try {
					RequeteHTTP requeteServeur = new RequeteHTTP(adresse_serveur);
					String isEnCours = requeteServeur.doGET("cmd=isRaceOnProgress");
					if ( isEnCours.equals("None") ) {
						mainAct.desabonnementGPS();
						mainAct.showAlert("Problème", "Le serveur n'est pas actif");
						dismiss();
					}
					else if ( isEnCours.equals("False") ) {
						Toast.makeText(mainAct, "Premier participant connecté \n  Initialisation de la course", Toast.LENGTH_LONG).show();
						requeteServeur.doGET("cmd=reinitRace");
					}
					requeteServeur.doGET("cmd=addParticipant&name="+nom);
					Toast.makeText(mainAct, "Ajout à la course", Toast.LENGTH_LONG).show();
					String[] cible = requeteServeur.doGET("cmd=getGoal&name="+nom).split(",");
					String cibleDesc = cible[0];
					Toast.makeText(mainAct, "Première cible : \n"+cibleDesc, Toast.LENGTH_LONG).show();
					double cibleLat = Double.parseDouble(cible[1]);
					double cibleLon = Double.parseDouble(cible[2]);
					String cibleTexte = " Cible : " + cibleDesc;
					mainAct.getCibleTv().setText(cibleTexte);
					mainAct.getCibleMarker().setSnippet(cibleDesc);
					mainAct.getCibleLoc().setLatitude(cibleLat);
					mainAct.getCibleLoc().setLongitude(cibleLon);
					mainAct.getCibleMarker().setPosition(new LatLng(cibleLat, cibleLon));
					mainAct.abonnementGPS();
					dismiss();
				} catch (MalformedURLException e) {
					mainAct.showAlert("Problème", "URL invalide");
					dismiss();
				} catch (IOException e) {
					mainAct.showAlert("Problème", "Pas d'accès au réseau");
					dismiss();
				}
			}
		});
	}

}
