package fr.blagnac.race;

import java.io.BufferedReader;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.widget.Toast;

public class RequeteHTTP {
	private String adresse_serveur;

	public RequeteHTTP(String adresse_serveur){
		this.adresse_serveur = adresse_serveur;
	}

	public String doGET(String parametres) throws MalformedURLException, IOException {
		//création de l'objet URL pour la requête
		URL url = new URL("http://" + adresse_serveur + "/?" + parametres );
		//ouverture de la connection et paramétrage de la requête
		HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
		httpUrlConnection.setReadTimeout(10000 /* milliseconds */);
		httpUrlConnection.setConnectTimeout(15000 /* milliseconds */);
		httpUrlConnection.setRequestMethod("GET");
		httpUrlConnection.setDoInput(true);
		//envoi de la requête au serveur
		httpUrlConnection.connect();
		if(httpUrlConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
		{
			//récupération de la réponse
			String reponse = "None";
			// InputStream in = new BufferedInputStream(urlConnection.getInputStream());
			InputStream in = httpUrlConnection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			reponse = reader.readLine();
			reader.close();
			in.close();
			httpUrlConnection.disconnect();
			return reponse;
		} else {
			httpUrlConnection.disconnect();
			throw new MalformedURLException();
		}
	}

}

