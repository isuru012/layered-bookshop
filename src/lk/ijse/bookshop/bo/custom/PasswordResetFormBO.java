package lk.ijse.bookshop.bo.custom;


/*

   ` Coded By Isuru Dulakshana
   ` Date     1/19/2023 9:10 AM

*/


import java.sql.SQLException;

public interface PasswordResetFormBO extends SuperBO {
    boolean checkUsername(String Username) throws SQLException, ClassNotFoundException;
}