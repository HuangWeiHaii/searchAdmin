package com.search.test;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.pagehelper.PageInfo;


/**
 * 使用Spring测试模块提供的测试请求功能，测试请求的正确性
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:applicationContext.xml",
		"file:src/main/webapp/WEB-INF/dispatcherServlet-servlet.xml" })
public class MvcTest {
	// 传入Springmvc的ioc
	@Autowired
	WebApplicationContext context;
	// 虚拟mvc请求，获取到处理结果。
	MockMvc mockMvc;

	@Before
	public void initMokcMvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void testPage1() throws Exception {
		//模拟请求拿到返回值
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/newspage").param("pn", "5")).andReturn();
		//请求成功以后，请求域中会有pageInfo；可以取出pageInfo进行验证
		MockHttpServletRequest request = result.getRequest();
		PageInfo pi = (PageInfo) request.getAttribute("pageInfo");
		System.out.println(pi);
		System.out.println("当前页码："+pi.getPageNum());
		System.out.println("总页码："+pi.getPages());
		System.out.println("总记录数："+pi.getTotal());
		System.out.println("在页面需要连续显示的页码");
	}

	@Test
	public void testPage2() throws Exception {
		//模拟请求拿到返回值
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/newspage").param("pn", "10")).andReturn();
		//请求成功以后，请求域中会有pageInfo；我们可以取出pageInfo进行验证
		MockHttpServletRequest request = result.getRequest();
		PageInfo pi = (PageInfo) request.getAttribute("pageInfo");
		System.out.println(pi);
		System.out.println("当前页码："+pi.getPageNum());
		System.out.println("总页码："+pi.getPages());
		System.out.println("总记录数："+pi.getTotal());
		System.out.println("在页面需要连续显示的页码");
	}


	@Test
	public void testPage3() throws Exception {
		//模拟请求拿到返回值
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/newspage").param("pn", "20")).andReturn();
		//请求成功以后，请求域中会有pageInfo；我们可以取出pageInfo进行验证
		MockHttpServletRequest request = result.getRequest();
		PageInfo pi = (PageInfo) request.getAttribute("pageInfo");
		System.out.println(pi);
		System.out.println("当前页码："+pi.getPageNum());
		System.out.println("总页码："+pi.getPages());
		System.out.println("总记录数："+pi.getTotal());
		System.out.println("在页面需要连续显示的页码");
	}


	@Test
	public void testCheckTitle1() throws Exception {
		//模拟请求拿到返回值
		mockMvc.perform(MockMvcRequestBuilders.get("/checktitle").param("title", "1"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andDo(print());

	}

	@Test
	public void testCheckTitle2() throws Exception {
		//模拟请求拿到返回值
		mockMvc.perform(MockMvcRequestBuilders.get("/checktitle").param("title", "5"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andDo(print());

	}

	@Test
	public void testCheckTitle3() throws Exception {
		//模拟请求拿到返回值
		mockMvc.perform(MockMvcRequestBuilders.get("/checktitle").param("title", "10"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andDo(print());

	}

	@Test
	public void testDeleteNews1() throws Exception {
		//模拟请求拿到返回值
		mockMvc.perform(MockMvcRequestBuilders.get("/news")
				.param("title", "1")
				.param("authors","2")
				.param("source","3"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andDo(print());
	}

	@Test
	public void testDeleteNews2() throws Exception {
		//模拟请求拿到返回值
		mockMvc.perform(MockMvcRequestBuilders.get("/news")
				.param("title", "10")
				.param("authors","20")
				.param("source","30"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andDo(print());
	}


	@Test
	public void testDeleteNews3() throws Exception {
		//模拟请求拿到返回值
		mockMvc.perform(MockMvcRequestBuilders.get("/news")
				.param("title", "5")
				.param("authors","10")
				.param("source","15"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andDo(print());
	}

	@Test
	public void testSaveNews1() throws Exception {
		//模拟请求拿到返回值
		mockMvc.perform(MockMvcRequestBuilders.put("/news")
				.param("title", "1")
				.param("authors","2")
				.param("source","3"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andDo(print());
	}

	@Test
	public void testSaveNews2() throws Exception {
		//模拟请求拿到返回值
		mockMvc.perform(MockMvcRequestBuilders.put("/news")
				.param("title", "10")
				.param("authors","20")
				.param("source","30"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andDo(print());
	}


	@Test
	public void testSaveNews3() throws Exception {
		//模拟请求拿到返回值
		mockMvc.perform(MockMvcRequestBuilders.put("/news")
				.param("title", "5")
				.param("authors","15")
				.param("source","20"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andDo(print());
	}
}
