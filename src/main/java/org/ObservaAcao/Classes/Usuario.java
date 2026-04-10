package org.ObservaAcao.Classes;

import org.ObservaAcao.DAOs.UsuarioDAO;
import org.ObservaAcao.Enums.TipoUsuario;
import org.ObservaAcao.Utilidades.Funcoes;

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

    /*
    public void definirNome(){
        definirNome(false);
    }

    public void definirNome(boolean registrando){
        String nome;

        do {
            System.out.print("Nome do Usuário: ");
            nome = leitor.nextLine();

            if (registrando && UsuarioDAO.buscarUsuario(nome) != null){
                System.out.println("\nEsse nome de usuário já foi cadastrado!\n");
                nome = "";
            }
        } while (nome == "");

        setNome(nome);
    }*/

    public void definirNome(boolean registrando) {
        String nomeInformado;

        do {
            System.out.print("Nome do Usuário: ");
            nomeInformado = leitor.nextLine().trim();

            if (nomeInformado.isEmpty()) {
                System.out.println("Nome não pode ser vazio.");
                continue;
            }

            if (registrando && UsuarioDAO.buscarUsuario(nomeInformado) != null) {
                System.out.println("\nEsse nome de usuário já foi cadastrado!\n");
                nomeInformado = "";
            }

        } while (nomeInformado.isEmpty());

        setNome(nomeInformado);
    }

    public void definirSenha(){
        String senha;

        do {
            System.out.print("Senha do Usuário: ");
            senha = leitor.nextLine();
        } while (senha.isEmpty());

        setSenha(senha);
    }

    public void definirTipoUsuario(){
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
        } while (tipoUsuario == null);

        setTipoUsuario(tipoUsuario);
    }

    public static Usuario logarUsuario() {
        Usuario usuarioValidado;

        do {
            Usuario temp = new Usuario();

            Funcoes.limparConsole();
            System.out.println("-=Login=-\n");

            temp.definirNome(false);
            temp.definirSenha();

            usuarioValidado = UsuarioDAO.buscarUsuario(temp.getNome(), temp.getSenha());

            if (usuarioValidado == null) {
                System.out.println("\nUsuário inválido!\n");
                System.out.print("Pressione ENTER para continuar...");
                leitor.nextLine();
            }
        } while (usuarioValidado == null);

        return usuarioValidado;
    }

    public static Usuario registrarUsuario() {
        Usuario usuario = new Usuario();

        Funcoes.limparConsole();
        System.out.println("-=Registrar=-\n");

        usuario.definirNome(true);
        usuario.definirSenha();
        usuario.definirTipoUsuario();

        UsuarioDAO.criarUsuario(usuario);
        Funcoes.pressioneContinuar();

        return usuario;
    }
}