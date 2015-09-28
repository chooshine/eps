package com.eps.dao.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.eps.dao.BaseDao;
import com.eps.domain.SLink;
import com.eps.utils.UStrMap;

@Repository
public class LinkDao extends BaseDao {
	
	@Value("${link.create}")
	private String insertSql;
	@Value("${link.update}")
	private String updateSql;
	@Value("${link.get.linkno}")
	private String getLinkSql;
	public void createLink(SLink link){
		UStrMap<Object> args = UStrMap.newInstance();
		args.put("user_id", link.getUserId());
		args.put("link_no", link.getLinkNo());
		args.put("create_date", link.getCreateDate());
		args.put("link_status", link.getLinkStatus());
		excute(insertSql, args);
	}
	
	public void updateLink(SLink link){
		UStrMap<Object> args = UStrMap.newInstance();
		args.put("user_id", link.getUserId());
		args.put("link_no", link.getLinkNo());
		args.put("create_date", link.getCreateDate());
		args.put("link_status", link.getLinkStatus());
		args.put("use_date", link.getUseDate());
		excute(updateSql, args);
	}
	
	public SLink getLink(String linkNo){
		UStrMap<Object> paramMap = UStrMap.newInstance();
		paramMap.put("link_no", linkNo);
		return getNameParameTemplate().queryForObject(getLinkSql, paramMap, new RowMapper<SLink>() {
			public SLink mapRow(ResultSet rs, int arg1) throws SQLException {
				SLink link = new SLink();
				link.setLinkId(rs.getInt("LINK_ID"));
				link.setUserId(rs.getInt("USER_ID"));
				link.setCreateDate(rs.getDate("CREATE_DATE"));
				link.setLinkNo(rs.getString("LINK_NO"));
				link.setLinkStatus(rs.getInt("LINK_STATUS"));
				link.setUseDate(rs.getDate("USE_DATE"));
				return link;
			}
		});
		//return null;
	}
}
