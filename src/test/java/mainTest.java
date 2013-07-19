package test.java;

import java.awt.*;
import java.awt.event.*;
import java.awt.FlowLayout;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import javax.swing.*;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTML.Tag;
import java.net.*;

import main.java.tests;
import main.java.juega1;




public class mainTest {

	
	/**
	 * @param args
	 * @throws Exception 
	 */
	
	@Test
	public void main() throws Exception {
		// TODO Auto-generated method stub

		//for (int i=0; i<args.length; i++) System.out.println(args[i]);
		
		
		String args=String.valueOf(System.getProperty("totest"));
		String url=String.valueOf(System.getProperty("url"));
		String xpath=String.valueOf(System.getProperty("xpath"));
		//remember another system property called batch
		
		
		//System.out.println(args.length);
		main.java.tests test = new main.java.tests();
		main.java.juega1 test2 = new main.java.juega1();
		
		
		
		if(!args.isEmpty()){
			
			if (args.equals("juega1")){
				
				test2.setUp();
			}else{
				String[] options={args,url,xpath};
				System.out.println(args+"     "+url+"      "+xpath);
				test.setUp(options);
			}
				
			}else{
				String[] options=new String[1];
				options[0]="nothing";
				test.setUp(options);
			
		}
		
		
		
		//test.setUp(args);
		//test.setUp(options);
		//test2.setUp();
		//call to tests class and run it.
	}
		
	

}


