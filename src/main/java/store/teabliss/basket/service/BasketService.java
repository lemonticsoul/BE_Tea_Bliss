package store.teabliss.basket.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.teabliss.basket.dto.BasketDto;
import store.teabliss.basket.dto.DeleteBasketDto;
import store.teabliss.basket.entity.Basket;
import store.teabliss.basket.mapper.BasketMapper;
import store.teabliss.member.entity.Member;
import store.teabliss.member.exception.NotFoundMemberByEmailException;
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


    public List<Basket> getbasket(String email){

        List<Basket> list=basketMapper.getbasket(email);

        return list;


    }


    public boolean postBaskets(BasketDto basketDto){

            Tea tea = teaMapper.search(basketDto.getProduct());

            Optional<Member> memberOpt=memberMapper.findByEmail(basketDto.getEmail());

            if (memberOpt.isPresent()){

                Basket basket = Basket.builder()
                        .email(basketDto.getEmail())
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
                throw new NotFoundMemberByEmailException("User with ID " + basketDto.getEmail() + " not found");
            }



    }

    public boolean patchBaskets(Long id,BasketDto basketDto){

        Optional<Member> memberOpt=memberMapper.findByEmail(basketDto.getEmail());
        Tea tea = teaMapper.search(basketDto.getProduct());



        if (memberOpt.isPresent()) {

            Basket basket = Basket.builder()
                    .email(basketDto.getEmail())
                    .img(tea.getImg())
                    .name(tea.getName())
                    .nameEng(tea.getNameEng())
                    .price(tea.getPrice())
                    .product(basketDto.getProduct())
                    .quantity(basketDto.getQuantity())
                    .type(basketDto.getType())
                    .build();


            basketMapper.update(id,basket);

            return true;

        }else {
            throw new NotFoundMemberByEmailException("User with ID " + basketDto.getEmail() + " not found");
        }
    }

    public boolean deleteBaskets(DeleteBasketDto deleteBasketDto){

        Optional<Member> memberOpt=memberMapper.findByEmail(deleteBasketDto.getEmail());

        if (memberOpt.isPresent()) {

            String UserId = deleteBasketDto.getEmail();
            Long id = deleteBasketDto.getId();

            boolean result = basketMapper.delete(id,UserId);

            return result;
        } else {
            throw new NotFoundMemberByEmailException("User with ID " + deleteBasketDto.getEmail() + " not found");
        }
    }



}
