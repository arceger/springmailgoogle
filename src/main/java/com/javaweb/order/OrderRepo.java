package com.javaweb.order;

import com.javaweb.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepo extends JpaRepository<Orders,String> {

    @Query("SELECT u FROM Orders u WHERE u.equipamento = ?1")
    Orders findBySerie(String serie);

}
