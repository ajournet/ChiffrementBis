import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;

import org.apache.commons.io.IOUtils;


public class Chiffrement {
	
	private InputStream is;
	private String message;
	
	public Chiffrement() {
		super();
	}

	public Chiffrement(InputStream is) {
		super();
		this.is = is;
	}

	public Chiffrement(InputStream ais, String message) {
		this.is = ais;
		this.message = message;
	}
	
	public byte[] chiffrerDebut(){
		byte[] retourFile = null;
		try {
			byte[] audioBytes = IOUtils.toByteArray(this.is);
			byte[] messageBytes = this.message.getBytes();
 			retourFile = addDebut(messageBytes, audioBytes);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return retourFile;
	}
	
	public byte[] chiffrerFin(){
		byte[] retourFile = null;
		try {
			byte[] audioBytes = IOUtils.toByteArray(this.is);
			byte[] messageBytes = this.message.getBytes();
			retourFile = addFin(messageBytes, audioBytes);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return retourFile;
	}

	public InputStream getIs() {
		return is;
	}

	public void setIs(InputStream is) {
		this.is = is;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public byte[] addDebut(byte[] message, byte[] audio){
		
		List<Byte> listeBytes = new ArrayList<Byte>();
		
		listeBytes.add((byte)message.length);
		
		for(int i = 0; i<message.length; i++) {
			listeBytes.add(message[i]);
		}
		for(int i = 0; i<audio.length; i++) {
			listeBytes.add(audio[i]);
		}
		
		Byte[] tabByte = new Byte[listeBytes.size()];
		tabByte = listeBytes.toArray(tabByte);
		
		byte[] tab = new byte[tabByte.length];
		int i = 0;
		for(Byte b : tabByte){
			tab[i]=b.byteValue();
			i++;
		}
		
//		byte[] tab = new byte[audio.length+message.length+1];
//		for(int i = 0; i<message.length; i++) {
//			tab[i] = message[i];
//		}
//		for(int i = 0; i<audio.length; i++) {
//			tab[message.length + i] = audio[i];
//		}
//		byte longueurMessage = (byte) message.length;
		
		return tab;
	}
	
	public byte[] addFin(byte[] audio, byte[] message){
		
		List<Byte> listeBytes = new ArrayList<Byte>();
		
		for(int i = 0; i<audio.length; i++) {
			listeBytes.add(audio[i]);
		}
		
		for(int i = 0; i<message.length; i++) {
			listeBytes.add(message[i]);
		}
		
		listeBytes.add((byte)message.length);
		
		Byte[] tabByte = new Byte[listeBytes.size()];
		tabByte = listeBytes.toArray(tabByte);
		
		byte[] tab = new byte[tabByte.length];
		int i = 0;
		for(Byte b : tabByte){
			tab[i]=b.byteValue();
			i++;
		}
		return tab;
	}

	
	public String dechiffrerDebut() {
		try {
			byte[] audioBytes = IOUtils.toByteArray(this.is);
			int taille = audioBytes[0];
			
			byte[] messageCode = new byte[taille];
			
			for(int i = 1; i < taille; i++){
				messageCode[i-1] = audioBytes[i];
			}
			
			this.message = new String(messageCode, "UTF-8");
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return this.message;
	}
	
	public String dechiffrerFin() {
		try {
			byte[] audioBytes = IOUtils.toByteArray(this.is);
			int taille = audioBytes[audioBytes.length-1];
			
			byte[] messageCode = new byte[taille];
			
			int y = 0;
			for(int i = audioBytes.length; i > audioBytes.length-taille; i--){
				messageCode[y] = audioBytes[i-1];
				y++;
			}
			
			this.message = new String(messageCode, "UTF-8");
			StringBuffer sb = (new StringBuffer(this.message)).reverse(); 
			this.message = sb.toString();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return this.message;
	}
}
