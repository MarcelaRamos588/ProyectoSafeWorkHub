import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class CapacitacionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String idCapacitacion = request.getParameter("idCapacitacion");
        String fecha = request.getParameter("fecha");
        String tema = request.getParameter("tema");
        String idEmpleado = request.getParameter("idEmpleado");

        Connection con = null;
        PreparedStatement ps = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/safeworkhub", "root", "");

            String sql = "INSERT INTO capacitacion (idCapacitacion, Fecha, Tema, id_empleado) VALUES (?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(idCapacitacion));
            ps.setString(2, fecha);
            ps.setString(3, tema);
            ps.setInt(4, Integer.parseInt(idEmpleado));

            int resultado = ps.executeUpdate();

            if (resultado > 0) {
                out.println("<h2>✅ Capacitacion registrada correctamente</h2>");
            } else {
                out.println("<h2>⚠️ No se pudo registrar la capacitación</h2>");
            }

        } catch (Exception e) {
            out.println("<h2>Error al registrar capacitación:</h2>");
            e.printStackTrace(out);
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
    }
}