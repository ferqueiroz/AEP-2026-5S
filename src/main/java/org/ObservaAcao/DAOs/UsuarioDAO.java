package org.ObservaAcao.DAOs;

import org.ObservaAcao.Classes.Usuario;
import org.ObservaAcao.Conexao.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public static List<Usuario> listarUsuarios(){
        List<Usuario> listaUsuarios = new ArrayList<>();

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement query = conexao.prepareStatement("SELECT * FROM usuarios");
             ResultSet usuarios = query.executeQuery()) {

            while (usuarios.next()){
                listaUsuarios.add(new Usuario(
                        usuarios.getLong("usu_id"),
                        usuarios.getString("usu_nome"),
                        usuarios.getString("usu_senha"),
                        usuarios.getString("usu_tipo"))
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaUsuarios;
    }

    public static Usuario buscarUsuario(Long id){
        try (Connection conexao = Conexao.getConexao();
             PreparedStatement query = conexao.prepareStatement("SELECT * FROM usuarios WHERE usu_id = ?")) {

            query.setLong(1, id);
            ResultSet usuario = query.executeQuery();

            return usuario.next() ?
                new Usuario(
                    usuario.getLong("usu_id"),
                    usuario.getString("usu_nome"),
                    usuario.getString("usu_senha"),
                    usuario.getString("usu_tipo")
                ) : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Usuario buscarUsuario(String nome, String senha){
        String sql = "SELECT * FROM usuarios WHERE usu_nome = ?";
        if (senha != "") sql += " AND usu_senha = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement query = conexao.prepareStatement(sql)) {

            query.setString(1, nome);
            if (senha != "") query.setString(2, senha);
            ResultSet usuario = query.executeQuery();

            return usuario.next() ?
                new Usuario(
                        usuario.getLong("usu_id"),
                        usuario.getString("usu_nome"),
                        usuario.getString("usu_senha"),
                        usuario.getString("usu_tipo")
                ) : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void criarUsuario(Usuario usuario) {
        try (Connection conexao = Conexao.getConexao();
             PreparedStatement query = conexao.prepareStatement("INSERT INTO usuarios (usu_nome, usu_senha, usu_tipo) VALUES (?, ?, ?)")) {

            query.setString(1, usuario.getNome());
            query.setString(2, usuario.getSenha());
            query.setString(3, usuario.getTipoUsuario().getTipoUsuario());
            query.executeUpdate();

            System.out.println("\n✅ Usuário criado com sucesso!\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void atualizarUsuario(Usuario usuario) {
        try (Connection conexao = Conexao.getConexao();
             PreparedStatement query = conexao.prepareStatement("UPDATE usuarios SET usu_nome = ?, usu_senha = ?, usu_tipo = ? WHERE usu_id = ?")) {

            query.setString(1, usuario.getNome());
            query.setString(2, usuario.getSenha());
            query.setString(3, usuario.getTipoUsuario().getTipoUsuario());
            query.setLong(4, usuario.getId());
            query.executeUpdate();

            System.out.println("\n✅ Usuário atualizado com sucesso!\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deletarUsuario(Long id) {
        String sql = "DELETE FROM usuarios WHERE usu_id = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

            System.out.println("🗑️ Usuário deletado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
