package util;

public class TextParser {
	public static String parseLatinToHTML(String text){
		String result = text;
		result = result.replace("Ã±", "&ntilde;");
		result = result.replace(">", "&gt;");
		result = result.replace("<", "&lt;");
		
		return result;
	}
	
	public static String parseJSONRFC4627(String text){
		String result = text;
		result = result.replace("\0", "\n");
		result = result.replace("\n", "\",\"");
		result = result.replace("\r", "");
		result = result.replace("\b", "");
		result = result.replace("\f", "");
		result = result.replace("\'", "\\'");
		result = "[\""+ result + "\"]";
		return result;
	}
}
