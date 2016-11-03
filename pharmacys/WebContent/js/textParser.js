function parse(text){
	var result = text.split('&ntilde;').join('ñ');
	return result;
}