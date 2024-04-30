package ru.sportlive.mvp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sportlive.mvp.models.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory,Integer> {
}
