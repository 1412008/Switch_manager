//package web.controllers;
//
//import java.io.BufferedReader;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.bson.Document;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.mongodb.DBObject;
//import com.mongodb.MongoClient;
//import com.mongodb.MongoClientURI;
//import com.mongodb.client.FindIterable;
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoDatabase;
//import com.mongodb.client.model.Filters;
//import com.mongodb.util.JSON;
//
//import web.DBHelper.DbConfig;
//import web.models.Switch;
//
//import org.springframework.web.bind.annotation.RequestMethod;
//
//@RestController
//public class SwitchController {
//
//	@RequestMapping(value = "getAll", method = RequestMethod.GET)
//	public String getAll() {
//		String rs = "[\n";
//		MongoClient mongoClient = null;
//		try {
//			mongoClient = new MongoClient(new MongoClientURI(DbConfig.URL));			
//			MongoDatabase database = mongoClient.getDatabase(DbConfig.DB_NAME);
//			MongoCollection<Document> coll = database.getCollection(DbConfig.DB_COLLECTION);
//			
//			FindIterable<Document> fi = coll.find();
//			if (fi != null) {
//				for (Document doc : fi) {
//					rs += doc.toJson() + "\n";
//				}			
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (mongoClient != null) {
//				mongoClient.close();
//			}
//		}
//		
//		return rs + "\n]";
//	}
//	
//	@RequestMapping(value = "switch", method = RequestMethod.GET)
//	public String getOne(HttpServletRequest req) {
//		String mac = req.getParameter("MAC");
//		String rs = "";
//		MongoClient mongoClient = null;
//		try {
//			if (mac != null && !mac.trim().isEmpty()) {
//				mongoClient = new MongoClient(new MongoClientURI(DbConfig.URL));			
//				MongoDatabase database = mongoClient.getDatabase(DbConfig.DB_NAME);
//				MongoCollection<Document> coll = database.getCollection(DbConfig.DB_COLLECTION);
//				
//				Document doc = coll.find(Filters.eq("MAC", mac)).first();
//				
//				if (doc != null) {
//					rs = doc.toJson();
//				}
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (mongoClient != null) {
//				mongoClient.close();
//			}
//		}
//		
//		return rs;
//	}
//	
//	@RequestMapping(value = "switch", method = RequestMethod.POST)
//	public ResponseEntity<HttpStatus> createSwitch(HttpServletRequest request/*, @RequestBody Switch body*/) {
//		MongoClient mongoClient = null;
//		try {
//			String jsonStr = BufferToString(request.getReader());
//			// System.out.println("00000000000");
//			DBObject obj = (DBObject) JSON.parse(jsonStr);
//			
//			if (obj.containsField("Name") && obj.containsField("MAC") && obj.containsField("Address") 
//					&& obj.containsField("Status") && obj.containsField("Type") && obj.containsField("Version")) {
//				Document doc = new Document();
//				doc.append("Name", (String)obj.get("Name"));
//				doc.append("MAC", (String)obj.get("MAC"));
//				doc.append("Address", (String)obj.get("Address"));
//				doc.append("Status", (Integer)obj.get("Status"));
//				doc.append("Type", (String)obj.get("Type"));
//				doc.append("Version", (String)obj.get("Version"));
//				
//				mongoClient = new MongoClient(new MongoClientURI(DbConfig.URL));			
//				MongoDatabase database = mongoClient.getDatabase(DbConfig.DB_NAME);
//				MongoCollection<Document> coll = database.getCollection(DbConfig.DB_COLLECTION);
//				
//				coll.insertOne(doc);
//				return ResponseEntity.ok(HttpStatus.OK);
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (mongoClient != null) {
//				mongoClient.close();
//			}
//		}
//		
//		return ResponseEntity.ok(HttpStatus.NOT_FOUND);
//	}
//	
//	private String BufferToString(BufferedReader br) {
//		String jsonStr = "";
//		try {
//			String tmp;
//			while ((tmp = br.readLine()) != null) {
//				jsonStr += tmp;
//			}
//			return jsonStr;
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//	
//	@RequestMapping(value = "switch", method = RequestMethod.PUT)
//	public ResponseEntity<HttpStatus> updateSwitch(HttpServletRequest request) {
//		MongoClient mongoClient = null;
//		try {
//			String jsonStr = BufferToString(request.getReader());
//			DBObject obj = (DBObject) JSON.parse(jsonStr);
//			System.out.println(obj.toString());
//
//			if (obj.containsField("MAC")) {
//				String mac = (String)obj.get("MAC");
//				
//				mongoClient = new MongoClient(new MongoClientURI(DbConfig.URL));			
//				MongoDatabase database = mongoClient.getDatabase(DbConfig.DB_NAME);
//				MongoCollection<Document> coll = database.getCollection(DbConfig.DB_COLLECTION);
//				
//				Document doc = new Document();
//				if (obj.containsField("Name")) {
//					doc.append("Name", (String)obj.get("Name"));
//				}
//				if (obj.containsField("Address")) {
//					doc.append("Address", (String)obj.get("Address"));
//				}
//				if (obj.containsField("Type")) {
//					doc.append("Type", (String)obj.get("Type"));
//				}
//				if (obj.containsField("Version")) {
//					doc.append("Version", (String)obj.get("Version"));
//				}
//				
//				coll.updateOne(Filters.eq("MAC", mac), new Document("$set", doc));
//				
//				return ResponseEntity.ok(HttpStatus.OK);
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (mongoClient != null) {
//				mongoClient.close();
//			}
//		}
//		
//		return ResponseEntity.ok(HttpStatus.NOT_FOUND);
//	}
//	
//	@RequestMapping(value = "switch", method = RequestMethod.DELETE)
//	public ResponseEntity<HttpStatus> deleteSwitch(HttpServletRequest request) {
//		MongoClient mongoClient = null;
//		try {
//			String jsonStr = BufferToString(request.getReader());
//			DBObject obj = (DBObject) JSON.parse(jsonStr);
//			
//			if (obj.containsField("MAC")) {
//				String mac = (String) obj.get("MAC");
//				
//				mongoClient = new MongoClient(new MongoClientURI(DbConfig.URL));			
//				MongoDatabase database = mongoClient.getDatabase(DbConfig.DB_NAME);
//				MongoCollection<Document> coll = database.getCollection(DbConfig.DB_COLLECTION);
//				
//				Document doc = coll.findOneAndDelete(Filters.eq("MAC", mac));
//				if (doc != null) {
//					System.out.println(doc.toJson());
//				}
//				
//				return ResponseEntity.ok(HttpStatus.OK);
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (mongoClient != null) {
//				mongoClient.close();
//			}
//		}
//		
//		return ResponseEntity.ok(HttpStatus.NOT_FOUND);
//	}
//}
