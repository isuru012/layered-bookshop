package lk.ijse.bookshop.bo.custom;


/*

   ` Coded By Isuru Dulakshana
   ` Date     1/19/2023 9:11 AM

*/


import java.sql.SQLException;

public interface TrueResetPasswordBO  extends SuperBO{
    boolean passwordReset(String username, String password) throws SQLException, ClassNotFoundException;
}