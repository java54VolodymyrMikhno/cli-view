package telran.view;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.function.Function;
import java.util.function.Predicate;

public interface InputOutput {
	String readString(String prompt);

	void writeString(String str);

	default void writeLine(Object obj) {
		writeString(obj.toString() + "\n");
	}

	default <T> T readObject(String prompt, String errorPrompt, Function<String, T> mapper) {
		T result = null;
		boolean running = false;
		do {
			String str = readString(prompt);
			running  = false;
			try {
				result = mapper.apply(str);
			} catch (RuntimeException e) {
				writeLine(errorPrompt + " " + e.getMessage());
				running = true;
			}
		} while (running);
		return result;
	}
	
	default Integer readInt(String prompt, String errorPrompt) {
		return  readObject(prompt, errorPrompt, Integer::parseInt);
	}
	default Long readLong(String prompt,String errorPrompt) {
		return readObject(prompt, errorPrompt,Long::parseLong);
		
	}
	default Double readDouble(String prompt,String errorPrompt) {
		
		return readObject(prompt, errorPrompt, Double::parseDouble);
		
	}
	default Double readNumberRange(String prompt, String errorPrompt, double min, double max) {
	    return readObject(prompt, errorPrompt, str -> {
	            Double doubleNumber = Double.parseDouble(str);
	            if (doubleNumber >= min && doubleNumber < max) {
	                return doubleNumber;
	            } else {
	                throw new RuntimeException(" from "+ min +" to "+ max);
	            }
	        
	    });
	}


	default String readStringPredicate(String prompt, String errorPrompt, Predicate<String> predicate) {
	    return readObject(prompt, errorPrompt, str -> {
	        if (predicate.test(str)) {
	            return str;
	        } else {
	            throw new RuntimeException("");
	        }
	    });
	}


	default String readStringOptions(String prompt, String errorPrompt, HashSet<String> options) {
	    return readObject(prompt, errorPrompt, str -> {
	        if (options.contains(str)) {
	            return str;
	        } else {
	            throw new RuntimeException("");
	        }
	    });
	}

	default LocalDate readIsoDate(String prompt, String errorPrompt) {
		return readObject(prompt, errorPrompt, LocalDate::parse);
	}

	default LocalDate readIsoDateRange(String prompt, String errorPrompt, LocalDate from, LocalDate to) {
	    return readObject(prompt, errorPrompt, str -> {
	        LocalDate date = LocalDate.parse(str);
	        if (date.isAfter(from) && date.isBefore(to)) {
	            return date;
	        } else {
	            throw new RuntimeException(" from " + from + " to " + to);
	        }
	    });
	}

}
