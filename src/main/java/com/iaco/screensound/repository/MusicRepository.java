package com.iaco.screensound.repository;

import com.iaco.screensound.models.Artist;
import com.iaco.screensound.models.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MusicRepository extends JpaRepository<Artist, Long> {
    Optional<Artist> findByNameContainingIgnoreCase(String artista);

    @Query("SELECT m FROM Artist a JOIN a.musics m WHERE a.name ILIKE %:artista%")
    List<Music> listaMusicasPorArtista(String artista);

    @Query("SELECT m FROM Artist a JOIN a.musics m WHERE m.name ILIKE %:trecho%")
    List<Music> listaMusicasPorTrecho(String trecho);
}
