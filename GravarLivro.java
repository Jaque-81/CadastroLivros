
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import model.Livro;

/**
 *
 * @author cgpre
 */
public class GravarLivro extends HttpServlet {

    private Livro livro;

    private String GravaAluno() {
        try {
            Context envContext = new InitialContext();
            Context initContext = (Context) envContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) initContext.lookup("jdbc/livros");

            Connection con = ds.getConnection();
            Statement stmt = con.createStatement();
            if (livro.getId() == 0) {
                String SQL = "insert into livro (titulo,autor,ano, isbn, editora, edicao)values("
                        + "'" + livro.getTitulo() + "',"                       
                        + "'" + livro.getAutor()+ "',"
                        + "'" + livro.getAno()+ "',"
                        + "'" + livro.getIsbn()+ "',"
                        + "'" + livro.getEditora()+ "',"
                        + "'" + livro.getEdicao() + "')";
                stmt.execute(SQL);
            } else {
                String SQL = "update Livros set "
                + "titulo='" + livro.getTitulo()+ "',"
                + "autor1='" + livro.getAutor() + "',"
                + "ano='" + livro.getAno()+ "',"
                + "isbn='" + livro.getIsbn()+ "',"
                + "editora='" + livro.getEditora()+ "',"
                + "edicao='" + livro.getEdicao() + "' "
                + " where id=" + livro.getId();
                
                stmt.execute(SQL);
            }

            con.close();
            return "Sucesso na operação";
        } catch (SQLException e) {
            return "erro no banco: " + e.getMessage();
        } catch (NamingException ne) {
            return "erro no context: " + ne.getMessage();
        }

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        livro = new Livro();
        livro.setTitulo(request.getParameter("titulo"));
        livro.setAutor(request.getParameter("autor"));
        livro.setAno(request.getParameter("ano"));
        livro.setIsbn(request.getParameter("isbn"));
        livro.setEditora(request.getParameter("editora"));
        livro.setEdicao(request.getParameter("edicao"));
        
        if (request.getParameter("id") == null) {
            livro.setId(0);
        } else {
            livro.setId(Integer.parseInt(request.getParameter("id")));
        }

        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Gravando no Livro</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<form>");
            out.println("<h2>Gravando novo Livro</h2>");
            out.println("<p>" + GravaAluno() + "</p>");
            out.println("<input type=\"submit\" formaction=\"ListarLivro\" value=\"Lista de livros\">");
            out.println("<input type=\"submit\" formaction=\"index.html\" value=\"Inicio\">");
            out.println("</form>");
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
