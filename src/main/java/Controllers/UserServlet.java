package Controllers;

import Utils.Util;
import dao.UserDAO;
import entities.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/")
public class UserServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");

        if(action==null) {

            getServletContext().getRequestDispatcher("/registration.jsp").forward(request, response);
        }
        else if (action.equals("list"))

            try {
                Connection conn = Util.getConnection();

                List<User> list = UserDAO.getUserList(conn);
                request.setAttribute("list", list);
                request.getRequestDispatcher("/userdetails.jsp").forward(request, response);

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


//        RequestDispatcher dispatcher = request.getRequestDispatcher("/registration.jsp");
//        dispatcher.forward(request, response);


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        int age = Integer.parseInt(request.getParameter("age"));

        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setAge(age);

        try {
            Connection conn = Util.getConnection();
            UserDAO.registerUser(user, conn);
            conn.close();
        } catch (Exception e) {

            e.printStackTrace();
        }

        response.sendRedirect("/?action=list");
//        getServletContext().getRequestDispatcher("/?action=list").forward(request, response);

    }
}
