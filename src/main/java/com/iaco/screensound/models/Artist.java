package com.iaco.screensound.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "artist")
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // @Column(name = "nomeQueEuQuiser")
    @Column(unique = true)
    private String name;
    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Music> musics = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private Categoria category;
    private String info;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMusics(List<Music> musics) {
        this.musics = musics;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Music> getMusics() {
        return musics;
    }

    public void setMusics(Music musica) {
        musics.add(musica);

    }



    public Categoria getCategory() {
        return category;
    }

    public void setCategory(Categoria category) {
        this.category = category;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
