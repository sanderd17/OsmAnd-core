package net.osmand;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.logging.Log;

/**
 * Basic algorithms that are not in jdk 
 */
public class Algoritms {
	private static final int BUFFER_SIZE = 1024;
	private static final Log log = LogUtil.getLog(Algoritms.class);
	
	public static boolean isEmpty(String s){
		return s == null || s.length() == 0;
	}
	
	/**
	 * Determine whether a file is a ZIP File.
	 */
	public static boolean isZipFile(File file) throws IOException {
		if (file.isDirectory()) {
			return false;
		}
		if (!file.canRead()) {
			throw new IOException("Cannot read file " + file.getAbsolutePath());
		}
		if (file.length() < 4) {
			return false;
		}
		DataInputStream in = new DataInputStream(new BufferedInputStream(
				new FileInputStream(file)));
		int test = in.readInt();
		in.close();
		return test == 0x504b0304;
	}
	
	public static String capitalizeFirstLetterAndLowercase(String s) {
		if (s != null && s.length() > 1) {
			// not very efficient algorithm
			return Character.toUpperCase(s.charAt(0)) + s.substring(1).toLowerCase();
		} else {
			return s;
		}
	}
	
	
	public static boolean objectEquals(Object a, Object b){
		if(a == null){
			return b == null;
		} else {
			return a.equals(b);
		}
	}
	
	public static int extractFirstIntegerNumber(String s) {
		int i = 0;
		for (int k = 0; k < s.length(); k++) {
			if (Character.isDigit(s.charAt(k))) {
				i = i * 10 + (s.charAt(k) - '0');
			} else {
				break;
			}
		}
		return i;
	}
	
	public static String extractIntegerSuffix(String s) {
		int k = 0;
		for (; k < s.length(); k++) {
			if (!Character.isDigit(s.charAt(k))) {
				return s.substring(k);
			}
		}
		return "";
	}
	
	
	public static void streamCopy(InputStream in, OutputStream out) throws IOException{
		byte[] b = new byte[BUFFER_SIZE];
		int read;
		while ((read = in.read(b)) != -1) {
			out.write(b, 0, read);
		}
	}
	
	public static void oneByteStreamCopy(InputStream in, OutputStream out) throws IOException{
		int read;
		while ((read = in.read()) != -1) {
			out.write(read);
		}
	}
	
	public static void closeStream(Closeable stream){
		try {
			if(stream != null){
				stream.close();
			}
		} catch(IOException e){
			log.warn("Closing stream warn", e); //$NON-NLS-1$
		}
	}
	
	public static void updateAllExistingImgTilesToOsmandFormat(File f){
		if(f.isDirectory()){
			for(File c : f.listFiles()){
				updateAllExistingImgTilesToOsmandFormat(c);
			}
		} else if(f.getName().endsWith(".png") || f.getName().endsWith(".jpg")){ //$NON-NLS-1$ //$NON-NLS-2$
			f.renameTo(new File(f.getAbsolutePath() + ".tile")); //$NON-NLS-1$
		} else if(f.getName().endsWith(".andnav2")) { //$NON-NLS-1$
			f.renameTo(new File(f.getAbsolutePath().substring(0, f.getAbsolutePath().length() - ".andnav2".length()) + ".tile")); //$NON-NLS-1$ //$NON-NLS-2$
		}
			
	}
	
	public static boolean removeAllFiles(File f) {
		if (f == null) {
			return false;
		}
		if (f.isDirectory()) {
			for (File c : f.listFiles()) {
				removeAllFiles(c);
			}
			return f.delete();
		} else {
			return f.delete();
		}
	}
	
	
	public static long parseLongFromBytes(byte[] bytes, int offset) {
		long o= 0xff & bytes[offset + 7];
		o = o << 8 | (0xff & bytes[offset + 6]);
		o = o << 8 | (0xff & bytes[offset + 5]);
		o = o << 8 | (0xff & bytes[offset + 4]);
		o = o << 8 | (0xff & bytes[offset + 3]);
		o = o << 8 | (0xff & bytes[offset + 2]);
		o = o << 8 | (0xff & bytes[offset + 1]);
		o = o << 8 | (0xff & bytes[offset + 0]);
		return o;
	}
	
	
	
	public static void putLongToBytes(byte[] bytes, int offset, long l){
		bytes[offset] = (byte) (l & 0xff);
		l >>= 8;
		bytes[offset + 1] = (byte) (l & 0xff);
		l >>= 8;
		bytes[offset + 2] = (byte) (l & 0xff);
		l >>= 8;
		bytes[offset + 3] = (byte) (l & 0xff);
		l >>= 8;
		bytes[offset + 4] = (byte) (l & 0xff);
		l >>= 8;
		bytes[offset + 5] = (byte) (l & 0xff);
		l >>= 8;
		bytes[offset + 6] = (byte) (l & 0xff);
		l >>= 8;
		bytes[offset + 7] = (byte) (l & 0xff);
	}
	
	
	public static int parseIntFromBytes(byte[] bytes, int offset) {
		int o = (0xff & bytes[offset + 3]) << 24;
		o |= (0xff & bytes[offset + 2]) << 16;
		o |= (0xff & bytes[offset + 1]) << 8;
		o |= (0xff & bytes[offset + 0]);
		return o;
	}
	
	public static void putIntToBytes(byte[] bytes, int offset, int l){
		bytes[offset] = (byte) (l & 0xff);
		l >>= 8;
		bytes[offset + 1] = (byte) (l & 0xff);
		l >>= 8;
		bytes[offset + 2] = (byte) (l & 0xff);
		l >>= 8;
		bytes[offset + 3] = (byte) (l & 0xff);
	}
	
	
	public static void writeLongInt(OutputStream stream, long l) throws IOException {
		stream.write((int) (l & 0xff));
		l >>= 8;
		stream.write((int) (l & 0xff));
		l >>= 8;
		stream.write((int) (l & 0xff));
		l >>= 8;
		stream.write((int) (l & 0xff));
		l >>= 8;
		stream.write((int) (l & 0xff));
		l >>= 8;
		stream.write((int) (l & 0xff));
		l >>= 8;
		stream.write((int) (l & 0xff));
		l >>= 8;
		stream.write((int) (l & 0xff));
	}
	
	public static void writeInt(OutputStream stream, int l) throws IOException {
		stream.write(l & 0xff);
		l >>= 8;
		stream.write(l & 0xff);
		l >>= 8;
		stream.write(l & 0xff);
		l >>= 8;
		stream.write(l & 0xff);
	}
	

	public static void writeSmallInt(OutputStream stream, int l) throws IOException {
		stream.write(l & 0xff);
		l >>= 8;
		stream.write(l & 0xff);
		l >>= 8;
	}
	
	public static int parseSmallIntFromBytes(byte[] bytes, int offset) {
		int s = (0xff & bytes[offset + 1]) << 8;
		s |= (0xff & bytes[offset + 0]);
		return s;
	}
	
	public static void putSmallIntBytes(byte[] bytes, int offset, int s){
		bytes[offset] = (byte) (s & 0xff);
		s >>= 8;
		bytes[offset + 1] = (byte) (s & 0xff);
		s >>= 8;
	}

	public static boolean containsDigit(String name) {
		for (int i = 0; i < name.length(); i++) {
			if (Character.isDigit(name.charAt(i))) {
				return true;
			}
		}
		return false;
	}
	
	public static String sendGetRequest(String urlText, String userNamePassword){
		URL url;
		try {
			log.info("GET : " + urlText);
			url = new URL(urlText);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(false);
			conn.setRequestMethod("GET");
			if(userNamePassword != null) {
				conn.setRequestProperty("Authorization", "Basic " + Base64.encode(userNamePassword)); //$NON-NLS-1$ //$NON-NLS-2$
			}
			
	        conn.setRequestProperty("User-Agent", "OsmAnd"); //$NON-NLS-1$ //$NON-NLS-2$
			log.info("Response code and message : " + conn.getResponseCode() + " " + conn.getResponseMessage());
			if(conn.getResponseCode() != 200){
				return conn.getResponseMessage();
			}
			InputStream is = conn.getInputStream();
			StringBuilder responseBody = new StringBuilder();
			if (is != null) {
				BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8")); //$NON-NLS-1$
				String s;
				boolean first = true;
				while ((s = in.readLine()) != null) {
					if(first){
						first = false;
					} else {
						responseBody.append("\n"); //$NON-NLS-1$
					}
					responseBody.append(s);
					
				}
				is.close();
			}
			String response = responseBody.toString();
			log.info("Response : " + response);
			if(response.startsWith("OK")){
				return null;
			}
			return response;
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			return e.getMessage();
		}
	}
	
	private static final String BOUNDARY = "CowMooCowMooCowCowCow"; //$NON-NLS-1$
	public static String uploadFile(String urlText, File fileToUpload, String userNamePassword, String formName, boolean gzip, Map<String, String> additionalMapData){
		URL url;
		try {
			boolean firstPrm =!urlText.contains("?");
			for (String key : additionalMapData.keySet()) {
				urlText += (firstPrm ? "?" : "&") + key + "=" + URLEncoder.encode(additionalMapData.get(key), "UTF-8");
				firstPrm = false;
			}
			log.info("Start uploading file to " + urlText + " " +fileToUpload.getName());
			url = new URL(urlText);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			if(userNamePassword != null) {
				conn.setRequestProperty("Authorization", "Basic " + Base64.encode(userNamePassword)); //$NON-NLS-1$ //$NON-NLS-2$
			}
			
	        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY); //$NON-NLS-1$ //$NON-NLS-2$
	        conn.setRequestProperty("User-Agent", "OsmAnd"); //$NON-NLS-1$ //$NON-NLS-2$

	        OutputStream ous = conn.getOutputStream();
//			for (String key : additionalMapData.keySet()) {
//				ous.write(("--" + BOUNDARY + "\r\n").getBytes());
//				ous.write(("content-disposition: form-data; name=\"" + key + "\"\r\n").getBytes()); //$NON-NLS-1$ //$NON-NLS-2$
//				ous.write((additionalMapData.get(key) + "\r\n").getBytes());
//			}
			ous.write(("--" + BOUNDARY+"\r\n").getBytes());
			String filename = fileToUpload.getName();
			if(gzip){
				filename+=".gz";
			}
			ous.write(("content-disposition: form-data; name=\""+formName+"\"; filename=\"" + filename + "\"\r\n").getBytes()); //$NON-NLS-1$ //$NON-NLS-2$
	        ous.write(("Content-Type: application/octet-stream\r\n\r\n").getBytes()); //$NON-NLS-1$
	        InputStream fis = new FileInputStream(fileToUpload);
			BufferedInputStream bis = new BufferedInputStream(fis, 20 * 1024);
			ous.flush();
			if(gzip){
				GZIPOutputStream gous = new GZIPOutputStream(ous, 1024);
				Algoritms.streamCopy(bis, gous);
				gous.flush();
				gous.finish();
			} else {
				Algoritms.streamCopy(bis, ous);
			}
			
	        ous.write(("\r\n--" + BOUNDARY + "--\r\n").getBytes()); //$NON-NLS-1$ //$NON-NLS-2$
			ous.flush();
			Algoritms.closeStream(bis);
			Algoritms.closeStream(ous);

			log.info("Finish uploading file " + fileToUpload.getName());
			log.info("Response code and message : " + conn.getResponseCode() + " " + conn.getResponseMessage());
			if(conn.getResponseCode() != 200){
				return conn.getResponseMessage();
			}
			InputStream is = conn.getInputStream();
			StringBuilder responseBody = new StringBuilder();
			if (is != null) {
				BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8")); //$NON-NLS-1$
				String s;
				boolean first = true;
				while ((s = in.readLine()) != null) {
					if(first){
						first = false;
					} else {
						responseBody.append("\n"); //$NON-NLS-1$
					}
					responseBody.append(s);
					
				}
				is.close();
			}
			String response = responseBody.toString();
			log.info("Response : " + response);
			return null;
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			return e.getMessage();
		}
	}

	
}
