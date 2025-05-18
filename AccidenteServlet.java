import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class AccidenteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String idAccidente = request.getParameter("idAccidente");
        String fecha = request.getParameter("fecha");
        String lugar = request.getParameter("lugar");
        String descripcion = request.getParameter("descripcion");
        String detalleLesion = request.getParameter("detalleLesion");
        String tratamiento = request.getParameter("tratamiento");
        String idEmpleado = request.getParameter("idEmpleado");

        Connection con = null;
        PreparedStatement ps = null;

        try {
            // Cargar el driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Conectar a la base de datos
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/safeworkhub", "root", "");

            // Insertar los datos
            String sql = "INSERT INTO accidente (idAccidente, Fecha, Lugar, Descripcion, Detalles_lesion, Tratamiento, id_empleado) VALUES (?, ?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(idAccidente));
            ps.setString(2, fecha);
            ps.setString(3, lugar);
            ps.setString(4, descripcion);
            ps.setString(5, detalleLesion);
            ps.setString(6, tratamiento);
            ps.setInt(7, Integer.parseInt(idEmpleado));

            int resultado = ps.executeUpdate();

            if (resultado > 0) {
                out.println("<h2>✅ Accidente registrado correctamente</h2>");
            } else {
                out.println("<h2>⚠️ No se pudo registrar el accidente</h2>");
            }

        } catch (Exception e) {
            out.println("<h2>Error al registrar accidente:</h2>");
            e.printStackTrace(out);
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
    }
}