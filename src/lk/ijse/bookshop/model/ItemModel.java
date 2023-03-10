package lk.ijse.bookshop.model;

import lk.ijse.bookshop.db.DBConnection;
import lk.ijse.bookshop.dto.OrderDetailDTO;
import lk.ijse.bookshop.dto.ItemDTO;
import lk.ijse.bookshop.dto.SupplierOrderDetailsDTO;
import lk.ijse.bookshop.dao.SQLUtil;
import lk.ijse.bookshop.view.tm.AdminItemTm;
import lk.ijse.bookshop.view.tm.CashierItemTm;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemModel {
    public static boolean updateStock(ArrayList<OrderDetailDTO> orderDetailDTOS) throws SQLException, ClassNotFoundException {
        for (OrderDetailDTO detail : orderDetailDTOS) {
            if (!updateItem(detail)) {
                return false;
            }
        }
        return true;
    }

    private static boolean updateItem(OrderDetailDTO detail) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getDBConnection().getConnection().prepareStatement("UPDATE item SET QuantityOnHand=QuantityOnHand-? WHERE ItemId=? AND SellingUnitPrice=?");
        stm.setObject(1, detail.getQuantity());
        stm.setObject(2, detail.getItemId());
        stm.setObject(3, detail.getUnitPrice());


        return stm.executeUpdate() > 0;
    }

    public static ArrayList getAllDetails() throws SQLException, ClassNotFoundException {
        String sql="SELECT * FROM item";
        ResultSet resultSet= SQLUtil.execute(sql);
        ArrayList arrayList=new ArrayList();
        while (resultSet.next()){
            if(resultSet.getString(7)==null){
                arrayList.add(new CashierItemTm(resultSet.getString(1), resultSet.getInt(2),
                        resultSet.getString(3),resultSet.getDouble(5),resultSet.getInt(6),
                        0));
            }else{
                String sql2="select Amount from offer where OfferId=? ";
                ResultSet resultSet1= SQLUtil.execute(sql2,resultSet.getString(7));
                if(resultSet1.next()) {
                    arrayList.add(new CashierItemTm(resultSet.getString(1), resultSet.getInt(2),
                            resultSet.getString(3), resultSet.getDouble(5), resultSet.getInt(6),
                            resultSet1.getDouble(1)));
                }
            }

        }
        return arrayList;
    }

    public static boolean updateSupplierStock(ArrayList<SupplierOrderDetailsDTO> supplierOrderDetailsDTOS) throws SQLException, ClassNotFoundException {
        for (SupplierOrderDetailsDTO detail : supplierOrderDetailsDTOS) {
            if (!updateSupplerItem(detail)) {
                return false;
            }
        }
        return true;
    }

    private static boolean updateSupplerItem(SupplierOrderDetailsDTO detail) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getDBConnection().getConnection().prepareStatement("UPDATE item SET QuantityOnHand=QuantityOnHand+? WHERE ItemId=?");
        stm.setObject(1, detail.getQuantity());
        stm.setObject(2, detail.getItemId());

        return stm.executeUpdate() > 0;
    }

    public static ArrayList getAllDetailsForAdminItem() throws SQLException, ClassNotFoundException {
        String sql="SELECT * FROM item";
        ResultSet resultSet= SQLUtil.execute(sql);
        ArrayList arrayList=new ArrayList();
        while (resultSet.next()){
            if(resultSet.getString(7)==null){
                arrayList.add(new AdminItemTm(resultSet.getString(1),resultSet.getInt(2),resultSet.getString(3),
                        resultSet.getDouble(4) ,resultSet.getDouble(5),resultSet.getInt(6),0));
            }else{
                String sql2="select Amount from offer where OfferId=? ";
                ResultSet resultSet1= SQLUtil.execute(sql2,resultSet.getString(7));
                if(resultSet1.next()) {
                    arrayList.add(new AdminItemTm(resultSet.getString(1),resultSet.getInt(2),resultSet.getString(3),
                            resultSet.getDouble(4),resultSet.getDouble(5),resultSet.getInt(6),resultSet1.getDouble(1)));
                }
            }

        }
        return arrayList;
    }

    public static String getBatchNumber() {
return null;
    }

    public static boolean insertItemData(ItemDTO item) throws SQLException, ClassNotFoundException {
        String sql="INSERT INTO item VALUES (?,?,?,?,?,?,?)";
        return SQLUtil.execute(sql,item.getItemId(),item.getBatchNumber(),item.getDescription(),item.getBuyingUnitPrice(),item.getSellingUnitPrice(),
                item.getQuantityOnHand(),item.getOfferId());
    }

    public static String getCurrentItemId() throws SQLException, ClassNotFoundException {
        String sql="SELECT ItemId FROM item ORDER BY ItemId DESC LIMIT 1";
        ResultSet resultSet= SQLUtil.execute(sql);
        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }

    public static boolean deleteItem(String itemId) throws SQLException, ClassNotFoundException {
        String sql="DELETE FROM item WHERE ItemId=? ";
        return SQLUtil.execute(sql,itemId);
    }

    public static boolean updateItem(String itemCode, int batchNumber, String desCriptionText, double buyingPrice, double sellingPrice, int quantity) throws SQLException, ClassNotFoundException {
        String sql="UPDATE item SET Description=?,BuyingUnitPrice=?,SellingUnitPrice=?,QuantityOnHand=? WHERE ItemId=? AND  BatchNumber=?";
        return SQLUtil.execute(sql,desCriptionText,buyingPrice,sellingPrice,quantity,itemCode,batchNumber);
    }
}
