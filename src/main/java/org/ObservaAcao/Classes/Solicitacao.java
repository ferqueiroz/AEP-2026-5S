package org.ObservaAcao.Classes;

import org.ObservaAcao.DAOs.CategoriaDAO;
import org.ObservaAcao.DAOs.HistoricoStatusSolicitacaoDAO;
import org.ObservaAcao.DAOs.SolicitacaoDAO;
import org.ObservaAcao.Enums.StatusSolicitacao;
import org.ObservaAcao.Main;
import org.ObservaAcao.Utilidades.Funcoes;

import javax.swing.*;
import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Solicitacao {
    private Long id;
    private Usuario usuario;
    private String protocolo;
    private Categoria categoria;
    private String descricao;
    private byte[] anexo;
    private int prioridade;
    private StatusSolicitacao status;
    private String localizacao;

    private static Scanner leitor = new Scanner(System.in);

    @Override
    public String toString() {
        System.out.println("---------------");
        System.out.printf("Id: %d\n", this.id);
        System.out.printf("Protocolo: %s\n", this.protocolo);
        System.out.printf("Categoria: %s\n", this.categoria.getDescricao());
        System.out.printf("Descrição: %s\n", this.descricao);
        System.out.printf("Prioridade: %s\n", this.prioridade);
        System.out.printf("Status: %s\n", this.status.getStatusSolicitacao());
        System.out.printf("Localização: %s\n\n", this.localizacao);

        List<HistoricoStatusSolicitacao> statusSolicitacao = HistoricoStatusSolicitacaoDAO.listarHistoricoStatusSolicitacao(this.id);
        String historicoStatus = "";

        for (HistoricoStatusSolicitacao status : statusSolicitacao){
            historicoStatus += (historicoStatus == "" ? "" : " -> ") + status.getStatus().getStatusSolicitacao();
        }

        System.out.println("Histórico de Status:\n");
        System.out.print(historicoStatus);

        return "";
    }

    public Solicitacao(Long id, Usuario usuario, String protocolo, Categoria categoria, String descricao, byte[] anexo, int prioridade, StatusSolicitacao status, String localizacao) {
        this.id = id;
        this.usuario = usuario;
        this.protocolo = protocolo;
        this.categoria = categoria;
        this.descricao = descricao;
        this.anexo = anexo;
        this.prioridade = prioridade;
        this.status = status;
        this.localizacao = localizacao;
    }

    public Solicitacao() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public byte[] getAnexo() {
        return anexo;
    }

    public void setAnexo(byte[] anexo) {
        this.anexo = anexo;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    public StatusSolicitacao getStatus() {
        return status;
    }

    public void setStatus(StatusSolicitacao status) {
        this.status = status;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public static void listarSolicitacoes(boolean visualizarDetalhes){
        listarSolicitacoes(0L, visualizarDetalhes);
    }

    public static void listarSolicitacoes(Long usuario, boolean visualizarDetalhes){
        List<Solicitacao> solicitacoes = SolicitacaoDAO.listarSolicitacoes(usuario);

        if (solicitacoes.isEmpty()){
            System.out.print("Não existe nenhuma solicitação cadastrada!\n");
            return;
        }

        Funcoes.limparConsole();
        System.out.println("-=Solicitações=-\n");
        for (Solicitacao solicitacao : solicitacoes){
            System.out.println(solicitacao);

            if (visualizarDetalhes) {
                System.out.println();

                List<HistoricoStatusSolicitacao> historicoStatus = HistoricoStatusSolicitacaoDAO.listarHistoricoStatusSolicitacao(solicitacao.getId());
                for (HistoricoStatusSolicitacao status : historicoStatus){
                    System.out.println(status);
                }
            }
        }

        System.out.println("---------------\n");
    }

    public static void buscarSolicitacaoPorProtocolo(){
        Funcoes.limparConsole();
        System.out.print("Digite o número de protocolo (0 para voltar): ");
        String protocolo = leitor.nextLine();

        Solicitacao solicitacao = SolicitacaoDAO.buscarSolicitacaoPorProtocolo(protocolo);

        if (solicitacao == null){
            System.out.println("Não foi encontrado nenhuma solicitação com esse número de protocolo!");
            return;
        }

        System.out.println("-=Solicitação=-\n");
        System.out.print(solicitacao);
        System.out.println("\n---------------\n");
    }

    public void definirCategoria(){
        List<Categoria> categorias = CategoriaDAO.listarCategorias();
        Categoria categoriaEscolhida;

        do {
            System.out.println("Categoria da Solicitação:\n");
            for (Categoria categoria : categorias) {
                System.out.printf("%d - %s\n", categoria.getId(), categoria.getDescricao());
            }

            Long opcao;
            while (true) {
                System.out.print("Opção: ");
                try {
                    opcao = leitor.nextLong();
                    leitor.nextLine();
                    break;
                } catch (NumberFormatException e) {
                }
            }

            categoriaEscolhida = CategoriaDAO.buscarCategoria(opcao);
        } while (categoriaEscolhida == null);

        setCategoria(categoriaEscolhida);
    }

    public void definirDescricao(){
        String descricao;

        do {
            System.out.print("Descrição da Solicitação: ");
            descricao = leitor.nextLine();
        } while (descricao == "");

        setDescricao(descricao);
    }

    public void definirAnexo(){
        String opcao;
        File arquivo = null;
        JFileChooser fileChooser = new JFileChooser();

        do {
            do {
                System.out.println("Deseja inserir um anexo (S/N)?");
                opcao = leitor.nextLine().toUpperCase();

                if (!opcao.equals("S")) {
                    if (opcao.equals("N")) return;
                    else opcao = "";
                }
            } while (opcao.isEmpty());

            // Para o dialog aparecer na frente da tela
            JFrame frame = new JFrame();
            frame.setAlwaysOnTop(true);
            frame.setVisible(true);

            int resultado = fileChooser.showOpenDialog(frame);

            frame.dispose();

            if (resultado == JFileChooser.APPROVE_OPTION) {
                arquivo = fileChooser.getSelectedFile();
            }
        } while (arquivo == null) ;

        try {
            setAnexo(Files.readAllBytes(arquivo.toPath()));
        } catch (Exception e){
            System.out.print("❌ Erro ao ler arquivo\n\nCausa: ");
            e.printStackTrace();
            Funcoes.pressioneVoltar();
        }
    }

    public void definirPrioridade(){
        int prioridade;

        do {
            System.out.print("Prioridade da Solicitação (0 a 10): ");
            try {
                prioridade = leitor.nextInt();
                leitor.nextLine();
            } catch (Exception e) {
                prioridade = -1;
            }
        } while (prioridade < 0 || prioridade > 10);

        setPrioridade(prioridade);
    }

    public void definirLocalizacao(){
        String localizacao;

        do {
            System.out.print("Localização da Solicitação: ");
            localizacao = leitor.nextLine();
        } while (localizacao == "");

        setLocalizacao(localizacao);
    }

    public static Solicitacao manutencaoSolicitacao(){
        Solicitacao solicitacao;

        do {
            solicitacao = new Solicitacao();

            Funcoes.limparConsole();
            solicitacao.setProtocolo(gerarProtocolo());
            solicitacao.definirCategoria();
            solicitacao.definirDescricao();
            solicitacao.definirLocalizacao();
            solicitacao.definirPrioridade();
            solicitacao.definirAnexo();
            solicitacao.setStatus(StatusSolicitacao.ATENDIMENTO);

            int opcao;
            while(true) {
                System.out.println("Como deseja enviar a Solicitação:\n");
                System.out.println("1 - Identificado");
                System.out.println("2 - Anônimo\n");
                System.out.print("Opção: ");

                try {
                    opcao = leitor.nextInt();
                    leitor.nextLine();

                    if (opcao == 1) {
                        solicitacao.setUsuario(Main.usuarioConectado);
                        break;
                    } else if (opcao == 2) break;
                } catch (Exception e){
                }
            }

            SolicitacaoDAO.criarSolicitacao(solicitacao);

            HistoricoStatusSolicitacao historicoStatus = new HistoricoStatusSolicitacao();

            historicoStatus.setSolicitacao(solicitacao);
            historicoStatus.setStatus(HistoricoStatusSolicitacaoDAO.buscarProximoStatusSolicitacao(solicitacao.getId()));
            historicoStatus.setDataMudanca(LocalDateTime.now());

            HistoricoStatusSolicitacaoDAO.criarHistoricoStatusSolicitacao(historicoStatus);
        } while (solicitacao == null);

        return solicitacao;
    }

    public static String gerarProtocolo(){
        Random random = new Random();
        String protocolo = String.format("%05d.%06d/%04d-%02d",
                random.nextInt(90000), random.nextInt(900000), LocalDate.now().getYear(), LocalDate.now().getMonthValue());

        if (SolicitacaoDAO.buscarSolicitacaoPorProtocolo(protocolo) != null) protocolo = gerarProtocolo();

        return protocolo;
    }

    public static void mudarStatusResponderSolicitacao(){
        Solicitacao solicitacao;
        StatusSolicitacao proximoStatus;

        Solicitacao.listarSolicitacoes(false);
        Long id;

        while(true) {
            System.out.print("Digite o ID da solicitação (0 para voltar): ");
            try {
                id = leitor.nextLong();
            } catch (Exception e) {
                continue;
            }

            if (id == 0) return;

            solicitacao = SolicitacaoDAO.buscarSolicitacao(id);

            if (solicitacao == null){
                System.out.println("\nSolicitação não existe!\n");
                Funcoes.pressioneContinuar();
                continue;
            }

            proximoStatus = HistoricoStatusSolicitacaoDAO.buscarProximoStatusSolicitacao(solicitacao.getId());

            if (proximoStatus == null){
                System.out.println("\nA Solicitação já está no status de encerrado!\n");
                Funcoes.pressioneContinuar();
                continue;
            }

            break;
        }

        HistoricoStatusSolicitacao statusSolicitacao;
        statusSolicitacao = HistoricoStatusSolicitacaoDAO.buscarUltimoStatusSolicitacao(solicitacao.getId());
        statusSolicitacao.setGerente(Main.usuarioConectado);
        statusSolicitacao.setDataFinalizacao(LocalDateTime.now());
        statusSolicitacao.definirResposta();
        HistoricoStatusSolicitacaoDAO.atualizarHistoricoStatusSolicitacao(statusSolicitacao.getId(), statusSolicitacao);

        solicitacao.setStatus(proximoStatus);
        SolicitacaoDAO.atualizarSolicitacao(solicitacao.getId(), solicitacao);

        statusSolicitacao = new HistoricoStatusSolicitacao();
        statusSolicitacao.setSolicitacao(solicitacao);
        statusSolicitacao.setDataMudanca(LocalDateTime.now());
        statusSolicitacao.setStatus(proximoStatus);
        HistoricoStatusSolicitacaoDAO.criarHistoricoStatusSolicitacao(statusSolicitacao);

        System.out.println("\n✅ Status da Solicitação atualizada com sucesso!\n");
    }

    public static void deletarSolicitacao(){
        Solicitacao.listarSolicitacoes(Main.usuarioConectado.getId(), false);

        Long id;

        do {
            System.out.print("Digite o ID da Solicitação para deletar (0 para voltar): ");

            try {
                id = leitor.nextLong();

                if (id == 0) return;
            } catch (Exception e) {
                id = 0L;
            }
        } while (!Solicitacao.validaSolicitacao(id));

        SolicitacaoDAO.deletarSolicitacao(id);
    }

    public static boolean validaSolicitacao(Long id){
        if (SolicitacaoDAO.buscarSolicitacao(id) == null) {
            System.out.println("\nSolicitação não existe!\n");
            Funcoes.pressioneVoltar();
            return false;
        }

        return true;
    }
}
