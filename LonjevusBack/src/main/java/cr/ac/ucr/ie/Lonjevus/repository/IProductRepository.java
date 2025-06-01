package cr.ac.ucr.ie.Lonjevus.repository;

import cr.ac.ucr.ie.Lonjevus.domain.Product;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface IProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "CALL get_all_products()", nativeQuery = true)
    List<Object[]> getAllProductsRaw();

    @Modifying
    @Transactional
    @Query(value = "CALL insert_product(:name, :price, :expirationDate, :category, :photoURL, :unitId, :supplierId)", nativeQuery = true)
    void insertProduct(
        @Param("name") String name,
        @Param("price") BigDecimal price,
        @Param("expirationDate") Date expirationDate,
        @Param("category") String category,
        @Param("photoURL") String photoURL,
        @Param("unitId") int unitId,
        @Param("supplierId") int supplierId
    );

    @Modifying
    @Transactional
    @Query(value = "CALL update_product(:id, :name, :price, :expirationDate, :category, :photoURL, :unitId, :supplierId)", nativeQuery = true)
    void updateProduct(
        @Param("id") int id,
        @Param("name") String name,
        @Param("price") BigDecimal price,
        @Param("expirationDate") Date expirationDate,
        @Param("category") String category,
        @Param("photoURL") String photoURL,
        @Param("unitId") int unitId,
        @Param("supplierId") int supplierId
    );

    @Modifying
    @Transactional
    @Query(value = "CALL delete_product(:id)", nativeQuery = true)
    void deleteProduct(@Param("id") int id);

    @Query(value = "CALL get_product_by_id(:id)", nativeQuery = true)
    List<Object[]> getProductById(@Param("id") int id);
}
