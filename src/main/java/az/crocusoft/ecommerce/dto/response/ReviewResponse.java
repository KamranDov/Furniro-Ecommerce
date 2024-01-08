package az.crocusoft.ecommerce.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewResponse {
        private String comment;
        private int rating;
        private String username;
        private Set<String> imageUrls = new HashSet<>();
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy MM dd")
        private Date createdAt;
}
