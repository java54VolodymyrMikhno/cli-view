package telran.view;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.function.Predicate;
import org.junit.jupiter.api.Test;

record User(String username, String password, LocalDate dateLastLogin, String phoneNumber, int numbersOfLoging) {

};

class InputOutputTest {
	InputOutput io = new SystemInputOutput();

	
	@Test
	void readObjectTest() {
		User user = io.readObject(
				"Enter user in format<username>#<password>#<dateLastLogin>" + "#<phone number>#<numbers of logins>",
				"Wrong user input format", str -> {
					String[] tokens = str.split("#");
					return new User(tokens[0], tokens[1], LocalDate.parse(tokens[2]), tokens[3],
							Integer.parseInt(tokens[4]));
				});
		io.writeLine(user);
	}

	@Test
	void getIntTest() {
		Integer longNumber = io.readInt("Enter a number", "Must be int");
		io.writeLine(longNumber);
	}

	@Test
	void getLongTest() {
		Long longNumber = io.readLong("Enter a Long number", "Must be Long");
		io.writeLine(longNumber);
	}

	@Test
	void getNumberRangeTest() {
		Double longNumber = io.readNumberRange("Enter a Double number in range", "Number  is not in range", 1, 2.0);
		io.writeLine(longNumber);
	}

	@Test
	void getDoubleTest() {
		Double longNumber = io.readDouble("Enter a Double number", "Must be Double");
		io.writeLine(longNumber);
	}

	@Test
	void getStringPredicateTest() {
		Predicate<String> validLengthPredicate = str -> str.length() >= 5;
		String result = io.readStringPredicate("Enter a string (length >= 5):", "Invalid input", validLengthPredicate);
		io.writeLine(result);
	}

	@Test
	void readStringOptionsGenderTest() {
		HashSet<String> genderSet = new HashSet<>();
		genderSet.add("Female");
		genderSet.add("Male");
		

		String genderSetString = String.join(", ", genderSet);
		String promptMessageError = "Please choose one of the following: " + genderSetString;
		String promptMessage="Enter Gender";
		String gender = io.readStringOptions(promptMessage,promptMessageError, genderSet);
		io.writeLine(gender);
	}

	@Test
	void getIsoDateTest() {
		LocalDate date = io.readIsoDate("Enter a date (yyyy-mm-dd):", "Invalid date format");
		io.writeLine(date);
	}

	@Test
	void getIsoDateRangeTest() {
		LocalDate from = LocalDate.of(2023, 1, 1);
		LocalDate to = LocalDate.of(2023, 12, 31);
		LocalDate date = io.readIsoDateRange("Enter a date(yyyy-mm-dd) from  " + from + " to " + to, "Date must be",
				from, to);
		io.writeLine(date);
	}

	@Test
	void readUserByFieldsTest() {
		String userName = io.readStringPredicate("Enter username (at least 6 letters, first capital):",
				"Invalid username format", str -> str.matches("[A-Z][a-z]{6,}"));
		String password = io.readStringPredicate(
				"Enter password (at least 8 symbols, one capital, one lower case, one digit, one special):",
				"Invalid password format",  passwordValidator);
		String phoneNumber = io.readStringPredicate("Enter phone number (Israel mobile):", "Invalid phone number",
				str -> str.matches("^(\\+972|0)?5[0-9]{8}$"));
		LocalDate dateLastLogin = io.readIsoDateRange("Enter date of last login (yyyy-mm-dd):",
				"Invalid date or date is in the future", LocalDate.MIN, LocalDate.now());
		int numberOfLogin = io.readNumberRange("Enter number of logins (positive number):",
				"Invalid number of logins",1,Integer.MAX_VALUE).intValue();
		User user = new User(userName, password, dateLastLogin, phoneNumber, numberOfLogin);
		io.writeLine(user);
	}


	private Predicate<String> passwordValidator = str ->
    str.length() >= 8 &&
    str.chars().anyMatch(Character::isUpperCase) &&
    str.chars().anyMatch(Character::isLowerCase) &&
    str.chars().anyMatch(Character::isDigit) &&
    str.chars().anyMatch(ch -> "#$*&%.".indexOf(ch) >= 0);

}
