package com.bizmobiz.bizmoweb.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.vividsolutions.jts.geom.Point;

public class PointSerializer extends StdSerializer<Point> {

	private static final long serialVersionUID = 3989592939684241672L;

	protected PointSerializer(Class<Point> t) {
		super(t);
	}

	@Override
	public void serialize(Point point, JsonGenerator generator, SerializerProvider arg2) throws IOException {
		generator.writeStartObject();
		generator.writeNumberField("latitud", point.getX());
		generator.writeNumberField("longitud", point.getY());
		generator.writeEndObject();
	}

}
