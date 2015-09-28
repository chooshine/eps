package com.eps.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.incrementer.AbstractColumnMaxValueIncrementer;

import com.eps.utils.LStrMap;

public class MaxValueINcrementer extends AbstractColumnMaxValueIncrementer {

	private static final String VALUE_SQL = "select last_insert_id()";
	// private long nextId = 0L;
	// private long maxId = 0L;

	public static LStrMap<Long> nextIds = LStrMap.newInstance();

	public static LStrMap<Long> maxIds = LStrMap.newInstance();

	private String tableName;
	
	private String tableColumn;

	public MaxValueINcrementer() {
	}

	public MaxValueINcrementer(DataSource dataSource, String incrementerName,
			String columnName) {
		super(dataSource, incrementerName, columnName);
	}

	@Override
	protected long getNextKey() {
		Connection con;
		Statement stmt;
		long maxId = maxIds.get(getTableName()) == null ? 0l : maxIds
				.get(getTableName());
		long nextId = nextIds.get(getTableName()) == null ? 0l : nextIds
				.get(getTableName());

		if (maxId == 0 || maxId == nextId) {
			con = DataSourceUtils.getConnection(getDataSource());
			stmt = null;
			try {
				stmt = con.createStatement();
				DataSourceUtils.applyTransactionTimeout(stmt, getDataSource());
				String columnName = getColumnName();
				String sql = "update " + getIncrementerName() + " set "
						+ columnName + " = last_insert_id(" + columnName
						+ " + " + getCacheSize() + ") where "+ getTableColumn() +" = '"
						+ getTableName() + "'";
				stmt.executeUpdate(sql);
				ResultSet rs = stmt.executeQuery(VALUE_SQL);
				try {
					if (!(rs.next())) {
						throw new DataAccessResourceFailureException(
								"last_insert_id() failed after executing an update");
					}
					maxId = rs.getLong(1);
					maxIds.put(getTableName(), maxId);
				} finally {
					JdbcUtils.closeResultSet(rs);
				}
				nextId = (maxId - getCacheSize() + 1L);
				nextIds.put(getTableName(), nextId);
				
			} catch (SQLException ex) {
				throw new DataAccessResourceFailureException(
						"Could not obtain last_insert_id()", ex);
			} finally {
				JdbcUtils.closeStatement(stmt);
				DataSourceUtils.releaseConnection(con, getDataSource());
			}
		}else{
			nextIds.put(getTableName(), ++nextId);
		}
		return nextId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableColumn() {
		return tableColumn;
	}

	public void setTableColumn(String tableColumn) {
		this.tableColumn = tableColumn;
	}

}
