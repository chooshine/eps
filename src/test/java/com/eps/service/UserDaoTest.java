package com.eps.service;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.eps.dao.Page;
import com.eps.mybatis.auto.UserMapper;
import com.eps.mybatis.auto.entity.User;
import com.eps.mybatis.auto.entity.UserExample;
import com.eps.service.user.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"/applicationContext.xml"})
public class UserDaoTest {
	
//	@Autowired
	private UserService service;
//	@Autowired
	private UserMapper user;
//	@Test
	public void testGetuser(){
		
//		User user = service.getUserByUserName("hejunwei");
//		user.setLastVisit(new Date());
//		user.setIp("127.0.0.1");
//		Gson gson = new GsonBuilder().create();
//		System.out.println(gson.toJson(user));
//		User user1 = gson.fromJson(gson.toJson(user), User.class);
//		System.out.println(user1);
	}
//	@Test
	public void case1(){
		UserExample example = new UserExample();
		
		example.setLimitStart(0);
		example.setLimitSize(10);
		int total = user.countByExample(example);
		List<User> list = user.selectByExample(example);
		Page<User> page = new Page<User>(0, total, 10, list);
		Assert.isTrue(page.getCurrentPageNo() == 1);
		Assert.isTrue(list.size() == 10);
	}
}
