package repos;

import models.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductsRepo {

    public int addProduct(Product product, Connection conn) throws SQLException {
        int result = 0;
        try (PreparedStatement stm = conn.prepareStatement("INSERT INTO products" +
                "(brand, name, price) VALUES " +
                " (?, ?, ?);")) {
            stm.setString(1, product.getBrand());
            stm.setString(2, product.getName());
            stm.setInt(3, product.getPrice());
            System.out.println(stm);
            result = stm.executeUpdate();
        }
        return result;
    }

    public Product getProductById(int id, Connection conn) throws SQLException {
        Product product = new Product();
        try (PreparedStatement preparedStatement = conn.prepareStatement("select id, brand, name, price from products where id =?;")) {
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
        try(PreparedStatement stm = conn.prepareStatement("select * from products;")) {
            ResultSet rs = stm.executeQuery();
            fillProductFromDb(rs, list);
        }
        return list;
    }

    public List<Product> getProductListByName(String name, Connection conn) throws SQLException {
        ResultSet rs;
        try (PreparedStatement stm = conn.prepareStatement("SELECT * FROM products WHERE name = ?")) {
            stm.setString(1, name);
            System.out.println(stm);
            rs = stm.executeQuery();
        }
        List<Product> list = new ArrayList<>();
        fillProductFromDb(rs, list);
        return list;
    }

    private void fillProductFromDb(ResultSet rs, List<Product> list) throws SQLException {
        while (rs.next()) {
            Product product = new Product();
            product.setId(rs.getInt("id"));
            product.setBrand(rs.getString("brand"));
            product.setName(rs.getString("name"));
            product.setPrice(rs.getInt("price"));
            list.add(product);
        }
    }

    public boolean deleteProductById(Connection conn, int id) throws SQLException {
        boolean productDeleted;
        try (PreparedStatement stm = conn.prepareStatement("delete from products where id = ?;")) {
            stm.setInt(1, id);
            productDeleted = stm.executeUpdate() > 0;
        }
        return productDeleted;
    }

    public boolean updateProduct(Product product, Connection conn) throws SQLException {
        boolean rowUpdated;
        try (PreparedStatement statement = conn.prepareStatement("UPDATE products SET brand = ?, name= ?, price =? where id = ?;")) {
            System.out.println(statement);
            statement.setString(1, product.getBrand());
            statement.setString(2, product.getName());
            statement.setInt(3, product.getPrice());
            statement.setInt(4, product.getId());
            System.out.println(statement);
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }
}