package com.search.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.search.bean.Msg;
import com.search.bean.News;
import com.search.service.NewsService;

@Controller
public class NewsController {
	@Autowired
	private NewsService newService;

	@ResponseBody
	@RequestMapping(value = "/news/{titles}/{authors}/{sources}", method = RequestMethod.DELETE)
	public Msg deleteEmp(@PathVariable("titles") String titles, @PathVariable("authors") String authors,
						 @PathVariable("sources") String sources) {
		String title = titles;
		String author = authors;
		String source = sources;
		System.out.println(title + "," + source + "," + author);
		newService.deleteNews(title, author, source);
		return Msg.success();
	}

	/**
	 * 检查用户名是否可用
	 *
	 * @param empName
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checktitle")
	public Msg checkuser(@RequestParam("title") String title) {
		// 先判断用户名是否是合法的表达式;

		if (title == "") {
			return Msg.fail().add("va_msg", "不能为空");
		}

		// 数据库用户名重复校验
		boolean b = newService.checkUser(title);
		if (b) {
			return Msg.success();
		} else {
			return Msg.fail().add("va_msg", "新闻名不可用");
		}
	}

	@RequestMapping("/news")
	@ResponseBody
	public Msg getEmpsWithJson(@RequestParam(value = "pn", defaultValue = "1") Integer pn) {

		// System.out.println(newService.getNews("平凡的感动-福州大学新闻网", "本站编辑", "福州大学"));
		// System.out.println("----------------------");
		// 这不是一个分页查询
		// 引入PageHelper分页插件
		// 在查询之前只需要调用，传入页码，以及每页的大小
		PageHelper.startPage(pn, 30);
		// startPage后面紧跟的这个查询就是一个分页查询
		List<News> emps = newService.getAll();
		// 使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就行了。
		// 封装了详细的分页信息,包括有我们查询出来的数据，传入连续显示的页数
		PageInfo page = new PageInfo(emps, 6);

		// News news = newService.getNews("平凡的感动-福州大学新闻网", "本站编辑", "福州大学");

		return Msg.success().add("pageInfo", page);
	}

	/**
	 * 新闻保存 1、支持JSR303校验 2、导入Hibernate-Validator
	 *
	 *
	 * @return
	 */
	@RequestMapping(value = "/news", method = RequestMethod.POST)
	@ResponseBody
	public Msg saveEmp(@Valid News news, BindingResult result) {
		if (result.hasErrors()) {
			Map<String, Object> map = new HashMap<String, Object>();
			List<FieldError> errors = result.getFieldErrors();
			System.out.println(news.getAuthor() + "," + news.getTitle());
			for (FieldError fieldError : errors) {
				System.out.println("错误的字段名：" + fieldError.getField());
				System.out.println("错误信息：" + fieldError.getDefaultMessage());
				map.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return Msg.fail().add("errorFields", map);
		} else {
			newService.saveNews(news);
			return Msg.success();
		}

	}

	@ResponseBody
	@RequestMapping(value = "/news/{title}/{author}/{source}", method = RequestMethod.PUT)
	public Msg saveEmp(News news, HttpServletRequest request) {
		newService.updateNews(news);
		return Msg.success();
	}

	@RequestMapping(value = "/news/{title}/{author}/{source}", method = RequestMethod.GET)
	@ResponseBody
	public Msg getEmp(@PathVariable("title") String title, @PathVariable("author") String author,
					  @PathVariable("source") String source) {

		News news = newService.getNews(title, author, source);

		return Msg.success().add("news", news);
	}

	/**
	 * 查询员工数据（分页查询）
	 *
	 * @return
	 */
	@RequestMapping("/newspage")
	public String getEmps(@RequestParam(value = "pn", defaultValue = "1") Integer pn, Model model) {

		PageHelper.startPage(pn, 7);
		List<News> news = newService.getAll();
		PageInfo page = new PageInfo(news, 7);
		model.addAttribute("pageInfo", page);
		return "list";
	}

}
