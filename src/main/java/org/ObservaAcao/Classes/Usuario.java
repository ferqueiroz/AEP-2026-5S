package org.ObservaAcao.Classes;

import org.ObservaAcao.DAOs.UsuarioDAO;
import org.ObservaAcao.Enums.TipoUsuario;
import org.ObservaAcao.Utilidades.Funcoes;

import java.io.IOException;
import java.util.Scanner;

public class Usuario {
    private Long id;
    private String nome;
    private String senha;
    private TipoUsuario tipoUsuario;

    private static Scanner leitor = new Scanner(System.in);

    public Usuario() {
    }

    public Usuario(Long id, String nome, String senha, String tipoUsuario) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.tipoUsuario = TipoUsuario.valueOf(tipoUsuario);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public void definirNome(){
        boolean nomeValido;
        String nome;

        do {
            System.out.print("Nome do Usuário: ");
            nome = leitor.nextLine();

            nomeValido = nome != "";
        } while (!nomeValido);

        setNome(nome);
    }

    public void definirSenha(){
        boolean senhaValido;
        String senha;

        do {
            System.out.print("Senha do Usuário: ");
            senha = leitor.nextLine();

            senhaValido = senha != "";
        } while (!senhaValido);

        setSenha(senha);
    }

    public void definirTipoUsuario(){
        boolean tipoUsuarioValido = false;
        TipoUsuario tipoUsuario = null;

        do {
            System.out.println("Tipo de Usuário:\n");
            System.out.println("1 - CIDADAO");
            System.out.println("2 - GERENTE");
            System.out.print("\nOpção: ");
            String opcao = leitor.nextLine();

            switch (opcao){
                case "1":
                    tipoUsuario = TipoUsuario.CIDADAO;
                    break;
                case "2":
                    tipoUsuario = TipoUsuario.GERENTE;
                    break;
                default:
                    continue;
            }

            tipoUsuarioValido = true;
        } while (!tipoUsuarioValido);

        setTipoUsuario(tipoUsuario);
    }

    public static Usuario logarUsuario() {
        boolean usuarioValido;
        Usuario usuario = null;

        do {
            usuario = new Usuario();

            Funcoes.LimparConsole();
            System.out.println("-=Login=-\n");

            usuario.definirNome();
            usuario.definirSenha();

            usuario = UsuarioDAO.buscarUsuario(usuario.getNome(), usuario.getSenha());

            usuarioValido = usuario != null;

            if (!usuarioValido) {
                System.out.println("\nUsuário inválido!\n");
                System.out.print("Pressione ENTER para continuar...");
                leitor.nextLine();
            }
        } while (!usuarioValido);

        return usuario;
    }

    public static Usuario registrarUsuario() {
        Funcoes.LimparConsole();
        Usuario usuario = new Usuario();

        System.out.println("-=Registrar=-\n");

        usuario.definirNome();
        usuario.definirSenha();
        usuario.definirTipoUsuario();

        UsuarioDAO.criarUsuario(usuario);
        return UsuarioDAO.buscarUsuario(usuario.getNome(), usuario.getSenha());
    }
}
