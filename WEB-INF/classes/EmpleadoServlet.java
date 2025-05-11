import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class EmpleadoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String documento = request.getParameter("documento");
        String nombre = request.getParameter("nombre");
        String cargo = request.getParameter("cargo");
        String estadoCapacitacion = request.getParameter("estadoCapacitacion");

        Connection con = null;
        PreparedStatement ps = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/safeworkhub", "root", "");

            String sql = "INSERT INTO empleado (idEmpleado, Nombre, Cargo, Estado_Capacitacion) VALUES (?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(documento));
            ps.setString(2, nombre);
            ps.setString(3, cargo);
            ps.setString(4, estadoCapacitacion);

            int resultado = ps.executeUpdate();

            if (resultado > 0) {
                out.println("<h2>Empleado registrado correctamente</h2>");
            } else {
                out.println("<h2>Error al registrar el empleado</h2>");
            }

        } catch (Exception e) {
            e.printStackTrace(out);
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
    }
}