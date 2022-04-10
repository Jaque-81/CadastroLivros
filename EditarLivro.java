package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
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


public class EditarLivro extends HttpServlet {

    private Livro livro;

    private void BuscarLivro() {
        try {
            Context envContext = new InitialContext();
            Context initContext = (Context) envContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) initContext.lookup("jdbc/livros");
            try (Connection con = ds.getConnection()) {
                Statement stmt = con.createStatement();
                String sql = "select * from livro where id=" + livro.getId();
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    livro = new Livro(rs.getInt("id"),rs.getString("titulo"),
                            rs.getString("autor"),rs.getString("ano"),
                            rs.getString("isbn"),
                            rs.getString("editora"),rs.getString("edicao"));
                }
            }
        } catch (SQLException e) {
            System.out.println( "erro no banco: " + e.getMessage());
        } catch (NamingException ne) {
            System.out.println( "erro no context: " + ne.getMessage());
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
            livro.setId(Integer.parseInt(request.getParameter("id")));
            BuscarLivro();
        }
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Alterar Registro</title>");
            out.println("<link href=\"css/estilo.css\"rel=\"stylesheet\">");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Alterar Registro</h1>");
            out.println("<form method=\"post\" action=\"GravarLivro\">");
            out.println("<label>id: " + livro.getId() + "</label><br>");
            out.println("<input  type=\"hidden\" id=\"id\" name=\"id\""
                    + " value=\"" + livro.getId() + "\"" + " size=\"10\" readonly ><br>");
            
            out.println("<label for=\"titulo\">Título</label><br>");
            out.println("<input type=\"text\" id=\"titulo\" name=\"titulo\" "
                    + "value=\"" + livro.getTitulo() + "\" size=\"60\" ><br>");
            
            out.println("<label for=\"autor\">Autor</label><br>");
            out.println("<input type=\"text\" id=\"autor\" name=\"autor1\" "
                    + "value=\"" + livro.getAutor()+ "\" size=\"60\" ><br>");
            
            out.println("<label for=\"ano\">Ano</label><br>");
            out.println("<input type=\"number\" id=\"ano\" name=\"ano\" "
                    + "value=\"" + livro.getAno()+ "\" ><br><br>");
            
            out.println("<label for=\"isbn\">Isbn</label><br>");
            out.println("<input type=\"number\" id=\"isbn\" name=\"isbn\" "
                    + "value=\"" + livro.getIsbn()+ "\" ><br><br>");
            
            out.println("<label for=\"editora\">Editora</label><br>");
            out.println("<input type=\"text\" id=\"editora\" name=\"editora\" "
                    + "value=\"" + livro.getEditora()+ "\" ><br><br>");
            
            out.println("<label for=\"edicao\">Edicao</label><br>");
            out.println("<input type=\"number\" id=\"edicao\" name=\"edicao\" "
                    + "value=\"" + livro.getEdicao()+ "\" ><br><br>");
            
            
            out.println("<input type=\"submit\" value=\"Enviar\" >");

            
            out.println("<input type=\"submit\" formaction=\"ListarLivro\" value=\"Cancelar\">");
            out.println("</form>");
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
