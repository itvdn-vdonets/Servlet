package controllers;

import utils.DBConnect;
import repos.ProductsRepo;
import models.Product;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/")
public class ProductsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ProductsRepo productsRepo;

    @Override
    public void init() throws ServletException {
        productsRepo = new ProductsRepo();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        Connection conn = DBConnect.getConnection();
        try {
            switch (action) {
                case "/insert":
                    insertProduct(request, response, conn);
                    break;
                case "/update":
                    updateProduct(request, response, conn);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        Connection conn = DBConnect.getConnection();
        try {
            switch (action) {
                case "/new":
                    showAddNewProductForm(request, response, conn);
                    break;
                case "/delete":
                    deleteProduct(request, response, conn);
                    break;
                case "/edit":
                    showEditForm(request, response, conn);
                    break;
                case "/filter":
                    filterSearchRequest(request, response, conn);
                    break;
                default:
                    getProductList(request, response, conn);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void filterSearchRequest(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException, ServletException, IOException {
        String name = request.getParameter("name");
        List<Product> listProduct;
        listProduct = productsRepo.getProductListByName(name, conn);
        if (listProduct.isEmpty()) {
            listProduct = productsRepo.getAllProducts(conn);
            request.setAttribute("message", "We haven't found any entries with the name " + name);
        }
        request.setAttribute("listProduct", listProduct);
        RequestDispatcher dispatcher = request.getRequestDispatcher("productlist.jsp");
        dispatcher.forward(request, response);
    }

    private void getProductList(HttpServletRequest request, HttpServletResponse response, Connection conn)
            throws SQLException, IOException, ServletException {
        List<Product> listProduct = productsRepo.getAllProducts(conn);
        request.setAttribute("listProduct", listProduct);
        RequestDispatcher dispatcher = request.getRequestDispatcher("productlist.jsp");
        dispatcher.forward(request, response);
    }


    private void showEditForm(HttpServletRequest request, HttpServletResponse response, Connection conn)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Product existingProduct = productsRepo.getProductById(id, conn);
        RequestDispatcher dispatcher = request.getRequestDispatcher("addproduct.jsp");
        request.setAttribute("product", existingProduct);
        dispatcher.forward(request, response);
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response, Connection conn)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Product updatedProduct = new Product();
        updatedProduct.setId(id);
        updatedProduct.setBrand(request.getParameter("brand"));
        updatedProduct.setName(request.getParameter("name"));
        updatedProduct.setPrice(Integer.parseInt(request.getParameter("price")));
        productsRepo.updateProduct(updatedProduct, conn);
        response.sendRedirect("list");
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response, Connection conn)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        productsRepo.deleteProductById(conn, id);
        response.sendRedirect("list");

    }

    private void insertProduct(HttpServletRequest request, HttpServletResponse response, Connection conn)
            throws SQLException, IOException {
        Product newProduct = new Product();
        newProduct.setBrand(request.getParameter("brand"));
        newProduct.setName(request.getParameter("name"));
        newProduct.setPrice(Integer.parseInt(request.getParameter("price")));
        productsRepo.addProduct(newProduct, conn);
        response.sendRedirect("list");
    }

    private void showAddNewProductForm(HttpServletRequest request, HttpServletResponse response, Connection conn)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("addproduct.jsp");
        dispatcher.forward(request, response);
    }

}
