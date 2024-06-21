package store.teabliss.basket.mapper;

import org.apache.ibatis.annotations.Mapper;
import store.teabliss.basket.entity.Basket;
import store.teabliss.member.entity.Member;
import store.teabliss.tea.entity.Tea;

import java.util.List;
import java.util.Optional;


@Mapper
public interface BasketMapper {

     List<Basket> getbasket(Long memberId);

     void update(Basket basket);

     boolean delete(Long id,Long memberId);

     void save(Basket basket);
}
