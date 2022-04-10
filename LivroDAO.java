/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Livro;


public class LivroDAO {

    public void Gravar(Livro livro) throws SQLException {
        ConectaBD bd = new ConectaBD();
        String SQL = "Insert into Livro(titulo, autor, ano, isbn, editora, edicao)values("
                + "'" + livro.getTitulo() + "',"
                + "'" + livro.getAutor() + "',"
                + "'" + livro.getAno() + "',"
                + "'" + livro.getIsbn() + "',"
                + "'" + livro.getEditora() + "',"
                + "'" + livro.getEdicao() + "')";
        bd.executa(SQL);
        bd.fecha();
    }

    public void Alterar(Livro livro) throws SQLException {
        ConectaBD bd = new ConectaBD();
        String SQL = "update Livro set "
                 + "'" + livro.getTitulo() + "',"
                + "'" + livro.getAutor() + "',"
                + "'" + livro.getAno() + "',"
                + "'" + livro.getIsbn() + "',"
                + "'" + livro.getEditora() + "',"
                + "'" + livro.getEdicao() + "'"
                + " where id=" + livro.getId();
        bd.executa(SQL);
        bd.fecha();
    }

    public void Excluir(Livro livro) throws SQLException {
        ConectaBD bd = new ConectaBD();
        String SQL = "delte from livro "
                + " where id=" + livro.getId();
        bd.executa(SQL);
        bd.fecha();
    }

    public void Excluir(String id) throws SQLException {
        ConectaBD bd = new ConectaBD();
        String SQL = "delete from livro "
                + " where id=" + id;
        bd.executa(SQL);
        bd.fecha();
    }

    public List<Livro> ListarLivros(String keyWord) {//ListaLivros
        List<Livro> lista = new ArrayList<>();
        ConectaBD bd = new ConectaBD();
        String SQL = "select * from livro"
                + " where nome like '" + keyWord + "%'"
                + " order by titulo";
        try {
            ResultSet r = bd.getResultSet(SQL);
            while (r.next()) {
                Livro a = new Livro(r.getInt("id"),r.getString("titulo"),
                        r.getString("autor"),r.getString("ano"), r.getString("isbn"), r.getString("editora"), r.getString("edicao"));
                lista.add(a);
            }
        } catch (SQLException e) {
        }

        bd.fecha();
        
        return lista;
    }

}
