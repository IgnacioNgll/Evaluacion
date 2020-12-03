package org.acmeTest;

import org.acme.Demo;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.powermock.api.mockito.PowerMockito;

public class DemoTest {

	
	@SuppressWarnings("static-access")
	@Test(expected = IllegalAccessError.class)
	public void demoTest() throws Exception {

		ResultSet rs = mock(ResultSet.class);
		when(rs.getString(eq("foo"))).thenReturn("This is mocked value");

		PreparedStatement stmt = mock(PreparedStatement.class);
		when(stmt.getResultSet()).thenReturn(rs);
		when(stmt.execute()).thenReturn(true);

		Connection connection = mock(Connection.class);
		when(connection.prepareStatement(anyString(), anyInt(), anyInt())).thenReturn(stmt);

		PowerMockito.mockStatic(DriverManager.class);
		PowerMockito.when(DriverManager.getConnection(anyString(), anyString(), anyString())).thenReturn(connection);
		assertTrue(stmt.isClosed());
		
		Demo demo = new Demo(false, false, false, false, false, false, null);

		demo.LogMessage("messageText", false, false, false);

	}

}
