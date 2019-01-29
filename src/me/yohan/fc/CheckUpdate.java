package me.yohan.fc;

import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import me.yohan.ui.GameConfig;


public class CheckUpdate {
	
	public static int Check() throws IOException { //Retourne la "version" la plus récente
		URL maj = new URL("https://www.dropbox.com/s/rgltjjfh4z3udl7/version.txt?dl=1");
		URLConnection connection = maj.openConnection();
		System.out.println("Version actuelle : " + GameConfig.version +"\nVersion la plus récente : "+connection.getContentLength());
		
		return connection.getContentLength();
	}
	
	
	public static void Update() throws IOException {
		System.out.println("Téléchargement de la nouvelle version");
		try {
			getFile(new URL("https://www.dropbox.com/s/toons1kyqz7iha7/Picolo.jar?dl=1"), new File("Picolo.jar"));
			getFile(new URL("https://www.dropbox.com/s/fulj32yw39j2bj6/PostList?dl=1"), new File("PostList"));
			Desktop desk = Desktop.getDesktop();
			desk.open(new File("Picolo.jar"));
			System.exit(1);
		} catch (MalformedURLException e) {
			System.out.println("Lien indisponible : " + e);
		}
	}
	
	private static void getFile(URL url, File file) throws IOException {
		URLConnection connexion = url.openConnection();
		System.out.println("Type : "+connexion.getContentType()+"\nLongueur : "+connexion.getContentLength());
		BufferedInputStream in = new BufferedInputStream(connexion.getInputStream( ));
	    BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
	    byte[] data = new byte[2048]; 
	    int nbRead = 0;
	    try {
	        while ((nbRead = in.read(data)) > 0) { 
	        	out.write(data, 0, nbRead);
	        	out.flush();
	        } 
	    } finally {
	        try {
	        	in.close();
	        } finally {
	        	out.close();
	        }
	    }
	}
	
}
