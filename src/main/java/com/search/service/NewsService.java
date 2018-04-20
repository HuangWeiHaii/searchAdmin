package com.search.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.search.bean.News;
import com.search.bean.NewsExample;
import com.search.bean.NewsKey;
import com.search.bean.NewsExample.Criteria;
import com.search.dao.NewsMapper;

@Service
public class NewsService {
	
	@Autowired
	private NewsMapper newsMapper;
	
	
	public List<News> getAll() {
		// TODO Auto-generated method stub
		return newsMapper.selectByExample(null);
	}
	public void saveNews(News news) {
		// TODO Auto-generated method stub
		newsMapper.insertSelective(news);
	}
	
	/**
	 * �����û����Ƿ����
	 * 
	 * @param empName
	 * @return  true������ǰ��������   fasle��������
	 */
	public boolean checkUser(String title) {
		// TODO Auto-generated method stub
		NewsExample example = new NewsExample();
		Criteria criteria = example.createCriteria();
		criteria.andTitleEqualTo(title);
		long count = newsMapper.countByExample(example);
		return count == 0;
	}
		
	/**
	 * ɾ��
	 * @param id
	 */
	public void deleteNews(String title,String author,String source) {
		// TODO Auto-generated method stub
	
		newsMapper.deleteByPrimaryKey(new NewsKey(title,author,source));
	}


	
	/**
	 * ���Ÿ���
	 * @param news
	 */
	public void updateNews(News news) {
		// TODO Auto-generated method stub
		System.out.println(news);
		newsMapper.updateByPrimaryKey(news);		
		newsMapper.updateByPrimaryKeySelective(news);
	}
	
	/**
	 * ����title��ѯ����
	 * @param title
	 * @return
	 */
	public News getNews(String title,String author,String source) {
		// TODO Auto-generated method stub
		NewsExample newsExample = new NewsExample();
		Criteria criteria = newsExample.createCriteria();
		criteria.andSourceEqualTo("���ݴ�ѧ").andAuthorEqualTo("��վ�༭");
	    System.out.println(newsMapper.selectByExample(newsExample));
	    News news = new News();
	    news.setTitle(UUID.randomUUID().toString().substring(0, 6));
	    news.setAuthor(UUID.randomUUID().toString().substring(0, 6));
	    news.setContent(UUID.randomUUID().toString().substring(0, 6));
	    news.setImgurl("n");
	    news.setTime(UUID.randomUUID().toString().substring(0, 6));
	    news.setUrl(UUID.randomUUID().toString().substring(0, 6));
	    news.setSource(UUID.randomUUID().toString().substring(0, 6));
	    newsMapper.updateByExample(news, newsExample);
		//News news = newsMapper.selectByPrimaryKey(new NewsKey(title,author,source));
		return null;
	
	}
	
	
	


}
