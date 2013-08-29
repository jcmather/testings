package com.rgm.monitoring;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.SharedMetricRegistries;
import com.codahale.metrics.Slf4jReporter;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;


@Configuration
@EnableMetrics(proxyTargetClass=true)
public class MonitoringConfig extends MetricsConfigurerAdapter
{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public MetricRegistry getMetricRegistry()
	{
		return SharedMetricRegistries.getOrCreate("springMetrics");
	}

	@Override
	public void configureReporters(MetricRegistry metricRegistry)
	{
		ConsoleReporter.forRegistry(getMetricRegistry()).outputTo(System.out).build().start(1, TimeUnit.MINUTES);
		Slf4jReporter.forRegistry(getMetricRegistry()).outputTo(logger).build().start(1, TimeUnit.MINUTES);
	}
}
