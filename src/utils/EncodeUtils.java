package utils;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class EncodeUtils {

	public static final Charset UTF_8_CHARSET        = StandardCharsets.UTF_8;      // Charset.forName("UTF-8");
	public static final Charset ISO_8859_1_CHARSET   = StandardCharsets.ISO_8859_1; // Charset.forName("ISO-8859-1");
	public static final Charset US_ASCII_CHARSET     = StandardCharsets.US_ASCII;   // Charset.forName("US-ASCII");
	public static final Charset WINDOWS_1252_CHARSET = Charset.forName("windows-1252");	
	
	
	/**
	 * @param bytes
	 * @param charsetIn
	 * @return
	 */
	public static String encodeBase64(byte[] bytes, Charset charsetIn) {
		ByteBuffer bb = ByteBuffer.wrap(bytes);
		CharBuffer cb = charsetIn.decode(bb);
		bb = UTF_8_CHARSET.encode(cb);
		String s = UTF_8_CHARSET.decode(bb).toString();		
		
		String encodedString = Base64.getEncoder().encodeToString(s.getBytes());
		return encodedString;
	}
	
	/**
	 * @param encodedString
	 * @return
	 */
	public static String decodeBase64(String encodedString) {
		byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
		String decodedString = new String(decodedBytes);
		return decodedString;
	}	
}
