package com.rgm.monitoring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations={"classpath:applicationContext-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestMySpeedTest
{
	@Autowired
	private TestMySpeed speed;
	
	
	@Test
	public void seeWhatHappens()
	{
		speed.runALoop();
	}
}