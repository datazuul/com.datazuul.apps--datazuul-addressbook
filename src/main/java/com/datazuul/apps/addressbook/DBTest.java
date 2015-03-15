package com.datazuul.apps.addressbook;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import org.apache.derby.drda.NetworkServerControl;

public class DBTest {

    public static void main(String[] args) {

	NetworkServerControl server = null;
	Connection conn = null;
	PreparedStatement prestat = null;
	Statement stmt = null;
	ResultSet result = null;

	try {
	    server = new NetworkServerControl(InetAddress.getByName("localhost"), 1527);
	    server.start(null);
	} catch (UnknownHostException e1) {
	    e1.printStackTrace();
	} catch (Exception e1) {
	    e1.printStackTrace();
	}

	try {
	    Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
	} catch (InstantiationException e) {
	    e.printStackTrace();
	} catch (IllegalAccessException e) {
	    e.printStackTrace();
	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	}
	try {
	    conn = DriverManager.getConnection("jdbc:derby://localhost:1527/MyDbTest;create=true");

	    stmt = conn.createStatement();
	    try {
		stmt.execute("CREATE TABLE newTestTable(id int primary key, name varchar(20))");
	    } catch (Exception e) {
		//e.printStackTrace();
	    }
	    try {
		stmt.execute("INSERT INTO newTestTable VALUES(10, 'Hey,'), (20, 'Look I changed'), (30, 'The code!')");
	    } catch (Exception e) {

	    }

	    prestat = conn.prepareStatement("SELECT * FROM newTestTable");
	    result = prestat.executeQuery();

	    StringBuilder builder = new StringBuilder();
	    while (result.next()) {
		builder.append(result.getInt(1) + ", " + result.getString(2));
		builder.append('\n');
	    }

	    JOptionPane.showMessageDialog(null, builder.toString());

	    result.close();
	    result = null;
	    prestat.close();
	    prestat = null;
	    conn.close();
	    conn = null;

	    server.shutdown();

	} catch (SQLException e) {
	    e.printStackTrace();
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    if (result != null) {
		try {
		    result.close();
		} catch (SQLException e) {
		    ;
		}
		result = null;
	    }
	    if (prestat != null) {
		try {
		    prestat.close();
		} catch (SQLException e) {
		    ;
		}
		prestat = null;
	    }
	    if (conn != null) {
		try {
		    conn.close();
		} catch (SQLException e) {
		    ;
		}
		conn = null;
	    }
	}

    }

}
