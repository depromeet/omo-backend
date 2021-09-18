package com.depromeet.omobackend.domain.menu;

import com.depromeet.omobackend.domain.omakase.Omakase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Menu {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String photoUrl;

    @Size(max = 45)
    @NotNull
    private String name;

    @NotNull
    private Integer price;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "omakase_id", nullable = false)
    private Omakase omakase;

    @Builder
    public Menu(String photoUrl, String name, Integer price, String description, Omakase omakase) {
        this.photoUrl = photoUrl;
        this.name = name;
        this.price = price;
        this.description = description;
        this.omakase = omakase;
    }

}
