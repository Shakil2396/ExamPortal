     package com.substring.quiz.category.entities;

     import jakarta.persistence.Entity;
     import jakarta.persistence.Table;
     import lombok.AllArgsConstructor;
     import lombok.Getter;
     import lombok.NoArgsConstructor;
     import lombok.Setter;


    @Entity
    @Table(name = "quiz_category")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class Category {

        @jakarta.persistence.Id
        private String Id;

        private String title;

        private String description;

        private boolean active;
}