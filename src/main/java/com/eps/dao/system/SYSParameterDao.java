package com.eps.dao.system;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.eps.dao.BaseDao;
import com.eps.domain.SParameter;

@Repository
public class SYSParameterDao extends BaseDao{
	@Value("${parameter.get.all}")
	private String getAllSystemParameter;
	
	public List<SParameter>getAllSystemParm(){
		return getJdbcTemplate().query(getAllSystemParameter, new RowMapper<SParameter>(){
			public SParameter mapRow(ResultSet rs, int arg1)
					throws SQLException {
				SParameter p = new SParameter();
				p.setId(rs.getInt("PARAMETER_ID"));
				p.setName(rs.getString("PARAMETER_NAME"));
				p.setValue(rs.getString("PARAMETER_VALUE"));
				p.setRemark(rs.getString("REMARK"));
				return p;
			}
		});
	}
}
