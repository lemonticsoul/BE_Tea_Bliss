package store.teabliss.basket.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.teabliss.basket.dto.BasketDto;
import store.teabliss.basket.dto.DeleteBasketDto;
import store.teabliss.basket.entity.Basket;
import store.teabliss.basket.mapper.BasketMapper;
import store.teabliss.tea.entity.Tea;
import store.teabliss.tea.mapper.TeaMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BasketService {


    private final TeaMapper teaMapper;
    private final BasketMapper basketMapper;


    public List<Basket> getbasket(){

        List<Basket> list=basketMapper.getbasket();

        return list;


    }


    public boolean postBaskets(BasketDto basketDto){

        Basket basket=new Basket();


            Tea tea = teaMapper.search(basketDto.getProductid());

            basket.setImg(tea.getImg());
            basket.setName(tea.getName());
            basket.setNameEng(tea.getNameEng());
            basket.setPrice(tea.getPrice());
            basket.setProductId(basketDto.getProductid());
            basket.setQuality(basketDto.getQuality());
            basket.setType(basketDto.getType());
            basketMapper.save(basket);
            return true;

    }

    public boolean patchBaskets(BasketDto basketDto){

        Long productId =basketDto.getProductid();
        String quality=basketDto.getQuality();
        String type=basketDto.getType();

        basketMapper.update(productId,quality,type);


        return true;
    }

    public boolean deleteBaskets(Long productid){

        boolean result =basketMapper.delete(productid);

        return result;
    }

    public boolean deleteselectBaskets(DeleteBasketDto deleteBasketDto){

        boolean result=basketMapper.delete(deleteBasketDto.getProductid());

        return result;
    }

}
