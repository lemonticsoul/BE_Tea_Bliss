package store.teabliss.basket.mapper;

import org.apache.ibatis.annotations.Mapper;
import store.teabliss.basket.entity.Basket;
import store.teabliss.tea.entity.Tea;

import java.util.List;


@Mapper
public interface BasketMapper {

     List<Basket> getbasket();

     void update(Long productId,String quality,String type);

     boolean delete(Long productid);

     void save(Basket basket);
}
