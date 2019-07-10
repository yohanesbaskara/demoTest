package com.example.demoHomeCredit.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.hibernate.annotations.Parameter;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demoHomeCredit.dao.APIDAOFactory;
import com.example.demoHomeCredit.dao.impl.APIDAOFactoryHibernate;
import com.example.demoHomeCredit.model.UserGroup;
import com.example.demoHomeCredit.model.Users;
import com.google.gson.Gson;

@RestController
public class MainController {

	APIDAOFactory apiDAOFactory = APIDAOFactory.instance(APIDAOFactoryHibernate.class);

	
	@RequestMapping(value = "/demo")
	public Map<String, Object> webApiSearchv2(@RequestParam String userId) {
		Map<String, Object> mapOutput = new HashMap<>();
		Map<String, String> mapData = null;
		List<UserGroup> userGroupList = new LinkedList<UserGroup>();
		LinkedList<Object> list = new LinkedList<Object>();
		Users user = new Users();
		Gson gson = new Gson();
		try {
			//IF USE BODY
			/*
			UsersTest test = gson.fromJson(string, UsersTest.class);
			
			if(test.userId!=null && test.userId>0) {
					user = apiDAOFactory.getUsersDAO().findById(test.userId);
					userGroupList = apiDAOFactory.getUserGroupDAO().findByCriteria(Order.asc("viewSeq"), Restrictions.eq("users", user));
			}*/
			
			if (userId.length()>0) {
				user = apiDAOFactory.getUsersDAO().findById(Long.valueOf(userId));
				userGroupList = apiDAOFactory.getUserGroupDAO().findByCriteria(Order.asc("viewSeq"), Restrictions.eq("users", user));
			}
			
			
			if (userGroupList.size()>0) {
				
				for (UserGroup type : userGroupList) {
					mapData = new LinkedHashMap<>();
					mapData.put("moduleName", type.getMenu().getName().toString());
					mapData.put("moduleOrder", String.valueOf(type.getViewSeq()));
					list.add(mapData);
				}
				mapOutput.put("modules", list);
				
			} else {
				mapOutput.put("Answer", "List Empty");
			}
			
					
					
		} catch (Throwable e1) {
			mapOutput.put("Answer", "Please Input Correctly");
			e1.printStackTrace();
		}
		return mapOutput;
		
	}
	
	public class UsersTest{
	    private Long userId;
	}
	
}
