package main.java;

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

public class tests {
  		
  static String separator="<p>\n------------------------------------------------------------------------------------------</p>\n\n";
  static String result="";
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  
  
  static Connection con = null;
	static String servername="db4free.net";
	static String username="hellpine";
	static String db="firsttry";
	static String pass="111111";
	public static ResultSet rs=null;
	public static ResultSet ls=null;
	public static ResultSet ss=null;
	public static ResultSet ns=null;
	
	public static Statement stat=null;
	public static Statement stat2=null;
	public static Statement stat3=null;
	public static Statement stat4=null;
	public int total=0;
	public int failed=0;
	
	
	public void readdatabase() throws Exception {
		
		String batchid; 
		String tkind;
		String tid;
		
		File file=new File("report1.htm");
		File file2=new File("repor.txt");
		file.delete();
		file2.delete();
		
		try{
			
			
			Class.forName("com.mysql.jdbc.Driver"); //load driver for Mysql
			
			
			con=DriverManager.getConnection("jdbc:mysql://"+servername+"/"+db, username, pass); //establish teh connection to the database 
			}catch(ClassNotFoundException e){
				System.out.println("Class Not Found: "+e.getMessage());
						
			}catch(SQLException e){
				System.out.println(e.getMessage());
			}	

		stat=con.createStatement(); //declare statements variables
		stat2= con.createStatement();
		
		
		rs= stat.executeQuery("select * from gotest"); //execute sql query
						
		rs.first();
		batchid=String.valueOf(System.getProperty("batch"));
		if(batchid.equals("null")){
		batchid=(rs.getString("batchid"));} //read from recordset
		System.out.println(batchid);
		//System.out.println(rs.getString("batchid"));
		//System.out.println(batchid);
		//rs.close();
		stat.clearBatch();
		rs= stat.executeQuery("select * from batch where batchid='"+batchid+"'");
		//System.out.println(rs.getString("testid"));
		rs.first();
		baseUrl=(rs.getString("url"));
		//System.out.println(rs.getString("url"));
		driver = new FirefoxDriver();
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	    driver.get(baseUrl);
		driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
		
		//System.out.println(rs.getRow());
		rs.last();
		int n,s;
		n=rs.getRow();
		s=0;
		rs.beforeFirst();
		
		FileWriter write = new FileWriter(file,true);
		String header="<p><FONT COLOR="+(char)34+"black"+(char)34+">\n------------------------------------------------------------------------------------------</p>\n\n<strong>Report for |||" +baseUrl+"||| </FONT></strong></p>\n</body>\n</html>\n";
		write.write(header);
		
		while(s != n){
			
			if (rs.next()){
			//System.out.println(rs.getRow());
			//System.out.println(rs.getString("testid"));
			stat2.clearBatch();
			ls=stat2.executeQuery("select * from tests where testid='"+rs.getString("testid")+"'");
					
			ls.first();
			//System.out.println(ls.getString("testkind"));
			
				
			//System.out.println(ls.getString("testkind"));
			s=s+1;
			if(ls.getString("testkind").equals("single")){
				//System.out.println(ls.getString("testkind"));	
				single(ls.getString("testid"));
			
			}
			//}
			
			}}
		
		
		
		
		//write.write(header);
    	//write.write("<p>"+result+"<p>");
    	//write.write(footer);
    	write.close();
		ls.close();
		rs.close();
		con.close();
		Desktop.getDesktop().open(file);
		//Desktop.getDesktop().open(file2);
		driver.close();
		driver.quit();
			
		}
		
		
		
	//}
  
	public void single(String testid) throws Exception{
		
		result="";
		
		stat3= con.createStatement();
		stat4= con.createStatement();
		
		//System.out.println(testid);
		//System.out.println(ls.getString("testkind"));
		String cus=""; 
		boolean success = true;
		
			try{
			
			
			Class.forName("com.mysql.jdbc.Driver");
			
			
			con=DriverManager.getConnection("jdbc:mysql://"+servername+"/"+db, username, pass); 
			}catch(ClassNotFoundException e){
				System.out.println("Class Not Found: "+e.getMessage());
						
			}catch(SQLException e){
				System.out.println(e.getMessage());
			}	

		//System.out.println(ls.getString("testkind"));
		ss= stat3.executeQuery("select * from linktofind where testid='"+testid+"'");
		ss.last();
		//System.out.println(ss.getRow());
		
	    // Looking for a Contact Us Link
	    
	    //System.out.println("result="+result);
		result=result+"<p><p><strong><h2>"+ss.getString("testid")+" TEST</h2></strong><p><p>";
		result=result+separator;
		ss.beforeFirst();
		int find=0;
		int nolink=0;
		while(ss.next()){
		
		//System.out.println(ss.getString("tofind"));
			try {
			success=true;
			cus=ss.getString("tofind");
			driver.findElement(By.linkText(cus));
	    } catch (NoSuchElementException e1){
	    		success=false;					//Control different spelling for Contact Us Link
	    		
	    		if(ss.isLast() & nolink<=0){
	    		result=(result + "<p><FONT COLOR="+(char)34+"red"+(char)34+">"+ss.getString("tofind")+" Not Finded</FONT><p>");} 
	       		//If no Contact Us 
	    	
	    } finally{
	    	
	    	if (success & find==0){
	    		//If a Contact Us finded
	    		nolink=1;
	    		find=1;
	    		result=(result + "<p><strong><h3>"+ss.getString("tofind")+"</strong><FONT COLOR="+(char)34+"green"+(char)34+">"+" Present</FONT></h3><p>");
	    		driver.findElement(By.linkText(cus)).click(); //Click on it
	    		System.out.println("Link finded");
	    		try {
					Thread.sleep(3000);
				} catch (InterruptedException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
	    		
	    		//String source="";
	    		//URL site = new URL(driver.getCurrentUrl());
		    	//BufferedReader in = new BufferedReader(
		    	  //         new InputStreamReader(
		    	    //        site.openStream()));

		    	//String inputLine;

		    	//while ((inputLine = in.readLine()) != null)
		    	  //  source= source + inputLine;

		    //	in.close();

		    	
		    	//System.out.println("articlecontainer");
	    		String source="";
	    		ls= stat4.executeQuery("select * from tofindin where testid='"+testid+"'");
	    		ls.beforeFirst();
	    		String rxpath="";
	    		String xpath="";
	    		boolean success2=false;
	    		
	    		while(ls.next()){
	    			
	    			System.out.println("vamos");
	    			try {
	    				success2=true;
	    				rxpath=ls.getString("xpath");
	    				rxpath=rxpath.replaceAll("¬","'");
	    				System.out.println(rxpath);
	    				driver.findElement(By.xpath(rxpath));
	    		    } catch (NoSuchElementException e1){
	    		    		success2=false;			//Control different spelling for Contact Us Link
    			    		    	
	    		    }
	    		    
	    		    	if (success2){
	    		    	
	    		    		xpath=rxpath;}}
	    		
	    		    		try{
	    			    		
	    		//WebElement container= driver.findElement(By.className("articlecontainer"));
	    		
	    		//List<WebElement> elements = driver.findElements(By.xpath("//div[contains(@class,'articlecontainer') or contains(@class,'content') or contains(@id,'content')]"));
	    			System.out.println(xpath);
	    		    List<WebElement> elements = driver.findElements(By.xpath(xpath));
	    		   
	    		   if(elements.size()>0){
	    			   
	    			   //System.out.print(elements.size()+"\n");
	    			   int i=0;
	    			   
	    			  
	    			   for (WebElement container: elements){ 
	    				   if(! container.getText().equals("") & ! container.getText().equals(" ")){
	    					   source = (String)((JavascriptExecutor)driver).executeScript("return arguments[0].innerHTML;",container);
	    					   //System.out.println(source);
	    				   }}}
	    			   
	    		   }catch(Exception e){ System.out.println(e);
	    		   }finally{;}
	    		   
	    		   
	    			   //System.out.println(source);  
	    					
	    			   
	    		 
	    		//source = (String)((JavascriptExecutor)driver).executeScript("return arguments[0].innerHTML;",container);}catch(Exception e){ System.out.println(e);}finally{
		    	//System.out.println(source);
	    		
	    		
	    //	try{
	    		
	    		//File file=new File("texto.txt");
		    	//String header="<html>\n<head>\n<title>Report</title>\n</head>\n<body>\n<h1>\nReports</h1>\n<p>\n-----------------------------------------------------------------------------------------</p>\n";
		    	//String footer="<p>\n------------------------------------------------------------------------------------------</p>\n\n<strong>Report End</strong></p>\n</body>\n</html>\n";
		    	//FileWriter write = new FileWriter(file,true);
		    	//write.write(header);
		    	//write.write(ls.getString("tofind"));
		    	//write.write(footer);
		    	//write.close();
		    	
		   // }catch(Exception e){
		    	
		    //	 JOptionPane.showMessageDialog(null,
		    	//  		    "File Error",
		    	  //		    "Error",
		    	  	//	    JOptionPane.ERROR_MESSAGE );
		   // }
	    		
	    		xpath=xpath.replaceAll("'","¬");
	    		ns= stat4.executeQuery("select * from tofindin where testid='"+testid+"' and xpath='" +xpath+"'");
	    		while(ns.next()){
	    		
	    			String text;
	    			String[] split;
	    			String[] nfsplit;
	    			text=ns.getString("tofind");
	    			total=text.length();
	    			failed=0;
	    			
	    			System.out.println("Total------------------" + total + "-----------------");
	    			
	    			text=text.replaceAll("\\s+", " ");
	    			split=text.split("ññ");
	    			//System.out.println(text);	    			 
	    			//System.out.println(split.length);
	    			//System.out.println(split[0]);
	    			
	    			int len=split.length;
	    			int nflen=0;
	    			int textpos = 0;
	    			int nfpos=0;
	    			
	    			String found="";
	    			String nfound="";
	    			//String page=driver.findElement(By.tagName("body")).getText().toLowerCase();
	    			//String page=driver.getPageSource();
	    			while (textpos!=len){
	    			
	    				if(split[textpos]!=""){
	    					
	    					split[textpos]=split[textpos].replaceAll("¬","'");
	    					//split[textpos]=split[textpos].replaceAll('(', ' ');
	    					//split[textpos]=split[textpos].replaceAll(")", "");
	    					//System.out.println(split[textpos]);
	    					//split[textpos]=split[textpos].replaceAll("\r", " ")+" ";
	    					//split[textpos]=split[textpos].replaceAll("\""," ")+" ";
	    	    			
	    					
	    					if(source.contains(split[textpos])){
	    							
	    						found=found+split[textpos]+" ";
	    						
	    						
	    					}else{
	    						
	    						//source=source.replaceAll("\\W"," ");
	    						//System.out.println(source);
	    						split[textpos]=split[textpos].replaceAll("\\W", " ");
	    						nfsplit=split[textpos].split(" ");
	    							    						
	    						nflen=nfsplit.length;
	    						
	    						 						
	    						if (nflen>1){
	    						
	    							
	    							nfpos=0;
	    						while (nfpos!=nflen){
	    							
	    							if(nfsplit[nfpos]!=" "){
	    							
	    								
	    								//System.out.println(nfsplit[nfpos]+"\n--------\n");
	    								//System.out.println(nfsplit[nfpos].length());
						
	    								
	    								//nfsplit[nfpos]=nfsplit[nfpos].replaceAll(" ","");
	    								if(source.contains(nfsplit[nfpos])){
	    		    						found=found+nfsplit[nfpos]+" ";
	    		    						//System.out.println("Matches");
	    								}else{
	    									nfound=nfound+nfsplit[nfpos]+" ";
	    									failed=failed+nfsplit[nfpos].length();
	    									//System.out.println("Failed------------------" + failed + "-----------------");
	    									System.out.println(nfsplit[nfpos]+" not found\n");
	    									}}
	    							
	    							nfpos=nfpos+1;
	    							//System.out.println(found);
	    							//System.out.println(nfound);
	    						}
	    			
	    					}
	    				}			
	    					textpos=textpos+1;
	    				}}
	    			
	    			
	    			float percent= (100-((failed*100.0f)/total));
	    			System.out.println(total + "-------"+ failed + "---------"+ percent);
	    			//if (found!=""){ result=result+"<p><FONT COLOR="+(char)34+"blue"+(char)34+">"+found+"<FONT COLOR="+(char)34+"green"+(char)34+">"+percent+"% present</FONT><p>";}
	    			if (found!=""){ result=result+"<p><FONT COLOR="+(char)34+"green"+(char)34+">"+percent+"% present</FONT><p>";}
	    			if (nfound!=""){ result=result+"<p><h1>Missing Stuff</h1><p><p><h3><FONT COLOR="+(char)34+"brown"+(char)34+">"+nfound+"<FONT COLOR="+(char)34+"red"+(char)34+">"+(100-percent)+"% absent</FONT><p><p>Visit <a href="+(char)34+driver.getCurrentUrl()+(char)34+" target="+(char)34+"_blank" + (char)34 + ">"+driver.getCurrentUrl()+"</a> To check manually<FONT COLOR="+(char)34+"black"+(char)34+">";}
	    			
	    		//System.out.println(result);	
	    		} 	result=result+separator;
	    		
	    		try{
	    		
	    		File file=new File("report1.htm");
	    		System.out.println(result);
	    		//String header="<html>\n<head>\n<title>Report</title>\n</head>\n<body>\n<h1>\nReports</h1>\n<p>\n-----------------------------------------------------------------------------------------</p>\n";
		    	
		    	//WebElement page=driver.getPageSource();
		    	FileWriter write = new FileWriter(file,true);
		    	//write.write(header);
		    	write.write("<p>"+result+"<p>");
		    	//write.write(driver.getPageSource().toString());
		    	//write.write(driver.findElement(By.tagName("body")).getText().toLowerCase());
		    	//write.write(footer);
		    	
		    //	URL site = new URL(driver.getCurrentUrl());
		    //	BufferedReader in = new BufferedReader(
		    //	         new InputStreamReader(
		    //	        site.openStream()));

		   // String inputLine;

		   // 	while ((inputLine = in.readLine()) != null)
		  //  	    write.write(inputLine);

		  // 	in.close();
		    	
		    	File file2=new File("repor.txt");
		    	
		    	
		    	//WebElement page=driver.getPageSource();
		    	FileWriter write2 = new FileWriter(file2,true);
		    	
		    	//WebElement container= driver.findElement(By.cssSelector("div[class='articlecontainer']"));
		    	//String contsource = (String)((JavascriptExecutor)driver).executeScript("return arguments[0].innerHTML;",container);
		    	//write2.write(contsource);
		    
		    	write.close();
		    	write2.close();
		    	
		    	
		    	
		    }catch(Exception e){
		    	
		    	 JOptionPane.showMessageDialog(null,
		    	  		    e,
		    	  		    "Error",
		    	  		    JOptionPane.ERROR_MESSAGE );
		    }}}}
	    		
	    		
	
		}
		  
	    		
	    		//System.out.println(result);
	    		
	    	
	    
	    //JUnitCore.main();
	    //result=result+separator;
	    //test();
		
		
	
	
	@Before
	public void setUp(String[] option) throws Exception {
	//public void setUp() throws Exception {
		//String[] option={"nothing","hola"};
		//System.out.println(option.length);
		//for(int i=0;i<option.length;i++){ System.out.println(i+"  "+option[i]);}
		
		if(option[0].equals("getcode")){
		
		driver = new FirefoxDriver();
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	    driver.get(option[1]);
		driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
		
		File file2=new File("repor.txt");
		file2.delete();
    	
    	
    	//WebElement page=driver.getPageSource();
    	FileWriter write2 = new FileWriter(file2,true);
    	String contsource = "";
    	try{
    	    	
    	//WebElement container= driver.findElement(By.cssSelector("div[class='articlecontainer']"));
    	String option1="";
    	if(option[2].equals("")){option1="//div[contains(@class,'articlecontainer') or contains(@class,'content') or contains(@id,'content')]";
    		
    	}else{
    		
    		option1=option[2];
    	}
    	
    	List<WebElement> elements = driver.findElements(By.xpath(option1));
		   
    		if(elements.size()>0){
			  
			   for (WebElement container: elements){ 
				   if(! container.getText().equals("") & ! container.getText().equals(" ")){
					   contsource = (String)((JavascriptExecutor)driver).executeScript("return arguments[0].innerHTML;",container);
					   //System.out.println(source);
				   }}}
			   
		   }catch(Exception e){ System.out.println(e);
		   }finally{
    	    	
		   		write2.write(contsource);
    	    	driver.close();	
    	    	write2.close();}
    	
    	Desktop.getDesktop().open(file2);
    		
    	driver.close();
    	driver.quit();
    	System.exit(0);
		
		
	}
		//System.out.println("antes readdatabase");
		readdatabase();
	//baseUrl= JOptionPane.showInputDialog(null,"Please write URL to test");
	//driver = new FirefoxDriver();
    //driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    //driver.get(baseUrl);
	//driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
	test();  
    //cus(result);
  }
  
	
	
  @Test
   
   public void test() throws Exception {
	  
  }
	  	  
	  
  }
