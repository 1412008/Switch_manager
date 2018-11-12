package web.Services;

import java.util.List;

import web.models.Switch;

public interface SwitchService {
	
	public List<Switch> getAll();
	
	public int insert(Switch sw);
	
	public int update(Switch sw);
	
	public int delete(Switch sw);
	
	public Switch getByMAC(String mac);

}
