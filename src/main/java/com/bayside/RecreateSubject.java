package com.bayside;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.bayside.app.util.DBUtil;

public class RecreateSubject {
public static void main(String[] args) {
	try {
		Connection conn = DBUtil.getConnection();
		String sql = "SELECT * FROM bs_subject WHERE TYPE=1 AND id !='db7cb0c1c5bf4e5195803b0e16e18d45'";
		ObjectMapper mapper = new ObjectMapper();
		String updatesql = "update bs_subject set subject_word=?,region_word=?,event_word=? where id=?";
		PreparedStatement pst = conn.prepareStatement(updatesql);
		List<Map<String,Object>> list = DBUtil.getResultMap(sql, conn);
		for (Map<String, Object> map : list) {
			String id = (String) map.get("ID");
			String combineword = (String) map.get("combined_word");
			String subjectWord = "";
			String regionword = "";
			String eventword = "";
			try {
				List<Map<String, String>> comList = mapper.readValue(combineword, ArrayList.class);
				for (Map<String, String> commap : comList) {
					String subject_word = commap.get("subject_word");
					if(subject_word!=null&&!"".equals(subject_word)){
						if(!subjectWord.contains(subject_word)){
							subjectWord+=subject_word+" ";
						}
					}
					String region_word = commap.get("region_word");
					if(region_word!=null&&!"".equals(region_word)){
						if(!regionword.contains(region_word)){
							regionword+=region_word+" ";
						}
					}
					String event_word = commap.get("event_word");
					if(event_word!=null&&!"".equals(event_word)){
						if(!eventword.contains(event_word)){
							eventword+=event_word+" ";
						}
					}
				}
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(id+"\t"+subjectWord+"\t"+regionword+"\t"+eventword);
			pst.setString(1, subjectWord);
			pst.setString(2, regionword);
			pst.setString(3, eventword);
			pst.setString(4, id);
			pst.executeUpdate();
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
