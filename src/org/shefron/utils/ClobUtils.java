package org.shefron.utils;

import java.io.StringReader;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClobUtils {

	public final static String getValueFromClob(Clob clob) throws SQLException {
		String value = null;

		if (clob != null) {
			int size = (int) clob.length();
			value = clob.getSubString(1, size);
		}

		return value;
	}

	public final static void setClobParameter(PreparedStatement ps, int i,
			String parameter) throws SQLException {

		StringReader reader = new StringReader(parameter);
		ps.setCharacterStream(i, reader, parameter.length());
	}

}
