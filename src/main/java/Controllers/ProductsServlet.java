package Controllers;

import Utils.Util;
import dao.ProductsRepo;
import entities.Product;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/")
public class ProductsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        System.out.println("DOPOST:" + request);
        try(Connection conn = Util.getConnection()) {
            switch (action) {
                case "/insert":
                    insertUser(request, response, conn);
                    break;
                case "/update":
                    updateUser(request, response, conn);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        try(Connection conn = Util.getConnection()) {
            switch (action) {
                case "/new":
                    showNewForm(request, response, conn);
                    break;
                case "/delete":
                    deleteUser(request, response, conn);
                    break;
                case "/edit":
                    showEditForm(request, response, conn);
                    break;
                case "/filter":
                    System.out.println("FILTER!!!!");
                    String name = request.getParameter("name");
                    List<Product> listProduct;
                    listProduct = ProductsRepo.getProductListByName(name, conn);
                    if (listProduct.isEmpty()) {
                        listProduct = ProductsRepo.getAllProducts(conn);
                        request.setAttribute("message", "We haven't found any entries with the name " + name);
                    }
                    request.setAttribute("listProduct", listProduct);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("productlist.jsp");
                    dispatcher.forward(request, response);
                    break;
                default:
                    listUser(request, response, conn);
                    break;
            }

//            switch (action) {
//                case "/new" -> showNewForm(request, response, conn);
//                case "/insert" -> insertUser(request, response, conn);
//                case "/delete" -> deleteUser(request, response, conn);
//                case "/edit" -> showEditForm(request, response, conn);
//                case "/update" -> updateUser(request, response, conn);
//                default -> listUser(request, response, conn);
//
//            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void listUser(HttpServletRequest request, HttpServletResponse response, Connection conn)
            throws SQLException, IOException, ServletException {
        List<Product> listProduct = ProductsRepo.getAllProducts(conn);
        request.setAttribute("listProduct", listProduct);
        RequestDispatcher dispatcher = request.getRequestDispatcher("productlist.jsp");
        dispatcher.forward(request, response);
    }


    private void showEditForm(HttpServletRequest request, HttpServletResponse response, Connection conn)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Product existingProduct = ProductsRepo.getProductById(id, conn);
        RequestDispatcher dispatcher = request.getRequestDispatcher("addproduct.jsp");
        request.setAttribute("product", existingProduct);
        dispatcher.forward(request, response);

    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response, Connection conn)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Product updatedProduct = new Product();
        updatedProduct.setId(id);
        updatedProduct.setBrand(request.getParameter("brand"));
        updatedProduct.setName(request.getParameter("name"));
        updatedProduct.setPrice(Integer.parseInt(request.getParameter("price")));
        ProductsRepo.updateProduct(updatedProduct, conn);
        response.sendRedirect("list");
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response, Connection conn)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        ProductsRepo.deleteProductById(conn, id);
        response.sendRedirect("list");

    }

    private void insertUser(HttpServletRequest request, HttpServletResponse response, Connection conn)
            throws SQLException, IOException {
        Product newProduct = new Product();
        newProduct.setBrand(request.getParameter("brand"));
        newProduct.setName(request.getParameter("name"));
        newProduct.setPrice(Integer.parseInt(request.getParameter("price")));
        ProductsRepo.registerUser(newProduct, conn);
        response.sendRedirect("list");
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response, Connection conn)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("addproduct.jsp");
        dispatcher.forward(request, response);
    }

}
