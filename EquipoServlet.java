import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class EquipoServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Obtener parámetros del formulario
        String idEquipo = request.getParameter("idEquipo_Proteccion");
        String tipo = request.getParameter("tipo_equipo");
        String fechaEntrega = request.getParameter("fecha_entrega");
        String fechaVencimiento = request.getParameter("fecha_vencimiento");
        String estado = request.getParameter("estado");
        String idEmpleado = request.getParameter("id_empleado");

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Cargar el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Conexión a la base de datos
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/safeworkhub", "root", "");

            // Consulta SQL para insertar
            String sql = "INSERT INTO equipo_proteccion_personal (idEquipo_Proteccion, tipo_equipo, fecha_entrega, fecha_vencimiento, estado, id_empleado) VALUES (?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, Integer.parseInt(idEquipo));
            stmt.setString(2, tipo);
            stmt.setDate(3, Date.valueOf(fechaEntrega));
            stmt.setDate(4, Date.valueOf(fechaVencimiento));
            stmt.setString(5, estado);
            stmt.setInt(6, Integer.parseInt(idEmpleado));

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                out.println("<h2>Registro de equipo de protección exitoso.</h2>");
            } else {
                out.println("<h2>Error al registrar el equipo de protección.</h2>");
            }

        } catch (Exception e) {
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
            e.printStackTrace(out);
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace(out);
            }
        }
    }
}