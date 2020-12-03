package org.acme.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

public class demoDal {

	private static Map dbParams;

	public Connection demoDal() {

		Connection connection = null;
		Properties connectionProps = new Properties();
		connectionProps.put("user", dbParams.get("userName"));
		connectionProps.put("password", dbParams.get("password"));

		try {
			return connection = DriverManager.getConnection("jdbc:" + dbParams.get("dbms") + "://"
					+ dbParams.get("serverName") + ":" + dbParams.get("portNumber") + "/", connectionProps);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;

	}

}
