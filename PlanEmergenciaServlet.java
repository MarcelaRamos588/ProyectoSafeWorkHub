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

@WebServlet("/PlanEmergenciaServlet")
public class PlanEmergenciaServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String idPlan = request.getParameter("idPlan");
        String tipo = request.getParameter("tipo");
        String procedimientos = request.getParameter("procedimientos");
        String responsables = request.getParameter("responsables");
        String idEmpleado = request.getParameter("id_empleado");

        try {
            int idPlanInt = Integer.parseInt(idPlan);
            int idEmpleadoInt = Integer.parseInt(idEmpleado);

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/safeworkhub", "root", "");

            String sql = "INSERT INTO plan_emergencia (idPlan_Emergencia, Tipo_emergencia, Procedimientos, Responsables, id_empleado) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idPlanInt);
            stmt.setString(2, tipo);
            stmt.setString(3, procedimientos);
            stmt.setString(4, responsables);
            stmt.setInt(5, idEmpleadoInt);

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                out.println("<script>alert('✅ Registro exitoso del plan de emergencia.'); window.location='formularioPlanEmergencia.html';</script>");
            } else {
                out.println("<h2 style='color:red;'>❌ Error al registrar el plan de emergencia.</h2>");
            }

            conn.close();
        } catch (Exception e) {
            out.println("<h2 style='color:red;'>Error al registrar plan de emergencia:</h2>");
            out.println("<pre>" + e.getMessage() + "</pre>");
        }
    }
}