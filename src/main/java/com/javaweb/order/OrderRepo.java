package com.javaweb.order;

import com.javaweb.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepo extends JpaRepository<Orders,String> {


    @Query("SELECT u FROM Orders u WHERE u.equipamento = ?1")
    Orders findBySerie(String serie);

    @Query("SELECT u FROM Orders u WHERE u.id = ?1")
    Orders findById(Long id);

    @Query("SELECT o FROM Orders o WHERE o.tecnico = :tecnico")
    List<Orders> findByTecnico(@Param("tecnico") String tecnicoName);



}
