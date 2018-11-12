package web.DAO;

import java.util.List;

import web.models.Switch;

public interface SwitchDao {

	public List<Switch> getAll();
	
	public int insert(Switch sw);
	
	public int update(Switch sw);
	
	public int delete(Switch sw);
	
	public Switch getByMAC(String mac);	
	
}
