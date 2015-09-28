package com.eps.dao.system;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.eps.dao.BaseDao;
import com.eps.domain.SCode;
import com.eps.utils.UStrMap;

@Repository
public class SYSCodeDao extends BaseDao{
	
	@Value("${scode.query.codecate}")
	private String getcodebycodeCate;
	
	@Value("${scode.query.all}")
	private String getAllCodeSql;
	public List<SCode> getCodeByCodeCate(String codeCate){
		UStrMap<Object> args = UStrMap.newInstance();
		args.put("code_cate", codeCate);
		return getNameParameTemplate().query(getcodebycodeCate, args, new RowMapper<SCode>() {
			public SCode mapRow(ResultSet rs, int arg1) throws SQLException {
				SCode code = new SCode();
				code.setCodeCate(rs.getString("CODE_CATE"));
				code.setCode(rs.getString("CODE"));
				code.setName(rs.getString("NAME"));
				code.setRemark(rs.getString("REMARK"));
				return code;
			}
		});
	}
	
	public UStrMap<List<SCode>> getAllCode(){
		final UStrMap<List<SCode>> map = UStrMap.newInstance();
		getJdbcTemplate().query(getAllCodeSql, new RowMapper<SCode>() {
			public SCode mapRow(ResultSet rs, int arg1) throws SQLException {
				String codeCate = rs.getString("CODE_CATE");
				List<SCode> codes = map.get(codeCate);
				if(codes==null) codes = new ArrayList<SCode>();
				SCode code = new SCode();
				code.setCodeCate(rs.getString("CODE_CATE"));
				code.setCode(rs.getString("CODE"));
				code.setName(rs.getString("NAME"));
				code.setRemark(rs.getString("REMARK"));
				codes.add(code);
				map.put(codeCate, codes);
				return code;
			}
		});
		return map;
	}
	//public List<>
}
