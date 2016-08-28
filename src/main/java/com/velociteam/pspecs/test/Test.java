package com.velociteam.pspecs.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.xml.bind.DatatypeConverter;

import junit.framework.Assert;

public class Test {
	
	@org.junit.Test
	public void test() throws ParseException{
		Assert.assertEquals("01/08/2016-10:05", new SimpleDateFormat("dd/MM/yyyy-hh:mm").parse("01/08/2016-10:05").getTime());
	}
	
	@org.junit.Test
	public void test1() throws ParseException{
		Assert.assertEquals("01/08/2016-10:05", DatatypeConverter.parseDate("01/08/2016-10:05"));
	}
}
