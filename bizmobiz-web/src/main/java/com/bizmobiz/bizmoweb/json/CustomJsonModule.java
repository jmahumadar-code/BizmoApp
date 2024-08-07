package com.bizmobiz.bizmoweb.json;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.vividsolutions.jts.geom.Point;

@Service
public class CustomJsonModule extends SimpleModule {

	private static final long serialVersionUID = -3870318763947783670L;

	public CustomJsonModule() {
		this.addSerializer(new PointSerializer(Point.class));
		this.addSerializer(new DateSerializer(Date.class));
	}
}
