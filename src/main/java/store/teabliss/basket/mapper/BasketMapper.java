package store.teabliss.basket.mapper;

import org.apache.ibatis.annotations.Mapper;
import store.teabliss.basket.entity.Basket;


import java.util.List;
import java.util.Optional;


@Mapper
public interface BasketMapper {

     List<Basket> getbasket(Long memberId);

     void update(Basket basket);

     boolean delete(Long id,Long memberId);

     void save(Basket basket);

     String search(String name,Long memberId);

     void deleteproduct(String name,Long memberId);
}
