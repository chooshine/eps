package com.eps.dao.corpus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.eps.dao.BaseDao;
import com.eps.domain.Card;
import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;
@Repository
public class CardDao extends BaseDao {

	@Value("${card.get.card.cardnopassword}")
	private String getCardByCardId;
	
	@Value("${card.get.service.cardid}")
	private String getServiceByCardId;
	
	@Value("${card.update.status.user.cardid}")
	private String updateStatusUserByCardId;
	
	@Value("${card.get.service.cardid.servicetype}")
	private String getServiceByCardIdServiceType;
	
	@Value("${card.update.service.serviceid}")
	private String updateServiceByServiceId;
	
	public Card getCardByCardId(UStrMap<Object> map){
		Card card = null;
		try {
			card = getNameParameTemplate().queryForObject(getCardByCardId, map, new CardRowMapper());
			return card;
		} catch (Exception e) {
			return null;
		}
	}
	
	public List<LStrMap<Object>> getServiceByCardId(Long card_id){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("card_id", card_id);
		return this.find(getServiceByCardId, map);
	}
	
	public List<LStrMap<Object>> getServiceByCardIdServiceType(Long card_id, String service_type){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("card_id", card_id);
		map.put("service_type", service_type);
		return this.find(getServiceByCardIdServiceType, map);
	}
	
	public void updateCardStatusUser(long card_id,long card_user){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("card_id", card_id);
		map.put("card_user", card_user);
		this.excute(updateStatusUserByCardId, map);
	}
	
	public void updateServiceStatus(int cardservice_id, int use_status) {
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("cardservice_id", cardservice_id);
		map.put("use_status", use_status);
		this.excute(updateServiceByServiceId, map);
	}
	
	class CardRowMapper implements RowMapper<Card>{
		public Card mapRow(ResultSet rs, int arg1) throws SQLException {
			Card Card = new Card();
			Card.setCardId(rs.getLong("CARD_ID"));
			Card.setCardNo(rs.getString("CARD_NO"));
			Card.setCardPassword(rs.getString("CARD_PASSWORD"));
			Card.setCardName(rs.getString("CARD_NAME"));
			Card.setCardType(rs.getInt("CARD_TYPE"));
			Card.setCardStatus(rs.getInt("CARD_STATUS"));
			Card.setCarOwner(rs.getLong("CARD_OWNER"));
			Card.setCardUser(rs.getLong("CARD_USER"));
			Card.setRemark(rs.getString("REMARK"));
			Card.setLockStatus(rs.getInt("LOCK_STATUS"));
			return Card;
		}
	}
}
