package repository;
import config.DBConnection;
import model.Category;
import model.Product;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {

    private Connection connection;

    public DataRetriever() throws Exception {
        connection = new DBConnection().getConnection();
    }

    public List<Category> getAllCategories() throws Exception {
        List<Category> categories = new ArrayList<>();
        ResultSet resultSet = connection.createStatement()
                .executeQuery("SELECT id, name FROM product_category");

        while (resultSet.next()) {
            categories.add(new Category(
                    resultSet.getInt("id"),
                    resultSet.getString("name")
            ));
        }
        return categories;
    }

    public List<Product> getProductList(int pageNumber, int pageSize) throws Exception {
        return getProducts(null, null, null, null, pageNumber, pageSize, true);
    }

    public List<Product> getProductsByCriteria(
            String productName,
            String categoryName,
            Instant creationDateMinimum,
            Instant creationDateMaximum
    ) throws Exception {
        return getProducts(productName, categoryName, creationDateMinimum, creationDateMaximum, 0, 0, false);
    }

    public List<Product> getProductsByCriteria(
            String productName,
            String categoryName,
            Instant creationDateMinimum,
            Instant creationDateMaximum,
            int pageNumber,
            int pageSize
    ) throws Exception {
        List<Product> filtered = getProducts(productName, categoryName, creationDateMinimum, creationDateMaximum, 0, 0, false);
        int start = (pageNumber - 1) * pageSize;
        int end = Math.min(start + pageSize, filtered.size());
        if (start >= filtered.size()) return new ArrayList<>();
        return filtered.subList(start, end);
    }

    private List<Product> getProducts(
            String productName,
            String categoryName,
            Instant creationDateMinimum,
            Instant creationDateMaximum,
            int pageNumber,
            int pageSize,
            boolean paginate
    ) throws Exception {

        String sql = """
                SELECT product.id, product.name, product.creation_datetime,
                       product_category.id AS category_id,
                       product_category.name AS category_name
                FROM product
                JOIN product_category ON product.id = product_category.product_id
                WHERE 1=1
                """;

        List<Object> values = new ArrayList<>();

        if (productName != null) {
            sql += " AND product.name ILIKE ?";
            values.add("%" + productName + "%");
        }

        if (categoryName != null) {
            sql += " AND product_category.name ILIKE ?";
            values.add("%" + categoryName + "%");
        }

        if (creationDateMinimum != null) {
            sql += " AND product.creation_datetime >= ?";
            values.add(Timestamp.from(creationDateMinimum));
        }

        if (creationDateMaximum != null) {
            sql += " AND product.creation_datetime <= ?";
            values.add(Timestamp.from(creationDateMaximum));
        }

        if (paginate) {
            sql += " ORDER BY product.id LIMIT ? OFFSET ?";
            values.add(pageSize);
            values.add((pageNumber - 1) * pageSize);
        }

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int index = 0; index < values.size(); index++) {
            preparedStatement.setObject(index + 1, values.get(index));
        }

        ResultSet resultSet = preparedStatement.executeQuery();
        List<Product> products = new ArrayList<>();

        while (resultSet.next()) {
            products.add(new Product(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getTimestamp("creation_datetime").toInstant(),
                    new Category(
                            resultSet.getInt("category_id"),
                            resultSet.getString("category_name")
                    )
            ));
        }
        return products;
    }
}
