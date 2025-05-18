import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ExamenMedicoServlet")
public class ExamenMedicoServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");
        String fecha = request.getParameter("fecha");
        String tipoExamen = request.getParameter("tipo");
        String resultado = request.getParameter("resultado");
        String idEmpleado = request.getParameter("id_empleado");

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/safeworkhub", "root", "");

            String query = "INSERT INTO examen_medico (idExamen_Medico, Fecha, Tipo_Examen, Resultados, id_empleado) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, id);
            pst.setString(2, fecha);
            pst.setString(3, tipoExamen);
            pst.setString(4, resultado);
            pst.setString(5, idEmpleado);

            int rowsInserted = pst.executeUpdate();

            out.println("<html><head><title>Registro de Examen Médico</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; background-color: #f4faff; text-align: center; padding-top: 100px; }");
            out.println(".icon { font-size: 90px; margin-bottom: 20px; color: green; }");
            out.println("h2 { color: #333; font-size: 24px; }");
            out.println(".error { color: red; }");
            out.println("</style></head><body>");

            if (rowsInserted > 0) {
                out.println("<div class='icon'>&#10004;</div>");
                out.println("<h2>Registro médico exitoso.</h2>");
            } else {
                out.println("<div class='icon error'>&#10060;</div>");
                out.println("<h2>Error al registrar el examen médico.</h2>");
            }

            out.println("</body></html>");

            con.close();

        } catch (Exception e) {
            out.println("<html><body><h2 class='error'>Error: " + e.getMessage() + "</h2></body></html>");
        }
    }
}