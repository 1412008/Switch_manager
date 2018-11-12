//package web.controllers;
//
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import web.Services.SwitchService;
//import web.models.Switch;
//
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//@RestController
//public class SwitchController2 {
//	
//	@Autowired
//	SwitchService swService;
//	
//	@RequestMapping(value = {"switches"}, method = RequestMethod.GET)
//	public @ResponseBody List<Switch> getAll() {
//		List<Switch> switches =  swService.getAll();		
//		return switches;
//	}
//	
//	@RequestMapping(value = "switch", method = RequestMethod.GET)
//	public @ResponseBody Switch getOne(HttpServletRequest req) {
//		String mac = req.getParameter("MAC");
//		if (mac != null && !mac.trim().isEmpty()) {
//			Switch sw = swService.getByMAC(mac);
//			return sw;
//		}		
//		return null;
//	}
//	
//	@RequestMapping(value = "switch", method = RequestMethod.POST)
//	public ResponseEntity<HttpStatus> createSwitch(@RequestBody Switch body) {
//		if (body.checkData()) {
//			if (swService.insert(body) == 1) {
//				return ResponseEntity.ok(HttpStatus.OK);
//			}
//		}
//		return ResponseEntity.ok(HttpStatus.NOT_FOUND);
//	}
//	
//	@RequestMapping(value = "switch", method = RequestMethod.PUT)
//	public ResponseEntity<HttpStatus> updateSwitch(@RequestBody Switch body) {
//		String mac = body.getMAC();
//		if (mac != null && !mac.trim().isEmpty()) {
//			Switch sw = swService.getByMAC(mac);
//			if (sw != null) {
//				System.out.println(sw.toString());
//				if (!StringUtils.isEmpty(body.getName())) {
//					sw.setName(body.getName());
//				}
//				if (!StringUtils.isEmpty(body.getType())) {
//					sw.setType(body.getType());
//				}
//				if (!StringUtils.isEmpty(body.getAddress())) {
//					sw.setAddress(body.getAddress());
//				}
//				if (!StringUtils.isEmpty(body.getVersion())) {
//					sw.setVersion(body.getVersion());
//				}
//				if(swService.update(sw) == 1) {
//					System.out.println("new: " + sw.toString());
//					return ResponseEntity.ok(HttpStatus.OK);
//				}
//			}
//		}		
//		return ResponseEntity.ok(HttpStatus.NOT_FOUND);
//	}
//	
//	@RequestMapping(value = "switch", method = RequestMethod.DELETE)
//	public ResponseEntity<HttpStatus> deleteSwitch(@RequestBody Switch body) {
//		String mac = body.getMAC();
//		if (mac != null && !mac.trim().isEmpty()) {
//			Switch sw = swService.getByMAC(mac);
//			if (sw != null) {
//				System.out.println(sw.toString());
//				if(swService.delete(sw) == 1) {
//					return ResponseEntity.ok(HttpStatus.OK);
//				}
//			}
//		}
//		
//		return ResponseEntity.ok(HttpStatus.NOT_FOUND);
//	}
//}
