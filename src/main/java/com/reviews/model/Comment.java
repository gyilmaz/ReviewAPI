package com.reviews.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Comment {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Please provide a title")
    private String title;

    @NotEmpty(message = "Please provide a text")
    private String text;

    @ManyToOne
    @JsonIgnore
    private Review review;
}
