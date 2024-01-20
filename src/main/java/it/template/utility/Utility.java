package it.template.utility;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

import javax.imageio.ImageIO;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Utility {

	public static String toDisplayCase(String s) {
		final String ACTIONABLE_DELIMITERS = " '-/"; // these cause the character following
		// to be capitalized
		StringBuilder sb = new StringBuilder();
		boolean capNext = true;
		for (char c : s.toCharArray()) {
			c = (capNext)
					? Character.toUpperCase(c)
							: Character.toLowerCase(c);
					sb.append(c);
					capNext = (ACTIONABLE_DELIMITERS.indexOf((int) c) >= 0); // explicit cast not needed
		}
		return sb.toString();
	}
	
	public static double getPrecisioDouble(Float value, int decimal){
		return new BigDecimal(value).setScale(decimal , BigDecimal.ROUND_HALF_EVEN).doubleValue();
	}
	
	public static String getPrecisioDouble(Double value, int decimal){
		return new BigDecimal(value.floatValue()).setScale(decimal , BigDecimal.ROUND_HALF_EVEN).toString().replace(".", ",");
	}
	
	public static String formatIntegerListString(List<Integer> ids) {
		String result = "(";
		int size = ids.size();
		for(int i = 0; i<ids.size();i++) {
			result +="'"+ids.get(i)+"'";
			if(i< (size-1)) {
				result +=",";
			}
		}
		result +=")";
		return result;
	}
	
	public static String formatStringListString(List<String> lista) {
		String result = "(";
		int size = lista.size();
		for(int i = 0; i<lista.size();i++) {
			result +="'"+lista.get(i)+"'";
			if(i< (size-1)) {
				result +=",";
			}
		}
		result +=")";
		return result;
	}
	
		
	public static String getExt(String absName){
		String ext = absName.substring(absName.lastIndexOf(".")+1, absName.length());
		return ext;
	}
	
	public static void writeToFile(InputStream uploadedInputStream,String uploadedFileLocation) throws  IOException{
			OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
	}
	
	
	public static void writeToFile(byte[] bytes,String uploadedFileLocation) throws  IOException{
		OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
		out.write(bytes, 0, bytes.length);
		out.flush();
		out.close();
}
	
	public static boolean saveFile(String absName, byte[] content){
		try {
			FileOutputStream fos = new FileOutputStream(absName);
			byte[] out = content;
			fos.write(out);
			fos.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static boolean deleteFile(String absName) throws  Exception{
		try {
			Files.delete(Paths.get(absName));
			return true;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static Long calculateNumberPage(Long count, Integer numberRow) {
		Long result = 0L;
		result = (count / numberRow);
		if ((count%numberRow) >0 ) {
			result +=1;
		}
		return result;
	}
	
	public static Object desiarilzed(Class clazz, byte[] content) throws IOException {
		 ObjectMapper objectMapper = new ObjectMapper();
		 return objectMapper.reader().readValue(content, clazz);		
	}
	
	public static byte[] siarilzed(Object obj) throws JsonProcessingException {
		 ObjectMapper objectMapper = new ObjectMapper();
		 byte[] json = objectMapper.writeValueAsBytes(obj);
		 return json;
	}
	
	public static byte[] getContentFromBase64(String base64) {
		 byte[] content = Base64.getDecoder().decode(base64);
		 return content;
	}
	
	public static String creaCartella(String nameFolder){
		File f = new File(nameFolder);
		if(!f.exists()){
			f.mkdir();
		}
		return nameFolder;
	}
}
