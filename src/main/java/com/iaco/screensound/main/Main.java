package com.iaco.screensound.main;

import com.iaco.screensound.models.Artist;
import com.iaco.screensound.models.Categoria;
import com.iaco.screensound.models.Music;
import com.iaco.screensound.repository.MusicRepository;
import com.iaco.screensound.service.GetInfoChatGPT;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    Scanner sc = new Scanner(System.in);
    @Autowired
    private MusicRepository repositorio;

    private Optional<Artist> artistaBuscado;
    private List<Artist> artistas = new ArrayList<>();

    public Main(MusicRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void menu() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("""
                    <><><> Bem vindo ao Screen Sound <><><>
                    1 - Cadastrar artistas
                    2 - Cadastrar músicas
                    3 - Listar músicas
                    4 - Buscar músicas por trecho de título
                    5 - Pesquisar dados sobre um artista
                                    
                    0 - Sair
                    """);


            opcao = sc.nextInt();
            sc.nextLine();
            switch (opcao) {
                case 1:
                    cadastrarArtistas();
                    break;
                case 2:
                    cadastrarMusicas();
                    break;
                case 3:
                    listarMusicas();
                    break;
                case 4:
                    buscarMusicasPorTrecho();
                    break;
                case 5:
                    pesquisarArtista();
                    break;
                case 0:
                    System.out.println("GoodBye!");
                    break;
                default:
                    System.out.println("Opção inválida");
            }


        }
    }

    private void cadastrarArtistas() {
        String continuar = "S";
        while (continuar.equalsIgnoreCase("S")) {
            Artist artista = new Artist();
            System.out.println("Informe o nome do artista: ");
            String nome = sc.nextLine();
            System.out.println("Informe o tipo do artista: (solo, dupla ou banda)");
            String tipo = sc.nextLine();
            Categoria categoria = Categoria.fromString(tipo);
            artista.setName(nome);
            artista.setCategory(categoria);
            String info = GetInfoChatGPT.getInfo(artista.getName());
            artista.setInfo(info);
            repositorio.save(artista);
            System.out.println("Deseja cadastrar um outro artista? Digite 'S' para continuar");
            continuar = sc.nextLine();
        }
    }

    private void cadastrarMusicas() {
        String continuar = "S";
        while (continuar.equalsIgnoreCase("S")) {
            System.out.println("Digite o nome do artista responsável pela música que deseja cadastrar: ");
            String artista = sc.nextLine();
            artistaBuscado = repositorio.findByNameContainingIgnoreCase(artista);
            if (artistaBuscado.isPresent()) {
                Music musica = new Music();
                musica.setArtist(artistaBuscado.get());
                System.out.println("Digite o nome da música:");
                musica.setName(sc.nextLine());
                System.out.println("Digite o nome do álbum:");
                musica.setAlbum(sc.nextLine());
//                artistaBuscado.get().setMusics(musica);
                artistaBuscado.get().getMusics().add(musica);
                repositorio.save(artistaBuscado.get());
            } else {
                System.out.println("Artista não encontrado. Favor cadastrar antes de inserir músicas.");
            }
            System.out.println("Deseja cadastrar uma outra música? Digite 'S' para continuar");
            continuar = sc.nextLine();
        }
    }
    private void listarMusicas() {
        artistas = repositorio.findAll();
        artistas.forEach(e -> System.out.println(e.getName()));
        System.out.println("Digite um artista da lista para listar as músicas cadastradas:");
        String search = sc.nextLine();
        List<Music> musicas = repositorio.listaMusicasPorArtista(search);
        musicas.stream().forEach(e -> System.out.println(e.getName()));
    }

    private void buscarMusicasPorTrecho() {
        System.out.println("Digite o trecho de o nome de uma música que deseja pesquisar: ");
        String trecho = sc.nextLine();
        List<Music> musicasEncontradas = repositorio.listaMusicasPorTrecho(trecho);
        musicasEncontradas.forEach(e -> System.out.println(e.getName()));
    }

    private void pesquisarArtista() {
        System.out.println("Digite o nome do artista que deseja buscar informações: ");
        String artista = sc.nextLine();
        artistaBuscado = repositorio.findByNameContainingIgnoreCase(artista);
        if (artistaBuscado.isPresent()) {
            System.out.println(artistaBuscado.get().getInfo());
        } else {
            String info = GetInfoChatGPT.getInfo(artista);
            System.out.println(info);
        }
    }
}