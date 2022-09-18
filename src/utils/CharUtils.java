//package utils;
//
//import org.apache.commons.lang3.ObjectUtils;
//
//import javax.swing.text.MaskFormatter;
//import java.text.ParseException;
//
//public class CharUtils {
//
//    public static String replaceSpecialCharacter(String texto){
//        return ObjectUtils.isEmpty(texto) ? null : texto.replaceAll("[\\-\\/\\+\\.\\^:,]","");
//    }
//
//	public static String formatString(String string, String mask) {
//		try {
//			MaskFormatter mf = new MaskFormatter(mask);
//			mf.setValueContainsLiteralCharacters(false);
//			return mf.valueToString(string);
//		} catch (ParseException ex) {
//			return string;
//		}
//	}
//}
