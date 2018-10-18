/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lynnc
 */
public class AuthProviderTest {
    
    public AuthProviderTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getInstance method, of class AuthProvider.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        AuthProvider expResult = null;
        AuthProvider result = AuthProvider.getInstance();
        assertNotNull(result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of addAccount method, of class AuthProvider.
     */
    @Test
    public void testAddAccount() {
        System.out.println("addAccount");

        // Happy path test
        String username = "testGoodUsername";
        String password = "testGoodPassword";
        String email = "testGoodEmail";
        AuthProvider instance = new AuthProvider();
        boolean expResult = true;
        boolean result = instance.addAccount(username, password, email);
        assertEquals(expResult, result);

        // user already exists
        username = "testGoodUsername";
        password = "test";
        email = "test2";
        expResult = false;
        result = instance.addAccount(username, password, email);
        assertEquals(expResult, result);

        // email already exists
        username = "testGoodUsername2";
        password = "test";
        email = "testGoodEmail";
        expResult = false;
        result = instance.addAccount(username, password, email);
        assertEquals(expResult, result);        
        
        // empty parameters tests
        // username empty test
        username = "";
        expResult = false;
        result = instance.addAccount(username, password, email);
        assertEquals(expResult, result);
        // password empty test
        username = "test";
        password = "";
        expResult = false;
        result = instance.addAccount(username, password, email);
        assertEquals(expResult, result);
        // email empty test
        username = "test";
        password = "test";
        email = "";
        expResult = false;
        result = instance.addAccount(username, password, email);
        assertEquals(expResult, result);

        // parameters too long tests
        //user name too long test
        username = "TooLargeTooLargeTooLargeTooLargeTooLargeTooLargeToo";
        expResult = false;
        result = instance.addAccount(username, password, email);
        assertEquals(expResult, result);
        // password too long test
        username = "test";
        password = "TooLargeTooLargeTooLargeTooLargeTooLargeTooLargeToo";
        expResult = false;
        result = instance.addAccount(username, password, email);
        assertEquals(expResult, result);
        // email too long
        username = "test";
        password = "test";
        email = "TooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLarge" +
                "TooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLarge" +
                "TooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLarge" +
                "TooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLarge" +
                "TooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLarge";
        expResult = false;
        result = instance.addAccount(username, password, email);
        assertEquals(expResult, result);
 
        // Delete the account just created
        username = "testGoodUsername";
        expResult = true;
        result = instance.deleteAccount(username);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of accountExists method, of class AuthProvider.
     */
    @Test
    public void testAccountExists() {
        System.out.println("accountExists");
        String username = "test";
        AuthProvider instance = new AuthProvider();
        boolean expResult = true;
        boolean result = instance.accountExists(username);
        assertEquals(expResult, result);

        // test nonexistant user
        username = "nonExistentUser";
        expResult = false;
        result = instance.accountExists(username);
        assertEquals(expResult, result);   
        
        //Username too long test
        username = "TooLargeTooLargeTooLargeTooLargeTooLargeTooLargeToo";
        expResult = false;
        result = instance.accountExists(username);
        assertEquals(expResult, result);
        
        // password empty test
        username = "";
        expResult = false;
        result = instance.accountExists(username);
        assertEquals(expResult, result);
    }

    /**
     * Test of emailInUse method, of class AuthProvider.
     */
    @Test
    public void testEmailInUse() {
        System.out.println("emailInUse");
        // not in use
        String email = "emailNotInUse";
        AuthProvider instance = new AuthProvider();
        boolean expResult = false;
        boolean result = instance.emailInUse(email);
        assertEquals(expResult, result);
        
        // email in use
        email = "test";
        expResult = true;
        result = instance.emailInUse(email);
        assertEquals(expResult, result);
        
        // email too long
        email = "TooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLarge" +
                "TooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLarge" +
                "TooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLarge" +
                "TooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLarge" +
                "TooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLarge";
        expResult = false;
        result = instance.emailInUse(email);
        assertEquals(expResult, result); 
    
        // email empty test
        email = "";
        expResult = false;
        result = instance.emailInUse(email);
        assertEquals(expResult, result);
    }

    /**
     * Test of accountLogin method, of class AuthProvider.
     */
    @Test
    public void testAccountLogin() {
        System.out.println("accountLogin");
        
        // Test unknown username
        String username = "unknownUsername";
        String password = "password";
        AuthProvider instance = new AuthProvider();
        boolean expResult = false;
        boolean result = instance.accountLogin(username, password);
        assertEquals(expResult, result);
 
        // Create an account to test
        username = "testGoodUsername";
        password = "testGoodPassword";
        String email = "testGoodEmail";       
        expResult = true;
        result = instance.addAccount(username, password, email);
        assertEquals(expResult, result);

        // Test existing username with a good password
        username = "testGoodUsername";
        password = "testGoodPassword"; 
        expResult = true;
        result = instance.accountLogin(username, password);
        assertEquals(expResult, result);
        
        // Test existing username with a bad password
        username = "testGoodUsername";
        password = "testBadPassword"; 
        expResult = false;
        result = instance.accountLogin(username, password);
        assertEquals(expResult, result);

        // Test empty username with a good password
        username = "";
        password = "testGoodPassword"; 
        expResult = false;
        result = instance.accountLogin(username, password);
        assertEquals(expResult, result);

        // Test existing username with an empty password
        username = "testGoodUsername";
        password = ""; 
        expResult = false;
        result = instance.accountLogin(username, password);
        assertEquals(expResult, result); 
        
        //Username too long test
        username = "TooLargeTooLargeTooLargeTooLargeTooLargeTooLargeToo";
        password = "testGoodPassword";
        expResult = false;
        result = instance.accountLogin(username, password);
        assertEquals(expResult, result);

                //Username too long test
        username = "testGoodUsername";
        password = "TooLargeTooLargeTooLargeTooLargeTooLargeTooLargeToo";
        expResult = false;
        result = instance.accountLogin(username, password);
        assertEquals(expResult, result);
        
        // Delete the account created for testing
        username = "testGoodUsername";
        expResult = true;
        result = instance.deleteAccount(username);
        assertEquals(expResult, result);
    }

    /**
     * Test of validUsernameLength method, of class AuthProvider.
     */
    @Test
    public void testValidUsernameLength() {
        System.out.println("validUsernameLength");
        //Happy path
        String username = "TestValidUsernameLength";
        AuthProvider instance = new AuthProvider();
        boolean expResult = true;
        boolean result = instance.validUsernameLength(username);
        assertEquals(expResult, result);

        //Username too long test
        username = "TooLargeTooLargeTooLargeTooLargeTooLargeTooLargeToo";
        expResult = false;
        result = instance.validUsernameLength(username);
        assertEquals(expResult, result);
        
        // password empty test
        username = "";
        expResult = false;
        result = instance.validUsernameLength(username);
        assertEquals(expResult, result);
    }

    /**
     * Test of validPasswordLength method, of class AuthProvider.
     */
    @Test
    public void testValidPasswordLength() {
        System.out.println("validPasswordLength");
        // Happy path
        String password = "TestValidPasswordLength";
        AuthProvider instance = new AuthProvider();
        boolean expResult = true;
        boolean result = instance.validPasswordLength(password);
        assertEquals(expResult, result);

        // password too long test
        password = "TooLargeTooLargeTooLargeTooLargeTooLargeTooLargeToo";
        expResult = false;
        result = instance.validPasswordLength(password);
        assertEquals(expResult, result);
        
        // password empty test
        password = "";
        expResult = false;
        result = instance.validPasswordLength(password);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of validEmailLength method, of class AuthProvider.
     */
    @Test
    public void testValidEmailLength() {
        System.out.println("validEmailLength");
        String email = "TestValidEmailLength";
        AuthProvider instance = new AuthProvider();
        boolean expResult = true;
        boolean result = instance.validEmailLength(email);
        assertEquals(expResult, result);
        
        // email too long
        email = "TooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLarge" +
                "TooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLarge" +
                "TooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLarge" +
                "TooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLarge" +
                "TooLargeTooLargeTooLargeTooLargeTooLargeTooLargeTooLarge";
        expResult = false;
        result = instance.validEmailLength(email);
        assertEquals(expResult, result); 
    
        // email empty test
        email = "";
        expResult = false;
        result = instance.validEmailLength(email);
        assertEquals(expResult, result);


    }

    /**
     * Test of deleteAccount method, of class AuthProvider.
     */
    @Test
    public void testDeleteAccount() {
        System.out.println("deleteAccount");
        //test deleting name not in data base
        String username = "testDeleteUsername";
        AuthProvider instance = new AuthProvider();
        boolean expResult = false;
        boolean result = instance.deleteAccount(username);
        assertEquals(expResult, result);
        
        // Add name to database
        username = "testDeleteUsername";
        String password = "test";
        String email = "test2";
        expResult = true;
        result = instance.addAccount(username, password, email);
        assertEquals(expResult, result);    
 
        //test deleting name in data base
        username = "testDeleteUsername";
        expResult = true;
        result = instance.deleteAccount(username);
        assertEquals(expResult, result);

        // username empty test
        username = "";
        expResult = false;
        result = instance.deleteAccount(username);
        assertEquals(expResult, result);

        //user name too long test
        username = "TooLargeTooLargeTooLargeTooLargeTooLargeTooLargeToo";
        expResult = false;
        result = instance.deleteAccount(username);
        assertEquals(expResult, result);
        
        // No account for username
        username = "testNoAccount";
        expResult = false;
        result = instance.deleteAccount(username);
        assertEquals(expResult, result);
    }
    
}
