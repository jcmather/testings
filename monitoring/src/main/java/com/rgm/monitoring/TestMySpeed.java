package com.rgm.monitoring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codahale.metrics.annotation.Timed;


@Controller
public class TestMySpeed
{
	@Timed
	@RequestMapping(value="/test", method=RequestMethod.GET)
	public void runALoop()
	{
		for (int i = 0; i < 500; i++)
		{
			System.out.println("Counting " + i);
		}
	}
}
