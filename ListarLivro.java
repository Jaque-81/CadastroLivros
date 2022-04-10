
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import model.Livro;
import java.util.List;


public class ListarLivro extends HttpServlet {
    private Livro livro;
    private final List<Livro> listaLivros = new ArrayList<>(); 
    private String kw;
    
    private String ListarLivro() {
        try {
            Context envContext = new InitialContext();
            Context initContext = (Context) envContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) initContext.lookup("jdbc/livros");
            Connection con = ds.getConnection();
            Statement stmt = con.createStatement();
            String sql = "select * from livro where "
                        + "titulo like '" + kw + "%'"
                        + " order by titulo";
                ResultSet rs = stmt.executeQuery(sql);
                listaLivros.clear();
                while (rs.next()) {
                    Livro a = new Livro(rs.getInt("id"), rs.getString("titulo"),
                            rs.getString("autor"), rs.getString("ano"),
                            rs.getString("isbn"), rs.getString("editora"),
                            rs.getString("edicao"));
                    listaLivros.add(a);
                }
            con.close();
                return "Lista de Livros";
            } catch (SQLException e) {
                return "erro no banco: " + e.getMessage();
            } catch (NamingException ne) {
                return "erro no context: " + ne.getMessage();
            }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        kw = request.getParameter("keyword");
        if (kw == null) {
            kw = "";
        }

        try (PrintWriter out = response.getWriter()) {
         
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta>");
            out.println("<link href=\"css/estilo.css\"rel=\"stylesheet\">");
            out.println("</meta>");
            out.println("<title>Lista de Livros</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Lista de Livros</h1>");
            out.println("<form>");
            out.println(ListarLivro());
            out.println("<table><tr><th>id</th><th>titulo</th><th >autor</th><th>ano</th>"
                    + "<th></th>"
                    + "<th ></th></tr>");
           /* listaLivros.forEach((Livro next) -> {
            out.println("<tr><td>" + next.getId() + "</td>"
            "<td>" + next.getTitulo() + "</td>"
            + "<td>" + next.getAutor() + "</td>"
            + "<td>" + next.getAno() + "</td>"
            + "<td><form><input id=\"id\" name=\"id\" type=\"hidden\" value=" + next.getId() + ">"
            + "<button type=\"submit\" value=\"alterar\" "
            + "formaction=\"EditarLivro\">alterar</button></form></td>"
            + "<td><form >"
            + "<input id=\"id\" name=\"id\" "
            + "type=\"hidden\" "
            + "value=" + next.getId() + ">"
            + "<button type=\"submit\" value=\"alterar\" "
            + "formaction=\"ExcluirLivro\">excluir</button></form></tr>"
                           );
            });*/
              for (Iterator<Livro> iterator = listaLivros.iterator(); iterator.hasNext();) {
                Livro next = iterator.next();
                out.println("<tr><td>" + next.getId() + "</td>"
                        + "<td>" + next.getTitulo() + "</td>"
                        + "<td>" + next.getAutor() + "</td>"
                        + "<td>" + next.getAno() + "</td>"
                         + "<td><form><input id=\"id\" name=\"id\" type=\"hidden\" value=" + next.getId() + ">"
                        + "<button type=\"submit\" value=\"alterar\" "
                        + "formaction=\"EditarLivro\">alterar</button></form></td>"
                        + "<td><form >"
                        + "<input id=\"id\" name=\"id\" "
                        + "type=\"hidden\" "
                        + "value=" + next.getId() + ">"
                        + "<button type=\"submit\" value=\"alterar\" "
                        + "formaction=\"ExcluirLivro\">excluir</button></form></tr>"
                );
            }
            out.println("</table><br>");
            out.println("<button type=\"button\" onclick=\"history.go(-1)\" value=\"voltar\">voltar</button>");
            out.println("<button type=\"submit\" formaction=\"index.html\" value=\"Inicio\">início</button>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
