package com.mohdev.dream_shop.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ApiResponse {
    private String message;
    private Object data;
}
