package web.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.DAO.SwitchDao;
import web.models.Switch;

@Service
public class SwitchServiceImpl implements SwitchService {

	@Autowired
	SwitchDao swdao;
	
	public List<Switch> getAll() {
		return swdao.getAll();
	}

	public int insert(Switch sw) {
		return swdao.insert(sw);
	}

	public int update(Switch sw) {
		return swdao.update(sw);
	}

	public int delete(Switch sw) {
		return swdao.delete(sw);
	}

	public Switch getByMAC(String mac) {
		return swdao.getByMAC(mac);
	}

	
}
