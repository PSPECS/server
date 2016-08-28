package com.velociteam.pspecs.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.Assert;

public class Test {
	
	@org.junit.Test
	public void test() throws ParseException{
		String date ="01/08/2016-10:05";
		String [] dateParts = date.split("/");
		Assert.assertEquals("ISODate('2016-08-01')", "ISODate('"+dateParts[2].split("-")[0]+"-"+dateParts[1]+"-"+dateParts[0]+"')");
	}

}
