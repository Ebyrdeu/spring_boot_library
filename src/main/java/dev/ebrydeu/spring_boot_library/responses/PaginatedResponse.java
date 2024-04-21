package dev.ebrydeu.spring_boot_library.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "JSend Specification")
public class PaginatedResponse<T> {

    private List<T> data;
    private int currentPage;
    private long totalItems;
    private int totalPages;


    public static <T> PaginatedResponse success(List<T> data, int currentPage, long totalItems, int totalPages) {
        return new PaginatedResponse<>(data, currentPage, totalItems, totalPages);
    }
}
