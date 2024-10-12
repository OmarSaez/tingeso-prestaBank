package edu.mtisw.prestabank_backend.Repository;

import edu.mtisw.prestabank_backend.Entity.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<LoanEntity, Long> {
    public List<LoanEntity> findByIdUser(Long idUser);
}
