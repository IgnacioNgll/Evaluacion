package org.acme;

import java.io.File;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.acme.dal.demoDal; //importacion de clase para conexion a BD

public class Demo {
	private static boolean logToFile;
	private static boolean logToConsole;
	private static boolean logMessage;
	private static boolean logWarning;
	private static boolean logError;
	private static boolean logToDatabase;
	// private boolean initialized; no se usa
	private static Map dbParams;
	private static Logger logger;

	public Demo(boolean logToFileParam, boolean logToConsoleParam, boolean logToDatabaseParam, boolean logMessageParam,
			boolean logWarningParam, boolean logErrorParam, Map dbParamsMap) {
		logger = Logger.getLogger("MyLog");
		logError = logErrorParam;
		logMessage = logMessageParam;
		logWarning = logWarningParam;
		logToDatabase = logToDatabaseParam;
		logToFile = logToFileParam;
		logToConsole = logToConsoleParam;
		dbParams = dbParamsMap;
	}

	@SuppressWarnings("resource")
	public static void LogMessage(String messageText, boolean message, boolean warning, boolean error)
			throws Exception {

		messageText.trim();

		// if (messageText == null || messageText.length() == 0) { Se corrigue
		// el messageText.length() se reemplaza por isEmpty()
		if (messageText == null || messageText.isEmpty()) {
			// return (el mensaje no retorna nada y se reemplaza por un
			// exception para recibir un mensaje de error)
			throw new Exception("Mensaje Vacio");
		}
		if (!logToConsole && (!logToFile && !logToDatabase)) {
			throw new Exception("Invalid configuration");
		}
		if ((!logError && (!logMessage && !logWarning)) || (!message && (!warning && !error))) {
			throw new Exception("Error or Warning or Message must be specified");
		}

		Connection connection = null;
		demoDal connectionDal = new demoDal();
		connection = (Connection) connectionDal;

		int logValues = 0; //se cambia el nombre de la variable "l" a "logValues" porque no pueden existir variable llamadas por solo letras solas
		if (message && logMessage) {
			logValues = 1;
		}

		if (error && logError) {
			logValues = 2;
		}

		if (warning && logWarning) {
			logValues = 3;
		}

		Statement stmt = connection.createStatement();  //esto no se usaría si comentamos la linea 119

		// String l = null; Se elimina la variable "l" porque no se usa en
		// ningun lugar

		try { // se agrega un catch para obtener un error, en caso se de
			File logFile = new File(dbParams.get("logFileFolder") + "/logFile.txt");
			if (!logFile.exists()) {
				logFile.createNewFile();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		FileHandler fh = new FileHandler(dbParams.get("logFileFolder") + "/logFile.txt");
		ConsoleHandler ch = new ConsoleHandler();

		// Se comenta todo el código porque es no tiene sentido almacenar valor
		// a la variable "l" si luego no se usa en oro lado

		/*
		 * if (error && logError) { l = l + "error " +
		 * DateFormat.getDateInstance(DateFormat.LONG).format(new Date()) +
		 * messageText; }
		 * 
		 * if (warning && logWarning) { l = l + "warning "
		 * +DateFormat.getDateInstance(DateFormat.LONG).format(new Date()) +
		 * messageText; }
		 * 
		 * if (message && logMessage) { l = l + "message "
		 * +DateFormat.getDateInstance(DateFormat.LONG).format(new Date()) +
		 * messageText; }
		 */

		if (logToFile) {
			logger.addHandler(fh);
			logger.log(Level.INFO, messageText);
		}

		if (logToConsole) {
			logger.addHandler(ch);
			logger.log(Level.INFO, messageText);
		}

		if (logToDatabase) {
			// stmt.executeUpdate("insert into Log_Values('" + message + "', " +
			// String.valueOf(logValues) + ")"); Este es un error, ya que no se
			// está usando para ejecutar sentencias INSERT, UPDATE ó DELETE
		}
	}
}
