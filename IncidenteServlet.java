import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/IncidenteServlet")
public class IncidenteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String idIncidente = request.getParameter("idIncidente");
        String fecha = request.getParameter("fecha");
        String lugar = request.getParameter("lugar");
        String descripcion = request.getParameter("descripcion");
        String medidas = request.getParameter("medidas");
        String idEmpleado = request.getParameter("id_empleado");

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/safeworkhub", "root", "");

            String query = "INSERT INTO incidente (idIncidente, Fecha, Lugar, Descripcion, Medidas_Preventivas, id_empleado) VALUES (?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(idIncidente));
            stmt.setString(2, fecha);
            stmt.setString(3, lugar);
            stmt.setString(4, descripcion);
            stmt.setString(5, medidas);
            stmt.setInt(6, Integer.parseInt(idEmpleado));

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                out.println("<html><body style='font-family: Arial; text-align: center; padding-top: 50px;'>");
                out.println("<h2 style='color: green;'>&#10004; Registro de incidente exitoso.</h2>");
                out.println("</body></html>");
            } else {
                out.println("<h2>Error al registrar el incidente.</h2>");
            }

        } catch (Exception e) {
            out.println("<h2 style='color:red;'>Error al registrar incidente:</h2>");
            out.println("<pre>" + e.toString() + "</pre>");
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