package store.teabliss.basket.mapper;

import org.apache.ibatis.annotations.Mapper;
import store.teabliss.basket.entity.Basket;
import store.teabliss.member.entity.Member;
import store.teabliss.tea.entity.Tea;

import java.util.List;
import java.util.Optional;


@Mapper
public interface BasketMapper {

     List<Basket> getbasket(String email);

     void update(Long id,Basket basket);

     boolean delete(Long id,String email);

     void save(Basket basket);
}
