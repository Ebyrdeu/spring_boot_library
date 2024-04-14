package dev.ebrydeu.spring_boot_library.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "JSend Specification")
public class JSendResponse {
    private String status;
    private String message;
    private Integer code;
    private Object data;


    // success/fail
    // if failed is needed to create a new method which calles this constructor
    public JSendResponse(String status, Object data) {
        this.status = status;
        this.data = data;
    }


    // error
    public JSendResponse(String status, String message, Integer code, Object data) {
        this.status = status;
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public static JSendResponse success(Object data) {
        return new JSendResponse("success", data);
    }


    public static JSendResponse error(String message, Integer code, Object data) {
        return new JSendResponse("error", message, code, data);
    }
}
