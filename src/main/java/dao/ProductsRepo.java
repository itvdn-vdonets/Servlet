package dao;

import entities.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductsRepo {

    private static final String SELECT_PRODUCT_BY_ID = "select id, brand, name, price from products where id =?;";
    private static final String SELECT_ALL_PRODUCTS = "select * from products;";
    private static final String DELETE_PRODUCT_SQL = "delete from products where id = ?;";
    private static final String UPDATE_PRODUCT_SQL = "UPDATE products SET brand = ?, name= ?, price =? where id = ?;";
    private static final String INSERT_PRODUCT_SQL = "INSERT INTO products" +
            "  (brand, name, price) VALUES " +
            " (?, ?, ?);";

    public int registerUser(Product product, Connection conn) throws SQLException {
        int result = 0;
        PreparedStatement stm = conn.prepareStatement(INSERT_PRODUCT_SQL);
        int i = 1;
        stm.setString(i++, product.getBrand());
        stm.setString(i++, product.getName());
        stm.setInt(i++, product.getPrice());
        System.out.println(stm);
        result = stm.executeUpdate();
        return result;
    }

    public Product getProductById(int id, Connection conn) throws SQLException {
        Product product = new Product();
        try (PreparedStatement preparedStatement = conn.prepareStatement(SELECT_PRODUCT_BY_ID)) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    product.setId(id);
                    product.setBrand(rs.getString("brand"));
                    product.setName(rs.getString("name"));
                    product.setPrice(rs.getInt("price"));
                }
            }
        }
        return product;
    }

    public List<Product> getAllProducts(Connection conn) throws SQLException {
        List<Product> list = new ArrayList<>();
        try(PreparedStatement stm = conn.prepareStatement(SELECT_ALL_PRODUCTS)) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setBrand(rs.getString("brand"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getInt("price"));
                list.add(product);
            }
        }
        return list;
    }

    public List<Product> getProductListByName(String name, Connection conn) throws SQLException {
        PreparedStatement stm = conn.prepareStatement("SELECT * FROM products WHERE name = ?");
        stm.setString(1, name);
        System.out.println(stm);
        ResultSet rs = stm.executeQuery();
        List<Product> list = new ArrayList<>();
        while (rs.next()) {
            Product product = new Product();
            product.setId(rs.getInt("id"));
            product.setBrand(rs.getString("brand"));
            product.setName(rs.getString("name"));
            product.setPrice(rs.getInt("price"));
            list.add(product);
        }
        return list;
    }

    public boolean deleteProductById(Connection conn, int id) throws SQLException {
        boolean userDeleted;
        PreparedStatement stm = conn.prepareStatement(DELETE_PRODUCT_SQL);
        int i = 1;
        stm.setInt(i++, id);
        userDeleted = stm.executeUpdate() > 0;
        return userDeleted;
    }

    public boolean updateProduct(Product product, Connection conn) throws SQLException {
        boolean rowUpdated;
        PreparedStatement statement = conn.prepareStatement(UPDATE_PRODUCT_SQL);
        System.out.println(statement);
        int i = 1;
        statement.setString(i++, product.getBrand());
        statement.setString(i++, product.getName());
        statement.setInt(i++, product.getPrice());
        statement.setInt(i++, product.getId());
        System.out.println(statement);
        rowUpdated = statement.executeUpdate() > 0;
        return rowUpdated;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}