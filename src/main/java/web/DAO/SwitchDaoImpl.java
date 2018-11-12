package web.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import web.models.Switch;

@Repository
public class SwitchDaoImpl implements SwitchDao {
	
	@Autowired
	MongoTemplate mgTemplate;
	
	public List<Switch> getAll() {
		return mgTemplate.findAll(Switch.class);
	}

	public int insert(Switch sw) {
		try {
			if (!mgTemplate.collectionExists(Switch.class)) {
				mgTemplate.createCollection(Switch.class);
			}
			mgTemplate.insert(sw);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	public int update(Switch sw) {
		try {
			mgTemplate.save(sw);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	public int delete(Switch sw) {
		try {
			mgTemplate.remove(sw);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	public Switch getByMAC(String mac) {
		return mgTemplate.findOne(new Query(Criteria.where("MAC").is(mac)), Switch.class);
	}

}
