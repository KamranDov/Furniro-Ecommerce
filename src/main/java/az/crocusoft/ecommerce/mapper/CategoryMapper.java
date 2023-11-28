package az.crocusoft.ecommerce.mapper;


import az.crocusoft.ecommerce.dto.CategoryDto;
import az.crocusoft.ecommerce.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {
    Category toEntity(CategoryDto categoryDto);
}
