package com.velociteam.pspecs.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.Assert;

public class Test {
	
	@org.junit.Test
	public void test() throws ParseException{
		String date ="01/08/2016-10:05";
		Assert.assertEquals("ISODate('2016-08-01')", new SimpleDateFormat("dd/MM/yyyy-hh:mm").parse(date).getTime());
	}

}

