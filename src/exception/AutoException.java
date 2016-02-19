/**
 * @author cvandera
 *
 */

package exception;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AutoException extends Exception implements FixAuto {

	private static final long serialVersionUID = 1L;
	private String errormsg = "";
	private Map<String, Object> information = new HashMap<String, Object>();
	private Map<String, Object> fix = new HashMap<String, Object>();
	private ExceptionType e;

	public static enum ExceptionType {
		MISSING_FILE(101), 
		INVALID_FILENAME(102), 
		IO_EXCEPTION(103), 
		INVALID_INPUT(104), 
		INVALID_FILETYPE(105), 

		MISSING_OPTION_NAME(201), 
		MISSING_OPTION_PRICE(202), 
		MISSING_OPTION(203), 
		MISSING_OPTION_SET(204), 
		MISSING_OPTION_SET_NAME(205), 
		MISSING_AUTO_PRICE(206),
		
		MISSING_ATTRIBUTE(301); 

		private int errno;

		private ExceptionType(int value) {
			this.errno = value;
		}

		public int getErrorNumber() {
			return this.errno;
		}

	}

	public AutoException(ExceptionType e) {
		this.e = e;

		// log the exception first
		logException(e.getErrorNumber());

		// fix the problem
		fix(e.getErrorNumber());
	}

	public AutoException(ExceptionType e, String errorMsg) {
		this.e = e;
		this.errormsg = errorMsg;

		// log the exception first
		logException(e.getErrorNumber());
	}

	public ExceptionType getType() {
		return e;
	}

	public void fix(int errno) {
		String s;
		switch (errno) {
		case 101:
			// MISSING_FILE
			System.out.println(errormsg);
			s = FixHelper.readInputFromUser("filename");
			fix.put("filename", s);

			break;
		case 102:
			// INVALID_FILENAME
			System.out.println(errormsg);
			s = FixHelper.readInputFromUser("filename");
			fix.put("filename", s);

			break;
		case 103:
			// IO_EXCEPTION
			System.out.println(errormsg);
			s = FixHelper.readInputFromUser("filename");
			fix.put("filename", s);

			break;
		case 104:
			// IO_EXCEPTION
			System.out.println(errormsg);
			s = FixHelper.readInputFromUser(null);
			fix.put("value", s);
			
			break;
		case 105:
			// INVALID_FILETYPE
			System.out.println(errormsg);
			s = FixHelper.readInputFromUser("filename");
			fix.put("filename", s);
			s = FixHelper.readInputFromUser("filetype");
			fix.put("filetype", s);
			
			break;
		case 201:
			// MISSING_OPTION_NAME
			System.out.println(errormsg);
			s = FixHelper.readInputFromUser("option name");
			fix.put("name", s);
			break;
		case 202:
			// MISSING_OPTION_PRICE
			System.out.println(errormsg);
			s = FixHelper.readInputFromUser("option price");
			fix.put("price", s);
			break;
		case 203:
			// MISSING_OPTION
			System.out.println(errormsg);
			s = FixHelper.readInputFromUser("new option name");
			fix.put("name", s);
			
			s = FixHelper.readInputFromUser("new option price");
			fix.put("price", s);
			
			break;
		case 204:
			// MISSING_OPTION_SET
			System.out.println(errormsg);
			s = FixHelper.readInputFromUser("new option set name");
			fix.put("setName", s);
			
			break;
		case 205:
			// MISSING_OPTION_SET_NAME
			System.out.println(errormsg);
			s = FixHelper.readInputFromUser("option set name");
			fix.put("optName", s);
			break;
		case 206:
			// MISSING_AUTO_PRICE
			System.out.println(errormsg);
			s = FixHelper.readInputFromUser("automobile base price");
			fix.put("price", s);
			break;
		case 301:
			// MISSING_ATTRIBUTE
			System.out.println(errormsg);
			break;
		}
	}

	public AutoException set(String key, Object value) {
		information.put(key, value);
		return this;
	}

	public Object get(String key) {
		return information.get(key);
	}

	public Object getFix(String key) {
		return fix.get(key);
	}

	public String getErrormsg() {
		return errormsg;
	}

	public AutoException setErrormsg(String errormsg) {
		this.errormsg = errormsg;
		return this;
	}

	private void logException(int errno) {
		Date date = new Date();
		StringBuilder sb = new StringBuilder();
		sb.append(new Timestamp(date.getTime()));
		sb.append("\t");
		sb.append("Exception with error code: ");
		sb.append(errno);
		sb.append(": ");
		sb.append(errormsg);

		try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("log.txt", true)))) {
			out.println(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
