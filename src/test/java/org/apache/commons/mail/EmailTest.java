package org.apache.commons.mail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.mail.Session;

//import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
//import org.powermock.api.easymock.PowerMock;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;

// PowerMock doesnt work with jdk 17
// powermock/mockito needs these statements:
// reference:https://github.com/powermock/powermock/wiki/Getting-Started
//@RunWith(PowerMockRunner.class)
//@PrepareForTest({Session.class})
public class EmailTest {
	
	// setup
	private EmailConcrete email;
	@Before	
	// establish uniform testing object, easier to implement.
	public void setup() {
		email = new EmailConcrete();
	}
	
// TEST THE FOLLOWING METHODS ##############################################################
	
// todo: Email   addBcc(String... email) - Done ----------------------------------------------------
	
	// add only valid email addresses = passed
	@Test
	public void testAddBcc() throws Exception {
		email.addBcc("bear@bears.com");
		assertEquals(1, email.getBccAddresses().size());
		// assert stored email is retrieved email
		assertEquals("bear@bears.com", email.getBccAddresses().get(0).getAddress());
	}
	
	// pass something that isnt an email - force failure = passed
	@Test(expected = EmailException.class)
	public void testAddBccIncorrect() throws Exception {
		email.addBcc("bear");
	}
	
	// pass blank, isnt an email - force failure = passed
	@Test(expected = EmailException.class)
	public void testAddBccWrong() throws Exception {
		email.addBcc("");
	}
	
	// pass multiple emails = passed
	@Test
	public void testAddBccMulti() throws Exception {
		email.addBcc("bear@bears.com");
		email.addBcc("bunny@bears.com");
		assertEquals(2, email.getBccAddresses().size());
	}

// todo: Email   addCc(String email) - Done -----------------------------------------------------------
	// see EmailConcrete for further notations
	@Test
	public void testAddCc() throws Exception {
		// EmailConcrete email = new EmailConcrete(); -> added setup after i did this test - SMB
		// pass a string argument that gets added to an ArrayList
		email.addCc("bear@bears.com");
		// assert size of array, should have a single indexed item = passed
		assertEquals(1, email.getCcAddresses().size());
	}
	
	// test varargs version (String ... emails) 
	@Test
	public void addCcVarargs() throws Exception {
		// EmailConcrete email = new EmailConcrete();
		// pass more than one email address to addCc() = failed (ends up as (String, name) not (String[])
		// pass more than one email explicitly as an array = passed
		email.addCc(new String[] {"bear@bears.com", "bunny@bears.com"});
		// assert size of array, should have 2 indexed items.
		assertEquals(2, email.getCcAddresses().size());
	}
	
	@Test(expected = EmailException.class)
	public void testAddCcVarargsEmpty() throws Exception {
		// EmailConcrete email = new EmailConcrete();
		// invoke addCc, no passed argument will trigger expected Exception = passed
		email.addCc();
	}

// todo: void    addHeader(String name, String value) - DONE -------------------------------------------
	
	// store header with <String name, String value> pair = passed
	@Test
	public void testAddHeaderPair() throws Exception {
		email.addHeader("Bear", "24");
		Field headersField = Email.class.getDeclaredField("headers");
		headersField.setAccessible(true);		
		Map<String, String> headers = (Map<String, String>)headersField.get(email);
		assertEquals("24", headers.get("Bear"));
	}
	
	// test addHeader with a null field added to key value pair.
	// should get illegalArgumentException per addHeader method declaration = passed
	@Test(expected = IllegalArgumentException.class)
	public void testAddHeaderNameNull() {
		// must have <String, String>, if null in either field get IAE
		email.addHeader(null, "value");
	}
	
	// test addHeader with other field null = passed
	@Test(expected = IllegalArgumentException.class)
	public void testAddHeaderValueNull() {
		email.addHeader("name", null);
	}	
	
// todo: Email   addReplyTo(String email, String name) - Done ------------------------------------------
		
	// add a valid email address to addReplyTo = passed
	@Test 
	public void testAddReplyTo() throws Exception {
		email.addReplyTo("bear@bears.com", "Testy McTesterson");
		// retrieve size of list - valid emails added to reply to
		assertEquals(1, email.getReplyToAddresses().size());
		// use getter method to check stored email is same as one passed to list
		assertEquals("bear@bears.com", email.getReplyToAddresses().get(0).getAddress());
	}
	
	// give addReplyTo an invalid argument = passed 
	@Test(expected = EmailException.class)
	public void testAddReplyToWrong() throws Exception {
		email.addReplyTo("", "Testy McTesterson");
	}
	
	// give add reply to multiple email addresses, populate a list = passed
	@Test
	public void testAddReplyToMulti() throws Exception {
		email.addReplyTo("bear@bears.com");
		email.addReplyTo("bunny@bears.com");
		assertEquals(2, email.getReplyToAddresses().size());
	}
	
	
	
// todo: Email   setFrom(String email) - Done -----------------------------------------------------------
	
	// use valid email address = passed
	@Test
	public void testSetFromAddress() throws Exception {
		email.setFrom("bear@bears.com");
		assertEquals("bear@bears.com", email.getFromAddress().getAddress());
	}
	
	// pass a bad email address = passed
	@Test(expected = EmailException.class)
	public void testSetFromIncorrectly() throws Exception {
		email.setFrom("");
	}
	
	
	
// todo: Date    getSentDate() - Done ------------------------------------------------------------------
	
	// verify that date object is created = passed
	@Test
	public void testGetSentDate() {
		Date clock = email.getSentDate();
		assertNotNull(clock);
	}
	
	// set a date and then retrieve it = passed
	@Test
	public void testGetSentDatePreset() {
		Date expected = new Date(0320);
		email.setSentDate(expected);
		Date result = email.getSentDate();
		assertEquals(expected, result);
	}
	
	
	
// todo: String  getHostName() - Done ------------------------------------------------------------------
	
	// set own host name = passed
	@Test
	public void testGetHostNamePreset() {
		email.setHostName("test.smtp.com");
		assertEquals("test.smtp.com", email.getHostName());
	}
	
	// get host name from Mail session properties = passed
	@Test
	public void testGetHostNameSession() throws Exception {
		Properties settings = new Properties();
		settings.setProperty("mail.smtp.host", "session.smtp.com");
		Session session = Session.getInstance(settings);
		email.setMailSession(session);
		assertEquals("session.smtp.com", email.getHostName());
	}
	
	// assertNull no host set = passed
	@Test
	public void testGetHostNull() {
		assertNull(email.getHostName());
	}
	
	
// todo: void    buildMimeMessage() -Done (71%) -------------------------------------------------------------
		
	// build a mime message, populate all fields = passed
	@Test
	public void testBuildMimeMessage() throws Exception {
		email.setHostName("a.b.com");
		email.setFrom("bunny@bears.com");
		email.addTo("bear@bears.com");
		email.setMsg("Test");
		email.buildMimeMessage();
		assertNotNull(email.getMimeMessage());
	}
	
	// build a mimeMessage twice = passed
	@Test(expected = IllegalStateException.class)
	public void testBuildMimeMessageTwice() throws Exception {
		email.setHostName("a.b.com");
		email.setFrom("bunny@bears.com");
		email.addTo("bear@bears.com");
		email.setMsg("Test");
		email.buildMimeMessage(); // this works
		email.buildMimeMessage(); // this throws Exception
	}
	
	
	// build a mime message, populate most fields, missing From = passed
	@Test(expected = EmailException.class)
	public void testBuildMimeMessageMissingFrom() throws Exception {
		email.setHostName("a.b.com");
		// email.setFrom("bunny@bears.com");
		email.addTo("bear@bears.com");
		email.setMsg("Test");
		email.buildMimeMessage();
		assertNotNull(email.getMimeMessage());
	}
	
	// build a mime message, populate most fields, missing To = passed
	@Test(expected = EmailException.class)
	public void testBuildMimeMessageMissingTo() throws Exception {
		email.setHostName("a.b.com");
		email.setFrom("bunny@bears.com");
		// email.addTo("bear@bears.com");
		email.setMsg("Test");
		email.buildMimeMessage();
		assertNotNull(email.getMimeMessage());
	}
	
	
	// build a mime message, populate most fields, missing hostName = passed
	@Test(expected = EmailException.class)
	public void testBuildMimeMessageMissingHost() throws Exception {
		// email.setHostName("a.b.com");
		email.setFrom("bunny@bears.com");
		email.addTo("bear@bears.com");
		email.setMsg("Test");
		email.buildMimeMessage();
		assertNotNull(email.getMimeMessage());
	}
	
	// test headers = passed
	@Test 
	public void testBuildMimeMessageWithHeaders() throws Exception {
		email.setHostName("a.b.com");
		email.setFrom("bunny@bears.com");
		email.addTo("bear@bears.com");
		email.setMsg("Test");
		email.addHeader("name", "value");
		email.buildMimeMessage();
		assertNotNull(email.getMimeMessage());
	}
	
	// set content (alternative for msg) = passed
	@Test
	public void testBuildMimeMessageWithContent() throws Exception {
		email.setHostName("a.b.com");
		email.setFrom("bunny@bears.com");
		email.addTo("bear@bears.com");
		email.setContent("<h1>Test<h1>", "text/html");
		email.buildMimeMessage();
		assertNotNull(email.getMimeMessage());
	}
	
	// check CC and Bcc = passed 
	@Test
	public void testBuildMimeMessageWithCcAndBcc() throws Exception {
		email.setHostName("a.b.com");
		email.setFrom("bunny@bears.com");
		email.addTo("bear@bears.com");
		email.setMsg("Test");
		email.addBcc("bcc@a.com");
		email.addCc("cc@b.com", "cc@a.com");
		email.buildMimeMessage();
		assertNotNull(email.getMimeMessage());
	}
	
	
// todo: Session getMailSession() - Done (96.6%) -------------------------------------------------------------
	
	// getMailSession() needs to return a valid session
	// mock static Session.getInstance()
	// testing with Mock reference: https://github.com/powermock/powermock/wiki/TestNG
	// PowerMock isnt compatible with jdk 17. Will attempt this without mocking.
	/*
	 * @Test public void testGetMailSession() throws Exception { // statement to
	 * start the mock PowerMock.mockStatic(Session.class);
	 * 
	 * // make a dummy session Session mockSession =
	 * EasyMock.createMock(Session.class);
	 * 
	 * // use a static call to return created fake session
	 * EasyMock.expect(Session.getInstance( EasyMock.anyObject(Properties.class),
	 * EasyMock.anyObject(javax.mail.Authenticator.class))).andReturn(mockSession);
	 * 
	 * PowerMock.replay(Session.class);
	 * 
	 * // test getMailSession at this point Session result = email.getMailSession();
	 * 
	 * // verify with assertions assertNotNull(result); assertEquals(mockSession,
	 * result);
	 * 
	 * PowerMock.verify(Session.class); }
	 */
	
	// test getMailSession directly = passed
	@Test
	public void testGetMailSession() throws Exception {
		email.setHostName("a.b.com");
		Session session = email.getMailSession();
		assertNotNull(session);
	}
	
	// no hostname = passed
	@Test(expected = EmailException.class)
	public void testGetMailSessionNoHostSet() throws Exception {
		email.getMailSession();
	}
	
	// handle authenticator for authenticator != null -> passed
	@Test
	public void testGetMailSessionWithAuthenticator() throws Exception {
		email.setHostName("a.b.com");
		email.setAuthenticator(new DefaultAuthenticator("user", "pass"));
		Session session = email.getMailSession();
		assertNotNull(session);
	}
	
	// check secure socket layer = passed
	@Test
	public void testGetMailSessionSSL() throws Exception {
		email.setHostName("a.b.com");
		email.setSSLOnConnect(true);
		Session session = email.getMailSession();
		assertNotNull(session);
	}
	
	// test enable TLS = passed
	@Test
	public void testGetMailSessionTLS() throws Exception {
		email.setHostName("a.b.com");
		email.setStartTLSEnabled(true);
		email.setSSLCheckServerIdentity(true);
		Session session = email.getMailSession();
		assertNotNull(session);
	}
	
	// check bounce address = passed
	@Test
	public void testGetMailSessionBounceAddress() throws Exception {
		email.setHostName("a.b.com");
		email.setBounceAddress("bear@bears.com");
		Session session = email.getMailSession();
		assertNotNull(session);
	}
	
	// check socket connection timeout > 0 -> passed
	@Test
	public void testGetMailSessionSocketConnectionTimeout() throws Exception {
		email.setHostName("a.b.com");
		email.setSocketConnectionTimeout(5000);
		Session session = email.getMailSession();
		assertNotNull(session);
	}
	
	// check socket connection timeout = 0 -> passed
		@Test
		public void testGetMailSessionSocketConnectionTimeoutAtZero() throws Exception {
			email.setHostName("a.b.com");
			email.setSocketConnectionTimeout(0);
			Session session = email.getMailSession();
			assertNotNull(session);
		}
	
	// check socket timeout > 0 -> passed
	@Test
	public void testGetMailSessionSocketTimeout() throws Exception {
		email.setHostName("a.b.com");
		email.setSocketTimeout(5000);
		Session session = email.getMailSession();
		assertNotNull(session);
	}
	
	// check socket timeout = 0 -> passed
		@Test
		public void testGetMailSessionSocketTimeoutAtZero() throws Exception {
			email.setHostName("a.b.com");
			email.setSocketTimeout(0);
			Session session = email.getMailSession();
			assertNotNull(session);
		}
	
	
	
// todo: int     getSocketConnectionTimeout() - Done ----------------------------------------------------
	
	// check the default value of the socket connection timeout = passed
	@Test
	public void testGetSocketConnectionTimeout() {
		assertEquals(60000, email.getSocketConnectionTimeout());
	}
	
	// assign a timeout value, return assigned value = passed
	@Test
	public void testGetSocketConnectionTimeoutPreset() {
		email.setSocketConnectionTimeout(1000);
		assertEquals(1000, email.getSocketConnectionTimeout());
	}
	
	
	
	
//#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#
	@After
	public void teardown() {
		// destroy email object and free memory
		email = null;
	}
}
