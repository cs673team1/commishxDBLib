/**
* <h1>AuthProvider!</h1>
* The AuthProvider class implements the methods to gain access to the commishx
* application. It provides login and account creation related capabilities.
*
* @author  Lynn Cistulli
* @version 1.0
* @since   2018-10-08
*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.QueryTool;
/**
 *
 * @author lynnc
 */
public class AuthProvider {
    private final String driver = "org.postgresql.Driver";
    private final String url = "jdbc:postgresql://localhost/commishx";
    private final String user = "postgres";
    private final String dbpassword = "postgres";

    static Connection con = null;
    final Object lock = new Object();

    // Singleton
    protected static final AuthProvider _INSTANCE = new AuthProvider();
    /**
     * This method returns an instance of AuthProvider 
     * @return This returns an instance of the AuthProvider
     */
    public static AuthProvider getInstance() { return _INSTANCE; }

    protected AuthProvider() {
        try {
            if ( con == null ) {
//                System.setProperty("derby.database.forceDatabaseLock", "true");
                Class.forName( driver ).newInstance();
                con = DriverManager.getConnection( url, user, dbpassword );
            }
        }
        catch ( Exception e ) {
                e.printStackTrace();
                System.exit(0);
        }

        if ( con != null )
            System.out.println("AuthProvider connected to the database");
        else
            System.out.println("AuthProvider NOT connected to the database");
    } 
    
    
     private boolean execQuery(String queryStr) {
        QueryTool query = null;
        try {
            query = new QueryTool(con);
            query.setQuery( queryStr );
            query.exec();
        }
        catch ( Exception e ) {
            e.printStackTrace();
            return false;
        }
        finally {
            query.close();
        }
        return true;
    }

  
    /**
     * This method adds an account to the account table. 
     * Exception is printed if username or email already exist or if password is NULL.
     * @param username This is the user's login name
     * @param password This is the user's password
     * @param email This is the user's email address
     * @return This returns true if an account is created for the user or false if an account can not be created
     */
    public boolean addAccount (String username, String password, String email) {
    	QueryTool query = null;
        boolean successful = false;
        if ((validUsernameLength(username)) && (validPasswordLength(password)) && 
                (validEmailLength(email)) && (!accountExists(username)) && (!emailInUse(email)))  {
            try {
                LocalDateTime created_on = LocalDateTime.now();
                LocalDateTime last_login = LocalDateTime.now();
                query = new QueryTool(con);
                String req = "insert into commishx.account (user_id, username, password, email, created_on, last_login)" +
                         " values (DEFAULT," + "'" + username + "','" + password + "','" + email + 
                         "',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);";

                PreparedStatement ps = query.setQuery( req );
                ResultSet rs = query.exec();
                successful = true;
                ps.close();
                //rs.close();
            }
            catch (SQLException ex) {
                Logger lgr = Logger.getLogger(AuthProvider.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);
            }
            catch ( Exception e ) {
                Logger lgr = Logger.getLogger(AuthProvider.class.getName());
                lgr.log(Level.SEVERE, e.getMessage(), e);
            }
        }
        return successful;
}

    /**
     * Check to see if an account exists for this username
     * @param username This is the user's login name
     * @return This returns true if the account exists and false if does not exist
     */
    public boolean accountExists(String username) {
    	boolean exists = false;
        if (validUsernameLength(username)) {
            try {
            
                QueryTool query = new QueryTool(con);
                String req = "select * from commishx.account where username = " + "'" + username + "';";
                PreparedStatement ps = query.setQuery( req );
                ResultSet rs = query.exec();
                if (rs.next()) exists = true;
                ps.close();
                //rs.close();
                }
            catch (SQLException ex) {
                Logger lgr = Logger.getLogger(AuthProvider.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);
            }
            catch ( Exception e ) {
                Logger lgr = Logger.getLogger(AuthProvider.class.getName());
                lgr.log(Level.SEVERE, e.getMessage(), e);
            }
        }
        return exists;
    }
    
   /**
     * Check to see if an email is in use
     * @param email This is the email in question
     * @return This returns true if the email is in use
     */
    public boolean emailInUse(String email) {
    	boolean exists = false;
        if (validEmailLength(email)) {
            try {
            
                QueryTool query = new QueryTool(con);
                String req = "select * from commishx.account where email = " + "'" + email + "';";
                PreparedStatement ps = query.setQuery( req );
                ResultSet rs = query.exec();
                if (rs.next()) exists = true;
                ps.close();
                //rs.close();
                }
            catch (SQLException ex) {
                Logger lgr = Logger.getLogger(AuthProvider.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);
            }
            catch ( Exception e ) {
                Logger lgr = Logger.getLogger(AuthProvider.class.getName());
                lgr.log(Level.SEVERE, e.getMessage(), e);
            }
        }
        return exists;
    }
    
    /**
     * Attempt to login for the user and password provided
     * @param username This is the user's login name
     * @param password This is the user's password
     * @return This returns true if login succeeds and false if the login fails
     */
    public boolean accountLogin(String username, String password) {
    	boolean exists = false;
        if ((validUsernameLength(username)) && (validPasswordLength(password))) {
            try {
            
                QueryTool query = new QueryTool(con);
                String req = "select * from commishx.account where username = " + 
                       "'" + username + "' and password = " + "'" + password + "';";
                PreparedStatement ps = query.setQuery( req );
                ResultSet rs = query.exec();
                if (rs.next()) exists = true;
                ps.close();
                //rs.close();
                }
            catch (SQLException ex) {
                Logger lgr = Logger.getLogger(AuthProvider.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);
            }
            catch ( Exception e ) {
                Logger lgr = Logger.getLogger(AuthProvider.class.getName());
                lgr.log(Level.SEVERE, e.getMessage(), e);
            }
        }
        return exists;
    }   

    /**
     * Check to see if the user name length is valid
     * @param username This is the username in question
     * @return This returns true if the username length is valid
     */
    public boolean validUsernameLength(String username) {
    	boolean valid = false;
        if((!"".equals(username)) && (username.length() <= 50)) {
            valid = true;
        }
        return valid;
    }

    /**
     * Check to see if the password length is valid
     * @param password This is the username in question
     * @return This returns true if the password length is valid
     */
    public boolean validPasswordLength(String password) {
    	boolean valid = true;
        if(("".equals(password)) || (password.length() > 50)) {
            valid = false;
        }
        return valid;
    }
    
    /**
     * Check to see if the email length is valid
     * @param email This is the username in question
     * @return This returns true if the email length is valid
     */
    public boolean validEmailLength(String email) {
    	boolean valid = true;
        if(("".equals(email)) || (email.length() > 355)) {
            valid = false;
        }
        return valid;
    }
   
    /**
     * Delete an account
     * @param username This is the username in question
     * @return This returns true if account is deleted
     */
    public boolean deleteAccount (String username) {
    	QueryTool query = null;
        boolean successful = false;
        if (accountExists(username)) {
            if (validUsernameLength(username))  {
                try {
                    query = new QueryTool(con);
                    String req = "delete from commishx.account where username = " + 
                       "'" + username + "';";

                    PreparedStatement ps = query.setQuery( req );
                    ResultSet rs = query.exec();
                    ps.close();
                    //rs.close();
                }
                catch (SQLException ex) {
                    Logger lgr = Logger.getLogger(AuthProvider.class.getName());
                    lgr.log(Level.SEVERE, ex.getMessage(), ex);
                }
                catch ( Exception e ) {
                    Logger lgr = Logger.getLogger(AuthProvider.class.getName());
                    lgr.log(Level.SEVERE, e.getMessage(), e);
                }
                if (accountExists(username) == false) {
                    successful = true;
                }     
            }
        }
        return successful;
    }
}
