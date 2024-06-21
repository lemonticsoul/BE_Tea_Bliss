package store.teabliss.basket.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.teabliss.basket.dto.BasketDto;
import store.teabliss.basket.dto.DeleteBasketDto;
import store.teabliss.basket.entity.Basket;
import store.teabliss.basket.mapper.BasketMapper;
import store.teabliss.member.entity.Member;
import store.teabliss.member.exception.NotFoundMemberByEmailException;
import store.teabliss.member.exception.NotFoundMemberByIdException;
import store.teabliss.member.mapper.MemberMapper;
import store.teabliss.tea.entity.Tea;
import store.teabliss.tea.mapper.TeaMapper;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BasketService {


    private final TeaMapper teaMapper;
    private final BasketMapper basketMapper;
    private final MemberMapper memberMapper;


    public List<Basket> getbasket(Long memId){

        List<Basket> list=basketMapper.getbasket(memId);

        return list;


    }


    public boolean postBaskets(BasketDto basketDto,Long memId){

            Tea tea = teaMapper.search(basketDto.getProduct());

            Optional<Member> memberOpt=memberMapper.findById(memId);

            if (memberOpt.isPresent()){

                Basket basket = Basket.builder()
                        .memId(memId)
                        .img(tea.getImg())
                        .name(tea.getName())
                        .nameEng(tea.getNameEng())
                        .price(tea.getPrice())
                        .product(basketDto.getProduct())
                        .quantity(basketDto.getQuantity())
                        .type(basketDto.getType())
                        .build();
                basketMapper.save(basket);
                return true;
            }else {
                return false;
            }



    }

    public boolean patchBaskets(Long id,BasketDto basketDto,Long memId){

        Optional<Member> memberOpt=memberMapper.findById(memId);
        Tea tea = teaMapper.search(basketDto.getProduct());

        if (memberOpt.isPresent()) {

            Basket basket = Basket.builder()
                    .id(id)
                    .memId(memId)
                    .img(tea.getImg())
                    .name(tea.getName())
                    .nameEng(tea.getNameEng())
                    .price(tea.getPrice())
                    .product(basketDto.getProduct())
                    .quantity(basketDto.getQuantity())
                    .type(basketDto.getType())
                    .build();


            basketMapper.update(basket);

            return true;

        }else {
            return false;
        }
    }

    public boolean deleteBaskets(DeleteBasketDto deleteBasketDto,Long memId){

        Optional<Member> memberOpt=memberMapper.findById(memId);

        if (memberOpt.isPresent()) {

            Long id = deleteBasketDto.getId();

            boolean result = basketMapper.delete(id,memId);

            return result;
        } else {
            return false;
        }
    }



}
