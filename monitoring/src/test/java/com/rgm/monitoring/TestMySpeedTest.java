package com.rgm.monitoring;

import static com.codahale.metrics.MetricRegistry.name;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.codahale.metrics.annotation.Timed;

@ContextConfiguration(locations ={ "classpath:applicationContext-test.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class TestMySpeedTest
{
	@Autowired
	private TestMySpeed speed;

	@Autowired
	private MetricRegistry springMetrics;

	@Test
	@Ignore // figure out why the count is off, is it just because of the polling?
	public void seeWhatHappens()
	{
		Timer timedMethodTimer = forTimedMethod(springMetrics, TestMySpeed.class, "runALoop");
		assertNotNull(timedMethodTimer);
		assertThat(timedMethodTimer.getCount(), is(0L));
		speed.runALoop();
		assertThat(timedMethodTimer.getCount(), is(1L)); // FIXME: coming through as 2
	}

	// ============= Helper methods copied over from ryantenney's github test code
	
	Timer forTimedMethod(MetricRegistry metricRegistry, Class<?> clazz, String methodName)
	{
		Method method = findMethod(clazz, methodName);
		String metricName = forTimedMethod(clazz, method, method.getAnnotation(Timed.class));
		return metricRegistry.getTimers().get(metricName);
	}

	String forTimedMethod(Class<?> klass, Member member, Timed annotation)
	{
		return chooseName(annotation.name(), annotation.absolute(), klass, member);
	}

	String chooseName(String explicitName, boolean absolute, Class<?> klass, Member member,
			String... suffixes)
	{
		if (explicitName != null && !explicitName.isEmpty())
		{
			if (absolute)
			{
				return explicitName;
			}
			return name(klass.getCanonicalName(), explicitName);
		}
		return name(name(klass.getCanonicalName(), member.getName()), suffixes);
	}

	Method findMethod(Class<?> clazz, String methodName)
	{
		List<Method> methodsFound = new ArrayList<>();
		for (Method method : clazz.getDeclaredMethods())
		{
			if (method.getName().equals(methodName))
			{
				methodsFound.add(method);
			}
		}
		if (methodsFound.size() == 1)
		{
			return methodsFound.get(0);
		}
		else
		{
			throw new RuntimeException("No unique method " + methodName + " found on class "
					+ clazz.getName());
		}
	}
}
