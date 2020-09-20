package com.adobe.prj.validation;

public class BookingValidation {

	// count booked slots
	public static int setBits(String timeSlots) {
		int count = 0, i;
		for (i = 0; i < timeSlots.length(); i++){
		    char c = timeSlots.charAt(i);        
		    if(c == '1')
		    	count++;
		}
		return count;
	}
	
	// verify timeSlots format
	public static boolean isBinaryString(String str) {
		int i;
		for (i = 0; i < str.length(); i++){
		    char c = str.charAt(i);        
		    if(c != '1' && c != '0')
		    	return false;
		}
		return true;
	}
}
