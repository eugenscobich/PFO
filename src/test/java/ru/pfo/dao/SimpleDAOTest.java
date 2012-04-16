package ru.pfo.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import ru.pfo.dao.impl.VideoNewsDAO;
import ru.pfo.model.Category;
import ru.pfo.model.VideoNews;

@ContextConfiguration(locations = { "classpath:/spring/service-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class SimpleDAOTest {

	@Autowired
	private VideoNewsDAO videoNewsDAO;

	@Test
	@Transactional
	public void testSave() {
		VideoNews videoNews = new VideoNews();
		videoNews.setTitle("fsdfsdfsdf");
		// videoNewsDAO.save(videoNews);
		Category c = new Category();
		c.getVideoNewses().add(videoNews);
		videoNewsDAO.save(c);
		assertNotNull(videoNews.getId());
	}
}
