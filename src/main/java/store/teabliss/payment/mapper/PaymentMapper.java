package store.teabliss.payment.mapper;

import org.apache.ibatis.annotations.Mapper;
import store.teabliss.payment.entity.*;

@Mapper
public interface PaymentMapper {


   void  saveproduct(DBProduct dbProduct);

   void savepayment(DBPayment payment);
   void saveamount(DBAmount amount);
   void savecustomer(DBCustomer dbCustomer);
   void savecard(DBCard dbCard);


}
