package com.substring.quiz.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {

    private String Id;

    private String title;

    private String description;

    private boolean active;
}
