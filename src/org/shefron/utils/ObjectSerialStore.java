package org.shefron.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 
 * handle serial object with oracle dbStore<br/>
 * eg: create table TEST_OBJECTSTORE ( CLASSNAME VARCHAR2(256), CONTENT BLOB )
 * 
 * @author Administrator
 * 
 */
public class ObjectSerialStore {

	private String tableName;
	private String classNameColumn;
	private String serialObjColumn;

	/**
	 * construct
	 * 
	 * @param tableName
	 * @param classNameColumn
	 * @param serialObjColumn
	 */
	public ObjectSerialStore(String tableName, String classNameColumn,
			String serialObjColumn) {
		this.tableName = tableName;
		this.classNameColumn = classNameColumn;
		this.serialObjColumn = serialObjColumn;
	}

	public final void storeSerialObject(Connection dbConn, String className,
			Object serialObj) {
		PreparedStatement pstmt = null;
		try {
			ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
			ObjectOutputStream objOuts = new ObjectOutputStream(byteArray);
			objOuts.writeObject(serialObj);

			final byte[] objBytes = byteArray.toByteArray();

			pstmt = dbConn.prepareStatement("insert into " + this.tableName
					+ " (" + this.classNameColumn + ", " + this.serialObjColumn
					+ ") values (?,?)");

			pstmt.setString(1, className);

			ByteArrayInputStream bis = new ByteArrayInputStream(objBytes);
			pstmt.setBinaryStream(2, bis, objBytes.length);

			pstmt.execute();
		} catch (Exception e) {
			System.out.println("The error when serial obj:" + e.getMessage());
		} finally {
			close(null, pstmt, dbConn);
		}
	}

	/**
	 * update the serial Object
	 * 
	 * @param dbConn
	 *            close after use
	 * @param className
	 *            serialObj.getClass().getName() or OBJ.class.getName()
	 * @param serialObj
	 */
	public final void updateSerialObject(Connection dbConn, String className,
			Object serialObj) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream objOuts = new ObjectOutputStream(out);
			objOuts.writeObject(serialObj);

			final byte[] objBytes = out.toByteArray();

			pstmt = dbConn.prepareStatement("update " + this.tableName
					+ " set " + this.serialObjColumn + "=? where "
					+ this.classNameColumn + "=?");

			ByteArrayInputStream input = new ByteArrayInputStream(objBytes);
			pstmt.setBinaryStream(1, input, objBytes.length);
			pstmt.setString(2, className);

			pstmt.execute();

			input.close();
			out.close();
			objOuts.close();
		} catch (Exception e) {
			System.out.println("The error when update serial obj:"
					+ e.getMessage());
		} finally {
			close(rs, pstmt, dbConn);
		}

	}

	public final Object getSerialObject(Connection dbConn, String className) {
		Statement stmt = null;
		ResultSet rs = null;

		Object returnObj = null;

		try {
			stmt = dbConn.createStatement();
			rs = stmt.executeQuery("select " + this.serialObjColumn + " from "
					+ this.tableName + " where " + this.classNameColumn + "='"
					+ className + "'");

			Blob blob = null;
			if (rs.next()) {
				blob = rs.getBlob(this.serialObjColumn);
			}

			byte[] getBytes = blob.getBytes(1, (int) blob.length());

			ObjectInputStream objInput = new ObjectInputStream(
					new ByteArrayInputStream(getBytes));
			returnObj = objInput.readObject();

			objInput.close();

		} catch (Exception e) {
			System.out.println("The error when deserial obj:" + e.getMessage());
		} finally {
			close(rs, stmt, dbConn);
		}

		return returnObj;
	}

	private void close(ResultSet rs, Statement stmt, Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
			}
		}

		if (stmt != null) {
			try {
				stmt.close();
			} catch (Exception e) {
			}
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
	}

}
