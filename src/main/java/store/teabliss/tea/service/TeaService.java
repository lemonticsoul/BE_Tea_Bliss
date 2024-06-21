package store.teabliss.tea.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.teabliss.ingredient.entity.Ingredient;
import store.teabliss.tea.dto.TeaDto;
import store.teabliss.tea.dto.TeaFinalDto;
import store.teabliss.tea.dto.TeaPatchDto;
import store.teabliss.tea.dto.TeaSearchDto;
import store.teabliss.tea.entity.TeaFlavor;
import store.teabliss.tea.entity.TeaIngredient;
import store.teabliss.tea.entity.Tea;
import store.teabliss.tea.mapper.TeaMapper;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeaService {

    private final TeaMapper teaMapper;

    public boolean createtea(TeaDto teaDto) throws JsonProcessingException {


            Tea tea = Tea.builder()
                    .category(teaDto.getCategory())
                    .price(teaDto.getPrice())
                    .review(teaDto.getReview())
                    .sale(teaDto.getSale())
                    .rating(teaDto.getRating())
                    .season(teaDto.getSeason())
                    .name(teaDto.getName())
                    .nameEng(teaDto.getNameEng())
                    .rate(teaDto.getRate())
                    .caffeine(teaDto.isCaffeine())
                    .description(teaDto.getDescription())
                    .img(teaDto.getImg())
                    .inventory(teaDto.getInventory())
                    .saleStatus(teaDto.getSaleStatus())
                    .build();

            /// flavor와 재료
            teaMapper.save(tea);

            List<Long> temp1=teaDto.getIngredient();
            for (Long t1:temp1){
                TeaIngredient teaingredient =new TeaIngredient();
                teaingredient.setIngredientId(t1);
                teaingredient.setTeaId(tea.getId());
                teaMapper.saveIngredient(teaingredient);
            }

            List<Long> temp2=teaDto.getFlavor();

            for (Long t2:temp2){
                TeaFlavor teaflavor =new TeaFlavor();
                teaflavor.setFlavor(t2);
                teaflavor.setTeaId(tea.getId());
                teaMapper.saveFlavor(teaflavor);
            }

            return true;
    }

    public List<TeaFinalDto> sort(int page,int limit){

        List<Tea> recommend=teaMapper.recommend();

        double all=recommend.size();
        int limitpage= (int) Math.ceil(all/limit);
        List<Tea> new_recommend=new ArrayList<>();

        for (int i =limit*(page-1);i<(page*limit);i++){
            if (i==all) {
                break;
            }
            if (page==limitpage){
                recommend.get(i).setIsLastPage(true);

                new_recommend.add(recommend.get(i));

            } else if (page !=limitpage) {
                recommend.get(i).setIsLastPage(false);

                new_recommend.add(recommend.get(i));

            }

        }

        List<TeaFinalDto> saveFinalDto = TeaFinalDto.of(new_recommend);



        return saveFinalDto;



    }

    public List<TeaFinalDto> salesort(int page,int limit){

        List<Tea> sale=teaMapper.sale();

        double all=sale.size();
        int limitpage= (int) Math.ceil(all/limit);
        List<Tea> new_recommend=new ArrayList<>();

        for (int i =limit*(page-1);i<(page*limit);i++){
            if (i==all) {
                break;
            }
            if (page==limitpage){
                sale.get(i).setIsLastPage(true);
                new_recommend.add(sale.get(i));

            } else if (page !=limitpage) {
                sale.get(i).setIsLastPage(false);
                new_recommend.add(sale.get(i));

            }

        }
        return TeaFinalDto.of(new_recommend);
    }

    public List<TeaFinalDto> topcostsort(int page,int limit){

        List<Tea> topcost=teaMapper.topcost();
        double all=topcost.size();
        int limitpage= (int) Math.ceil(all/limit);
        List<Tea> new_recommend=new ArrayList<>();

        for (int i =limit*(page-1);i<(page*limit);i++){
            if (i==all) {
                break;
            }
            if (page==limitpage){
                topcost.get(i).setIsLastPage(true);
                new_recommend.add(topcost.get(i));

            } else {
                topcost.get(i).setIsLastPage(false);
                new_recommend.add(topcost.get(i));

            }

        }
        return TeaFinalDto.of(new_recommend);
    }
    public List<TeaFinalDto> lowcostsort(int page,int limit){

        List<Tea> lowcost=teaMapper.lowcost();
        double all=lowcost.size();
        int limitpage= (int) Math.ceil(all/limit);
        List<Tea> new_recommend=new ArrayList<>();

        for (int i =limit*(page-1);i<(page*limit);i++){
            if (i==all) {
                break;
            }
            if (page==limitpage){
                lowcost.get(i).setIsLastPage(true);
                new_recommend.add(lowcost.get(i));

            } else {
                lowcost.get(i).setIsLastPage(false);
                new_recommend.add(lowcost.get(i));

            }

        }
        return TeaFinalDto.of(new_recommend);

    }

    public List<TeaFinalDto> all(int page,int limit){

        List<Tea> alllist=teaMapper.all();
        System.out.println(alllist);
        double all=alllist.size();
        int limitpage= (int) Math.ceil(all/limit);
        List<Tea> new_recommend=new ArrayList<>();

        for (int i =limit*(page-1);i<(page*limit);i++){
            if (i==all) {
                break;
            }
            if (page == limitpage){
                alllist.get(i).setIsLastPage(true);
                new_recommend.add(alllist.get(i));

            } else {
                alllist.get(i).setIsLastPage(false);
                new_recommend.add(alllist.get(i));
            }
        }
        return TeaFinalDto.of(new_recommend);
    }

    public Long count(){

        Long count=teaMapper.count();
        return count;
    }

    public TeaSearchDto find(int id){

        Tea tea=teaMapper.findbyid(id);
        ArrayList<Long> ingredient=teaMapper.findbyingredient(id);
        ArrayList<Long> flavor=teaMapper.findbyflavor(id);

        TeaSearchDto dto=new TeaSearchDto();

        dto.setId(tea.getId());
        dto.setPrice(tea.getPrice());
        dto.setCategory(tea.getCategory());
        dto.setReview(tea.getReview());
        dto.setSale(tea.getSale());
        dto.setRating(tea.getRating());
        dto.setRate(tea.getRate());
        dto.setSeason(tea.getSeason());
        dto.setName(tea.getName());
        dto.setNameEng(tea.getNameEng());
        dto.setCaffeine(tea.isCaffeine());
        dto.setDescription(tea.getDescription());
        dto.setImg(tea.getImg());
        dto.setInventory(tea.getInventory());
        dto.setSaleStatus(tea.getSaleStatus());
        dto.setFlavor(flavor);
        dto.setIngredient(ingredient);


        return dto;

    }

    public List<TeaFinalDto> categorysort(int page,int limit,String category){


        List<Tea> categorylist=teaMapper.category(category);
        double all=categorylist.size();

        int limitpage= (int) Math.ceil(all/limit);

        List<Tea> new_recommend=new ArrayList<>();

        for (int i =limit*(page-1);i<(page*limit);i++){
            if (i==all) {
                break;
            }
            if (page==limitpage){
                categorylist.get(i).setIsLastPage(true);
                new_recommend.add(categorylist.get(i));

            } else {
                categorylist.get(i).setIsLastPage(false);
                new_recommend.add(categorylist.get(i));

            }

        }

        return TeaFinalDto.of(new_recommend);

    }

    public Long categorycount(String category){

        Long count=teaMapper.countByCategory(category);

        return count;
    }

    public List<TeaFinalDto> sortseason(int page,int limit,String season){


        List<Tea> categorylist=teaMapper.seasonsort(season);
        double all=categorylist.size();

        int limitpage= (int) Math.ceil(all/limit);

        List<Tea> new_recommend=new ArrayList<>();

        for (int i =limit*(page-1);i<(page*limit);i++){
            if (i==all) {
                break;
            }
            if (page==limitpage){
                categorylist.get(i).setIsLastPage(true);
                new_recommend.add(categorylist.get(i));

            } else {
                categorylist.get(i).setIsLastPage(false);
                new_recommend.add(categorylist.get(i));

            }

        }

        return TeaFinalDto.of(new_recommend);

    }

    public List<TeaFinalDto> caffeinesort(int page,int limit,boolean caffeine){

        List<Tea> categorylist=teaMapper.caffeinesort(caffeine);

        double all=categorylist.size();

        int limitpage= (int) Math.ceil(all/limit);

        List<Tea> new_recommend=new ArrayList<>();

        for (int i =limit*(page-1);i<(page*limit);i++){

            if (i==all) {
                break;
            }
            if (page==limitpage){

                categorylist.get(i).setIsLastPage(true);
                new_recommend.add(categorylist.get(i));

            } else {
                categorylist.get(i).setIsLastPage(false);
                new_recommend.add(categorylist.get(i));

            }

        }



        return TeaFinalDto.of(new_recommend);
    }

    public boolean deletetea(int id){

        boolean result=teaMapper.deletetea(id);

        return result;
    }

    public boolean patchtea(Long id,TeaPatchDto teaPatchDto){

        Tea updatetea = Tea.builder()
                .id(id)
                .price(teaPatchDto.getPrice())
                .category(teaPatchDto.getCategory())
                .name(teaPatchDto.getName())
                .nameEng(teaPatchDto.getNameEng())
                .caffeine(teaPatchDto.isCaffeine())
                .description(teaPatchDto.getDescription())
                .img(teaPatchDto.getImg())
                .inventory(teaPatchDto.getInventory())
                .saleStatus(teaPatchDto.getSaleStatus())
                .season(teaPatchDto.getSeason())
                .build();

        List<Long> temp1=teaPatchDto.getIngredient();

        for (Long t1:temp1){
            TeaIngredient teaingredient =TeaIngredient.builder()
                                    .ingredientId(t1)
                                    .teaId(id)
                                            .build();

            teaMapper.updateIngredient(teaingredient);
        }

        List<Long> temp2=teaPatchDto.getFlavor();

        for (Long t2:temp2){
            TeaFlavor teaflavor =TeaFlavor.builder()
                    .flavor(t2)
                    .teaId(id)
                    .build();
            teaMapper.updateFlavor(teaflavor);
        }


        boolean result=teaMapper.patchtea(updatetea);

        return result;
    }



}
