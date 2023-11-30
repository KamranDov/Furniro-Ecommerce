package az.crocusoft.ecommerce.mapper;


import az.crocusoft.ecommerce.dto.BlogCategoryDto;
import az.crocusoft.ecommerce.model.BlogCategory;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface BlogCategoryMapper {
    BlogCategory toEntity(BlogCategoryDto categoryDto);
}
